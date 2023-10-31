package com.alphay.boot.attachment.api.service;

import com.alphay.boot.attachment.api.domain.SysAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IAttachmentUploadService {

  /**
   * 上传文件，返回文件访问URL
   *
   * @param file 待上传文件
   * @return
   */
  SysAttachment uploadFile(File file);

  /**
   * 上传文件，返回文件访问URL
   *
   * @param file 待上传文件
   * @param fileName 文件名称
   * @return
   */
  SysAttachment uploadFile(File file, String fileName);

  /**
   * 上传文件，返回文件访问URL
   *
   * @param file 待上传文件
   * @return
   */
  SysAttachment uploadFile(MultipartFile file);

  /**
   * 上传文件，返回文件访问URL
   *
   * @param file 待上传文件
   * @param fileName 文件名称
   * @return
   */
  SysAttachment uploadFile(MultipartFile file, String fileName);

  /**
   * 删除文件
   *
   * @param bucketName
   * @param fileName
   */
  void deleteFile(String bucketName, String fileName);
}
