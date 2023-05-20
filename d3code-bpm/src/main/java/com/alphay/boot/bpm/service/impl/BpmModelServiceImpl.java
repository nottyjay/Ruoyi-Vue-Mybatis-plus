package com.alphay.boot.bpm.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alphay.boot.bpm.convert.BpmModelConvert;
import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.bpm.enums.BpmModelFormTypeEnum;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.bpm.service.IBpmFormService;
import com.alphay.boot.bpm.service.IBpmModelService;
import com.alphay.boot.bpm.service.IBpmProcessDefinitionService;
import com.alphay.boot.bpm.service.IBpmTaskAssignRuleService;
import com.alphay.boot.common.exception.ServiceException;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.ValidationUtil;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import com.alphay.boot.bpm.model.dto.BpmProcessDefinitionCreateRequestDTO;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.bpm.model.dto.BpmModelMetaInfoRespDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * 流程模型业务接口实现
 *
 * @author Nottyjay
 */
@Service
@Slf4j
public class BpmModelServiceImpl implements IBpmModelService {

  @Resource private RepositoryService repositoryService;
  @Resource private IBpmFormService formService;
  @Resource private IBpmProcessDefinitionService processDefinitionService;
  @Resource private IBpmTaskAssignRuleService taskAssignRuleService;

  @Override
  public List<BpmModelItemResponseVo> selectModelList(BpmModelQueryRequestVo requestVo) {
    Page page = PageHelper.getLocalPage();
    PageHelper.clearPage();
    ModelQuery modelQuery = repositoryService.createModelQuery();
    if (StringUtils.isNotBlank(requestVo.getKey())) {
      modelQuery.modelKey(requestVo.getKey());
    }
    if (StrUtil.isNotBlank(requestVo.getName())) {
      modelQuery.modelNameLike("%" + requestVo.getName() + "%"); // 模糊匹配
    }
    if (StrUtil.isNotBlank(requestVo.getCategory())) {
      modelQuery.modelCategory(requestVo.getCategory());
    }

    modelQuery.orderByCreateTime().desc();

    List<Model> models = null;
    if (page == null) {
      models = modelQuery.list();
    } else {
      models =
          modelQuery.listPage((page.getPageNum() - 1) * page.getPageSize(), page.getPageSize());
    }

    Set<Long> formIds =
        CollectionUtil.convertSet(
            models,
            model -> {
              BpmModelMetaInfoRespDTO metaInfoRespDTO =
                  JsonUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class);
              return metaInfoRespDTO != null ? metaInfoRespDTO.getFormId() : null;
            });

    Map<Long, BpmForm> formMap = formService.getFormMap(formIds);

    // 获得 Deployment Map
    Set<String> deploymentIds = new HashSet<>();
    models.forEach(model -> CollectionUtil.addIfNotNull(deploymentIds, model.getDeploymentId()));
    Map<String, Deployment> deploymentMap =
        processDefinitionService.getDeploymentMap(deploymentIds);
    // 获得 ProcessDefinition Map
    List<ProcessDefinition> processDefinitions =
        processDefinitionService.getProcessDefinitionListByDeploymentIds(deploymentIds);
    Map<String, ProcessDefinition> processDefinitionMap =
        CollectionUtil.convertMap(processDefinitions, ProcessDefinition::getDeploymentId);

    List<BpmModelItemResponseVo> result =
        BpmModelConvert.INSTANCE.convertList(models, formMap, deploymentMap, processDefinitionMap);
    if (page == null) {
      return result;
    } else {
      long modelCount = modelQuery.count();
      page.setTotal(modelCount);
      page.addAll(result);
      return page;
    }
  }

  @Override
  public BpmModelRespVo getModel(String id) {
    Model model = repositoryService.getModel(id);
    if (model == null) {
      return null;
    }
    BpmModelRespVo modelRespVO = BpmModelConvert.INSTANCE.convert(model);
    // 拼接 bpmn XML
    byte[] bpmnBytes = repositoryService.getModelEditorSource(id);
    modelRespVO.setBpmnXml(StrUtil.utf8Str(bpmnBytes));
    return modelRespVO;
  }

  @Override
  public BpmnModel getBpmnModel(String modelId) {
    byte[] bpmnBytes = repositoryService.getModelEditorSource(modelId);
    if (ArrayUtil.isEmpty(bpmnBytes)) {
      return null;
    }
    BpmnXMLConverter converter = new BpmnXMLConverter();
    return converter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
  }

  @Override
  public void deployModel(String id) {
    // 1.1 校验流程模型存在
    Model model = repositoryService.getModel(id);
    if (ObjectUtils.isEmpty(model)) {
      throw new ServiceException("流程模型不存在");
    }
    // 1.2 校验流程图
    // TODO 芋艿：校验流程图的有效性；例如说，是否有开始的元素，是否有结束的元素；
    byte[] bpmnBytes = repositoryService.getModelEditorSource(model.getId());
    if (bpmnBytes == null) {
      throw new ServiceException("流程模型不存在");
    }
    // 1.3 校验表单已配
    BpmForm form = checkFormConfig(model.getMetaInfo());
    // 1.4 校验任务分配规则已配置
    taskAssignRuleService.checkTaskAssignRuleAllConfig(id);

    // 1.5 校验模型是否发生修改。如果未修改，则不允许创建
    BpmProcessDefinitionCreateRequestDTO definitionCreateReqDTO =
        BpmModelConvert.INSTANCE.convert2(model, form).setBpmnBytes(bpmnBytes);
    if (processDefinitionService.isProcessDefinitionEquals(definitionCreateReqDTO)) { // 流程定义的信息相等
      ProcessDefinition oldProcessDefinition =
          processDefinitionService.getProcessDefinitionByDeploymentId(model.getDeploymentId());
      if (oldProcessDefinition != null
          && taskAssignRuleService.isTaskAssignRulesEquals(
              model.getId(), oldProcessDefinition.getId())) {
        throw new ServiceException("流程定义部署失败，原因：信息未发生变化");
      }
    }

    // 2.1 创建流程定义
    String definitionId = processDefinitionService.createProcessDefinition(definitionCreateReqDTO);

    // 2.2 将老的流程定义进行挂起。也就是说，只有最新部署的流程定义，才可以发起任务。
    updateProcessDefinitionSuspended(model.getDeploymentId());

    // 2.3 更新 model 的 deploymentId，进行关联
    ProcessDefinition definition = processDefinitionService.getProcessDefinition(definitionId);
    model.setDeploymentId(definition.getDeploymentId());
    repositoryService.saveModel(model);

    // 2.4 复制任务分配规则
    taskAssignRuleService.copyTaskAssignRules(id, definition.getId());
  }

  @Override
  public void updateModelState(String id, Integer state) {
    // 校验流程模型存在
    Model model = repositoryService.getModel(id);
    if (model == null) {
      throw new ServiceException("流程模型不存在");
    }
    // 校验流程定义存在
    ProcessDefinition definition =
        processDefinitionService.getProcessDefinitionByDeploymentId(model.getDeploymentId());
    if (definition == null) {
      throw new ServiceException("流程定义不存在");
    }

    // 更新状态
    processDefinitionService.updateProcessDefinitionState(definition.getId(), state);
  }

  @Override
  public void deleteModel(String modelId) {
    // 校验流程模型存在
    Model model = repositoryService.getModel(modelId);
    if (model == null) {
      throw new ServiceException("流程模型不存在");
    }
    // 执行删除
    repositoryService.deleteModel(modelId);
    // 禁用流程实例
    updateProcessDefinitionSuspended(model.getDeploymentId());
  }

  @Override
  public void updateModel(BpmModelUpdateRequestVo requestVo) {
    // 校验流程模型存在
    Model model = repositoryService.getModel(requestVo.getId());
    if (model == null) {
      throw new ServiceException("流程模型不存在");
    }

    // 修改流程定义
    BpmModelConvert.INSTANCE.copy(model, requestVo);
    // 更新模型
    repositoryService.saveModel(model);
    // 更新 BPMN XML
    saveModelBpmnXml(model, requestVo.getBpmnXml());
  }

  @Override
  public String createModel(@Valid BpmModelCreateRequestVo requestVo, String bpmnXml) {
    // 校验流程标识是否合法
    checkKeyNCName(requestVo.getKey());
    // 校验流程标识是否已存在
    Model keyModel = getModelByKey(requestVo.getKey());
    if (keyModel != null) {
      throw new ServiceException("已经存在流程标识为【" + requestVo.getKey() + "】的流程");
    }

    // 创建流程定义
    Model model = repositoryService.newModel();
    BpmModelConvert.INSTANCE.copy(model, requestVo);
    // 保存流程定义
    repositoryService.saveModel(model);
    // 保存 BPMN XML
    saveModelBpmnXml(model, bpmnXml);
    return model.getId();
  }

  private Model getModelByKey(String key) {
    return repositoryService.createModelQuery().modelKey(key).singleResult();
  }

  private void saveModelBpmnXml(Model model, String bpmnXml) {
    if (StrUtil.isEmpty(bpmnXml)) {
      return;
    }
    repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(bpmnXml));
  }

  private void checkKeyNCName(String key) {
    if (!ValidationUtil.isXmlNCName(key)) {
      throw new ServiceException("流程标识格式不正确，需要以字母或下划线开头，后接任意字母、数字、中划线、下划线、句点！");
    }
  }

  /**
   * 校验流程表单已配置
   *
   * @param metaInfoStr 流程模型 metaInfo 字段
   * @return 流程表单
   */
  private BpmForm checkFormConfig(String metaInfoStr) {
    BpmModelMetaInfoRespDTO metaInfo = JsonUtil.toBean(metaInfoStr, BpmModelMetaInfoRespDTO.class);
    if (metaInfo == null || metaInfo.getFormType() == null) {
      throw new ServiceException("部署流程失败，原因：流程表单未配置，请点击【修改流程】按钮进行配置");
    }
    // 校验表单存在
    if (Objects.equals(metaInfo.getFormType(), BpmModelFormTypeEnum.NORMAL.getType())) {
      BpmForm form = formService.getById(metaInfo.getFormId());
      if (form == null) {
        throw new ServiceException("动态表单不存在");
      }
      return form;
    }
    return null;
  }

  /**
   * 挂起 deploymentId 对应的流程定义。 这里一个deploymentId 只关联一个流程定义
   *
   * @param deploymentId 流程发布Id.
   */
  private void updateProcessDefinitionSuspended(String deploymentId) {
    if (StrUtil.isEmpty(deploymentId)) {
      return;
    }
    ProcessDefinition oldDefinition =
        processDefinitionService.getProcessDefinitionByDeploymentId(deploymentId);
    if (oldDefinition == null) {
      return;
    }
    processDefinitionService.updateProcessDefinitionState(
        oldDefinition.getId(), SuspensionState.SUSPENDED.getStateCode());
  }
}
