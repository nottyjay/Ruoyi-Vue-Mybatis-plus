package com.alphay.boot.bpm.convert;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.bpm.domain.BpmForm;
import com.alphay.boot.bpm.model.dto.BpmModelMetaInfoRespDTO;
import com.alphay.boot.bpm.model.dto.BpmProcessDefinitionCreateRequestDTO;
import com.alphay.boot.bpm.model.vo.*;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.common.utils.collection.CollectionUtil;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Bpm 流程模型 Convert
 *
 * @author Nottyjay
 */
@Mapper
public interface BpmModelConvert {

  BpmModelConvert INSTANCE = Mappers.getMapper(BpmModelConvert.class);

  default void copy(Model model, BpmModelCreateRequestVo bean) {
    model.setName(bean.getName());
    model.setKey(bean.getKey());
    model.setMetaInfo(buildMetaInfoStr(null, bean.getDescription(), null, null, null, null));
  }

  default void copy(Model model, BpmModelUpdateRequestVo bean) {
    model.setName(bean.getName());
    model.setCategory(bean.getCategory());
    model.setMetaInfo(
        buildMetaInfoStr(
            JsonUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class),
            bean.getDescription(),
            bean.getFormType(),
            bean.getFormId(),
            bean.getFormCustomCreatePath(),
            bean.getFormCustomViewPath()));
  }

  BpmModelCreateRequestVo convert(BpmModelImportRequestVo bean);

  default List<BpmModelItemResponseVo> convertList(
      List<Model> list,
      Map<Long, BpmForm> formMap,
      Map<String, Deployment> deploymentMap,
      Map<String, ProcessDefinition> processDefinitionMap) {
    return CollectionUtil.convertList(
        list,
        model -> {
          BpmModelMetaInfoRespDTO metaInfo =
              JsonUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class);
          BpmForm form = metaInfo != null ? formMap.get(metaInfo.getFormId()) : null;
          Deployment deployment =
              model.getDeploymentId() != null ? deploymentMap.get(model.getDeploymentId()) : null;
          ProcessDefinition processDefinition =
              model.getDeploymentId() != null
                  ? processDefinitionMap.get(model.getDeploymentId())
                  : null;
          return convert(model, form, deployment, processDefinition);
        });
  }

  default BpmModelItemResponseVo convert(
      Model model, BpmForm form, Deployment deployment, ProcessDefinition processDefinition) {
    BpmModelItemResponseVo modelVo = new BpmModelItemResponseVo();
    modelVo.setId(model.getId());
    modelVo.setCreateTime(DateUtil.toLocalDateTime(model.getCreateTime()));
    // 通用 copy
    copyTo(model, modelVo);
    // Form
    if (form != null) {
      modelVo.setFormId(form.getId());
      modelVo.setFormName(form.getName());
    }
    // ProcessDefinition
    modelVo.setProcessDefinition(this.convert(processDefinition));
    if (modelVo.getProcessDefinition() != null) {
      modelVo
          .getProcessDefinition()
          .setSuspensionState(
              processDefinition.isSuspended()
                  ? SuspensionState.SUSPENDED.getStateCode()
                  : SuspensionState.ACTIVE.getStateCode());
      modelVo
          .getProcessDefinition()
          .setDeploymentTime(DateUtil.toLocalDateTime(deployment.getDeploymentTime()));
    }
    return modelVo;
  }

  default BpmProcessDefinitionCreateRequestDTO convert2(Model model, BpmForm form) {
    BpmProcessDefinitionCreateRequestDTO createReqDTO = new BpmProcessDefinitionCreateRequestDTO();
    createReqDTO.setModelId(model.getId());
    createReqDTO.setName(model.getName());
    createReqDTO.setKey(model.getKey());
    createReqDTO.setCategory(model.getCategory());
    BpmModelMetaInfoRespDTO metaInfo =
        JsonUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class);
    // metaInfo
    copyTo(metaInfo, createReqDTO);
    // form
    if (form != null) {
      createReqDTO.setFormConf(form.getConf());
      createReqDTO.setFormFields(form.getFields());
    }
    return createReqDTO;
  }

  void copyTo(BpmModelMetaInfoRespDTO from, @MappingTarget BpmProcessDefinitionCreateRequestDTO to);

  default void copyTo(Model model, BpmModelBaseVO to) {
    to.setName(model.getName());
    to.setKey(model.getKey());
    to.setCategory(model.getCategory());
    // metaInfo
    BpmModelMetaInfoRespDTO metaInfo =
        JsonUtil.toBean(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class);
    copyTo(metaInfo, to);
  }

  void copyTo(BpmModelMetaInfoRespDTO from, @MappingTarget BpmModelBaseVO to);

  BpmModelItemResponseVo.ProcessDefinition convert(ProcessDefinition bean);

  default BpmModelRespVo convert(Model model) {
    BpmModelRespVo modelRespVO = new BpmModelRespVo();
    modelRespVO.setId(model.getId());
    modelRespVO.setCreateTime(DateUtil.toLocalDateTime(model.getCreateTime()));
    // 通用 copy
    copyTo(model, modelRespVO);
    return modelRespVO;
  }

  default String buildMetaInfoStr(
      BpmModelMetaInfoRespDTO metaInfo,
      String description,
      Integer formType,
      Long formId,
      String formCustomCreatePath,
      String formCustomViewPath) {
    if (metaInfo == null) {
      metaInfo = new BpmModelMetaInfoRespDTO();
    }
    // 只有非空，才进行设置，避免更新时的覆盖
    if (StrUtil.isNotEmpty(description)) {
      metaInfo.setDescription(description);
    }
    if (Objects.nonNull(formType)) {
      metaInfo.setFormType(formType);
      metaInfo.setFormId(formId);
      metaInfo.setFormCustomCreatePath(formCustomCreatePath);
      metaInfo.setFormCustomViewPath(formCustomViewPath);
    }
    return JsonUtil.toJsonHex(metaInfo);
  }
}
