package com.alphay.boot.bpm.api;

import com.alphay.boot.bpm.api.model.dto.BpmProcessInstanceCreateReqDTO;
import com.alphay.boot.bpm.api.service.IBpmProcessInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

@Service
@Validated
public class BpmProcessInstanceApiImpl implements BpmProcessInstanceApi {

  @Resource private IBpmProcessInstanceService processInstanceService;

  @Override
  public String createProcessInstance(Long userId, BpmProcessInstanceCreateReqDTO createReqDTO) {
    return processInstanceService.createProcessInstance(userId, createReqDTO);
  }
}
