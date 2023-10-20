package com.alphay.boot.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alphay.boot.common.mybatis.query.QueryWrapperX;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.system.common.domain.SysLogininfor;
import com.alphay.boot.system.common.service.ISysLogininforService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.alphay.boot.system.mapper.SysLogininforMapper;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author d3code
 */
@Service
public class SysLogininforServiceImpl extends ServiceImplX<SysLogininforMapper, SysLogininfor>
    implements ISysLogininforService {

  /**
   * 新增系统登录日志
   *
   * @param logininfor 访问日志对象
   */
  @Override
  public boolean save(SysLogininfor logininfor) {
    logininfor.setLoginTime(new Date());
    return super.save(logininfor);
  }

  /**
   * 查询系统登录日志集合
   *
   * @param logininfor 访问日志对象
   * @return 登录记录集合
   */
  @Override
  public List<SysLogininfor> selectLogininforList(
      SysLogininfor logininfor, IPage<SysLogininfor> page) {
    QueryWrapperX<SysLogininfor> queryWrapper =
        new QueryWrapperX<SysLogininfor>()
            .eqIfPresent("status", logininfor.getStatus())
            .likeIfPresent("ipaddr", logininfor.getIpaddr())
            .likeIfPresent("user_name", logininfor.getUserName());
    parseBeginTimeAndEndTime(logininfor.getParams(), queryWrapper, "login_time");
    queryWrapper.orderByDesc("info_id");
    return this.list(page, queryWrapper);
  }

  /** 清空系统登录日志 */
  @Override
  public void cleanLogininfor() {
    this.baseMapper.cleanLogininfor();
  }
}
