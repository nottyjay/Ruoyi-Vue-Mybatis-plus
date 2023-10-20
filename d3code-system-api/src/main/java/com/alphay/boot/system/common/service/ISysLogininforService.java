package com.alphay.boot.system.common.service;

import com.alphay.boot.system.common.domain.SysLogininfor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author d3code
 */
public interface ISysLogininforService extends IService<SysLogininfor> {

  /**
   * 查询系统登录日志集合
   *
   * @param logininfor 访问日志对象
   * @return 登录记录集合
   */
  List<SysLogininfor> selectLogininforList(SysLogininfor logininfor, IPage<SysLogininfor> page);

  /** 清空系统登录日志 */
  public void cleanLogininfor();
}
