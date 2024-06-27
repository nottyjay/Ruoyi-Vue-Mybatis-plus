package com.alphay.boot.web.controller.system;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import com.alphay.boot.common.core.domain.entity.SysArea;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.file.FileUtils;
import com.alphay.boot.web.manager.AsyncManager;
import org.apache.commons.io.IOUtils;
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
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.system.common.service.ISysAreaService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 地区管理Controller
 *
 * @author d3code
 * @date 2024-06-21
 */
@RestController
@RequestMapping("/system/area")
public class SysAreaController extends BaseController {
  @Autowired private ISysAreaService sysAreaService;

  /** 查询地区管理列表 */
  @PreAuthorize("@ss.hasPermi('system:area:list')")
  @GetMapping("/list")
  public AjaxResult list(SysArea sysArea) {
    List<SysArea> list = sysAreaService.selectSysAreaList(sysArea);
    return success(list);
  }

  @PreAuthorize("@ss.hasPermi('system:area:list')")
  @GetMapping("/getTree")
  public AjaxResult getTreeList(SysArea sysArea) {
    return success(sysAreaService.selectSysAreaTreeList(sysArea));
  }

  /** 导出地区管理列表 */
  @PreAuthorize("@ss.hasPermi('system:area:export')")
  @Log(title = "地区管理", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysArea sysArea) {
    List<SysArea> list = sysAreaService.selectSysAreaList(sysArea);
    ExcelUtil<SysArea> util = new ExcelUtil<SysArea>(SysArea.class);
    util.exportExcel(response, list, "地区管理数据");
  }

  /** 导出地区管理列表 */
  @PreAuthorize("@ss.hasPermi('system:area:import')")
  @Log(title = "地区管理", businessType = BusinessType.EXPORT)
  @PostMapping("/importFile")
  public void importFile(HttpServletResponse response, MultipartFile file) throws IOException {
    String content = IOUtils.toString(file.getInputStream(), "UTF-8");
    AsyncManager.me()
        .execute(
            new TimerTask() {
              @Override
              public void run() {
                sysAreaService.syncArea(content);
              }
            });
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
    response.setContentType("application/json; charset=UTF-8");
    response.getWriter().write(JsonUtil.toJsonHex(AjaxResult.success("文件上传成功，等待同步....")));
  }

  @PreAuthorize("@ss.hasPermi('system:area:import')")
  @GetMapping("/syncProcess")
  public AjaxResult syncProcess() {
    return success(sysAreaService.getSyncProcess());
  }

  /** 获取地区管理详细信息 */
  @PreAuthorize("@ss.hasPermi('system:area:query')")
  @GetMapping(value = "/{id}")
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(sysAreaService.getById(id));
  }

  /** 新增地区管理 */
  @PreAuthorize("@ss.hasPermi('system:area:add')")
  @Log(title = "地区管理", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody SysArea sysArea) {
    sysArea.setCreateBy(getUsername());
    return toAjax(sysAreaService.save(sysArea));
  }

  /** 修改地区管理 */
  @PreAuthorize("@ss.hasPermi('system:area:edit')")
  @Log(title = "地区管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody SysArea sysArea) {
    sysArea.setUpdateBy(getUsername());
    return toAjax(sysAreaService.updateById(sysArea));
  }

  /** 删除地区管理 */
  @PreAuthorize("@ss.hasPermi('system:area:remove')")
  @Log(title = "地区管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(sysAreaService.removeByIds(Arrays.asList(ids)));
  }
}
