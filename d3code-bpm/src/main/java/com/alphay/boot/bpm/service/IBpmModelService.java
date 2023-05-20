package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.bpm.model.vo.*;
import org.flowable.bpmn.model.BpmnModel;

import javax.validation.Valid;
import java.util.List;

/**
 * 流程模型业务接口
 *
 * @author Nottyjay
 */
public interface IBpmModelService {

  /**
   * 获取符合条件的流程模型列表
   *
   * @param requestVo
   * @return
   */
  List<BpmModelItemResponseVo> selectModelList(BpmModelQueryRequestVo requestVo);

  /**
   * 获取流程模块
   *
   * @param id
   * @return
   */
  BpmModelRespVo getModel(String id);

  /**
   * 创建流程模型
   *
   * @param requestVo
   * @param bpmnXml
   * @return
   */
  String createModel(@Valid BpmModelCreateRequestVo requestVo, String bpmnXml);

  /**
   * 修改流程模型
   *
   * @param requestVo
   */
  void updateModel(BpmModelUpdateRequestVo requestVo);

  /**
   * 将流程模型，部署成一个流程定义
   *
   * @param id
   */
  void deployModel(String id);

  /**
   * 通过模型ID获取模型
   *
   * @param modelId
   * @return
   */
  BpmnModel getBpmnModel(String modelId);

  /**
   * 修改模型的状态，实际更新的部署的流程定义的状态
   *
   * @param modelId
   * @param state
   */
  void updateModelState(String modelId, Integer state);

  /**
   * 删除模型
   *
   * @param modelId
   */
  void deleteModel(String modelId);
}
