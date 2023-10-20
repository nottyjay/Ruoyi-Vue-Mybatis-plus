package com.alphay.boot.attachment.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.alphay.boot.attachment.api.domain.SysOssBucket;
import com.alphay.boot.attachment.api.service.ISysOssBucketService;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.common.core.page.TableDataInfo;

/**
 * 存储桶Controller
 *
 * @author d3code
 * @date 2023-10-13
 */
@RestController
@RequestMapping("/bucket/bucket")
public class SysOssBucketController extends BaseController {
  @Autowired private ISysOssBucketService sysOssBucketService;

  /** 查询存储桶列表 */
  @PreAuthorize("@ss.hasPermi('bucket:bucket:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysOssBucket sysOssBucket) {
    List<SysOssBucket> list = sysOssBucketService.selectSysOssBucketList(sysOssBucket, startPage());
    return getDataTable(list);
  }

  /** 导出存储桶列表 */
  @PreAuthorize("@ss.hasPermi('bucket:bucket:export')")
  @Log(title = "存储桶", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysOssBucket sysOssBucket) {
    List<SysOssBucket> list = sysOssBucketService.selectSysOssBucketList(sysOssBucket);
    ExcelUtil<SysOssBucket> util = new ExcelUtil<SysOssBucket>(SysOssBucket.class);
    util.exportExcel(response, list, "存储桶数据");
  }

  /** 获取存储桶详细信息 */
  @PreAuthorize("@ss.hasPermi('bucket:bucket:query')")
  @GetMapping(value = "/{id}")
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(sysOssBucketService.getById(id));
  }

  /** 新增存储桶 */
  @PreAuthorize("@ss.hasPermi('bucket:bucket:add')")
  @Log(title = "存储桶", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody SysOssBucket sysOssBucket) {
    sysOssBucket.setCreateBy(getUsername());
    return toAjax(sysOssBucketService.save(sysOssBucket));
  }

  /** 修改存储桶 */
  @PreAuthorize("@ss.hasPermi('bucket:bucket:edit')")
  @Log(title = "存储桶", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody SysOssBucket sysOssBucket) {
    sysOssBucket.setUpdateBy(getUsername());
    return toAjax(sysOssBucketService.updateById(sysOssBucket));
  }

  /** 删除存储桶 */
  @PreAuthorize("@ss.hasPermi('bucket:bucket:remove')")
  @Log(title = "存储桶", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(sysOssBucketService.removeByIds(Arrays.asList(ids)));
  }
}
