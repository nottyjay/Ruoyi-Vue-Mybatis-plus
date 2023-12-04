package com.alphay.boot.bpm.api;

import com.alphay.boot.bpm.api.model.dto.BpmProcessInstanceCreateReqDTO;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;

public interface BpmProcessInstanceApi {

  /**
   * 创建流程实例（提供给内部）
   *
   * @param userId 用户编号
   * @param createReqDTO 创建信息
   * @return 实例的编号
   */
  String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO createReqDTO);
}
