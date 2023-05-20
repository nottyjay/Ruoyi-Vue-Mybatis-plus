package com.alphay.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alphay.boot.bpm.convert.BpmTaskConvert;
import com.alphay.boot.bpm.domain.BpmTaskExt;
import com.alphay.boot.bpm.enums.BpmProcessInstanceDeleteReasonEnum;
import com.alphay.boot.bpm.enums.BpmProcessInstanceResultEnum;
import com.alphay.boot.bpm.mapper.BpmTaskExtMapper;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.bpm.service.IBpmMessageService;
import com.alphay.boot.bpm.service.IBpmProcessInstanceService;
import com.alphay.boot.bpm.service.IBpmTaskService;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.system.api.AdminApi;
import com.alphay.boot.system.api.DeptApi;
import com.alphay.boot.bpm.model.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Bpm模块 流程任务 实现
 *
 * @author Nottyjay
 */
@Service
@Slf4j
public class BpmTaskServiceImpl implements IBpmTaskService {

  @Resource private TaskService taskService;
  @Resource private HistoryService historyService;

  @Resource private IBpmProcessInstanceService processInstanceService;
  @Resource private BpmTaskExtMapper taskExtMapper;
  @Resource private IBpmMessageService messageService;

  @Resource private AdminApi adminApi;
  @Resource private DeptApi deptApi;

  @Override
  public List<BpmTaskTodoItemResponseVo> getTodoTaskList(Long userId, BpmTaskExt bpmTaskExt) {
    Page page = PageHelper.getLocalPage();
    PageHelper.clearPage();
    // 查询待办任务
    TaskQuery taskQuery =
        taskService
            .createTaskQuery()
            .taskAssignee(String.valueOf(userId)) // 分配给自己
            .orderByTaskCreateTime()
            .desc(); // 创建时间倒序
    if (StrUtil.isNotBlank(bpmTaskExt.getName())) {
      taskQuery.taskNameLike("%" + bpmTaskExt.getName() + "%");
    }
    if (bpmTaskExt.getParams().get("beginTime") != null) {
      taskQuery.taskCreatedAfter(
          DateUtil.parseDate((String) bpmTaskExt.getParams().get("beginTime")));
    }
    if (bpmTaskExt.getParams().get("endTime") != null) {
      taskQuery.taskCreatedBefore(
          DateUtil.parseDate((String) bpmTaskExt.getParams().get("endTime")));
    }
    List<Task> tasks = null;
    // 执行查询
    if (page != null) {
      tasks = taskQuery.listPage(page.getPageSize() * (page.getPageNum() - 1), page.getPageSize());
      if (CollUtil.isEmpty(tasks)) {
        page.setTotal(0);
        return page;
      }
    } else {
      tasks = taskQuery.list();
      if (CollUtil.isEmpty(tasks)) {
        return new ArrayList<>();
      }
    }

    // 获得 ProcessInstance Map
    Map<String, ProcessInstance> processInstanceMap =
        processInstanceService.getProcessInstanceMap(
            CollectionUtil.convertSet(tasks, Task::getProcessInstanceId));
    // 获得 User Map
    Map<Long, SysUser> userMap =
        adminApi.getUserMap(
            CollectionUtil.convertSet(
                processInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId())));
    // 拼接结果
    List<BpmTaskTodoItemResponseVo> list =
        BpmTaskConvert.INSTANCE.convertList1(tasks, processInstanceMap, userMap);
    if (page == null) {
      return list;
    } else {
      page.addAll(list);
      page.setTotal(taskQuery.count());
      return page;
    }
  }

  @Override
  public List<BpmTaskDoneItemResponseVo> getDoneTaskList(Long userId, BpmTaskExt bpmTaskExt) {
    Page page = PageHelper.getLocalPage();
    PageHelper.clearPage();
    // 查询已办任务
    HistoricTaskInstanceQuery taskQuery =
        historyService
            .createHistoricTaskInstanceQuery()
            .finished() // 已完成
            .taskAssignee(String.valueOf(userId)) // 分配给自己
            .orderByHistoricTaskInstanceEndTime()
            .desc(); // 审批时间倒序
    if (StrUtil.isNotBlank(bpmTaskExt.getName())) {
      taskQuery.taskNameLike("%" + bpmTaskExt.getName() + "%");
    }
    if (bpmTaskExt.getParams().get("beginTime") != null) {
      taskQuery.taskCreatedAfter(
          DateUtil.parseDate((String) bpmTaskExt.getParams().get("beginTime")));
    }
    if (bpmTaskExt.getParams().get("endTime") != null) {
      taskQuery.taskCreatedBefore(
          DateUtil.parseDate((String) bpmTaskExt.getParams().get("endTime")));
    }
    // 执行查询
    List<HistoricTaskInstance> tasks = null;
    // 执行查询
    if (page != null) {
      tasks = taskQuery.listPage(page.getPageSize() * (page.getPageNum() - 1), page.getPageSize());
      if (CollUtil.isEmpty(tasks)) {
        page.setTotal(0);
        return page;
      }
    } else {
      tasks = taskQuery.list();
      if (CollUtil.isEmpty(tasks)) {
        return new ArrayList<>();
      }
    }

    // 获得 TaskExtDO Map
    List<BpmTaskExt> bpmTaskExtDOs =
        taskExtMapper.selectListByTaskIds(
            CollectionUtil.convertSet(tasks, HistoricTaskInstance::getId));
    Map<String, BpmTaskExt> bpmTaskExtDOMap =
        CollectionUtil.convertMap(bpmTaskExtDOs, BpmTaskExt::getTaskId);
    // 获得 ProcessInstance Map
    Map<String, HistoricProcessInstance> historicProcessInstanceMap =
        processInstanceService.getHistoricProcessInstanceMap(
            CollectionUtil.convertSet(tasks, HistoricTaskInstance::getProcessInstanceId));
    // 获得 User Map
    Map<Long, SysUser> userMap =
        adminApi.getUserMap(
            CollectionUtil.convertSet(
                historicProcessInstanceMap.values(),
                instance -> Long.valueOf(instance.getStartUserId())));
    // 拼接结果
    List<BpmTaskDoneItemResponseVo> list =
        BpmTaskConvert.INSTANCE.convertList2(
            tasks, bpmTaskExtDOMap, historicProcessInstanceMap, userMap);
    if (page == null) {
      return list;
    } else {
      page.addAll(list);
      page.setTotal(taskQuery.count());
      return page;
    }
  }

  @Override
  public List<BpmTaskResponseVo> getTaskListByProcessInstanceId(String processInstanceId) {
    // 获得任务列表
    List<HistoricTaskInstance> tasks =
        historyService
            .createHistoricTaskInstanceQuery()
            .processInstanceId(processInstanceId)
            .orderByHistoricTaskInstanceStartTime()
            .desc() // 创建时间倒序
            .list();
    if (CollUtil.isEmpty(tasks)) {
      return Collections.emptyList();
    }

    // 获得 TaskExtDO Map
    List<BpmTaskExt> bpmTaskExtDOs =
        taskExtMapper.selectListByTaskIds(
            CollectionUtil.convertSet(tasks, HistoricTaskInstance::getId));
    Map<String, BpmTaskExt> bpmTaskExtDOMap =
        CollectionUtil.convertMap(bpmTaskExtDOs, BpmTaskExt::getTaskId);
    // 获得 ProcessInstance Map
    HistoricProcessInstance processInstance =
        processInstanceService.getHistoricProcessInstance(processInstanceId);
    // 获得 User Map
    Set<Long> userIds =
        CollectionUtil.convertSet(tasks, task -> NumberUtil.parseLong(task.getAssignee()));
    userIds.add(NumberUtil.parseLong(processInstance.getStartUserId()));
    Map<Long, SysUser> userMap = adminApi.getUserMap(userIds);
    // 获得 Dept Map
    Map<Long, SysDept> deptMap =
        deptApi.getDeptMap(CollectionUtil.convertSet(userMap.values(), SysUser::getDeptId));

    // 拼接数据
    return BpmTaskConvert.INSTANCE.convertList3(
        tasks, bpmTaskExtDOMap, processInstance, userMap, deptMap);
  }

  @Override
  public void approveTask(Long userId, BpmTaskCheckRequestVo requestVo) {
    // 校验任务存在
    Task task = checkTask(userId, requestVo.getId());
    // 校验流程实例存在
    ProcessInstance instance =
        processInstanceService.getProcessInstance(task.getProcessInstanceId());
    if (instance == null) {
      throw new ServiceException("流程实例不存在");
    }

    // 完成任务，审批通过
    taskService.complete(task.getId(), instance.getProcessVariables());

    // 更新任务拓展表为通过
    taskExtMapper.updateByTaskId(
        new BpmTaskExt()
            .setTaskId(task.getId())
            .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult())
            .setReason(requestVo.getReason()));
  }

  @Override
  public void rejectTask(Long userId, BpmTaskCheckRequestVo requestVo) {

    Task task = checkTask(userId, requestVo.getId());
    // 校验流程实例存在
    ProcessInstance instance =
        processInstanceService.getProcessInstance(task.getProcessInstanceId());
    if (instance == null) {
      throw new ServiceException("流程实例不存在");
    }

    // 更新流程实例为不通过
    processInstanceService.updateProcessInstanceExtReject(
        instance.getProcessInstanceId(), requestVo.getReason());

    // 更新任务拓展表为不通过
    taskExtMapper.updateByTaskId(
        new BpmTaskExt()
            .setTaskId(task.getId())
            .setResult(BpmProcessInstanceResultEnum.REJECT.getResult())
            .setEndTime(LocalDateTime.now())
            .setReason(requestVo.getReason()));
  }

  @Override
  public void updateTaskAssignee(Long userId, BpmTaskUpdateAssigneeRequestVo requestVo) {
    // 校验任务存在
    Task task = checkTask(userId, requestVo.getId());
    // 更新负责人
    updateTaskAssignee(task.getId(), requestVo.getAssigneeUserId());
  }

  @Override
  public void updateTaskAssignee(String id, Long userId) {
    taskService.setAssignee(id, String.valueOf(userId));
  }

  @Override
  public void createTaskExt(Task task) {
    BpmTaskExt taskExt =
        BpmTaskConvert.INSTANCE
            .convert2TaskExt(task)
            .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
    taskExtMapper.insert(taskExt);
  }

  @Override
  public void updateTaskExtComplete(Task task) {
    BpmTaskExt taskExt =
        BpmTaskConvert.INSTANCE
            .convert2TaskExt(task)
            .setResult(
                BpmProcessInstanceResultEnum.APPROVE
                    .getResult()) // 不设置也问题不大，因为 Complete 一般是审核通过，已经设置
            .setEndTime(LocalDateTime.now());
    taskExtMapper.updateByTaskId(taskExt);
  }

  @Override
  public void updateTaskExtCancel(String taskId) {
    // 需要在事务提交后，才进行查询。不然查询不到历史的原因
    TransactionSynchronizationManager.registerSynchronization(
        new TransactionSynchronization() {

          @Override
          public void afterCommit() {
            // 可能只是活动，不是任务，所以查询不到
            HistoricTaskInstance task = getHistoricTask(taskId);
            if (task == null) {
              return;
            }

            // 如果任务拓展表已经是完成的状态，则跳过
            BpmTaskExt taskExt = taskExtMapper.selectByTaskId(taskId);
            if (taskExt == null) {
              log.error("[updateTaskExtCancel][taskId({}) 查找不到对应的记录，可能存在问题]", taskId);
              return;
            }
            // 如果已经是最终的结果，则跳过
            if (BpmProcessInstanceResultEnum.isEndResult(taskExt.getResult())) {
              log.error(
                  "[updateTaskExtCancel][taskId({}) 处于结果({})，无需进行更新]", taskId, taskExt.getResult());
              return;
            }

            // 更新任务
            taskExtMapper.updateById(
                new BpmTaskExt()
                    .setId(taskExt.getId())
                    .setResult(BpmProcessInstanceResultEnum.CANCEL.getResult())
                    .setEndTime(LocalDateTime.now())
                    .setReason(
                        BpmProcessInstanceDeleteReasonEnum.translateReason(
                            task.getDeleteReason())));
          }
        });
  }

  @Override
  public void updateTaskExtAssign(Task task) {
    BpmTaskExt taskExtDO =
        new BpmTaskExt()
            .setAssigneeUserId(NumberUtil.parseLong(task.getAssignee()))
            .setTaskId(task.getId());
    taskExtMapper.updateByTaskId(taskExtDO);
    // 发送通知。在事务提交时，批量执行操作，所以直接查询会无法查询到 ProcessInstance，所以这里是通过监听事务的提交来实现。
    TransactionSynchronizationManager.registerSynchronization(
        new TransactionSynchronization() {
          @Override
          public void afterCommit() {
            ProcessInstance processInstance =
                processInstanceService.getProcessInstance(task.getProcessInstanceId());
            SysUser startUser = adminApi.getUser(Long.valueOf(processInstance.getStartUserId()));
            messageService.sendMessageWhenTaskAssigned(
                BpmTaskConvert.INSTANCE.convert(processInstance, startUser, task));
          }
        });
  }

  @Override
  public List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds) {
    if (CollUtil.isEmpty(processInstanceIds)) {
      return Collections.emptyList();
    }
    return taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
  }

  /**
   * 校验任务是否存在， 并且是否是分配给自己的任务
   *
   * @param userId 用户 id
   * @param taskId task id
   */
  private Task checkTask(Long userId, String taskId) {
    Task task = getTask(taskId);
    if (task == null) {
      throw new ServiceException("审批任务失败，原因：该任务不处于未审批");
    }
    if (!Objects.equals(userId, NumberUtil.parseLong(task.getAssignee()))) {
      throw new ServiceException("审批任务失败，原因：该任务的审批人不是你");
    }
    return task;
  }

  private Task getTask(String id) {
    return taskService.createTaskQuery().taskId(id).singleResult();
  }

  private HistoricTaskInstance getHistoricTask(String id) {
    return historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
  }
}
