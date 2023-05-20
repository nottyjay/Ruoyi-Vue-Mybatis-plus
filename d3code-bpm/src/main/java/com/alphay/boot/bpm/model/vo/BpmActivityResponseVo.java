package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BpmActivityResponseVo {

  private String key;

  private String type;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String taskId;
}
