package com.alphay.boot.bpm.controller;

import com.alphay.boot.bpm.service.IBpmActivityService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * BPM 流程活动实例 控制器
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/bpm/activity")
@Validated
public class BpmActivityController extends BaseController {

  @Resource private IBpmActivityService activityService;

  @GetMapping("/list")
  public AjaxResult list(String processInstanceId) {
    return success(activityService.getActivityListByProcessInstanceId(processInstanceId));
  }
}
