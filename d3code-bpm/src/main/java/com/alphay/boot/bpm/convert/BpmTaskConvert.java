package com.alphay.boot.bpm.convert;

import cn.hutool.core.util.NumberUtil;
import com.alphay.boot.bpm.domain.BpmTaskExt;
import com.alphay.boot.bpm.model.dto.BpmMessageSendWhenTaskCreatedRequestDTO;
import com.alphay.boot.bpm.model.vo.BpmTaskDoneItemResponseVo;
import com.alphay.boot.bpm.model.vo.BpmTaskResponseVo;
import com.alphay.boot.bpm.model.vo.BpmTaskTodoItemResponseVo;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface BpmTaskConvert {

  BpmTaskConvert INSTANCE = Mappers.getMapper(BpmTaskConvert.class);

  default List<BpmTaskTodoItemResponseVo> convertList1(
      List<Task> tasks,
      Map<String, ProcessInstance> processInstanceMap,
      Map<Long, SysUser> userMap) {
    return CollectionUtil.convertList(
        tasks,
        task -> {
          BpmTaskTodoItemResponseVo respVO = convert1(task);
          ProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
          if (processInstance != null) {
            SysUser startUser = userMap.get(NumberUtil.parseLong(processInstance.getStartUserId()));
            respVO.setProcessInstance(convert(processInstance, startUser));
          }
          return respVO;
        });
  }

  default List<BpmTaskDoneItemResponseVo> convertList2(
      List<HistoricTaskInstance> tasks,
      Map<String, BpmTaskExt> bpmTaskExtDOMap,
      Map<String, HistoricProcessInstance> historicProcessInstanceMap,
      Map<Long, SysUser> userMap) {
    return CollectionUtil.convertList(
        tasks,
        task -> {
          BpmTaskDoneItemResponseVo respVO = convert2(task);
          BpmTaskExt taskExtDO = bpmTaskExtDOMap.get(task.getId());
          copyTo(taskExtDO, respVO);
          HistoricProcessInstance processInstance =
              historicProcessInstanceMap.get(task.getProcessInstanceId());
          if (processInstance != null) {
            SysUser startUser = userMap.get(NumberUtil.parseLong(processInstance.getStartUserId()));
            respVO.setProcessInstance(convert(processInstance, startUser));
          }
          return respVO;
        });
  }

  default List<BpmTaskResponseVo> convertList3(
      List<HistoricTaskInstance> tasks,
      Map<String, BpmTaskExt> bpmTaskExtDOMap,
      HistoricProcessInstance processInstance,
      Map<Long, SysUser> userMap,
      Map<Long, SysDept> deptMap) {
    return CollectionUtil.convertList(
        tasks,
        task -> {
          BpmTaskResponseVo respVO = convert3(task);
          BpmTaskExt taskExtDO = bpmTaskExtDOMap.get(task.getId());
          copyTo(taskExtDO, respVO);
          if (processInstance != null) {
            SysUser startUser = userMap.get(NumberUtil.parseLong(processInstance.getStartUserId()));
            respVO.setProcessInstance(convert(processInstance, startUser));
          }
          SysUser assignUser = userMap.get(NumberUtil.parseLong(task.getAssignee()));
          if (assignUser != null) {
            respVO.setAssigneeUser(convert3(assignUser));
            SysDept dept = deptMap.get(assignUser.getDeptId());
            if (dept != null) {
              respVO.getAssigneeUser().setDeptName(dept.getDeptName());
            }
          }
          return respVO;
        });
  }

  @Mapping(
      source = "suspended",
      target = "suspensionState",
      qualifiedByName = "convertSuspendedToSuspensionState")
  @Mapping(
      target = "claimTime",
      expression =
          "java(bean.getClaimTime()==null?null: LocalDateTime.ofInstant(bean.getClaimTime().toInstant(),ZoneId.systemDefault()))")
  @Mapping(
      target = "createTime",
      expression =
          "java(bean.getCreateTime()==null?null:LocalDateTime.ofInstant(bean.getCreateTime().toInstant(),ZoneId.systemDefault()))")
  BpmTaskTodoItemResponseVo convert1(Task bean);

  @Named("convertSuspendedToSuspensionState")
  default Integer convertSuspendedToSuspensionState(boolean suspended) {
    return suspended
        ? SuspensionState.SUSPENDED.getStateCode()
        : SuspensionState.ACTIVE.getStateCode();
  }

  @Mappings({
    @Mapping(source = "processInstance.id", target = "id"),
    @Mapping(source = "processInstance.name", target = "name"),
    @Mapping(source = "processInstance.startUserId", target = "startUserId"),
    @Mapping(source = "processInstance.processDefinitionId", target = "processDefinitionId"),
    @Mapping(source = "startUser.nickName", target = "startUserNickname")
  })
  BpmTaskTodoItemResponseVo.ProcessInstance convert(
      ProcessInstance processInstance, SysUser startUser);

  default BpmMessageSendWhenTaskCreatedRequestDTO convert(
      ProcessInstance processInstance, SysUser startUser, Task task) {
    BpmMessageSendWhenTaskCreatedRequestDTO reqDTO = new BpmMessageSendWhenTaskCreatedRequestDTO();
    reqDTO
        .setProcessInstanceId(processInstance.getProcessInstanceId())
        .setProcessInstanceName(processInstance.getName())
        .setStartUserId(startUser.getUserId())
        .setStartUserNickname(startUser.getNickName())
        .setTaskId(task.getId())
        .setTaskName(task.getName())
        .setAssigneeUserId(NumberUtil.parseLong(task.getAssignee()));
    return reqDTO;
  }

  @Mappings({
    @Mapping(source = "processInstance.id", target = "id"),
    @Mapping(source = "processInstance.name", target = "name"),
    @Mapping(source = "processInstance.startUserId", target = "startUserId"),
    @Mapping(source = "processInstance.processDefinitionId", target = "processDefinitionId"),
    @Mapping(source = "startUser.nickName", target = "startUserNickname")
  })
  BpmTaskTodoItemResponseVo.ProcessInstance convert(
      HistoricProcessInstance processInstance, SysUser startUser);

  BpmTaskDoneItemResponseVo convert2(HistoricTaskInstance bean);

  @Mapping(source = "nickName", target = "nickname")
  @Mapping(source = "userId", target = "id")
  BpmTaskResponseVo.User convert3(SysUser bean);

  @Mapping(source = "taskDefinitionKey", target = "definitionKey")
  @Mapping(
      target = "createTime",
      expression =
          "java(bean.getCreateTime() == null ? null : LocalDateTime.ofInstant(bean.getCreateTime().toInstant(), ZoneId.systemDefault()))")
  @Mapping(
      target = "endTime",
      expression =
          "java(bean.getEndTime() == null ? null : LocalDateTime.ofInstant(bean.getEndTime().toInstant(), ZoneId.systemDefault()))")
  BpmTaskResponseVo convert3(HistoricTaskInstance bean);

  @Mapping(target = "id", ignore = true)
  void copyTo(BpmTaskExt from, @MappingTarget BpmTaskDoneItemResponseVo to);

  default BpmTaskExt convert2TaskExt(Task task) {
    BpmTaskExt taskExt =
        new BpmTaskExt()
            .setTaskId(task.getId())
            .setAssigneeUserId(NumberUtil.parseLong(task.getAssignee()))
            .setName(task.getName())
            .setProcessDefinitionId(task.getProcessDefinitionId())
            .setProcessInstanceId(task.getProcessInstanceId());
    taskExt.setCreateTime(task.getCreateTime());
    return taskExt;
  }
}
