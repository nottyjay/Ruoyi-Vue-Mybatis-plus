package com.d3code.bpm.service.impl;

import com.d3code.bpm.model.dto.BpmMessageSendWhenProcessInstanceCheckRequestDTO;
import com.d3code.bpm.model.dto.BpmMessageSendWhenTaskCreatedRequestDTO;
import com.d3code.bpm.service.IBpmMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * BPM 消息 Service 实现类
 *
 * @author Nottyjay
 */
@Service
@Validated
@Slf4j
public class BpmMessageServiceImpl implements IBpmMessageService {

  @Override
  public void sendMessageWhenProcessInstanceApprove(
      BpmMessageSendWhenProcessInstanceCheckRequestDTO reqDTO) {}

  @Override
  public void sendMessageWhenProcessInstanceReject(
      BpmMessageSendWhenProcessInstanceCheckRequestDTO reqDTO) {}

  @Override
  public void sendMessageWhenTaskAssigned(BpmMessageSendWhenTaskCreatedRequestDTO reqDTO) {}
}
