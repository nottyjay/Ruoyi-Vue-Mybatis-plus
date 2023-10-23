package com.alphay.boot.attachment.storage.cos;

import com.alphay.boot.attachment.api.bean.TencentCosConfig;
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.exception.BucketException;
import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.StringUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TencentCosStorageEngine implements StorageEngine {

  private TencentCosConfig config;
  private COSClient client;
  private TransferManager transferManager;

  private SysOssConfig ossConfig;

  public TencentCosStorageEngine(SysOssConfig ossConfig) {
    this.ossConfig = ossConfig;
    this.config = JsonUtil.toBean(ossConfig.getConfig(), TencentCosConfig.class);
    COSCredentials cred = new BasicCOSCredentials(config.getSecretId(), config.getSecretKey());
    Region region = new Region(config.getRegion());
    ClientConfig clientConfig = new ClientConfig(region);
    clientConfig.setHttpProtocol(HttpProtocol.https);
    client = new COSClient(cred, clientConfig);
    this.transferManager = createTransferManager();
  }

  private TransferManager createTransferManager() {
    ExecutorService threadPool = Executors.newFixedThreadPool(16);
    this.transferManager = new TransferManager(this.client, threadPool);

    // 设置分开上传阈值
    TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
    transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
    // 设置分开大小
    transferManagerConfiguration.setMultipartCopyPartSize(1 * 1024 * 1024);
    transferManager.setConfiguration(transferManagerConfiguration);
    return transferManager;
  }

  @Override
  public Long getOssConfigId() {
    return ossConfig.getId();
  }

  @Override
  public String getStorageType() {
    return "COS";
  }

  @Override
  public String getDefaultBucket() {
    return this.config.getBucketName();
  }

  @Override
  public void createBucket(String bucketName) {
    CreateBucketRequest createBucketRequest = new CreateBucketRequest(getBucketName(bucketName));
    createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
    try {
      client.createBucket(createBucketRequest);
    } catch (CosServiceException serverException) {
      throw new BucketException(serverException.toString());
    } catch (CosClientException clientException) {
      throw new BucketException(clientException.toString());
    }
  }

  @Override
  public boolean exitsBucket(String bucketName) {
    return client.doesBucketExist(getBucketName(bucketName));
  }

  @Override
  public void deleteBucket(String bucketName) {
    DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(getBucketName(bucketName));
    try {
      client.deleteBucket(deleteBucketRequest);
    } catch (CosServiceException serverException) {
      serverException.printStackTrace();
    }
  }

  @Override
  public String uploadFileSync(File file, String fileName) {
    return uploadFileSync(file, getDefaultBucket(), fileName);
  }

  @Override
  public String uploadFileSync(File file, String bucketName, String key) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.getDefaultBucket();
    }
    PutObjectRequest putObjectRequest = new PutObjectRequest(getBucketName(bucketName), key, file);
    try {
      Upload upload = this.transferManager.upload(putObjectRequest);
      UploadResult uploadResult = upload.waitForUploadResult();
      return getObjectUrl(bucketName, key);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String uploadFileSync(MultipartFile file, String fileName) {
    return uploadFileSync(file, getDefaultBucket(), fileName);
  }

  @Override
  public String uploadFileSync(MultipartFile file, String bucketName, String key) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.getDefaultBucket();
    }
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    try {
      PutObjectRequest putObjectRequest =
          new PutObjectRequest(
              getBucketName(bucketName), key, file.getInputStream(), objectMetadata);
      Upload upload = this.transferManager.upload(putObjectRequest);
      UploadResult uploadResult = upload.waitForUploadResult();
      return getObjectUrl(bucketName, key);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteObject(String bucketName, String key) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.getDefaultBucket();
    }
    this.client.deleteObject(getBucketName(bucketName), key);
  }

  /**
   * 获取资源的访问URL
   *
   * @param bucketName
   * @param key
   * @return
   */
  private String getObjectUrl(String bucketName, String key) {
    return this.client.getObjectUrl(getBucketName(bucketName), key).toString();
  }

  private String getBucketName(String bucketName) {
    return bucketName + "-" + config.getAppId();
  }
}
