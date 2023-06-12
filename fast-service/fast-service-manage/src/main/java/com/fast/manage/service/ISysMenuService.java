package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.query.SysMenuQuery;
import com.fast.manage.vo.SysMenuVO;

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
    List<SysMenuVO> list(SysMenuQuery query);

    List<SysMenuVO> save(List<SysMenuVO> vo);

    boolean update(SysMenuVO vo);

    boolean delete(List<String> idList);
}
