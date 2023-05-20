package com.alphay.boot.bpm.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alphay.boot.bpm.convert.BpmProcessDefinitionConvert;
import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.bpm.domain.BpmProcessDefinitionExt;
import com.alphay.boot.bpm.mapper.BpmProcessDEfinitionExtMapper;
import com.alphay.boot.bpm.model.dto.BpmProcessDefinitionCreateRequestDTO;
import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionPageItemResponseVo;
import com.alphay.boot.bpm.service.IBpmFormService;
import com.alphay.boot.bpm.service.IBpmProcessDefinitionService;
import com.alphay.boot.bpm.utils.FlowableUtil;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.bpm.model.vo.BpmProcessDefinitionQueryRequestVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.query.Query;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static java.util.Collections.emptyList;

/**
 * BPM 流程定义接口实现
 *
 * @author Nottyjay
 */
@Service
@Validated
@Slf4j
public class BpmProcessDefinitionServiceImpl implements IBpmProcessDefinitionService {

  private static final String BPMN_FILE_SUFFIX = ".bpmn";
  @Resource private RepositoryService repositoryService;
  @Resource private IBpmFormService formService;
  @Resource private BpmProcessDEfinitionExtMapper processDEfinitionExtMapper;

  @Override
  public List<ProcessDefinition> getProcessDefinitionListByDeploymentIds(
      Set<String> deploymentIds) {
    if (CollUtil.isEmpty(deploymentIds)) {
      return emptyList();
    }
    return repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).list();
  }

  @Override
  public ProcessDefinition getActiveProcessDefinition(String key) {
    return repositoryService
        .createProcessDefinitionQuery()
        .processDefinitionKey(key)
        .active()
        .singleResult();
  }

  @Override
  public boolean isProcessDefinitionEquals(
      BpmProcessDefinitionCreateRequestDTO definitionCreateReqDTO) {
    // 校验 name、description 是否更新
    ProcessDefinition oldProcessDefinition =
        getActiveProcessDefinition(definitionCreateReqDTO.getKey());
    if (oldProcessDefinition == null) {
      return false;
    }
    BpmProcessDefinitionExt oldProcessDefinitionExt =
        getProcessDefinitionExt(oldProcessDefinition.getId());
    if (!StrUtil.equals(definitionCreateReqDTO.getName(), oldProcessDefinition.getName())
        || !StrUtil.equals(
            definitionCreateReqDTO.getDescription(), oldProcessDefinitionExt.getDescription())
        || !StrUtil.equals(
            definitionCreateReqDTO.getCategory(), oldProcessDefinition.getCategory())) {
      return false;
    }
    // 校验 form 信息是否更新
    if (!ObjectUtil.equal(
            definitionCreateReqDTO.getFormType(), oldProcessDefinitionExt.getFormType())
        || !ObjectUtil.equal(
            definitionCreateReqDTO.getFormId(), oldProcessDefinitionExt.getFormId())
        || !ObjectUtil.equal(
            definitionCreateReqDTO.getFormConf(), oldProcessDefinitionExt.getFormConf())
        || !ObjectUtil.equal(
            definitionCreateReqDTO.getFormFields(), oldProcessDefinitionExt.getFormFields())
        || !ObjectUtil.equal(
            definitionCreateReqDTO.getFormCustomCreatePath(),
            oldProcessDefinitionExt.getFormCustomCreatePath())
        || !ObjectUtil.equal(
            definitionCreateReqDTO.getFormCustomViewPath(),
            oldProcessDefinitionExt.getFormCustomViewPath())) {
      return false;
    }
    // 校验 BPMN XML 信息
    BpmnModel newModel = buildBpmnModel(definitionCreateReqDTO.getBpmnBytes());
    BpmnModel oldModel = getBpmnModel(oldProcessDefinition.getId());
    // 对比字节变化
    if (!FlowableUtil.equals(oldModel, newModel)) {
      return false;
    }
    // 最终发现都一致，则返回 true
    return true;
  }

  @Override
  public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId) {
    if (StringUtils.isEmpty(deploymentId)) {
      return null;
    }
    return repositoryService
        .createProcessDefinitionQuery()
        .deploymentId(deploymentId)
        .singleResult();
  }

  @Override
  public String createProcessDefinition(
      @Valid BpmProcessDefinitionCreateRequestDTO definitionCreateReqDTO) {
    // 创建 Deployment 部署
    Deployment deploy =
        repositoryService
            .createDeployment()
            .key(definitionCreateReqDTO.getKey())
            .name(definitionCreateReqDTO.getName())
            .category(definitionCreateReqDTO.getCategory())
            .addBytes(
                definitionCreateReqDTO.getKey() + BPMN_FILE_SUFFIX,
                definitionCreateReqDTO.getBpmnBytes())
            .deploy();

    // 设置 ProcessDefinition 的 category 分类
    ProcessDefinition definition =
        repositoryService
            .createProcessDefinitionQuery()
            .deploymentId(deploy.getId())
            .singleResult();
    repositoryService.setProcessDefinitionCategory(
        definition.getId(), definitionCreateReqDTO.getCategory());
    // 注意 1，ProcessDefinition 的 key 和 name 是通过 BPMN 中的 <bpmn2:process /> 的 id 和 name 决定
    // 注意 2，目前该项目的设计上，需要保证 Model、Deployment、ProcessDefinition 使用相同的 key，保证关联性。
    //          否则，会导致 ProcessDefinition 的分页无法查询到。
    if (!Objects.equals(definition.getKey(), definitionCreateReqDTO.getKey())) {
      throw new ServiceException(
          "流程定义的标识期望是("
              + definitionCreateReqDTO.getKey()
              + ")，当前是("
              + definition.getKey()
              + ")，请修改 BPMN 流程图");
    }
    if (!Objects.equals(definition.getName(), definitionCreateReqDTO.getName())) {
      throw new ServiceException(
          "流程定义的名字期望是("
              + definitionCreateReqDTO.getName()
              + ")，当前是("
              + definition.getName()
              + ")，请修改 BPMN 流程图");
    }

    // 插入拓展表
    BpmProcessDefinitionExt definitionDO =
        BpmProcessDefinitionConvert.INSTANCE
            .convert2(definitionCreateReqDTO)
            .setProcessDefinitionId(definition.getId());
    processDEfinitionExtMapper.insert(definitionDO);
    return definition.getId();
  }

  @Override
  public void updateProcessDefinitionState(String id, int stateCode) {
    // 激活
    if (Objects.equals(SuspensionState.ACTIVE.getStateCode(), stateCode)) {
      repositoryService.activateProcessDefinitionById(id, false, null);
      return;
    }
    // 挂起
    if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), stateCode)) {
      // suspendProcessInstances = false，进行中的任务，不进行挂起。
      // 原因：只要新的流程不允许发起即可，老流程继续可以执行。
      repositoryService.suspendProcessDefinitionById(id, false, null);
      return;
    }
    log.error("[updateProcessDefinitionState][流程定义({}) 修改未知状态({})]", id, stateCode);
  }

  @Override
  public ProcessDefinition getProcessDefinition(String definitionId) {
    return repositoryService.getProcessDefinition(definitionId);
  }

  @Override
  public BpmProcessDefinitionExt getProcessDefinitionExt(String processDefinitionId) {
    return processDEfinitionExtMapper.selectOne(
        new LambdaQueryWrapperX<BpmProcessDefinitionExt>()
            .eq(BpmProcessDefinitionExt::getProcessDefinitionId, processDefinitionId));
  }

  @Override
  public Deployment getDeployment(String id) {
    if (StrUtil.isEmpty(id)) {
      return null;
    }
    return repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
  }

  @Override
  public BpmnModel getBpmnModel(String processDefinitionId) {
    return repositoryService.getBpmnModel(processDefinitionId);
  }

  @Override
  public List<Deployment> getDeployments(Set<String> ids) {
    if (CollUtil.isEmpty(ids)) {
      return emptyList();
    }
    List<Deployment> list = new ArrayList<>(ids.size());
    for (String id : ids) {
      CollectionUtil.addIfNotNull(list, getDeployment(id));
    }
    return list;
  }

  @Override
  public String getProcessDefinitionBpmnXML(String id) {
    BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
    if (bpmnModel == null) {
      return null;
    }
    BpmnXMLConverter converter = new BpmnXMLConverter();
    return StrUtil.utf8Str(converter.convertToXML(bpmnModel));
  }

  @Override
  public List<BpmProcessDefinitionPageItemResponseVo> getProcessDefinitionList(
      BpmProcessDefinitionQueryRequestVo requestVo) {
    Page page = PageHelper.getLocalPage();
    PageHelper.clearPage();

    ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
    if (StrUtil.isNotBlank(requestVo.getKey())) {
      definitionQuery.processDefinitionKey(requestVo.getKey());
    }

    if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), requestVo.getSuspensionState())) {
      definitionQuery.suspended();
    } else if (Objects.equals(
        SuspensionState.ACTIVE.getStateCode(), requestVo.getSuspensionState())) {
      definitionQuery.active();
    }

    // 执行查询
    Query query = definitionQuery.orderByProcessDefinitionVersion().desc();
    List<ProcessDefinition> processDefinitions = null;
    if (page != null) {
      processDefinitions =
          query.listPage((page.getPageNum() - 1) * page.getPageSize(), page.getPageSize());
    } else {
      processDefinitions = query.list();
    }

    if (CollUtil.isEmpty(processDefinitions)) {
      return page;
    }
    // 获得 Deployment Map
    Set<String> deploymentIds = new HashSet<>();
    processDefinitions.forEach(
        definition -> CollectionUtil.addIfNotNull(deploymentIds, definition.getDeploymentId()));
    Map<String, Deployment> deploymentMap = getDeploymentMap(deploymentIds);

    // 获得 BpmProcessDefinitionDO Map
    List<BpmProcessDefinitionExt> processDefinitionDOs =
        processDEfinitionExtMapper.selectListByProcessDefinitionIds(
            CollectionUtil.convertList(processDefinitions, ProcessDefinition::getId));
    Map<String, BpmProcessDefinitionExt> processDefinitionDOMap =
        CollectionUtil.convertMap(
            processDefinitionDOs, BpmProcessDefinitionExt::getProcessDefinitionId);

    // 获得 Form Map
    Set<Long> formIds =
        CollectionUtil.convertSet(processDefinitionDOs, BpmProcessDefinitionExt::getFormId);
    Map<Long, BpmForm> formMap = formService.getFormMap(formIds);

    List<BpmProcessDefinitionPageItemResponseVo> result =
        BpmProcessDefinitionConvert.INSTANCE.convertList(
            processDefinitions, deploymentMap, processDefinitionDOMap, formMap);

    if (page == null) {
      return result;
    } else {
      // 拼接结果
      long definitionCount = definitionQuery.count();
      page.setTotal(definitionCount);
      page.addAll(result);
      return page;
    }
  }

  @Override
  public ProcessDefinition getProcessDefinition2(String processDefinitionId) {
    return repositoryService
        .createProcessDefinitionQuery()
        .processDefinitionId(processDefinitionId)
        .singleResult();
  }

  /**
   * 构建对应的 BPMN Model
   *
   * @param bpmnBytes 原始的 BPMN XML 字节数组
   * @return BPMN Model
   */
  private BpmnModel buildBpmnModel(byte[] bpmnBytes) {
    // 转换成 BpmnModel 对象
    BpmnXMLConverter converter = new BpmnXMLConverter();
    return converter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
  }
}
