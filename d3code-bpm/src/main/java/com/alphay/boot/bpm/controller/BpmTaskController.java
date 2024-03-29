package com.alphay.boot.bpm.controller;

import com.alphay.boot.bpm.api.domain.BpmTaskExt;
import com.alphay.boot.bpm.api.model.vo.BpmTaskCheckRequestVo;
import com.alphay.boot.bpm.api.model.vo.BpmTaskDoneItemResponseVo;
import com.alphay.boot.bpm.api.model.vo.BpmTaskTodoItemResponseVo;
import com.alphay.boot.bpm.api.model.vo.BpmTaskUpdateAssigneeRequestVo;
import com.alphay.boot.bpm.api.service.IBpmTaskService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 流程任务实例
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/bpm/task")
public class BpmTaskController extends BaseController {

  @Resource private IBpmTaskService taskService;

  @GetMapping("/todo-page")
  public TableDataInfo getTodoTaskPage(BpmTaskExt bpmTaskExt) {
    List<BpmTaskTodoItemResponseVo> result =
        taskService.selectTodoTaskList(getUserId(), bpmTaskExt, startPage());
    return getDataTable(result);
  }

  @GetMapping("/done-page")
  public TableDataInfo getDoneTaskPage(BpmTaskExt bpmTaskExt) {
    List<BpmTaskDoneItemResponseVo> result =
        taskService.selectDoneTaskList(getUserId(), bpmTaskExt, startPage());
    return getDataTable(result);
  }

  @GetMapping("/list-by-process-instance-id")
  public AjaxResult getTaskListByProcessInstanceId(String processInstanceId) {
    return success(taskService.getTaskListByProcessInstanceId(processInstanceId));
  }

  @PutMapping("/approve")
  public AjaxResult approveTask(@Valid @RequestBody BpmTaskCheckRequestVo requestVo) {
    taskService.approveTask(getUserId(), requestVo);
    return success(true);
  }

  @PutMapping("/reject")
  public AjaxResult rejectTask(@Valid @RequestBody BpmTaskCheckRequestVo requestVo) {
    taskService.rejectTask(getUserId(), requestVo);
    return success(true);
  }

  @PutMapping("/update-assignee")
  public AjaxResult updateTaskAssignee(
      @Valid @RequestBody BpmTaskUpdateAssigneeRequestVo requestVo) {
    taskService.updateTaskAssignee(getUserId(), requestVo);
    return success(true);
  }
}
