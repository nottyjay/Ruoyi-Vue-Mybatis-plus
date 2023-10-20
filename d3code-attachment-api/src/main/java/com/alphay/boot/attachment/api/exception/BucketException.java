package com.alphay.boot.attachment.api.exception;

import com.alphay.boot.common.exception.ServiceException;

/**
 * 桶操作异常
 *
 * @author Nottyjay
 */
public class BucketException extends RuntimeException {

  /** 错误码 */
  private Integer code;

  /** 错误提示 */
  private String message;

  /** 空构造方法，避免反序列化问题 */
  public BucketException() {}

  public BucketException(String message) {
    this.message = message;
  }

  public BucketException(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }

  public BucketException setMessage(String message) {
    this.message = message;
    return this;
  }
}
