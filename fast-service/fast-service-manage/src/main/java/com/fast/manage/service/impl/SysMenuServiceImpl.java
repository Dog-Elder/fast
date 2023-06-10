package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.dao.SysMenuDao;
import com.fast.core.common.util.SUtil;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单权限Service业务层处理
 *
 * @Author: 黄嘉浩
 * @Date: 2021-06-29
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu> implements ISysMenuService {

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
