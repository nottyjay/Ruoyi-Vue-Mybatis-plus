package com.alphay.boot.attachment.storage.local;

import com.alphay.boot.attachment.api.bean.LocalStorageConfig;
import com.alphay.boot.attachment.api.domain.SysOssConfig;
import com.alphay.boot.attachment.api.enums.StorageType;
import com.alphay.boot.attachment.api.exception.BucketException;
import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.common.config.D3codeConfig;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.RedisUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.attachment.utils.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
public class LocalFileStorageEngine implements StorageEngine {

  private SysOssConfig ossConfig;
  private LocalStorageConfig config;

  public LocalFileStorageEngine(SysOssConfig ossConfig) {
    this.ossConfig = ossConfig;
    this.config = JsonUtil.toBean(ossConfig.getConfig(), LocalStorageConfig.class);
    String filePath = getBucketFullPath(this.getDefaultBucket());
    File defaultBucketDirectory = new File(filePath);
    if (!defaultBucketDirectory.exists()) {
      defaultBucketDirectory.mkdirs();
    }
    RedisUtils.set("oss_local_domain", this.config.getDomain());
  }

  @Override
  public Long getOssConfigId() {
    return this.ossConfig.getId();
  }

  @Override
  public String getStorageType() {
    return StorageType.LOCAL.toString();
  }

  @Override
  public String getDefaultBucket() {
    return this.config.getBucketName();
  }

  @Override
  public void createBucket(String bucketName) {
    String filePath = this.getBucketFullPath(bucketName);
    try {
      FileUtils.forceMkdir(new File(filePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean exitsBucket(String bucketName) {
    String filePath = this.getBucketFullPath(bucketName);
    return new File(filePath).exists();
  }

  @Override
  public void deleteBucket(String bucketName) {
    String filePath = this.getBucketFullPath(bucketName);
    File bucketFileDirectory = new File(filePath);
    try {
      if (!FileUtils.isEmptyDirectory(bucketFileDirectory)) {
        throw new BucketException("文件夹内还有其他文件，无法删除", 500);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String uploadFileSync(File file, String fileName) {
    return uploadFileSync(file, getDefaultBucket(), fileName);
  }

  @Override
  public String uploadFileSync(File file, String bucketName, String fileName) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.config.getBucketName();
    }
    try {
      File dest =
          com.alphay.boot.common.utils.file.FileUtils.getAbsoluteFile(
              getBucketFullPath(bucketName), fileName);
      FileUtils.copyFile(file, dest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this.getAccessReadFileUrl(bucketName, fileName);
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
      File dest =
          com.alphay.boot.common.utils.file.FileUtils.getAbsoluteFile(
              getBucketFullPath(bucketName), fileName);
      log.debug("file absolute path: {}", dest.getAbsolutePath());
      file.transferTo(Paths.get(dest.getAbsolutePath()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return this.getAccessReadFileUrl(bucketName, fileName);
  }

  @Override
  public void deleteObject(String bucketName, String fileName) {
    if (StringUtils.isEmpty(bucketName)) {
      bucketName = this.config.getBucketName();
    }
    File dest = new File(getBucketFullPath(bucketName) + fileName);
    dest.delete();
  }

  private String getBucketFullPath(String bucketName) {
    return (D3codeConfig.getProfile() + File.separator + bucketName + File.separator)
        .replace("//", "/");
    //    return StringUtils.join(Arrays.asList(this.config.getFilePath(), bucketName),
    // File.separator);
  }

  private String getBucketPath(String bucketName) {
    return (bucketName + "/").replace("//", "/");
  }

  private String getAccessReadFileUrl(String bucketName, String fileName) {
    return ("/profile/" + getBucketPath(bucketName) + fileName).replace("//", "/");
  }
}
