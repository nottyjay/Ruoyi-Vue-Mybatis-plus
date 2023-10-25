package com.alphay.boot.system.mapper;

import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.system.common.domain.SysUserGroupRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserGroupRoleMapper extends BaseMapperX<SysUserGroupRole> {
  int batchUserGroupRole(List<SysUserGroupRole> list);

  int deleteUserGroupRoleInfo(SysUserGroupRole groupRole);

  int deleteUserGroupRoleInfos(
      @Param("roleId") Long roleId, @Param("userGroupIds") Long[] userGroupIds);
}
