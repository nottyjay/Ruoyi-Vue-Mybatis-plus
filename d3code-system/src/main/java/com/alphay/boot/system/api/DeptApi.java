package com.alphay.boot.system.api;

import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.service.ISysDeptService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 部门 Api
 *
 * @author Nottyjay
 */
@Component
public class DeptApi {

  @Resource private ISysDeptService deptService;

  /**
   * 校验部门们是否有效。如下情况，视为无效： 1. 部门编号不存在 2. 部门被禁用
   *
   * @param ids
   */
  public void validateDeptList(Collection<Long> ids) {
    deptService.validateDeptList(ids);
  }

  /**
   * 获得部门信息
   *
   * @param deptId
   * @return
   */
  public SysDept getDept(Long deptId) {
    return deptService.selectDeptById(deptId);
  }

  /**
   * 获得指定编号的部门 map
   *
   * @param ids
   * @return
   */
  public Map<Long, SysDept> getDeptMap(Set<Long> ids) {
    List<SysDept> list = getDeptList(ids);
    return CollectionUtil.convertMap(list, SysDept::getDeptId);
  }

  public List<SysDept> getDeptList(Collection<Long> ids) {
    return deptService.getDeptList(ids);
  }
}
