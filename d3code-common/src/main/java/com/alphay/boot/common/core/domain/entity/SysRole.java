package com.alphay.boot.common.core.domain.entity;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alphay.boot.common.annotation.Excel;
import com.alphay.boot.common.annotation.Excel.ColumnType;
import com.alphay.boot.common.core.domain.BaseEntity;

/**
 * 角色表 sys_role
 *
 * @author d3code
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRole extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 角色ID */
  @Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
  @TableId(type = IdType.AUTO)
  private Long roleId;

  /** 角色名称 */
  @Excel(name = "角色名称")
  @NotBlank(message = "角色名称不能为空")
  @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
  private String roleName;

  /** 角色权限 */
  @Excel(name = "角色权限")
  @NotBlank(message = "权限字符不能为空")
  @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
  private String roleKey;

  /** 角色排序 */
  @Excel(name = "角色排序")
  @NotNull(message = "显示顺序不能为空")
  private Integer roleSort;

  /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
  @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
  private String dataScope;

  /** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
  private boolean menuCheckStrictly;

  /** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
  private boolean deptCheckStrictly;

  /** 角色状态（0正常 1停用） */
  @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
  private String status;

  /** 用户是否存在此角色标识 默认不存在 */
  @TableField(exist = false)
  private boolean flag = false;

  /** 菜单组 */
  @TableField(exist = false)
  private Long[] menuIds;

  /** 部门组（数据权限） */
  @TableField(exist = false)
  private Long[] deptIds;

  /** 角色菜单权限 */
  @TableField(exist = false)
  private Set<String> permissions;

  @TableField(exist = false)
  private SysRole childRole;

  public SysRole(Long roleId) {
    this.roleId = roleId;
  }

  public boolean isAdmin() {
    return isAdmin(this.roleId);
  }

  public static boolean isAdmin(Long roleId) {
    return roleId != null && 1L == roleId;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("roleId", getRoleId())
        .append("roleName", getRoleName())
        .append("roleKey", getRoleKey())
        .append("roleSort", getRoleSort())
        .append("dataScope", getDataScope())
        .append("menuCheckStrictly", isMenuCheckStrictly())
        .append("deptCheckStrictly", isDeptCheckStrictly())
        .append("status", getStatus())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .append("remark", getRemark())
        .toString();
  }
}
