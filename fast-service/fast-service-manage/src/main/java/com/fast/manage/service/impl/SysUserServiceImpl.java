package com.fast.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.entity.base.User;
import com.fast.common.vo.UserInfoVO;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.*;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.FastRedis;
import com.fast.manage.config.security.secure.AuthManageUtil;
import com.fast.manage.dao.SysUserDao;
import com.fast.manage.entity.SysUser;
import com.fast.manage.query.SysUserQuery;
import com.fast.manage.service.ISysUserService;
import com.fast.manage.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台用户
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-20
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements ISysUserService {

    private final FastRedis fastRedis;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)

    public List<SysUserVO> list(SysUserQuery query) {
        List<SysUser> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList, SysUserVO.class);
    }

    private LambdaQueryWrapper<SysUser> getWrapper(SysUserQuery query) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId()) && SUtil.isNotEmpty(query.getId()), SysUser::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getCode()) && SUtil.isNotEmpty(query.getCode()), SysUser::getCode, query.getCode());
        wrapper.eq(Util.isNotNull(query.getUsername()) && SUtil.isNotEmpty(query.getUsername()), SysUser::getUsername, query.getUsername());
        wrapper.like(Util.isNotNull(query.getNickname()) && SUtil.isNotEmpty(query.getNickname()), SysUser::getNickname, query.getNickname());
        wrapper.eq(Util.isNotNull(query.getEmail()) && SUtil.isNotEmpty(query.getEmail()), SysUser::getEmail, query.getEmail());
        wrapper.like(Util.isNotNull(query.getRealName()) && SUtil.isNotEmpty(query.getRealName()), SysUser::getRealName, query.getRealName());
        wrapper.eq(Util.isNotNull(query.getSex()), SysUser::getSex, query.getSex());
        wrapper.eq(Util.isNotNull(query.getPhoneNumber()) && SUtil.isNotEmpty(query.getPhoneNumber()), SysUser::getPhoneNumber, query.getPhoneNumber());
        wrapper.eq(Util.isNotNull(query.getStatus()), SysUser::getStatus, query.getStatus());
        return wrapper;
    }

    /**
     * 新增后台用户
     *
     * @param vo 后台用户
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysUserVO> save(List<SysUserVO> vo) {
        List<SysUser> entityList = BUtil.copyList(vo, SysUser.class);
        List<String> username = CUtil.getPropertyList(entityList, User::getUsername);
        List<String> elements = CUtil.getDuplicateElements(username);
        if (CUtil.isNotEmpty(elements)) {
            throw new ServiceException("用户名重复:" + elements);
        }
        List<SysUser> elementUser = list(new LambdaQueryWrapper<SysUser>().select(User::getUsername).in(User::getUsername, username));
        if (CUtil.isNotEmpty(elementUser)) {
            throw new ServiceException("用户名已存在:" + CUtil.getPropertyList(elementUser, SysUser::getUsername));
        }
        saveBatch(entityList);
        return BUtil.copyList(entityList, SysUserVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysUserVO vo) {
        SysUser entity = BUtil.copy(vo, SysUser.class);
        return updateById(entity);
    }

    @Override
    public SysUser getUserByCode(String code) {
        SysUser user = fastRedis.getObject(AuthManageUtil.getUserInfoKeyPath(code), SysUser.class);
        if (Util.isNotNull(user)) {
            return user;
        }
        // 缓存不存在则查库
        user = getOne(getWrapper(new SysUserQuery().setCode(code)));
        // 更新缓存
        fastRedis.setObject(SUtil.format(CacheConstant.User.INFO, AuthManageUtil.getLoginAccountType(), user.getCode()), user, CacheConstant.ONE_DAY);
        return user;
    }

    /**
     * 初始化密码
     **/
    @Override
    public boolean initializePassword(UserInfoVO vo) {
        //TODO 作者:黄嘉浩 初始化密码
        return false;
    }

    /**
     * 更新密码
     *
     * @param vo 签证官
     * @return boolean
     */
    @Override
    public boolean updateThePassword(UserInfoVO vo) {
        String oldPassword = vo.getPassword();
        SysUser user = getById(vo.getId());
        // 校验密码
        if (!Md5Util.verifyPassword(oldPassword, user.getPassword())) {
            throw new ServiceException("原密码错误!");
        }
        updateUserInfo(vo);
        return false;
    }

    /**
     * 更新用户信息
     *
     * @param vo 签证官
     * @return boolean
     */
    @Override
    public boolean updateUserInfo(UserInfoVO vo) {
        SysUser user = BUtil.copy(vo, SysUser.class);
        return updateById(user);
    }
}
