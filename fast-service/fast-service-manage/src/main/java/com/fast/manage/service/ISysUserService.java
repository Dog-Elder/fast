package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.dto.user.SysUserDTO;
import com.fast.manage.dto.user.SysUserPasswordDTO;
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

    boolean update(SysUserDTO dto);

    /**
     * 更加用户编码获取用户
     *
     * @param code 代码
     * @return {@link SysUser}
     */
    SysUser getUserByCode(String code);

    /**
     * 初始化密码
     *
     * @param dto 用户密码DTO
     * @return boolean
     */
    boolean initializePassword(SysUserPasswordDTO dto);

    /**
     * 更新密码
     *
     * @param dto
     * @return boolean
     */
    boolean updateThePassword(SysUserPasswordDTO dto);

}