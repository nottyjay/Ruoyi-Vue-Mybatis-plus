package com.alphay.boot.bpm.service.impl;

import java.util.Collection;
import java.util.List;

import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.bpm.mapper.BpmFormMapper;
import com.alphay.boot.bpm.service.IBpmFormService;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
