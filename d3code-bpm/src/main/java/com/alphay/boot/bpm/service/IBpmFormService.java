package com.alphay.boot.bpm.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.common.utils.collection.CollectionUtil;

/**
 * 业务流表单Service接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface IBpmFormService extends IService<BpmForm> {

  /**
   * 查询业务流表单列表
   *
   * @param bpmForm 业务流表单
   * @return 业务流表单集合
   */
  List<BpmForm> selectBpmFormList(BpmForm bpmForm);

  /**
   * 获得动态表单列表
   *
   * @param ids 编号
   * @return 动态表单列表
   */
  List<BpmForm> getFormList(Collection<Long> ids);

  /**
   * 获得动态表单 Map
   *
   * @param ids 编号
   * @return 动态表单 Map
   */
  default Map<Long, BpmForm> getFormMap(Collection<Long> ids) {
    if (CollUtil.isEmpty(ids)) {
      return Collections.emptyMap();
    }
    return CollectionUtil.convertMap(this.getFormList(ids), BpmForm::getId);
  }
}
