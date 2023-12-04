package com.alphay.boot.attachment.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alphay.boot.attachment.api.service.IAttachmentUploadService;
import com.alphay.boot.attachment.utils.StorageEngineUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.alphay.boot.attachment.api.service.ISysAttachmentService;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.common.core.page.TableDataInfo;

/**
 * 文件管理Controller
 *
 * @author d3code
 * @date 2023-10-13
 */
@RestController
@RequestMapping("/attachment")
public class SysAttachmentController extends BaseController {
  @Autowired private ISysAttachmentService sysAttachmentService;
  @Resource private IAttachmentUploadService attachmentUploadService;

  /** 查询文件管理列表 */
  @PreAuthorize("@ss.hasPermi('attachment:attachment:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysAttachment sysAttachment) {
    //    if (sysAttachment.getConfigId() == null) {
    sysAttachment.setConfigId(StorageEngineUtil.getInstance().getOssConfigId());
    //    }
    List<SysAttachment> list =
        sysAttachmentService.selectSysAttachmentList(sysAttachment, startPage());
    return getDataTable(list);
  }

  @PreAuthorize("@ss.hasPermi('attachment:attachment:list')")
  @GetMapping("/listByIds")
  public AjaxResult list(@RequestParam List<Long> ids) {
    List<SysAttachment> list = sysAttachmentService.selectSysAttachmentListByIds(ids);
    return success(list);
  }

  /** 导出文件管理列表 */
  @PreAuthorize("@ss.hasPermi('attachment:attachment:export')")
  @Log(title = "文件管理", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysAttachment sysAttachment) {
    List<SysAttachment> list = sysAttachmentService.selectSysAttachmentList(sysAttachment);
    ExcelUtil<SysAttachment> util = new ExcelUtil<SysAttachment>(SysAttachment.class);
    util.exportExcel(response, list, "文件管理数据");
  }

  /** 获取文件管理详细信息 */
  @PreAuthorize("@ss.hasPermi('attachment:attachment:query')")
  @GetMapping(value = "/{id}")
  public AjaxResult getInfo(@PathVariable("id") Long id) {
    return success(sysAttachmentService.getById(id));
  }

  /** 新增文件管理 */
  @PreAuthorize("@ss.hasPermi('attachment:attachment:add')")
  @Log(title = "文件管理", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody SysAttachment sysAttachment) {
    sysAttachment.setCreateBy(getUsername());
    return toAjax(sysAttachmentService.save(sysAttachment));
  }

  /** 修改文件管理 */
  @PreAuthorize("@ss.hasPermi('attachment:attachment:edit')")
  @Log(title = "文件管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody SysAttachment sysAttachment) {
    sysAttachment.setUpdateBy(getUsername());
    return toAjax(sysAttachmentService.updateById(sysAttachment));
  }

  /** 删除文件管理 */
  @PreAuthorize("@ss.hasPermi('attachment:attachment:remove')")
  @Log(title = "文件管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  public AjaxResult remove(@PathVariable Long[] ids) {
    for (Long id : ids) {
      SysAttachment attachment = sysAttachmentService.getById(id);
      attachmentUploadService.deleteFile(attachment.getBucketName(), attachment.getPath());
    }
    return toAjax(sysAttachmentService.removeByIds(Arrays.asList(ids)));
  }
}
