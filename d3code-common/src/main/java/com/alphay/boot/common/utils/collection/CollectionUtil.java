package com.alphay.boot.common.utils.collection;

import cn.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** 集合工具类 */
public class CollectionUtil {

  /**
   * 将一个集合按规则转化为一个Set集合
   *
   * @param from
   * @param func
   * @return
   * @param <T>
   * @param <U>
   */
  public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func) {
    if (CollUtil.isEmpty(from)) {
      return new HashSet<>();
    }
    return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
  }

  /**
   * 添加item到coll集合中，item为null时忽略
   *
   * @param coll
   * @param item
   * @param <T>
   */
  public static <T> void addIfNotNull(Collection<T> coll, T item) {
    if (item == null) {
      return;
    }
    coll.add(item);
  }

  /**
   * 集合按规则转换为List
   *
   * @param from
   * @param func
   * @return
   * @param <T>
   * @param <U>
   */
  public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
    if (CollUtil.isEmpty(from)) {
      return new ArrayList<>();
    }
    return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toList());
  }

  /**
   * 将集合转化为Map表
   *
   * @param from
   * @param keyFunc
   * @return
   * @param <T>
   * @param <K>
   */
  public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc) {
    if (CollUtil.isEmpty(from)) {
      return new HashMap<>();
    }
    return convertMap(from, keyFunc, Function.identity());
  }

  public static <T, K> Map<K, T> convertMap(
      Collection<T> from, Function<T, K> keyFunc, Supplier<? extends Map<K, T>> supplier) {
    if (CollUtil.isEmpty(from)) {
      return supplier.get();
    }
    return convertMap(from, keyFunc, Function.identity(), supplier);
  }

  public static <T, K, V> Map<K, V> convertMap(
      Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
    if (CollUtil.isEmpty(from)) {
      return new HashMap<>();
    }
    return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
  }

  public static <T, K, V> Map<K, V> convertMap(
      Collection<T> from,
      Function<T, K> keyFunc,
      Function<T, V> valueFunc,
      BinaryOperator<V> mergeFunction) {
    if (CollUtil.isEmpty(from)) {
      return new HashMap<>();
    }
    return convertMap(from, keyFunc, valueFunc, mergeFunction, HashMap::new);
  }

  public static <T, K, V> Map<K, V> convertMap(
      Collection<T> from,
      Function<T, K> keyFunc,
      Function<T, V> valueFunc,
      Supplier<? extends Map<K, V>> supplier) {
    if (CollUtil.isEmpty(from)) {
      return supplier.get();
    }
    return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
  }

  public static <T, K, V> Map<K, V> convertMap(
      Collection<T> from,
      Function<T, K> keyFunc,
      Function<T, V> valueFunc,
      BinaryOperator<V> mergeFunction,
      Supplier<? extends Map<K, V>> supplier) {
    if (CollUtil.isEmpty(from)) {
      return new HashMap<>();
    }
    return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
  }

  public static <T, K> Map<K, List<T>> convertMultiMap(Collection<T> from, Function<T, K> keyFunc) {
    if (CollUtil.isEmpty(from)) {
      return new HashMap<>();
    }
    return from.stream()
        .collect(Collectors.groupingBy(keyFunc, Collectors.mapping(t -> t, Collectors.toList())));
  }
}
