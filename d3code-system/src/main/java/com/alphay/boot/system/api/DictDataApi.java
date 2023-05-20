package com.alphay.boot.system.api;

import com.alphay.boot.system.service.ISysDictDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 系统字典 api
 *
 * @author Nottyjay
 */
@Component
public class DictDataApi {

  @Resource private ISysDictDataService dictDataService;

  /**
   * 校验字典数据们是否有效。如下情况，视为无效： 1. 字典数据不存在 2. 字典数据被禁用
   *
   * @param dictType
   * @param values
   */
  public void validateDictDataList(String dictType, Collection<String> values) {
    dictDataService.validateDictDataList(dictType, values);
  }
}
