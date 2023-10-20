package com.alphay.boot.attachment.storage;

import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.enums.StorageType;
import com.alphay.boot.attachment.storage.cos.TencentCosStorageEngine;
import com.alphay.boot.attachment.storage.local.LocalFileStorageEngine;
import com.alphay.boot.common.utils.StringUtils;

public class StorageEngineFactory {

  public static StorageEngine createStorageEngine(SysOssConfig ossConfig) {
    StorageEngine storageEngine = null;
    if (StringUtils.equals(ossConfig.getOssType(), StorageType.TENCENT_COS.toString())) {
      storageEngine = new TencentCosStorageEngine(ossConfig);
    } else if (StringUtils.equals(ossConfig.getOssType(), StorageType.LOCAL.toString())) {
      storageEngine = new LocalFileStorageEngine(ossConfig);
    } else {
      storageEngine = new NullStorageEngine();
    }
    return storageEngine;
  }
}
