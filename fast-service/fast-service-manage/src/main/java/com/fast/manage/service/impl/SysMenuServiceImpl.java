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
@Transactional
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    /**
     * 查询菜单权限
     *
     * @param id 菜单权限ID
     * @return 菜单权限
     */
    @Override
    public SysMenu selectById(Long id) {
        return baseMapper.selectSysMenuById(id);
    }

    /**
     * 查询菜单权限列表
     *
     * @param sysMenu 菜单权限
     * @return 菜单权限
     */
    @Override
    public List<SysMenu> list(SysMenu sysMenu) {
        return baseMapper.selectSysMenuList(sysMenu);
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
        return baseMapper.deleteSysMenuByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除菜单权限信息
     *
     * @param id 菜单权限ID
     * @return 结果
     */
    @Override
    public int deleteSysMenuById(String id) {
        return baseMapper.deleteSysMenuById(id);
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
}
