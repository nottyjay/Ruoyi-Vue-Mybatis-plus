package com.alphay.boot.bpm.controller;

import com.alphay.boot.bpm.domain.BpmProcessInstanceExt;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceCancelRequestVo;
import com.alphay.boot.bpm.model.vo.BpmProcessInstanceItemResponseVo;
import com.alphay.boot.bpm.service.IBpmProcessInstanceService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * BPM 流程实例
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/bpm/process-instance")
public class BpmProcessInstanceController extends BaseController {

  @Resource private IBpmProcessInstanceService processInstanceService;

  @GetMapping("/list")
  public TableDataInfo list(BpmProcessInstanceExt processInstanceExt) {
    startPage();
    List<BpmProcessInstanceItemResponseVo> result =
        processInstanceService.selectMyProcessInstanceList(getUserId(), processInstanceExt);
    return getDataTable(result);
  }

  @PostMapping("/create")
  public AjaxResult create(@RequestBody BpmProcessInstanceExt processInstanceExt) {
    return success(processInstanceService.createProcessInstance(getUserId(), processInstanceExt));
  }

  @GetMapping("/get/{id}")
  public AjaxResult getProcessInstance(@PathVariable String id) {
    return success(processInstanceService.getProcessInstanceVO(id));
  }

  @DeleteMapping("/cancel")
  public AjaxResult cancelProcessInstance(
      @RequestBody @Valid BpmProcessInstanceCancelRequestVo cancelRequestVo) {
    processInstanceService.cancelProcessInstance(getUserId(), cancelRequestVo);
    return toAjax(true);
  }
}
