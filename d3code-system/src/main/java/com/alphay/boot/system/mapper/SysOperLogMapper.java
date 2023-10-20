package com.alphay.boot.system.mapper;

import java.util.List;

import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.system.common.domain.SysOperLog;

/**
 * 操作日志 数据层
 *
 * @author d3code
 */
public interface SysOperLogMapper extends BaseMapperX<SysOperLog> {

  /** 清空操作日志 */
  void cleanOperLog();
}
