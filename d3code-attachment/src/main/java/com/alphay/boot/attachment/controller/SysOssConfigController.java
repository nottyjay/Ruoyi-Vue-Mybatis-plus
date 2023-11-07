package com.alphay.boot.attachment.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.attachment.utils.StorageEngineUtil;
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
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.service.ISysOssConfigService;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.common.core.page.TableDataInfo;

/**
 * 存储配置Controller
 *
 * @author d3code
 * @date 2023-10-13
 */
@RestController
@RequestMapping("/oss/oss_config")
public class SysOssConfigController extends BaseController {
  @Autowired private ISysOssConfigService sysOssConfigService;

  /** 查询存储配置列表 */
  @PreAuthorize("@ss.hasPermi('oss:oss_config:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysOssConfig sysOssConfig) {
    List<SysOssConfig> list = sysOssConfigService.selectSysOssConfigList(sysOssConfig, startPage());
    return getDataTable(list);
  }

  /** 导出存储配置列表 */
  @PreAuthorize("@ss.hasPermi('oss:oss_config:export')")
  @Log(title = "存储配置", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysOssConfig sysOssConfig) {
    List<SysOssConfig> list = sysOssConfigService.selectSysOssConfigList(sysOssConfig);
    ExcelUtil<SysOssConfig> util = new ExcelUtil<SysOssConfig>(SysOssConfig.class);
    util.exportExcel(response, list, "存储配置数据");
  }

  /** 获取存储配置详细信息 */
  @PreAuthorize("@ss.hasPermi('oss:oss_config:query')")
  @GetMapping(value = "/{id}")
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(sysOssConfigService.getById(id));
  }

  /** 新增存储配置 */
  @PreAuthorize("@ss.hasPermi('oss:oss_config:add')")
  @Log(title = "存储配置", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody SysOssConfig sysOssConfig) {
    sysOssConfig.setCreateBy(getUsername());
    return toAjax(sysOssConfigService.save(sysOssConfig));
  }

  /** 修改存储配置 */
  @PreAuthorize("@ss.hasPermi('oss:oss_config:edit')")
  @Log(title = "存储配置", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody SysOssConfig sysOssConfig) {
    sysOssConfig.setUpdateBy(getUsername());
    return toAjax(sysOssConfigService.updateById(sysOssConfig));
  }

  /** 删除存储配置 */
  @PreAuthorize("@ss.hasPermi('oss:oss_config:remove')")
  @Log(title = "存储配置", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(sysOssConfigService.removeByIds(Arrays.asList(ids)));
  }

  @PreAuthorize("@ss.hasPermi('oss:oss_config:edit')")
  @Log(title = "存储配置", businessType = BusinessType.UPDATE)
  @PutMapping("/switch/{id}")
  public AjaxResult switchEngine(@PathVariable Long id) {
    sysOssConfigService.switchStorageEngine(id);
    return success();
  }

  @GetMapping("/getEnabledEngineConfig")
  public AjaxResult getEnabledEngineConfig() {
    return success(sysOssConfigService.getById(StorageEngineUtil.getInstance().getOssConfigId()));
  }

  @GetMapping("/refreshEngine")
  public AjaxResult refreshEngine() {
    sysOssConfigService.refreshEngine();
    return success();
  }
}
