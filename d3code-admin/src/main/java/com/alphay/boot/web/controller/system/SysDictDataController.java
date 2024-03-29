package com.alphay.boot.web.controller.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.system.common.service.ISysDictDataService;
import com.alphay.boot.system.common.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典信息
 *
 * @author d3code
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
  @Autowired private ISysDictDataService dictDataService;

  @Autowired private ISysDictTypeService dictTypeService;

  @PreAuthorize("@ss.hasPermi('system:dict:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysDictData dictData) {
    List<SysDictData> list = dictDataService.selectDictDataList(dictData, startPage());
    return getDataTable(list);
  }

  @Log(title = "字典数据", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:dict:export')")
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysDictData dictData) {
    List<SysDictData> list = dictDataService.selectDictDataList(dictData, null);
    ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
    util.exportExcel(response, list, "字典数据");
  }

  /** 查询字典数据详细 */
  @PreAuthorize("@ss.hasPermi('system:dict:query')")
  @GetMapping(value = "/{dictCode}")
  public AjaxResult getInfo(@PathVariable Long dictCode) {
    return success(dictDataService.getById(dictCode));
  }

  /** 根据字典类型查询字典数据信息 */
  @GetMapping(value = "/type/{dictType}")
  public AjaxResult dictType(@PathVariable String dictType) {
    List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
    if (StringUtils.isNull(data)) {
      data = new ArrayList<SysDictData>();
    }
    return success(data);
  }

  /** 新增字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:add')")
  @Log(title = "字典数据", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@Validated @RequestBody SysDictData dict) {
    dict.setCreateBy(getUsername());
    return toAjax(dictDataService.save(dict));
  }

  /** 修改保存字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:edit')")
  @Log(title = "字典数据", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
    dict.setUpdateBy(getUsername());
    return toAjax(dictDataService.updateById(dict));
  }

  /** 删除字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:remove')")
  @Log(title = "字典类型", businessType = BusinessType.DELETE)
  @DeleteMapping("/{dictCodes}")
  public AjaxResult remove(@PathVariable Long[] dictCodes) {
    dictDataService.removeByIds(Arrays.asList(dictCodes));
    return success();
  }
}
