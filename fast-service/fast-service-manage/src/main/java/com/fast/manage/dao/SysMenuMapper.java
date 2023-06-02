package com.fast.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.manage.entity.SysMenu;

import java.util.List;

/**
 * 菜单权限Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface SysMenuMapper extends BaseMapper<SysMenu>
{
    /**
     * 查询菜单权限
     * 
     * @param id 菜单权限ID
     * @return 菜单权限
     */
    public SysMenu selectSysMenuById(Long id);

    /**
     * 查询菜单权限列表
     * 
     * @param sysMenu 菜单权限
     * @return 菜单权限集合
     */
    public List<SysMenu> selectSysMenuList(SysMenu sysMenu);

    /**
     * 新增菜单权限
     * 
     * @param sysMenu 菜单权限
     * @return 结果
     */
    public int insertSysMenu(SysMenu sysMenu);

    /**
     * 修改菜单权限
     * 
     * @param sysMenu 菜单权限
     * @return 结果
     */
    public int updateSysMenu(SysMenu sysMenu);

    /**
     * 删除菜单权限
     * 
     * @param id 菜单权限ID
     * @return 结果
     */
    public int deleteSysMenuById(Long id);

    /**
     * 批量删除菜单权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMenuByIds(String[] ids);
}
