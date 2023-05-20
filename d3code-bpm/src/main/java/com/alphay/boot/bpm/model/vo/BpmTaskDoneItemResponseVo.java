package com.alphay.boot.bpm.model.vo;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BpmTaskDoneItemResponseVo extends BpmTaskTodoItemResponseVo {

  private LocalDateTime endTime;
  private Long durationInMillis;

  private Integer result;
  private String reason;
}
