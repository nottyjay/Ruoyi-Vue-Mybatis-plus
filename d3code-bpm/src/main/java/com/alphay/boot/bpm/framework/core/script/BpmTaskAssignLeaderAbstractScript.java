package com.alphay.boot.bpm.framework.core.script;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import com.alphay.boot.bpm.service.IBpmProcessInstanceService;
import com.alphay.boot.common.core.domain.entity.SysDept;
import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.system.api.AdminApi;
import com.alphay.boot.system.api.DeptApi;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * 分配给发起人的 Leader 审批的 Script 实现类 目前 Leader 的定义是，
 *
 * @author Nottyjay
 */
public abstract class BpmTaskAssignLeaderAbstractScript implements BpmTaskAssignScript {

  @Resource private AdminApi adminApi;
  @Resource private DeptApi deptApi;
  @Resource @Lazy // 解决循环依赖
  private IBpmProcessInstanceService bpmProcessInstanceService;

  protected Set<Long> calculateTaskCandidateUsers(DelegateExecution execution, int level) {
    Assert.isTrue(level > 0, "level 必须大于 0");
    // 获得发起人
    ProcessInstance processInstance =
        bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());
    Long startUserId = NumberUtil.parseLong(processInstance.getStartUserId());
    // 获得对应 leve 的部门
    SysDept dept = null;
    for (int i = 0; i < level; i++) {
      // 获得 level 对应的部门
      if (dept == null) {
        dept = getStartUserDept(startUserId);
        if (dept == null) { // 找不到发起人的部门，所以无法使用该规则
          return emptySet();
        }
      } else {
        SysDept parentDept = deptApi.getDept(dept.getParentId());
        if (parentDept == null) { // 找不到父级部门，所以只好结束寻找。原因是：例如说，级别比较高的人，所在部门层级比较少
          break;
        }
        dept = parentDept;
      }
    }
    return dept.getLeaderUserId() != null
        ? CollUtil.set(false, dept.getLeaderUserId())
        : emptySet();
  }

  private SysDept getStartUserDept(Long startUserId) {
    SysUser startUser = adminApi.getUser(startUserId);
    if (startUser.getDeptId() == null) { // 找不到部门，所以无法使用该规则
      return null;
    }
    return deptApi.getDept(startUser.getDeptId());
  }
}
