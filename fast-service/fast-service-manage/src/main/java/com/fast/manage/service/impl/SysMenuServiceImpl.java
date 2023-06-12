package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.dao.SysMenuDao;
import com.fast.core.common.util.SUtil;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.query.SysMenuQuery;
import com.fast.manage.service.ISysMenuService;
import com.fast.manage.vo.SysMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysMenuVO> list(SysMenuQuery query) {
        List<SysMenu> entityList = list(getWrapper(query));
        return BUtil.cloneList(entityList,SysMenuVO.class);
    }

    private LambdaQueryWrapper<SysMenu> getWrapper(SysMenuQuery query){
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysMenu::getId, query.getId());
        wrapper.like(Util.isNotNull(query.getName()) && SUtil.isNotEmpty(query.getName()), SysMenu::getName, query.getName());
        wrapper.eq(Util.isNotNull(query.getParentId())&& SUtil.isNotEmpty(query.getParentId()), SysMenu::getParentId, query.getParentId());
        wrapper.eq(Util.isNotNull(query.getTarget())&& SUtil.isNotEmpty(query.getTarget()), SysMenu::getTarget, query.getTarget());
        wrapper.eq(Util.isNotNull(query.getMenuType())&& SUtil.isNotEmpty(query.getMenuType()), SysMenu::getMenuType, query.getMenuType());
        wrapper.eq(Util.isNotNull(query.getVisible())&& SUtil.isNotEmpty(query.getVisible()), SysMenu::getVisible, query.getVisible());
        wrapper.eq(Util.isNotNull(query.getIsRefresh())&& SUtil.isNotEmpty(query.getIsRefresh()), SysMenu::getIsRefresh, query.getIsRefresh());
        wrapper.eq(Util.isNotNull(query.getPerms())&& SUtil.isNotEmpty(query.getPerms()), SysMenu::getPerms, query.getPerms());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysMenuVO> save(List<SysMenuVO> vo) {
        List<SysMenu> entityList = BUtil.copyList(vo,SysMenu.class);
        saveBatch(entityList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysMenuVO vo) {
        SysMenu entity = BUtil.copy(vo,SysMenu.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

}
