package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.core.annotation.Cache;
import com.fast.core.util.FastRedis;
import com.fast.manage.dao.SysRoleMapper;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.entity.SysRole;
import com.fast.manage.entity.SysRoleMenu;
import com.fast.manage.entity.SysUserRole;
import com.fast.manage.service.ISysMenuService;
import com.fast.manage.service.ISysRoleMenuService;
import com.fast.manage.service.ISysRoleService;
import com.fast.manage.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fast.common.constant.cache.CacheConstant.MANAGE;

/**
 * 角色Service业务层处理
 *
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    private final ISysUserRoleService userRoleService;
    private final ISysRoleMenuService roleMenuService;
    private final ISysMenuService menuService;
    private final FastRedis fastRedis;

    /**
     * 查询角色
     *
     * @param id 角色ID
     * @return 角色
     */
    @Override

    public SysRole selectById(String id) {
        return baseMapper.selectSysRoleById(id);
    }

    /**
     * 查询角色列表
     *
     * @param sysRole 角色
     * @return 角色
     */
    @Override
    public List<SysRole> list(SysRole sysRole) {
        return baseMapper.selectSysRoleList(sysRole);
    }


    /**
     * 真删除角色对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids) {
        return baseMapper.deleteSysRoleByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除角色信息
     *
     * @param id 角色ID
     * @return 结果
     */
    @Override
    public int deleteSysRoleById(String id) {
        return baseMapper.deleteSysRoleById(id);
    }

    /**
     * 角色逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }


    @Override
    public Set<SysRole> qryRolesByUser(String id, Integer roleType) {
        if (SUtil.isBlank(id)) {
            return new HashSet<>();
        }
        Set<String> roleIds = userRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, id)
                        .eq(roleType != null, SysUserRole::getType, roleType))
                .stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        return new HashSet<>(list(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, roleIds)));
    }

    @Override
    public Set<String> qryRoleIdsByUser(String id, Integer roleType) {
        return qryRolesByUser(id, roleType).stream().map(SysRole::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<String> qryRoleKeysByUser(String id, Integer roleType) {
        return qryRolesByUser(id, roleType).stream().map(SysRole::getKey).collect(Collectors.toSet());
    }

    @Override
    public List<String> qryPermsById(String userId) {
        //获取用户所有对应角色
        List<SysUserRole> sysUserRoles = listUserRoleAll();
        Set<String> roleIds = sysUserRoles.stream().filter(ele -> userId.equals(ele.getUserId())).map(SysUserRole::getRoleId).collect(Collectors.toSet());
        //获取角色对应权限
        List<SysRoleMenu> sysRoleMenus = listRoleMenuAll();
        Set<String> menuIds = sysRoleMenus.stream().filter(ele -> roleIds.contains(ele.getRoleId())).map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
        //获取菜单权限
        List<SysMenu> menus = listMenuAll();
        return menus.stream().filter(ele -> menuIds.contains(ele.getId())).map(SysMenu::getPerms).collect(Collectors.toList());
    }

    @Override
    public List<String> qryRoleById(String userId) {
        //获取用户所有对应角色
        List<SysUserRole> sysUserRoles = listUserRoleAll();
        Set<String> roleIds = sysUserRoles.stream().filter(ele -> userId.equals(ele.getUserId())).map(SysUserRole::getRoleId).collect(Collectors.toSet());
        //获取角色对应权限
        List<SysRole> roles = listRoleAll();
        return roles.stream().filter(ele -> roleIds.contains(ele.getId())).map(SysRole::getKey).collect(Collectors.toList());
    }

    @Cache(MANAGE + "role")
    List<SysRole> listRoleAll() {
        return super.list();
    }

    @Cache(MANAGE + "user_role")
    List<SysUserRole> listUserRoleAll() {
        return userRoleService.list();
    }
    @Cache(MANAGE + "role_menu")
    List<SysRoleMenu> listRoleMenuAll() {
        return roleMenuService.list();
    }
    @Cache(MANAGE + "menu")
    List<SysMenu> listMenuAll() {
        return menuService.list();
    }

}
