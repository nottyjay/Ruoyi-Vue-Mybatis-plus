package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.model.vo.BpmActivityResponseVo;
import org.flowable.engine.history.HistoricActivityInstance;

import java.util.List;

/**
 * Bpm 流程活动实例接口
 *
 * @author Nottyjay
 */
public interface IBpmActivityService {

  /**
   * 获得指定流程实例的活动实例列表
   *
   * @param processInstanceId 流程实例的编号
   * @return 活动实例列表
   */
  List<BpmActivityResponseVo> getActivityListByProcessInstanceId(String processInstanceId);

  /**
   * 获得执行编号对应的活动实例
   *
   * @param executionId
   * @return
   */
  List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId);
}
