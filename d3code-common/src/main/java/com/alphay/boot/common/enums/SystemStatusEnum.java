package com.alphay.boot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 系统开关状态枚举
 *
 * @author Nottyjay
 */
@Getter
@AllArgsConstructor
public enum SystemStatusEnum {
  ENABLE("0", "开启"),
  DISABLE("1", "关闭");

  public static final String[] ARRAYS =
      Arrays.stream(values()).map(SystemStatusEnum::getStatus).toArray(String[]::new);

  /** 状态值 */
  private final String status;
  /** 状态名 */
  private final String name;

  public String[] array() {
    return ARRAYS;
  }
}
