package com.alphay.boot.attachment.storage;

import com.alphay.boot.attachment.api.domain.SysOssConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface StorageEngine {

  /**
   * 获取OssConfig的ID
   *
   * @return
   */
  Long getOssConfigId();

  /**
   * 获取引擎名称
   *
   * @return
   */
  String getStorageType();

  /**
   * 获取默认的Bucket名称
   *
   * @return
   */
  String getDefaultBucket();

  /**
   * 创建桶
   *
   * @param bucketName 桶名称
   */
  void createBucket(String bucketName);

  /**
   * 检查是否已经创建对应的bucket
   *
   * @param bucketName
   * @return
   */
  boolean exitsBucket(String bucketName);

  /**
   * 删除桶
   *
   * @param bucketName 桶名称
   */
  void deleteBucket(String bucketName);

  /**
   * 同步上传文件到容器中
   *
   * @param file
   * @param bucketName
   */
  String uploadFileSync(File file, String bucketName, String fileName);

  /**
   * 同步上传文件到容器中
   *
   * @param file
   * @param bucketName
   */
  String uploadFileSync(MultipartFile file, String bucketName, String fileName);

  /**
   * 删除对象
   *
   * @param fileName
   */
  void deleteObject(String fileName);
}
