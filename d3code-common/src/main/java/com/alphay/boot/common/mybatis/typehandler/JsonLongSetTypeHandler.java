package com.alphay.boot.common.mybatis.typehandler;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;

public class JsonLongSetTypeHandler extends AbstractJsonTypeHandler<Object> {

  private static final com.fasterxml.jackson.core.type.TypeReference<Set<Long>> TYPE_REFERENCE =
      new TypeReference<Set<Long>>() {};

  private static ObjectMapper OBJECT_MAPPER;

  @Override
  protected Object parse(String json) {
    try {
      return getObjectMapper().readValue(json, TYPE_REFERENCE);
    } catch (IOException var3) {
      throw new RuntimeException(var3);
    }
  }

  @Override
  protected String toJson(Object obj) {
    try {
      return getObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException var3) {
      throw new RuntimeException(var3);
    }
  }

  public static ObjectMapper getObjectMapper() {
    if (null == OBJECT_MAPPER) {
      OBJECT_MAPPER = new ObjectMapper();
    }

    return OBJECT_MAPPER;
  }

  public static void setObjectMapper(ObjectMapper objectMapper) {
    Assert.notNull(objectMapper, "ObjectMapper should not be null", new Object[0]);
    OBJECT_MAPPER = objectMapper;
  }
}
