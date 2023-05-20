package com.d3code.bpm.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.d3code.bpm.domain.BpmForm;

/**
 * 业务流表单Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public interface BpmFormMapper extends BaseMapper<BpmForm>
{
    /**
     * 查询业务流表单列表
     *
     * @param bpmForm 业务流表单
     * @return 业务流表单集合
     */
    public List<BpmForm> selectBpmFormList(BpmForm bpmForm);

}
