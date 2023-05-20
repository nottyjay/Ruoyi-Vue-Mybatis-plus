package com.alphay.boot.bpm.service.impl;

import com.alphay.boot.bpm.convert.BpmActivityConvert;
import com.alphay.boot.bpm.model.vo.BpmActivityResponseVo;
import com.alphay.boot.bpm.service.IBpmActivityService;
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
