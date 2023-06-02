package com.fast.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.manage.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu>
{
    /**
     * 查询角色和菜单关联
     * 
     * @param roleId 角色和菜单关联ID
     * @return 角色和菜单关联
     */
    public SysRoleMenu selectSysRoleMenuById(Long roleId);

    /**
     * 查询角色和菜单关联列表
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 角色和菜单关联集合
     */
    public List<SysRoleMenu> selectSysRoleMenuList(SysRoleMenu sysRoleMenu);

    /**
     * 新增角色和菜单关联
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    public int insertSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 修改角色和菜单关联
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    public int updateSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 删除角色和菜单关联
     * 
     * @param roleId 角色和菜单关联ID
     * @return 结果
     */
    public int deleteSysRoleMenuById(Long roleId);

    /**
     * 批量删除角色和菜单关联
     * 
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRoleMenuByIds(String[] roleIds);
}
