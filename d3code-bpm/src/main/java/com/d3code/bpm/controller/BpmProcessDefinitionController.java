package com.d3code.bpm.controller;

import com.d3code.bpm.model.vo.BpmProcessDefinitionPageItemResponseVo;
import com.d3code.bpm.model.vo.BpmProcessDefinitionQueryRequestVo;
import com.d3code.bpm.service.IBpmProcessDefinitionService;
import com.d3code.common.core.controller.BaseController;
import com.d3code.common.core.domain.AjaxResult;
import com.d3code.common.core.page.TableDataInfo;
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
