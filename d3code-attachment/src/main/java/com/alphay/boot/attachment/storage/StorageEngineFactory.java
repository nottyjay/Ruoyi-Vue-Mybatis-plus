package com.alphay.boot.attachment.storage;

import com.alphay.boot.attachment.api.bean.TencentCosConfig;
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.enums.StorageType;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.StringUtils;

import javax.validation.constraints.Null;

public class StorageEngineFactory {

  public static StorageEngine createStorageEngine(SysOssConfig ossConfig) {
    StorageEngine storageEngine = null;
    if (StringUtils.equals(ossConfig.getOssType(), StorageType.TENCENT_COS.toString())) {
      storageEngine = new TencentCosStorageEngine(ossConfig);
    } else {
      storageEngine = new NullStorageEngine();
    }
    return storageEngine;
  }
}
