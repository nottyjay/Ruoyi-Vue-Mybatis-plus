package com.d3code.bpm.service.impl;

import com.d3code.bpm.convert.BpmActivityConvert;
import com.d3code.bpm.model.vo.BpmActivityResponseVo;
import com.d3code.bpm.service.IBpmActivityService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Bpm 流程活动实例接口实现
 *
 * @author Nottyjay
 */
@Service
public class BpmActivityServiceImpl implements IBpmActivityService {

  @Resource private HistoryService historyService;

  @Override
  public List<BpmActivityResponseVo> getActivityListByProcessInstanceId(String processInstanceId) {
    List<HistoricActivityInstance> activityList =
        historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(processInstanceId)
            .list();
    return BpmActivityConvert.INSTANCE.convertList(activityList);
  }

  @Override
  public List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId) {
    return historyService.createHistoricActivityInstanceQuery().executionId(executionId).list();
  }
}
