package com.alphay.boot.bpm.model.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 导入模型vo
 *
 * @author Nottyjay
 */
@Data
public class BpmModelImportRequestVo extends BpmModelCreateRequestVo {

  @NotNull(message = "BPMN 文件不能为空")
  private MultipartFile bpmnFile;
}
