package com.alphay.boot.bpm.controller;

import com.alphay.boot.bpm.model.vo.BpmTaksAssignRuleCreateRequestVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleUpdateRequestVo;
import com.alphay.boot.bpm.service.IBpmTaskAssignRuleService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/bpm/task-assign-rule")
@Validated
public class BpmTaskAssignRuleController extends BaseController {

  @Resource private IBpmTaskAssignRuleService taskAssignRuleService;

  @GetMapping("/list")
  public AjaxResult getTaskAssignRuleList(
      @RequestParam(value = "modelId", required = false) String modelId,
      @RequestParam(value = "processDefinitionId", required = false) String processDefinitionId) {
    return success(taskAssignRuleService.getTaskAssignRuleList(modelId, processDefinitionId));
  }

  @PostMapping("/create")
  public AjaxResult createTaskAssignRule(
      @Valid @RequestBody BpmTaksAssignRuleCreateRequestVo requestVo) {
    return success(taskAssignRuleService.createTaskAssignRule(requestVo));
  }

  @PutMapping("/update")
  public AjaxResult updateTaskAssignRule(
      @Valid @RequestBody BpmTaskAssignRuleUpdateRequestVo requestVo) {
    taskAssignRuleService.updateTaskAssignRule(requestVo);
    return success();
  }
}
