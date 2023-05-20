package com.alphay.boot.bpm.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.bpm.service.IBpmFormService;
import com.alphay.boot.bpm.convert.BpmFormConvert;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.common.core.page.TableDataInfo;

/**
 * 业务流表单Controller
 *
 * @author Nottyjay
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/bpm/bpmForm")
public class BpmFormController extends BaseController {
  @Autowired private IBpmFormService bpmFormService;

  /** 查询业务流表单列表 */
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:list')")
  @GetMapping("/list")
  public TableDataInfo list(BpmForm bpmForm) {
    startPage();
    List<BpmForm> list = bpmFormService.selectBpmFormList(bpmForm);
    return getDataTable(list);
  }

  @GetMapping("/list-all-simple")
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:list')")
  public AjaxResult getSimpleForms() {
    List<BpmForm> list = bpmFormService.selectBpmFormList(new BpmForm());
    return success(BpmFormConvert.INSTANCE.convertList2(list));
  }

  /** 导出业务流表单列表 */
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:export')")
  @Log(title = "业务流表单", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, BpmForm bpmForm) {
    List<BpmForm> list = bpmFormService.selectBpmFormList(bpmForm);
    ExcelUtil<BpmForm> util = new ExcelUtil<BpmForm>(BpmForm.class);
    util.exportExcel(response, list, "业务流表单数据");
  }

  /** 获取业务流表单详细信息 */
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:query')")
  @GetMapping(value = "/{id}")
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(bpmFormService.getById(id));
  }

  /** 新增业务流表单 */
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:add')")
  @Log(title = "业务流表单", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody BpmForm bpmForm) {
    return toAjax(bpmFormService.save(bpmForm));
  }

  /** 修改业务流表单 */
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:edit')")
  @Log(title = "业务流表单", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody BpmForm bpmForm) {
    return toAjax(bpmFormService.updateById(bpmForm));
  }

  /** 删除业务流表单 */
  @PreAuthorize("@ss.hasPermi('bpm:bpmForm:remove')")
  @Log(title = "业务流表单", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(bpmFormService.removeByIds(Arrays.asList(ids)));
  }
}
