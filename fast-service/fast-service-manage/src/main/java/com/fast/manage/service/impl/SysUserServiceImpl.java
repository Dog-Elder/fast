package com.fast.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.entity.base.User;
import com.fast.core.annotation.Cache;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
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
import java.util.Optional;

import static com.fast.common.constant.cache.CacheConstant.MANAGE_USER;

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
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        // 判断是否删除系统管理员
        LambdaQueryWrapper<SysUser> lambdaQuery = Wrappers.<SysUser>lambdaQuery()
                .in(SysUser::getId, idList)
                .eq(SysUser::getAdministrator, Constants.Y);
        SysUser adminUser = getOne(lambdaQuery);
        if (ObjectUtils.isNotEmpty(adminUser)) {
            throw new ServiceException("不能删除系统管理员");
        }
        return removeByIds(idList);
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
        fastRedis.setObject(SUtil.format(CacheConstant.User.INFO, AuthManageUtil.getLoginAccountType(), user.getCode()), user, CacheConstant.EXRP_DAY);
        return user;
    }
}
