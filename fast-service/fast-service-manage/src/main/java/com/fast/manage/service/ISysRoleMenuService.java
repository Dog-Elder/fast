package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联Service接口
 * 
 * @author 黄嘉浩
 * @date 2021-06-29
 */
public interface ISysRoleMenuService extends BaseService<SysRoleMenu>
{
    /**
     * 查询角色和菜单关联
     *
     * @param roleId 角色和菜单关联ID
     * @return 角色和菜单关联
     */
    SysRoleMenu selectById(Long roleId);

    /**
     * 修改角色和菜单关联
     *
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    boolean update(SysRoleMenu sysRoleMenu);

    /**
     * 真批量删除角色和菜单关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteByIds(String ids);

    /**
     * 真删除角色和菜单关联信息
     *
     * @param roleId 角色和菜单关联ID
     * @return 结果
     */
    int deleteSysRoleMenuById(Long roleId);

    /**
     * 角色和菜单关联逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    boolean logicRemove(String ids);

    /**
     * 所有角色菜单
     *
     * @return {@link List}<{@link SysRoleMenu}>
     */
    List<SysRoleMenu> listRoleMenuAll();
}
