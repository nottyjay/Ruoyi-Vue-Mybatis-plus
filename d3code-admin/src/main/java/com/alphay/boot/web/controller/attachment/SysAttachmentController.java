package com.alphay.boot.web.controller.attachment;

import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.alphay.boot.attachment.api.service.ISysAttachmentService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class SysAttachmentController extends BaseController {

  @Resource private ISysAttachmentService attachmentService;

  @GetMapping("/list")
  public TableDataInfo list(SysAttachment attachment) {
    List<SysAttachment> result = attachmentService.selectSysAttachmentList(attachment, startPage());
    return getDataTable(result);
  }
}
