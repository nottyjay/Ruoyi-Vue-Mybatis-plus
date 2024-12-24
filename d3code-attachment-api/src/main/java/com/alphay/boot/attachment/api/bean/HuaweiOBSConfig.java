package com.alphay.boot.attachment.api.bean;

import lombok.Data;

@Data
public class HuaweiOBSConfig {

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

  /** 终端节点 */
  private String endPoint;
}
