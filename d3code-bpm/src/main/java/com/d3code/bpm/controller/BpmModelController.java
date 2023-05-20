package com.d3code.bpm.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.d3code.bpm.convert.BpmModelConvert;
import com.d3code.bpm.model.vo.*;
import com.d3code.bpm.service.IBpmModelService;
import com.d3code.common.core.controller.BaseController;
import com.d3code.common.core.domain.AjaxResult;
import com.d3code.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 流程模型业务接口
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/bpm/model")
public class BpmModelController extends BaseController {

  @Resource private IBpmModelService modelService;

  @GetMapping("/list")
  public TableDataInfo list(BpmModelQueryRequestVo requestVo) {
    startPage();
    List<BpmModelItemResponseVo> result = modelService.selectModelList(requestVo);
    return getDataTable(result);
  }

  @GetMapping("/info/{id}")
  public AjaxResult getModelInfo(@PathVariable String id) {
    return success(modelService.getModel(id));
  }

  @PostMapping("/add")
  public AjaxResult create(@Valid @RequestBody BpmModelCreateRequestVo requestVo) {
    return success(modelService.createModel(requestVo, null));
  }

  @PutMapping("/update")
  public AjaxResult update(@Valid @RequestBody BpmModelUpdateRequestVo requestVo) {
    modelService.updateModel(requestVo);
    return success();
  }

  @PostMapping("/import")
  public AjaxResult importModel(@Valid BpmModelImportRequestVo requestVo) throws IOException {
    BpmModelCreateRequestVo createReqVO = BpmModelConvert.INSTANCE.convert(requestVo);
    // 读取文件
    String bpmnXml = IoUtil.readUtf8(requestVo.getBpmnFile().getInputStream());
    return success(modelService.createModel(createReqVO, bpmnXml));
  }

  @PostMapping("/deploy/{id}")
  public AjaxResult deployModel(@PathVariable String id) {
    modelService.deployModel(id);
    return success();
  }

  @PutMapping("/state")
  public AjaxResult updateModelState(@Valid @RequestBody BpmModelUpdateStateRequestVo requestVo) {
    modelService.updateModelState(requestVo.getId(), requestVo.getState());
    return success();
  }

  @DeleteMapping("/delete/{id}")
  public AjaxResult deleteModel(@PathVariable String id) {
    modelService.deleteModel(id);
    return success();
  }
}
