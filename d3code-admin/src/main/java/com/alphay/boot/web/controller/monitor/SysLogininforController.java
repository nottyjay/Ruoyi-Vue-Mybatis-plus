package com.alphay.boot.web.controller.monitor;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.system.common.domain.SysLogininfor;
import com.alphay.boot.system.common.service.ISysLogininforService;
import com.alphay.boot.web.service.SysPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统访问记录
 *
 * @author d3code
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
  @Autowired private ISysLogininforService logininforService;

  @Autowired private SysPasswordService passwordService;

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysLogininfor logininfor) {
    startPage();
    List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
    return getDataTable(list);
  }

  @Log(title = "登录日志", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysLogininfor logininfor) {
    List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
    ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
    util.exportExcel(response, list, "登录日志");
  }

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
  @Log(title = "登录日志", businessType = BusinessType.DELETE)
  @DeleteMapping("/{infoIds}")
  public AjaxResult remove(@PathVariable Long[] infoIds) {
    return toAjax(logininforService.deleteLogininforByIds(infoIds));
  }

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
  @Log(title = "登录日志", businessType = BusinessType.CLEAN)
  @DeleteMapping("/clean")
  public AjaxResult clean() {
    logininforService.cleanLogininfor();
    return success();
  }

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
  @Log(title = "账户解锁", businessType = BusinessType.OTHER)
  @GetMapping("/unlock/{userName}")
  public AjaxResult unlock(@PathVariable("userName") String userName) {
    passwordService.clearLoginRecordCache(userName);
    return success();
  }
}
