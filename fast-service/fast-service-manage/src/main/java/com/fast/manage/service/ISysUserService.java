package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.SysUser;
import com.fast.manage.query.SysUserQuery;
import com.fast.manage.vo.SysUserVO;

import java.util.List;
/**
 * 后台用户
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-20
 */
public interface ISysUserService extends BaseService<SysUser> {

    List<SysUserVO> list(SysUserQuery query);

    List<SysUserVO> save(List<SysUserVO> vo);

    boolean update(SysUserVO vo);

    boolean delete(List<String> idList);
}