package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.core.common.util.SUtil;
import com.fast.manage.dao.SysRoleMenuMapper;
import com.fast.manage.entity.SysRoleMenu;
import com.fast.manage.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色和菜单关联Service业务层处理
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService
{
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 查询角色和菜单关联
     * 
     * @param roleId 角色和菜单关联ID
     * @return 角色和菜单关联
     */
    @Override
    public SysRoleMenu selectById(Long roleId)
    {
        return sysRoleMenuMapper.selectSysRoleMenuById(roleId);
    }

    /**
     * 查询角色和菜单关联列表
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 角色和菜单关联
     */
    @Override
    public List<SysRoleMenu> list(SysRoleMenu sysRoleMenu)
    {
        return sysRoleMenuMapper.selectSysRoleMenuList(sysRoleMenu);
    }

    /**
     * 新增角色和菜单关联
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    @Transactional
    @Override
    public boolean save(SysRoleMenu sysRoleMenu)
    {
        return save(sysRoleMenu);
    }

    /**
     * 修改角色和菜单关联
     * 
     * @param sysRoleMenu 角色和菜单关联
     * @return 结果
     */
    @Transactional
    @Override
    public boolean update(SysRoleMenu sysRoleMenu)
    {
        return updateById(sysRoleMenu);
    }

    /**
     * 真删除角色和菜单关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids)
    {
        return sysRoleMenuMapper.deleteSysRoleMenuByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除角色和菜单关联信息
     * 
     * @param roleId 角色和菜单关联ID
     * @return 结果
     */
    @Override
    public int deleteSysRoleMenuById(Long roleId)
    {
        return sysRoleMenuMapper.deleteSysRoleMenuById(roleId);
    }

    /**
     * 角色和菜单关联逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }

}
