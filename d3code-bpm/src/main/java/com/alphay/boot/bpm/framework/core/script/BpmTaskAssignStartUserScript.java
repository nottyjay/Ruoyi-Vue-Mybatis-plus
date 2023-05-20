package com.alphay.boot.bpm.framework.core.script;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.alphay.boot.bpm.enums.BpmTaskRuleScriptEnum;
import com.alphay.boot.bpm.service.IBpmProcessInstanceService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 分配给发起人审批的 Script 实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmTaskAssignStartUserScript implements BpmTaskAssignScript {

  @Resource @Lazy // 解决循环依赖
  private IBpmProcessInstanceService bpmProcessInstanceService;

  @Override
  public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
    ProcessInstance processInstance =
        bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());
    Long startUserId = NumberUtil.parseLong(processInstance.getStartUserId());
    return CollUtil.set(false, startUserId);
  }

  @Override
  public BpmTaskRuleScriptEnum getEnum() {
    return BpmTaskRuleScriptEnum.START_USER;
  }
}
