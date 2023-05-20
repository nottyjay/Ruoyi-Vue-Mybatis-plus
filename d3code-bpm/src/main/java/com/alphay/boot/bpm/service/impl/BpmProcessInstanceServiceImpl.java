package com.alphay.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alphay.boot.bpm.convert.BpmProcessInstanceConvert;
import com.alphay.boot.bpm.domain.BpmProcessDefinitionExt;
import com.alphay.boot.bpm.domain.BpmProcessInstanceExt;
import com.alphay.boot.bpm.enums.BpmProcessInstanceDeleteReasonEnum;
import com.alphay.boot.bpm.enums.BpmProcessInstanceResultEnum;
import com.alphay.boot.bpm.enums.BpmProcessInstanceStatusEnum;
import com.alphay.boot.bpm.framework.event.BpmProcessInstanceResultEventPublisher;
import com.alphay.boot.bpm.mapper.BpmProcessInstanceExtMapper;
import com.alphay.boot.bpm.service.IBpmMessageService;
import com.alphay.boot.bpm.service.IBpmProcessDefinitionService;
import com.alphay.boot.bpm.service.IBpmProcessInstanceService;
import com.alphay.boot.bpm.service.IBpmTaskService;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.api.AdminApi;
import com.alphay.boot.system.api.DeptApi;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceCancelRequestVo;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceItemResponseVo;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceResponseVo;
import com.alphay.boot.bpm.service.*;
import com.github.pagehelper.Page;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 流程实例接口实现
 *
 * @author Nottyjay
 */
@Service
public class BpmProcessInstanceServiceImpl implements IBpmProcessInstanceService {

  @Resource private BpmProcessInstanceExtMapper processInstanceExtMapper;
  @Resource @Lazy private IBpmTaskService taskService;
  @Resource private IBpmProcessDefinitionService processDefinitionService;
  @Resource private IBpmMessageService messageService;

  @Resource private BpmProcessInstanceResultEventPublisher processInstanceResultEventPublisher;

  @Resource private AdminApi adminApi;
  @Resource private DeptApi deptApi;

  @Resource private RuntimeService runtimeService;
  @Resource private HistoryService historyService;

  @Override
  public List<BpmProcessInstanceItemResponseVo> selectMyProcessInstanceList(
      Long userId, BpmProcessInstanceExt processInstanceExt) {
    processInstanceExt.setStartUserId(userId);
    List<BpmProcessInstanceExt> result = processInstanceExtMapper.selectList(processInstanceExt);
    if (CollUtil.isEmpty(result)) {
      return new Page<BpmProcessInstanceItemResponseVo>();
    }

    // 获得流程 Task Map
    List<String> processInstanceIds =
        CollectionUtil.convertList(result, BpmProcessInstanceExt::getProcessDefinitionId);
    Map<String, List<Task>> taskMap =
        taskService.getTaskMapByProcessInstanceIds(processInstanceIds);
    // 转换返回

    List<BpmProcessInstanceItemResponseVo> itemResponseVoList =
        BpmProcessInstanceConvert.INSTANCE.convertList(result, taskMap);
    if (result instanceof Page) {
      Page resultPage = (Page) result;
      Page<BpmProcessInstanceItemResponseVo> returnResult =
          new Page<>(resultPage.getPageSize(), resultPage.getPageNum());
      returnResult.setTotal(resultPage.getTotal());
      returnResult.addAll(itemResponseVoList);
      return returnResult;
    } else {
      return itemResponseVoList;
    }
  }

  @Override
  public BpmProcessInstanceResponseVo getProcessInstanceVO(String id) {
    // 获得流程实例
    HistoricProcessInstance processInstance = getHistoricProcessInstance(id);
    if (processInstance == null) {
      return null;
    }
    BpmProcessInstanceExt processInstanceExt =
        processInstanceExtMapper.selectByProcessInstanceId(id);
    Assert.notNull(processInstanceExt, "流程实例拓展({}) 不存在", id);

    // 获得流程定义
    ProcessDefinition processDefinition =
        processDefinitionService.getProcessDefinition(processInstance.getProcessDefinitionId());
    Assert.notNull(processDefinition, "流程定义({}) 不存在", processInstance.getProcessDefinitionId());
    BpmProcessDefinitionExt processDefinitionExt =
        processDefinitionService.getProcessDefinitionExt(processInstance.getProcessDefinitionId());
    Assert.notNull(processDefinitionExt, "流程定义拓展({}) 不存在", id);
    String bpmnXml =
        processDefinitionService.getProcessDefinitionBpmnXML(
            processInstance.getProcessDefinitionId());

    // 获得 User
    SysUser startUser = adminApi.getUser(NumberUtil.parseLong(processInstance.getStartUserId()));
    SysDept dept = null;
    if (startUser != null) {
      dept = deptApi.getDept(startUser.getDeptId());
    }

    // 拼接结果
    return BpmProcessInstanceConvert.INSTANCE.convert2(
        processInstance,
        processInstanceExt,
        processDefinition,
        processDefinitionExt,
        bpmnXml,
        startUser,
        dept);
  }

  @Override
  public void cancelProcessInstance(
      Long userId, BpmProcessInstanceCancelRequestVo cancelRequestVo) {
    // 校验流程实例存在
    ProcessInstance instance = getProcessInstance(cancelRequestVo.getId());
    if (instance == null) {
      throw new ServiceException("流程取消失败，流程不处于运行中");
    }
    // 只能取消自己的
    if (!Objects.equals(instance.getStartUserId(), String.valueOf(userId))) {
      throw new ServiceException("流程取消失败，该流程不是你发起的");
    }

    // 通过删除流程实例，实现流程实例的取消,
    // 删除流程实例，正则执行任务 ACT_RU_TASK. 任务会被删除。通过历史表查询
    deleteProcessInstance(
        cancelRequestVo.getId(),
        BpmProcessInstanceDeleteReasonEnum.CANCEL_TASK.format(cancelRequestVo.getReason()));
  }

  @Override
  public HistoricProcessInstance getHistoricProcessInstance(String id) {
    return historyService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
  }

  @Override
  public ProcessInstance getProcessInstance(String id) {
    return runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
  }

  @Override
  public List<ProcessInstance> getProcessInstances(Set<String> ids) {
    return runtimeService.createProcessInstanceQuery().processInstanceIds(ids).list();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String createProcessInstance(Long userId, BpmProcessInstanceExt processInstanceExt) {
    // 获得流程定义
    ProcessDefinition definition =
        processDefinitionService.getProcessDefinition(processInstanceExt.getProcessDefinitionId());
    return createProcessInstance0(userId, definition, processInstanceExt.getFormVariables(), null);
  }

  @Override
  public List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids) {
    return historyService.createHistoricProcessInstanceQuery().processInstanceIds(ids).list();
  }

  @Override
  public void updateProcessInstanceExtReject(String processInstanceId, String reason) {
    // 需要主动查询，因为 instance 只有 id 属性
    ProcessInstance processInstance = getProcessInstance(processInstanceId);
    // 删除流程实例，以实现驳回任务时，取消整个审批流程
    deleteProcessInstance(
        processInstanceId,
        StrUtil.format(BpmProcessInstanceDeleteReasonEnum.REJECT_TASK.format(reason)));

    // 更新 status + result
    // 注意，不能和上面的逻辑更换位置。因为 deleteProcessInstance 会触发流程的取消，进而调用 updateProcessInstanceExtCancel 方法，
    // 设置 result 为 BpmProcessInstanceStatusEnum.CANCEL，显然和 result 不一定是一致的
    BpmProcessInstanceExt instanceExt =
        new BpmProcessInstanceExt()
            .setProcessInstanceId(processInstanceId)
            .setStatus(BpmProcessInstanceStatusEnum.FINISH.getStatus())
            .setResult(BpmProcessInstanceResultEnum.REJECT.getResult());
    processInstanceExtMapper.updateByProcessInstanceId(instanceExt);

    // 发送流程被不通过的消息
    messageService.sendMessageWhenProcessInstanceReject(
        BpmProcessInstanceConvert.INSTANCE.convert2RejectReq(processInstance, reason));

    // 发送流程实例的状态事件
    processInstanceResultEventPublisher.sendProcessInstanceResultEvent(
        BpmProcessInstanceConvert.INSTANCE.convert(this, processInstance, instanceExt.getResult()));
  }

  @Override
  public void createProcessInstanceExt(ProcessInstance instance) {
    // 获得流程定义
    ProcessDefinition definition =
        processDefinitionService.getProcessDefinition2(instance.getProcessDefinitionId());
    // 插入 BpmProcessInstanceExtDO 对象
    BpmProcessInstanceExt instanceExt =
        new BpmProcessInstanceExt()
            .setProcessInstanceId(instance.getId())
            .setProcessDefinitionId(definition.getId())
            .setName(instance.getProcessDefinitionName())
            .setStartUserId(Long.valueOf(instance.getStartUserId()))
            .setCategory(definition.getCategory())
            .setStatus(BpmProcessInstanceStatusEnum.RUNNING.getStatus())
            .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());

    processInstanceExtMapper.insert(instanceExt);
  }

  @Override
  public void updateProcessInstanceExtCancel(FlowableCancelledEvent event) {
    // 判断是否为 Reject 不通过。如果是，则不进行更新.
    // 因为，updateProcessInstanceExtReject 方法，已经进行更新了
    if (BpmProcessInstanceDeleteReasonEnum.isRejectReason((String) event.getCause())) {
      return;
    }

    // 需要主动查询，因为 instance 只有 id 属性
    // 另外，此时如果去查询 ProcessInstance 的话，字段是不全的，所以去查询了 HistoricProcessInstance
    HistoricProcessInstance processInstance =
        getHistoricProcessInstance(event.getProcessInstanceId());
    // 更新拓展表
    BpmProcessInstanceExt instanceExt =
        new BpmProcessInstanceExt()
            .setProcessInstanceId(event.getProcessInstanceId())
            .setEndTime(LocalDateTime.now()) // 由于 ProcessInstance 里没有办法拿到 endTime，所以这里设置
            .setStatus(BpmProcessInstanceStatusEnum.FINISH.getStatus())
            .setResult(BpmProcessInstanceResultEnum.CANCEL.getResult());
    processInstanceExtMapper.updateByProcessInstanceId(instanceExt);

    // 发送流程实例的状态事件
    processInstanceResultEventPublisher.sendProcessInstanceResultEvent(
        BpmProcessInstanceConvert.INSTANCE.convert(this, processInstance, instanceExt.getResult()));
  }

  @Override
  public void updateProcessInstanceExtComplete(ProcessInstance instance) {
    // 需要主动查询，因为 instance 只有 id 属性
    // 另外，此时如果去查询 ProcessInstance 的话，字段是不全的，所以去查询了 HistoricProcessInstance
    HistoricProcessInstance processInstance = getHistoricProcessInstance(instance.getId());
    // 更新拓展表
    BpmProcessInstanceExt instanceExt =
        new BpmProcessInstanceExt()
            .setProcessInstanceId(instance.getProcessInstanceId())
            .setEndTime(LocalDateTime.now()) // 由于 ProcessInstance 里没有办法拿到 endTime，所以这里设置
            .setStatus(BpmProcessInstanceStatusEnum.FINISH.getStatus())
            .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult()); // 如果正常完全，说明审批通过
    processInstanceExtMapper.updateByProcessInstanceId(instanceExt);

    // 发送流程被通过的消息
    messageService.sendMessageWhenProcessInstanceApprove(
        BpmProcessInstanceConvert.INSTANCE.convert2ApprovedReq(instance));

    // 发送流程实例的状态事件
    processInstanceResultEventPublisher.sendProcessInstanceResultEvent(
        BpmProcessInstanceConvert.INSTANCE.convert(this, processInstance, instanceExt.getResult()));
  }

  private String createProcessInstance0(
      Long userId,
      ProcessDefinition definition,
      Map<String, Object> variables,
      String businessKey) {
    // 校验流程定义
    if (definition == null) {
      throw new ServiceException("流程定义不存在");
    }
    if (definition.isSuspended()) {
      throw new ServiceException("流程定义处于挂起状态");
    }

    // 创建流程实例
    ProcessInstance instance =
        runtimeService.startProcessInstanceById(definition.getId(), businessKey, variables);
    // 设置流程名字
    runtimeService.setProcessInstanceName(instance.getId(), definition.getName());

    // 补全流程实例的拓展表
    processInstanceExtMapper.updateByProcessInstanceId(
        new BpmProcessInstanceExt()
            .setProcessInstanceId(instance.getId())
            .setFormVariables(variables));
    return instance.getId();
  }

  private void deleteProcessInstance(String id, String reason) {
    runtimeService.deleteProcessInstance(id, reason);
  }
}
