package com.alphay.boot.system.mapper;

import java.util.List;

import com.alphay.boot.common.core.domain.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单表 数据层
 *
 * @author d3code
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
  /**
   * 查询系统菜单列表
   *
   * @param menu 菜单信息
   * @return 菜单列表
   */
  public List<SysMenu> selectMenuList(SysMenu menu);

  /**
   * 根据用户所有权限
   *
   * @return 权限列表
   */
  public List<String> selectMenuPerms();

  /**
   * 根据用户查询系统菜单列表
   *
   * @param menu 菜单信息
   * @return 菜单列表
   */
  public List<SysMenu> selectMenuListByUserId(SysMenu menu);

  /**
   * 根据角色ID查询权限
   *
   * @param roleId 角色ID
   * @return 权限列表
   */
  public List<String> selectMenuPermsByRoleId(Long roleId);

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  public List<String> selectMenuPermsByUserId(Long userId);

  /**
   * 根据用户ID查询菜单
   *
   * @return 菜单列表
   */
  public List<SysMenu> selectMenuTreeAll();

  /**
   * 根据用户ID查询菜单
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  public List<SysMenu> selectMenuTreeByUserId(Long userId);

  /**
   * 根据角色ID查询菜单树信息
   *
   * @param roleId 角色ID
   * @param menuCheckStrictly 菜单树选择项是否关联显示
   * @return 选中菜单列表
   */
  public List<Long> selectMenuListByRoleId(
      @Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

  /**
   * 是否存在菜单子节点
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  public int hasChildByMenuId(Long menuId);

  /**
   * 校验菜单名称是否唯一
   *
   * @param menuName 菜单名称
   * @param parentId 父菜单ID
   * @return 结果
   */
  public SysMenu checkMenuNameUnique(
      @Param("menuName") String menuName, @Param("parentId") Long parentId);
}
