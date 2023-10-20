package com.alphay.boot.web.controller.monitor;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.system.common.domain.SysOperLog;
import com.alphay.boot.system.common.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志记录
 *
 * @author d3code
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
  @Autowired private ISysOperLogService operLogService;

  @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysOperLog operLog) {
    List<SysOperLog> list = operLogService.selectOperLogList(operLog, startPage());
    return getDataTable(list);
  }

  @Log(title = "操作日志", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysOperLog operLog) {
    List<SysOperLog> list = operLogService.selectOperLogList(operLog, null);
    ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
    util.exportExcel(response, list, "操作日志");
  }

  @Log(title = "操作日志", businessType = BusinessType.DELETE)
  @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
  @DeleteMapping("/{operIds}")
  public AjaxResult remove(@PathVariable Long[] operIds) {
    return toAjax(operLogService.removeBatchByIds(Arrays.asList(operIds)));
  }

  @Log(title = "操作日志", businessType = BusinessType.CLEAN)
  @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
  @DeleteMapping("/clean")
  public AjaxResult clean() {
    operLogService.cleanOperLog();
    return success();
  }
}
