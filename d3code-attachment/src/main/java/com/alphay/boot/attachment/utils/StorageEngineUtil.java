package com.alphay.boot.attachment.utils;

import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.attachment.storage.StorageEngineFactory;

public class StorageEngineUtil {

  private static SysOssConfig config;
  private static StorageEngine engine;

  public static StorageEngine getInstance(SysOssConfig ossConfig) {
    if (StorageEngineUtil.config == null || StorageEngineUtil.config.getId() != ossConfig.getId()) {
      StorageEngineUtil.config = config;
      StorageEngine storageEngine = StorageEngineFactory.createStorageEngine(ossConfig);
      StorageEngineUtil.engine = storageEngine;
    }
    return StorageEngineUtil.engine;
  }

  public static StorageEngine getInstance() {
    return StorageEngineUtil.engine;
  }
}
