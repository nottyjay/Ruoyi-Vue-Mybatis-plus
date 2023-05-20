package com.alphay.boot.bpm.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class BpmProcessInstanceResponseVo {

  private String id;

  private String name;

  private String category;

  private Integer status;

  private Integer result;

  private LocalDateTime createTime;

  private LocalDateTime endTime;

  private Map<String, Object> formVariables;

  private String businessKey;

  /** 发起流程的用户 */
  private User startUser;

  /** 流程定义 */
  private ProcessDefinition processDefinition;

  @Data
  public static class User {

    private Long id;
    private String nickName;

    private Long deptId;
    private String deptName;
  }

  @Data
  public static class ProcessDefinition {

    private String id;

    private Integer formType;
    private Long formId;
    private String formConf;
    private List<String> formFields;
    private String formCustomCreatePath;
    private String formCustomViewPath;

    private String bpmnXml;
  }
}
