package com.alphay.boot.web.controller.system;

import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.system.common.domain.SysUserGroup;
import com.alphay.boot.system.common.domain.SysUserGroupRelation;
import com.alphay.boot.system.common.service.ISysDeptService;
import com.alphay.boot.system.common.service.ISysUserGroupService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.system.common.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * BPM 流程 用户分组
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/system/user-group")
public class SysUserGroupController extends BaseController {

  @Resource private ISysUserGroupService userGroupService;
  @Resource private ISysUserService userService;
  @Resource private ISysDeptService deptService;

  @PreAuthorize("@ss.hasPermi('system:userGroup:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysUserGroup userGroup) {
    List<SysUserGroup> result = userGroupService.selectUserGroupList(userGroup, startPage());
    return getDataTable(result);
  }

  @PreAuthorize("@ss.hasPermi('system:userGroup:query')")
  @GetMapping("/{id}")
  public AjaxResult getInfo(@PathVariable Long id) {
    return success(userGroupService.getById(id));
  }

  @PreAuthorize("@ss.hasPermi('system:userGroup:add')")
  @Log(title = "用户组管理", businessType = BusinessType.INSERT)
  @PostMapping("/create")
  public AjaxResult add(@Valid @RequestBody SysUserGroup userGroup) {
    userGroup.setCreateBy(getUsername());
    return toAjax(userGroupService.save(userGroup));
  }

  @PreAuthorize("@ss.hasPermi('system:userGroup:edit')")
  @Log(title = "用户组管理", businessType = BusinessType.UPDATE)
  @PutMapping("/update")
  public AjaxResult update(@Valid @RequestBody SysUserGroup userGroup) {
    userGroup.setUpdateBy(getUsername());
    return toAjax(userGroupService.updateById(userGroup));
  }

  @PreAuthorize("@ss.hasPermi('system:userGroup:edit')")
  @Log(title = "用户组管理", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public AjaxResult changeStatus(@RequestBody SysUserGroup userGroup) {
    userGroup.setUpdateBy(getUsername());
    return toAjax(userGroupService.updateUserGroupStatus(userGroup));
  }

  @PreAuthorize("@ss.hasPermi('system:userGroup:remove')")
  @Log(title = "用户组管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/delete/{id}")
  public AjaxResult delete(@PathVariable Long id) {
    return toAjax(userGroupService.removeById(id));
  }

  /** 获取用户组内列表 */
  @PreAuthorize("@ss.hasAnyPermi('system:userGroup:edit, system:userGroup:query')")
  @GetMapping("/listInGroup")
  public TableDataInfo listInGroup(Long groupId) {
    return getDataTable(userService.selectUserListByGroupId(groupId, startPage()));
  }

  /** 删除组内用户 */
  @PreAuthorize("@ss.hasPermi('system:userGroup:remove')")
  @DeleteMapping("/deleteUserInGroup")
  public AjaxResult removeRelation(@RequestBody SysUserGroupRelation userGroupRelation) {
    return toAjax(userService.deleteUserInGroup(userGroupRelation));
  }
}
