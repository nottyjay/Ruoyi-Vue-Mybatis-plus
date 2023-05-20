package com.alphay.boot.bpm.service.impl;

import com.alphay.boot.bpm.service.IBpmMessageService;
import com.alphay.boot.bpm.model.dto.BpmMessageSendWhenProcessInstanceCheckRequestDTO;
import com.alphay.boot.bpm.model.dto.BpmMessageSendWhenTaskCreatedRequestDTO;
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
