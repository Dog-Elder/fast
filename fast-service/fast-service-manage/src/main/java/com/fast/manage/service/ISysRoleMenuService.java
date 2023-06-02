package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.manage.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联Service接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu>
{
    /**
     * 查询角色和菜单关联
     * 
     * @param roleId 角色和菜单关联ID
     * @return 角色和菜单关联
     */
    public SysRoleMenu selectById(Long roleId);

    /**
     * 查询角色和菜单关联列表
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 角色和菜单关联集合
     */
    public List<SysRoleMenu> list(SysRoleMenu sysRoleMenu);

    /**
     * 新增角色和菜单关联
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    public boolean save(SysRoleMenu sysRoleMenu);

    /**
     * 修改角色和菜单关联
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    public boolean update(SysRoleMenu sysRoleMenu);

    /**
     * 真批量删除角色和菜单关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除角色和菜单关联信息
     * 
     * @param roleId 角色和菜单关联ID
     * @return 结果
     */
    public int deleteSysRoleMenuById(Long roleId);

    /**
     * 角色和菜单关联逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);
}
