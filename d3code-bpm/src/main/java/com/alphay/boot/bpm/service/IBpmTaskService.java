package com.alphay.boot.bpm.service;

import com.alphay.boot.bpm.domain.BpmTaskExt;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * 流程任务实例 Service 接口
 *
 * @author Nottyjay
 */
public interface IBpmTaskService {

  /**
   * 获得待办的流程任务
   *
   * @param userId
   * @param bpmTaskExt
   * @return
   */
  List<BpmTaskTodoItemResponseVo> getTodoTaskList(Long userId, BpmTaskExt bpmTaskExt);

  /**
   * 获得已办的流程任务分页
   *
   * @param userId
   * @param bpmTaskExt
   * @return
   */
  List<BpmTaskDoneItemResponseVo> getDoneTaskList(Long userId, BpmTaskExt bpmTaskExt);

  /**
   * 获得指令流程实例的流程任务列表，包括所有状态的
   *
   * @param processInstanceId
   * @return
   */
  List<BpmTaskResponseVo> getTaskListByProcessInstanceId(String processInstanceId);

  /**
   * 通过任务
   *
   * @param userId
   * @param requestVo
   */
  void approveTask(Long userId, BpmTaskCheckRequestVo requestVo);

  /**
   * 拒绝任务
   *
   * @param userId
   * @param requestVo
   */
  void rejectTask(Long userId, BpmTaskCheckRequestVo requestVo);

  /**
   * 将流程任务分配给指定用户
   *
   * @param userId
   * @param requestVo
   */
  void updateTaskAssignee(Long userId, BpmTaskUpdateAssigneeRequestVo requestVo);

  /**
   * 将流程任务分配给指定用户
   *
   * @param id
   * @param userId
   */
  void updateTaskAssignee(String id, Long userId);

  /**
   * 创建 Task 拓展记录
   *
   * @param task
   */
  void createTaskExt(Task task);

  /**
   * 更新 Task 拓展记录为完成
   *
   * @param task
   */
  void updateTaskExtComplete(Task task);

  /**
   * 更新 Task 拓展记录为已取消
   *
   * @param taskId
   */
  void updateTaskExtCancel(String taskId);

  /**
   * 更新 Task 拓展记录，并发送通知
   *
   * @param task
   */
  void updateTaskExtAssign(Task task);

  /**
   * 获得流程任务 Map
   *
   * @param processInstanceIds
   * @return
   */
  default Map<String, List<Task>> getTaskMapByProcessInstanceIds(List<String> processInstanceIds) {
    return CollectionUtil.convertMultiMap(
        getTasksByProcessInstanceIds(processInstanceIds), Task::getProcessInstanceId);
  }

  /**
   * 获得流程任务列表
   *
   * @param processInstanceIds
   * @return
   */
  List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds);
}
