package com.alphay.boot.system.common.service;

import java.util.List;
import com.alphay.boot.system.common.domain.SysOperLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 操作日志 服务层
 *
 * @author d3code
 */
public interface ISysOperLogService extends IService<SysOperLog> {

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  List<SysOperLog> selectOperLogList(SysOperLog operLog, IPage page);

  /** 清空操作日志 */
  void cleanOperLog();
}
