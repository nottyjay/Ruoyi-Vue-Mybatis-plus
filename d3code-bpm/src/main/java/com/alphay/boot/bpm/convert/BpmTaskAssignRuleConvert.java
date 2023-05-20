package com.alphay.boot.bpm.convert;

import com.alphay.boot.bpm.domain.BpmTaskAssignRule;
import com.alphay.boot.bpm.model.vo.BpmTaksAssignRuleCreateRequestVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleResponseVo;
import com.alphay.boot.bpm.model.vo.BpmTaskAssignRuleUpdateRequestVo;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.bpmn.model.UserTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface BpmTaskAssignRuleConvert {

  BpmTaskAssignRuleConvert INSTANCE = Mappers.getMapper(BpmTaskAssignRuleConvert.class);

  BpmTaskAssignRule convert(BpmTaksAssignRuleCreateRequestVo bean);

  BpmTaskAssignRule convert(BpmTaskAssignRuleUpdateRequestVo bean);

  default List<BpmTaskAssignRuleResponseVo> convertList(
      List<UserTask> userTasks, List<BpmTaskAssignRule> rules) {
    Map<String, BpmTaskAssignRule> ruleMap =
        CollectionUtil.convertMap(rules, BpmTaskAssignRule::getTaskDefinitionKey);
    // 以 UserTask 为主维度，原因是：流程图编辑后，一些规则实际就没用了。
    return CollectionUtil.convertList(
        userTasks,
        task -> {
          BpmTaskAssignRuleResponseVo respVO = convert(ruleMap.get(task.getId()));
          if (respVO == null) {
            respVO = new BpmTaskAssignRuleResponseVo();
            respVO.setTaskDefinitionKey(task.getId());
          }
          respVO.setTaskDefinitionName(task.getName());
          return respVO;
        });
  }

  BpmTaskAssignRuleResponseVo convert(BpmTaskAssignRule bean);

  List<BpmTaskAssignRule> convertList2(List<BpmTaskAssignRuleResponseVo> list);
}
