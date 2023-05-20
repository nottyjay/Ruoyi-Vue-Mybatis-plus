package com.alphay.boot.bpm.convert;

import cn.hutool.core.util.NumberUtil;
import com.alphay.boot.bpm.domain.BpmProcessDefinitionExt;
import com.alphay.boot.bpm.domain.BpmProcessInstanceExt;
import com.alphay.boot.bpm.framework.event.BpmProcessInstanceResultEvent;
import com.alphay.boot.bpm.model.dto.BpmMessageSendWhenProcessInstanceCheckRequestDTO;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceItemResponseVo;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceResponseVo;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.core.domain.entity.SysUser;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface BpmProcessInstanceConvert {

  BpmProcessInstanceConvert INSTANCE = Mappers.getMapper(BpmProcessInstanceConvert.class);

  default List<BpmProcessInstanceItemResponseVo> convertList(
      List<BpmProcessInstanceExt> list, Map<String, List<Task>> taskMap) {
    List<BpmProcessInstanceItemResponseVo> responseVoList = convertList(list);
    responseVoList.forEach(
        responseVo -> responseVo.setTasks(convertList2(taskMap.get(responseVo.getId()))));
    return responseVoList;
  }

  List<BpmProcessInstanceItemResponseVo> convertList(List<BpmProcessInstanceExt> list);

  @Mapping(source = "processInstanceId", target = "id")
  BpmProcessInstanceItemResponseVo convert(BpmProcessInstanceExt bean);

  List<BpmProcessInstanceItemResponseVo.Task> convertList2(List<Task> tasks);

  default BpmProcessInstanceResponseVo convert2(
      HistoricProcessInstance processInstance,
      BpmProcessInstanceExt processInstanceExt,
      ProcessDefinition processDefinition,
      BpmProcessDefinitionExt processDefinitionExt,
      String bpmnXml,
      SysUser startUser,
      SysDept dept) {
    BpmProcessInstanceResponseVo respVO = convert2(processInstance);
    copyTo(processInstanceExt, respVO);
    // definition
    respVO.setProcessDefinition(convert2(processDefinition));
    copyTo(processDefinitionExt, respVO.getProcessDefinition());
    respVO.getProcessDefinition().setBpmnXml(bpmnXml);
    // user
    if (startUser != null) {
      respVO.setStartUser(convert2(startUser));
      if (dept != null) {
        respVO.getStartUser().setDeptName(dept.getDeptName());
      }
    }
    return respVO;
  }

  BpmProcessInstanceResponseVo convert2(HistoricProcessInstance bean);

  BpmProcessInstanceResponseVo.User convert2(SysUser bean);

  BpmProcessInstanceResponseVo.ProcessDefinition convert2(ProcessDefinition bean);

  @Mapping(source = "from.id", target = "to.id", ignore = true)
  void copyTo(BpmProcessInstanceExt from, @MappingTarget BpmProcessInstanceResponseVo to);

  @Mapping(source = "from.id", target = "to.id", ignore = true)
  void copyTo(
      BpmProcessDefinitionExt from,
      @MappingTarget BpmProcessInstanceResponseVo.ProcessDefinition to);

  default BpmProcessInstanceResultEvent convert(
      Object source, ProcessInstance instance, Integer result) {
    BpmProcessInstanceResultEvent event = new BpmProcessInstanceResultEvent(source);
    event.setId(instance.getId());
    event.setProcessDefinitionKey(instance.getProcessDefinitionKey());
    event.setBusinessKey(instance.getBusinessKey());
    event.setResult(result);
    return event;
  }

  default BpmProcessInstanceResultEvent convert(
      Object source, HistoricProcessInstance instance, Integer result) {
    BpmProcessInstanceResultEvent event = new BpmProcessInstanceResultEvent(source);
    event.setId(instance.getId());
    event.setProcessDefinitionKey(instance.getProcessDefinitionKey());
    event.setBusinessKey(instance.getBusinessKey());
    event.setResult(result);
    return event;
  }

  default BpmMessageSendWhenProcessInstanceCheckRequestDTO convert2RejectReq(
      ProcessInstance instance, String reason) {
    return new BpmMessageSendWhenProcessInstanceCheckRequestDTO()
        .setProcessInstanceName(instance.getName())
        .setProcessInstanceId(instance.getId())
        .setReason(reason)
        .setStartUserId(NumberUtil.parseLong(instance.getStartUserId()));
  }

  default BpmMessageSendWhenProcessInstanceCheckRequestDTO convert2ApprovedReq(
      ProcessInstance instance) {
    return new BpmMessageSendWhenProcessInstanceCheckRequestDTO()
        .setStartUserId(NumberUtil.parseLong(instance.getStartUserId()))
        .setProcessInstanceId(instance.getId())
        .setProcessInstanceName(instance.getName());
  }
}
