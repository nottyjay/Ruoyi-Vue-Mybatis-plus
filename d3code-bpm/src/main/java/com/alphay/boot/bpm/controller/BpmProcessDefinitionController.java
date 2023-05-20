package com.alphay.boot.bpm.controller;

import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionPageItemResponseVo;
import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionQueryRequestVo;
import com.alphay.boot.bpm.service.IBpmProcessDefinitionService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * BPM 模型定义控制器
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/bpm/process-definition")
public class BpmProcessDefinitionController extends BaseController {

  @Resource private IBpmProcessDefinitionService bpmProcessDefinitionService;

  @GetMapping("/list")
  public TableDataInfo list(BpmProcessDefinitionQueryRequestVo requestVo) {
    startPage();
    List<BpmProcessDefinitionPageItemResponseVo> result =
        bpmProcessDefinitionService.getProcessDefinitionList(requestVo);
    return getDataTable(result);
  }

  @GetMapping("/get-bpmn-xml")
  public AjaxResult getProcessDefinitionBpmnXML(String id) {
    String bpmnXML = bpmProcessDefinitionService.getProcessDefinitionBpmnXML(id);
    AjaxResult result = new AjaxResult();
    result.put("data", bpmnXML);
    return result;
  }
}
