package com.alphay.boot.attachment.api.enums;

public enum StorageType {
  LOCAL("local", "本地存储"),
  AMAZON_S3("amazon_s3", "亚马逊S3"),
  ALIYUN_OSS("aliyun_oss", "阿里云OSS"),
  HUAWEI_CLOUD_OBS("huawei_cloud_obs", "华为云OBS"),
  TENCENT_COS("tencent_cos", "腾讯云COS");

  private String code;
  private String msg;

  StorageType(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String toString() {
    return this.code;
  }
}
