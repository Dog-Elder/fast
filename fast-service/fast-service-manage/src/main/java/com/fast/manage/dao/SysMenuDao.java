package com.fast.manage.dao;


import com.fast.core.mybatis.dao.BaseDao;
import com.fast.manage.entity.SysMenu;

import java.util.List;

/**
 * 菜单权限Mapper接口
 * 
 * @author 黄嘉浩
 * @date 2021-06-29
 */
public interface SysMenuDao extends BaseDao<SysMenu>
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
    public int deleteSysMenuById(String id);

    /**
     * 批量删除菜单权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMenuByIds(String[] ids);
}
