package com.alphay.boot.common.enums;

/**
 * @author nottyjay
 */
public enum SystemBooleanEnum {
  TRUE("yes"),
  FALSE("no");

  private String status;

  SystemBooleanEnum(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return status;
  }
}
