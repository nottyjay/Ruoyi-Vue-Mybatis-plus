package com.alphay.boot.system.mapper;

import com.alphay.boot.system.common.domain.SysUserGroup;
import com.alphay.boot.common.mybatis.mapper.BaseMapperX;
import com.alphay.boot.common.mybatis.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserGroupMapper extends BaseMapperX<SysUserGroup> {

  default List<SysUserGroup> selectList(SysUserGroup userGroup, IPage page) {
    return selectList(
        page,
        new LambdaQueryWrapperX<SysUserGroup>()
            .likeIfPresent(SysUserGroup::getName, userGroup.getName())
            .eqIfPresent(SysUserGroup::getStatus, userGroup.getStatus())
            .betweenIfPresent(
                SysUserGroup::getCreateTime,
                new Object[] {
                  userGroup.getParams().get("beginTime"), userGroup.getParams().get("endTime")
                })
            .orderByDesc(SysUserGroup::getId));
  }

  SysUserGroup selectFullUserGroupInfoById(Long id);

  /**
   * 获取授权的用户组
   *
   * @param userGroup
   * @param page
   * @return
   */
  List<SysUserGroup> selectAllocatedUserGroupList(
      @Param("group") SysUserGroup userGroup, IPage page);

  /**
   * 获取尚未授权的用户组
   *
   * @param userGroup
   * @param page
   * @return
   */
  List<SysUserGroup> selectUnallocatedUserGroupList(
      @Param("group") SysUserGroup userGroup, IPage page);
}
