package com.alphay.boot.system.service.impl;

import java.util.Date;
import java.util.List;

import com.alphay.boot.common.mybatis.query.QueryWrapperX;
import com.alphay.boot.common.mybatis.service.ServiceImplX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alphay.boot.system.common.domain.SysOperLog;
import com.alphay.boot.system.mapper.SysOperLogMapper;
import com.alphay.boot.system.common.service.ISysOperLogService;

/**
 * 操作日志 服务层处理
 *
 * @author d3code
 */
@Service
public class SysOperLogServiceImpl extends ServiceImplX<SysOperLogMapper, SysOperLog>
    implements ISysOperLogService {
  @Autowired private SysOperLogMapper operLogMapper;

  /**
   * 新增操作日志
   *
   * @param operLog 操作日志对象
   */
  @Override
  public boolean save(SysOperLog operLog) {
    operLog.setOperTime(new Date());
    return super.save(operLog);
  }

  /**
   * 查询系统操作日志集合
   *
   * @param operLog 操作日志对象
   * @return 操作日志集合
   */
  @Override
  public List<SysOperLog> selectOperLogList(SysOperLog operLog, IPage page) {
    QueryWrapperX<SysOperLog> queryWrapper =
        new QueryWrapperX<SysOperLog>()
            .likeIfPresent("title", operLog.getTitle())
            .eqIfPresent("business_type", operLog.getBusinessType())
            .inIfPresent("business_type", operLog.getBusinessTypes())
            .eqIfPresent("status", operLog.getStatus())
            .likeIfPresent("oper_name", operLog.getOperName());
    parseBeginTimeAndEndTime(operLog.getParams(), queryWrapper, "oper_time");
    queryWrapper.orderByDesc("oper_id");
    return this.list(page, queryWrapper);
  }

  /** 清空操作日志 */
  @Override
  public void cleanOperLog() {
    operLogMapper.cleanOperLog();
  }
}
