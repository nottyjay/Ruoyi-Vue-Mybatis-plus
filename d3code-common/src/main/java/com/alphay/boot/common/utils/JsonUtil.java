package com.alphay.boot.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Nottyjay
 * @date 2019/11/20
 */
public class JsonUtil {
  private static ObjectMapper mapper = new ObjectMapper();

  public static String toJsonHex(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] toJsonBytes(Object obj) {
    try {
      return mapper.writeValueAsBytes(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toBean(String json, Class<T> tClass) {
    if (StringUtils.isBlank(json)) {
      return null;
    }
    try {
      return mapper.readValue(json, tClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T toBean(byte[] bytes, Class<T> tClass) {
    try {
      return mapper.readValue(bytes, tClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Map toMap(String json) {
    return toBean(json, Map.class);
  }

  public static Map toMap(byte[] bytes) {
    return toBean(bytes, Map.class);
  }

  public static <T> List<T> toList(String source, Class<T> T) {
    try {
      return mapper.readValue(source, getCollectionType(ArrayList.class, T));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
    return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }
}
