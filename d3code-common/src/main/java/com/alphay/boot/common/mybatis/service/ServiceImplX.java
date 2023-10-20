package com.alphay.boot.common.mybatis.service;

import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

public class ServiceImplX<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

  protected LambdaQueryWrapperX<T> lambdaQueryWrapperX() {
    return new LambdaQueryWrapperX<T>();
  }

  protected void parseBeginTimeAndEndTime(
      Map<String, Object> params, QueryWrapper queryWrapper, String column) {
    if (params != null) {
      if (params.containsKey("beginTime")) {
        String beginTime = (String) params.get("beginTime");
        if (StringUtils.isNotBlank(beginTime)) {
          queryWrapper.ge(
              "date_format(" + column + ", '%Y%m%d')",
              "date_format('" + beginTime + "', '%Y%m%d')");
        }
      }
      if (params.containsKey("endTime")) {
        String endTime = (String) params.get("endTime");
        if (StringUtils.isNotBlank(endTime)) {
          queryWrapper.le(
              "date_format(" + column + ", '%Y%m%d')", "date_format('" + endTime + "', '%Y%m%d')");
        }
      }
    }
  }
}
