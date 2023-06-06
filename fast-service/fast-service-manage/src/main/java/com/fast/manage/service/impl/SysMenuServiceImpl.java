package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.manage.dao.SysMenuMapper;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.SUtil;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.entity.SysRoleMenu;
import com.fast.manage.service.ISysMenuService;
import com.fast.manage.service.ISysRoleMenuService;
import com.fast.manage.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限Service业务层处理
 *
 * @Author: 黄嘉浩
 * @Date: 2021-06-29
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    private final SysMenuMapper sysMenuMapper;
    private final ISysRoleService roleService;
    private final ISysRoleMenuService roleMenuService;

    /**
     * 查询菜单权限
     *
     * @param id 菜单权限ID
     * @return 菜单权限
     */
    @Override
    public SysMenu selectById(Long id) {
        return sysMenuMapper.selectSysMenuById(id);
    }

    /**
     * 查询菜单权限列表
     *
     * @param sysMenu 菜单权限
     * @return 菜单权限
     */
    @Override
    public List<SysMenu> list(SysMenu sysMenu) {
        return sysMenuMapper.selectSysMenuList(sysMenu);
    }

    /**
     * 新增菜单权限
     *
     * @param sysMenu 菜单权限
     * @return 结果
     */
    @Transactional
    @Override
    public boolean save(SysMenu sysMenu) {
        return super.save(sysMenu);
    }

    /**
     * 修改菜单权限
     *
     * @param sysMenu 菜单权限
     * @return 结果
     */
    @Transactional
    @Override
    public boolean update(SysMenu sysMenu) {
        return updateById(sysMenu);
    }

    /**
     * 真删除菜单权限对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids) {
        return sysMenuMapper.deleteSysMenuByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除菜单权限信息
     *
     * @param id 菜单权限ID
     * @return 结果
     */
    @Override
    public int deleteSysMenuById(Long id) {
        return sysMenuMapper.deleteSysMenuById(id);
    }

    /**
     * 菜单权限逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> qryPermsByUserId(Long userId, Integer userRoleType) {
        Set<Long> roleIds = roleService.qryRoleIdsByUser(userId, userRoleType);
        Set<SysMenu> menus = qryMenusByRoleIds(roleIds);
        return menus.stream().map(SysMenu::getPerms).collect(Collectors.toSet());
    }

    /**
     * 根基角色id 查询对应的 菜单
     **/
    public Set<SysMenu> qryMenusByRoleIds(Set<Long> roleIds) {
        Set<Long> menuIds = roleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                .in(SysRoleMenu::getRoleId, roleIds)).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
        return new HashSet<>(list(new LambdaQueryWrapper<SysMenu>()
                .in(CUtil.isNotEmpty(menuIds), SysMenu::getId, menuIds)));
    }
}
