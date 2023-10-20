package com.alphay.boot.attachment.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alphay.boot.attachment.api.domain.SysAttachment;
import com.alphay.boot.attachment.api.service.IAttachmentUploadService;
import com.alphay.boot.attachment.api.service.ISysAttachmentService;
import com.alphay.boot.attachment.storage.StorageEngine;
import com.alphay.boot.attachment.utils.StorageEngineUtil;
import com.alphay.boot.common.utils.DateUtils;
import com.alphay.boot.common.utils.StringUtils;
import com.alphay.boot.common.utils.file.MimeTypeUtils;
import com.alphay.boot.common.utils.uuid.Seq;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Objects;

/**
 * 附件上传业务
 *
 * @author nottyjay
 */
@Service
public class AttachmentUploadServiceImpl implements IAttachmentUploadService {

  @Resource private ISysAttachmentService attachmentService;

  @Override
  public String uploadFile(File file) {
    return null;
  }

  @Override
  public String uploadFile(MultipartFile file) {
    return uploadFile(file, getDefaultRandomFileName(file));
  }

  @Override
  public String uploadFile(MultipartFile file, String fileName) {
    StorageEngine engine = StorageEngineUtil.getInstance();
    String url = engine.uploadFileSync(file, engine.getDefaultBucket(), fileName);
    SysAttachment attachment =
        SysAttachment.builder()
            .name(FilenameUtils.getBaseName(file.getOriginalFilename()))
            .path(fileName)
            .url(url)
            .storageType(engine.getStorageType())
            .extension(getExtension(file))
            .configId(engine.getOssConfigId())
            .build();
    attachmentService.save(attachment);
    return url;
  }

  @Override
  public String uploadFile(File file, String fileName) {
    StorageEngine engine = StorageEngineUtil.getInstance();
    String url = engine.uploadFileSync(file, engine.getDefaultBucket(), fileName);
    SysAttachment attachment =
        SysAttachment.builder()
            .name(FilenameUtils.getBaseName(file.getName()))
            .path(fileName)
            .url(url)
            .storageType(engine.getStorageType())
            .configId(engine.getOssConfigId())
            .extension(getExtension(file))
            .build();
    attachmentService.save(attachment);
    file.delete();
    return url;
  }

  @Override
  public void deleteFile(String fileName) {
    StorageEngine engine = StorageEngineUtil.getInstance();
    engine.deleteObject(fileName);
  }

  private String getDefaultRandomFileName(MultipartFile file) {
    return StringUtils.format(
        "{}/{}_{}.{}",
        DateUtils.datePath(),
        IdUtil.randomUUID(),
        Seq.getId(Seq.uploadSeqType),
        getExtension(file));
  }

  private String getExtension(MultipartFile file) {
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    if (StringUtils.isEmpty(extension)) {
      extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
    }
    return extension;
  }

  private String getExtension(File file) {
    return FilenameUtils.getExtension(file.getName());
  }
}
