package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限Service接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface ISysMenuService extends BaseService<SysMenu>
{
    /**
     * 查询菜单权限
     * 
     * @param id 菜单权限ID
     * @return 菜单权限
     */
    public SysMenu selectById(Long id);

    /**
     * 修改菜单权限
     * 
     * @param sysMenu 菜单权限
     * @return 结果
     */
    public boolean update(SysMenu sysMenu);

    /**
     * 真批量删除菜单权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除菜单权限信息
     * 
     * @param id 菜单权限ID
     * @return 结果
     */
    public int deleteSysMenuById(String id);

    /**
     * 菜单权限逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);

    List<SysMenu> list(SysMenu sysMenu);
}
