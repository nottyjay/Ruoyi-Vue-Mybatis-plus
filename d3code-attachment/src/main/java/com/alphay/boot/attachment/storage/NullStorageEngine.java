package com.alphay.boot.attachment.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class NullStorageEngine implements StorageEngine {

  @Override
  public Long getOssConfigId() {
    return null;
  }

  @Override
  public String getStorageType() {
    return "null";
  }

  @Override
  public String getDefaultBucket() {
    return null;
  }

  @Override
  public void createBucket(String bucketName) {}

  @Override
  public void deleteBucket(String bucketName) {}

  @Override
  public String uploadFileSync(File file, String bucketName, String fileName) {
    return null;
  }

  @Override
  public String uploadFileSync(MultipartFile file, String bucketName, String fileName) {
    return null;
  }

  @Override
  public void deleteObject(String buckerName, String fileName) {}

  @Override
  public boolean exitsBucket(String bucketName) {
    return true;
  }
}
