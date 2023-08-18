package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.query.SysMenuQuery;
import com.fast.manage.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单权限Service接口
 *
 * @author 黄嘉浩
 * @date 2021-06-29
 */
public interface ISysMenuService extends BaseService<SysMenu> {
    List<SysMenuVO> list(SysMenuQuery query);

    List<SysMenuVO> save(List<SysMenuVO> vo);

    boolean update(SysMenuVO vo);

    boolean delete(List<String> idList);

    /**
     * 所有列表菜单
     *
     * @return {@link List}<{@link SysMenu}>
     */
    List<SysMenu> listMenuAll();

    List<SysMenuVO> tree(SysMenuQuery query);
}
