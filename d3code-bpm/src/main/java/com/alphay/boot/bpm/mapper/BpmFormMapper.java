package com.alphay.boot.bpm.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alphay.boot.bpm.api.domain.BpmForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * 业务流表单Mapper接口
 *
 * @author d3code
 * @date 2023-04-07
 */
public interface BpmFormMapper extends BaseMapper<BpmForm> {
  /**
   * 查询业务流表单列表
   *
   * @param bpmForm 业务流表单
   * @return 业务流表单集合
   */
  List<BpmForm> selectBpmFormList(@Param("form") BpmForm bpmForm, IPage page);
}
