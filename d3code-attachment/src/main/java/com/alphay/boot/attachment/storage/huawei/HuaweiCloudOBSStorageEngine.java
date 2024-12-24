package com.alphay.boot.attachment.storage.huawei;

import com.alphay.boot.attachment.api.bean.HuaweiOBSConfig;
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.enums.StorageType;
import com.alphay.boot.attachment.api.exception.BucketException;
import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.StringUtils;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.AccessControlList;
import com.obs.services.model.CreateBucketRequest;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public class HuaweiCloudOBSStorageEngine implements StorageEngine {

  private HuaweiOBSConfig config;
  private ObsClient client;

  private SysOssConfig ossConfig;

  public HuaweiCloudOBSStorageEngine(SysOssConfig ossConfig) {
    this.ossConfig = ossConfig;
    this.config = JsonUtil.toBean(ossConfig.getConfig(), HuaweiOBSConfig.class);
    String endPoint = config.getEndPoint();
    client = new ObsClient(config.getSecretId(), config.getSecretKey(), endPoint);
  }

  @Override
  public Long getOssConfigId() {
    return ossConfig.getId();
  }

  @Override
  public String getStorageType() {
    return StorageType.HUAWEI_CLOUD_OBS.toString();
  }

  @Override
  public String getDefaultBucket() {
    return this.config.getBucketName();
  }

  @Override
  public void createBucket(String bucketName) {
    CreateBucketRequest request = new CreateBucketRequest(getBucketName(bucketName));
    request.setLocation(config.getRegion());
    request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
    try {
      client.createBucket(request);
    } catch (ObsException ex) {
      throw new BucketException(ex.getMessage());
    }
  }

  @Override
  public boolean exitsBucket(String bucketName) {
    return client.headBucket(getBucketName(bucketName));
  }

  @Override
  public void deleteBucket(String bucketName) {
    client.deleteBucket(getBucketName(bucketName));
  }

  @Override
  public String uploadFileSync(File file, String fileName) {
    return uploadFileSync(file, getDefaultBucket(), fileName);
  }

  @Override
  public String uploadFileSync(File file, String bucketName, String fileName) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.getDefaultBucket();
    }
    PutObjectRequest putObjectRequest =
        new PutObjectRequest(getBucketName(bucketName), fileName, file);
    PutObjectResult result = client.putObject(putObjectRequest);
    return result.getObjectUrl();
  }

  @Override
  public String uploadFileSync(MultipartFile file, String fileName) {
    return uploadFileSync(file, getDefaultBucket(), fileName);
  }

  @Override
  public String uploadFileSync(MultipartFile file, String bucketName, String fileName) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.getDefaultBucket();
    }
    try {
      PutObjectRequest putObjectRequest =
          new PutObjectRequest(getBucketName(bucketName), fileName, file.getInputStream());
      PutObjectResult result = client.putObject(putObjectRequest);
      return result.getObjectUrl();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void deleteObject(String bucketName, String fileName) {
    client.deleteObject(getBucketName(bucketName), fileName);
  }

  private String getBucketName(String bucketName) {
    return bucketName;
  }
}
