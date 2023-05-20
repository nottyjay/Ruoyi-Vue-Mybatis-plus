package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.domain.BpmProcessInstanceExt;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceCancelRequestVo;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceItemResponseVo;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceResponseVo;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 流程实例接口
 *
 * @author Nottyjay
 */
public interface IBpmProcessInstanceService {

  /**
   * 获得流程实例列表
   *
   * @param userId
   * @param processInstanceExt
   * @return
   */
  List<BpmProcessInstanceItemResponseVo> selectMyProcessInstanceList(
      Long userId, BpmProcessInstanceExt processInstanceExt);

  /**
   * 创建流程实例（提供给前端）
   *
   * @param userId
   * @param processInstanceExt
   * @return
   */
  String createProcessInstance(Long userId, BpmProcessInstanceExt processInstanceExt);

  /**
   * 获得流程实例 Vo 信息
   *
   * @param id
   * @return
   */
  BpmProcessInstanceResponseVo getProcessInstanceVO(String id);

  /**
   * 获得历史的流程实例
   *
   * @param id 流程实例的编号
   * @return 历史的流程实例
   */
  HistoricProcessInstance getHistoricProcessInstance(String id);

  /**
   * 取消流程实例
   *
   * @param userId
   * @param cancelRequestVo
   */
  void cancelProcessInstance(Long userId, BpmProcessInstanceCancelRequestVo cancelRequestVo);

  /**
   * 获得流程实例
   *
   * @param id
   * @return
   */
  ProcessInstance getProcessInstance(String id);

  /**
   * 获得流程实例列表
   *
   * @param ids 流程实例的编号集合
   * @return 流程实例列表
   */
  List<ProcessInstance> getProcessInstances(Set<String> ids);

  /**
   * 获得流程实例 Map
   *
   * @param ids 流程实例的编号集合
   * @return 流程实例列表 Map
   */
  default Map<String, ProcessInstance> getProcessInstanceMap(Set<String> ids) {
    return CollectionUtil.convertMap(
        getProcessInstances(ids), ProcessInstance::getProcessInstanceId);
  }

  /**
   * 获得历史的流程实例列表
   *
   * @param ids
   * @return
   */
  List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids);

  /**
   * 获得历史的流程实例 Map
   *
   * @param ids
   * @return
   */
  default Map<String, HistoricProcessInstance> getHistoricProcessInstanceMap(Set<String> ids) {
    return CollectionUtil.convertMap(
        getHistoricProcessInstances(ids), HistoricProcessInstance::getId);
  }

  /**
   * 更新 ProcessInstance 拓展记录为不通过
   *
   * @param processInstanceId
   * @param reason
   */
  void updateProcessInstanceExtReject(String processInstanceId, String reason);

  /**
   * 创建 ProcessInstance 拓展记录
   *
   * @param instance
   */
  void createProcessInstanceExt(ProcessInstance instance);

  /**
   * 更新 ProcessInstance 拓展记录为取消
   *
   * @param event
   */
  void updateProcessInstanceExtCancel(FlowableCancelledEvent event);

  /**
   * 更新 ProcessInstance 拓展记录为完成
   *
   * @param instance
   */
  void updateProcessInstanceExtComplete(ProcessInstance instance);
}
