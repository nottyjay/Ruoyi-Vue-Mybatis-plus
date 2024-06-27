package com.alphay.boot.system.service.impl;

import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.alphay.boot.common.core.domain.TreeSelect;
import com.alphay.boot.common.core.domain.entity.SysArea;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.tree.TreeUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.system.mapper.SysAreaMapper;
import com.alphay.boot.system.common.service.ISysAreaService;

/**
 * 地区管理Service业务层处理
 *
 * @author d3code
 * @date 2024-06-21
 */
@Service
public class SysAreaServiceImpl extends ServiceImplX<SysAreaMapper, SysArea>
    implements ISysAreaService {

  private Double syncProcess;

  /**
   * 查询地区管理列表
   *
   * @param sysArea 地区管理
   * @param page 分页
   * @return 地区管理
   */
  @Override
  public List<SysArea> selectSysAreaList(SysArea sysArea, IPage page) {
    return baseMapper.selectSysAreaList(page, sysArea);
  }

  @Override
  public void syncArea(String content) {
    syncProcess = 0.0;
    baseMapper.truncate();
    List<AreaNode> areaNodes = JsonUtil.toList(content, AreaNode.class);
    if (CollUtil.isNotEmpty(areaNodes)) {

      this.saveArea(areaNodes, null);
    }
  }

  @Override
  public Double getSyncProcess() {
    return this.syncProcess;
  }

  private void saveArea(List<AreaNode> areaNodes, SysArea parentArea) {
    Double step = 0.0;
    List<SysArea> areas = new ArrayList<>(areaNodes.size());
    for (int i = 0; i < areaNodes.size(); i++) {
      AreaNode areaNode = areaNodes.get(i);
      SysArea area = SysArea.builder().code(areaNode.getCode()).name(areaNode.getName()).build();
      area.setOrderNum(i);
      if (parentArea == null) {
        area.setAncestors("0");
        area.setParentName("");
        area.setParentCode("0");
        area.setParentId(0L);
        area.setLevel(1);
      } else {
        area.setParentId(parentArea.getId());
        area.setLevel(parentArea.getLevel() + 1);
        area.setParentCode(parentArea.getCode());
        area.setParentName(parentArea.getName());
        area.setAncestors(parentArea.getAncestors() + "," + parentArea.getId());
      }
      areas.add(area);
    }
    baseMapper.insertBatch(areas, 1000);
    if (parentArea == null) {
      step = 100.0 / areaNodes.size();
    }
    for (int i = 0; i < areaNodes.size(); i++) {
      if (CollUtil.isNotEmpty(areaNodes.get(i).getChildren())) {
        this.saveArea(areaNodes.get(i).getChildren(), areas.get(i));
      }
      if (parentArea == null) {
        this.syncProcess = step * i;
      }
    }
    if (parentArea == null) {
      this.syncProcess = 100.0;
    }
  }

  @Override
  public List<TreeSelect> selectSysAreaTreeList(SysArea sysArea) {
    return TreeUtil.buildTreeSelect(selectSysAreaList(sysArea), SysArea::getId, SysArea::getName);
  }

  @Override
  @Cacheable(key = "#id", value = "sysArea")
  public SysArea getById(Serializable id) {
    return super.getById(id);
  }

  @Override
  public boolean save(SysArea entity) {
    if (entity.getParentId() != null && entity.getParentId() != 0L) {
      SysArea info = this.getById(entity.getParentId());
      // 如果父节点不为正常状态，则不允许新增子节点
      if (info == null) {
        throw new ServiceException("地区停用，不允许新增");
      }
      entity.setLevel(info.getLevel() + 1);
      entity.setParentCode(info.getCode());
      entity.setParentName(info.getName());
      entity.setAncestors(info.getAncestors() + "," + info.getId());
    } else {
      entity.setAncestors("0");
      entity.setParentName("");
      entity.setParentCode("0");
      entity.setParentId(0L);
      entity.setLevel(1);
    }
    return super.save(entity);
  }

  @Override
  public boolean updateById(SysArea entity) {
    SysArea parentData = this.getById(entity.getParentId());
    SysArea persistentData = this.getById(entity.getId());
    if (ObjUtil.isNotNull(parentData) && ObjUtil.isNotNull(persistentData)) {
      String newAncestors = parentData.getAncestors() + "," + parentData.getId();
      String oldAncestors = persistentData.getAncestors();
      entity.setParentName(parentData.getName());
      entity.setParentCode(parentData.getCode());
      entity.setLevel(parentData.getLevel() + 1);
      this.updateChildren(entity.getId(), newAncestors, oldAncestors);
    } else {
      entity.setAncestors("0");
      entity.setLevel(1);
      entity.setParentCode("0");
      entity.setParentName("");
      entity.setParentId(0l);
    }
    return super.updateById(entity);
  }

  private void updateChildren(Long id, String newAncestors, String oldAncestors) {
    List<SysArea> children = baseMapper.selectChildrenListByTreeId(id);
    if (CollUtil.isNotEmpty(children)) {
      for (SysArea child : children) {
        child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
      }
      this.updateBatchById(children);
    }
  }

  private static class AreaNode {
    private String code;
    private String name;
    private List<AreaNode> children;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<AreaNode> getChildren() {
      return children;
    }

    public void setChildren(List<AreaNode> children) {
      this.children = children;
    }
  }
}
