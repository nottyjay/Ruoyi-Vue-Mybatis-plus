package com.alphay.boot.attachment.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 本地存储引擎配置
 *
 * @author Nottyjay
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalStorageConfig {

  private String bucketName;
  private String domain;
}
