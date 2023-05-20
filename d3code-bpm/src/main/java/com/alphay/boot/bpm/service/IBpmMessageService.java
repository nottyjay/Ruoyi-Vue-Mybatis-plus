package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.model.dto.BpmMessageSendWhenProcessInstanceCheckRequestDTO;
import com.alphay.boot.bpm.model.dto.BpmMessageSendWhenTaskCreatedRequestDTO;

import javax.validation.Valid;

/**
 * Bpm 消息 业务接口
 *
 * @author Nottyjay
 */
public interface IBpmMessageService {

  /**
   * 发送流程实例被通过的消息
   *
   * @param requestDTO
   */
  void sendMessageWhenProcessInstanceApprove(
      @Valid BpmMessageSendWhenProcessInstanceCheckRequestDTO requestDTO);

  /**
   * 发送流程实例被不通过的消息
   *
   * @param requestDTO
   */
  void sendMessageWhenProcessInstanceReject(
      @Valid BpmMessageSendWhenProcessInstanceCheckRequestDTO requestDTO);

  /**
   * 发送任务被分配的消息
   *
   * @param requestDTO
   */
  void sendMessageWhenTaskAssigned(@Valid BpmMessageSendWhenTaskCreatedRequestDTO requestDTO);
}
