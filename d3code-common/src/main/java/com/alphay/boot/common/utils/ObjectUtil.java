package com.alphay.boot.common.utils;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * Object 工具类
 *
 * @author Nottyjay
 */
public class ObjectUtil {

  public static boolean equals(Object obj1, Object obj2) {
    return equal(obj1, obj2);
  }

  public static boolean equal(Object obj1, Object obj2) {
    return obj1 instanceof BigDecimal && obj2 instanceof BigDecimal
        ? NumberUtil.equals((BigDecimal) obj1, (BigDecimal) obj2)
        : Objects.equals(obj1, obj2);
  }

  public static boolean notEqual(Object obj1, Object obj2) {
    return !equal(obj1, obj2);
  }

  @SafeVarargs
  public static <T> boolean equalsAny(T obj, T... array) {
    return Arrays.asList(array).contains(obj);
  }
}
