package com.d3code.bpm.service.impl;

import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d3code.common.mybatis.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import com.d3code.bpm.mapper.BpmFormMapper;
import com.d3code.bpm.domain.BpmForm;
import com.d3code.bpm.service.IBpmFormService;

/**
 * 业务流表单Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
public class BpmFormServiceImpl extends ServiceImpl<BpmFormMapper, BpmForm>
    implements IBpmFormService {

  /**
   * 查询业务流表单列表
   *
   * @param bpmForm 业务流表单
   * @return 业务流表单
   */
  @Override
  public List<BpmForm> selectBpmFormList(BpmForm bpmForm) {
    return baseMapper.selectBpmFormList(bpmForm);
  }

  @Override
  public List<BpmForm> getFormList(Collection<Long> ids) {
    return list(new LambdaQueryWrapperX<BpmForm>().inIfPresent(BpmForm::getId, ids));
  }
}
