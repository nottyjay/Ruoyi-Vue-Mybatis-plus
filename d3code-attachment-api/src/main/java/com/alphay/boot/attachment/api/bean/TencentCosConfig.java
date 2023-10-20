package com.alphay.boot.attachment.api.bean;

import lombok.Data;

/**
 * 腾讯云COS 配置信息
 *
 * @author nottyjay
 */
@Data
public class TencentCosConfig {

  /** 用户APPID */
  private String appId;

  /** 访问密钥ID */
  private String secretId;

  /** 访问密钥Key */
  private String secretKey;

  /** 默认桶名称 */
  private String bucketName;

  /** 地域 */
  private String region;
}
