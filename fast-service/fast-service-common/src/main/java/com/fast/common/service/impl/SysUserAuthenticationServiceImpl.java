package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.dao.SysUserAuthenticationDao;
import com.fast.common.entity.SysUserAuthentication;
import com.fast.common.query.SysUserAuthenticationQuery;
import com.fast.common.service.ISysUserAuthenticationService;
import com.fast.common.vo.SysUserAuthenticationVO;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户认证管理
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-22
 */
@Service
@RequiredArgsConstructor
public class SysUserAuthenticationServiceImpl extends BaseServiceImpl<SysUserAuthenticationDao, SysUserAuthentication> implements ISysUserAuthenticationService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysUserAuthenticationVO> list(SysUserAuthenticationQuery query) {
        List<SysUserAuthentication> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysUserAuthenticationVO.class);
    }

    private LambdaQueryWrapper<SysUserAuthentication> getWrapper(SysUserAuthenticationQuery query){
        LambdaQueryWrapper<SysUserAuthentication> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysUserAuthentication::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getUserCode())&& SUtil.isNotEmpty(query.getUserCode()), SysUserAuthentication::getUserCode, query.getUserCode());
        wrapper.eq(Util.isNotNull(query.getUsername())&& SUtil.isNotEmpty(query.getUsername()), SysUserAuthentication::getUsername, query.getUsername());
        wrapper.eq(Util.isNotNull(query.getPasswordHash())&& SUtil.isNotEmpty(query.getPasswordHash()), SysUserAuthentication::getPasswordHash, query.getPasswordHash());
        wrapper.eq(Util.isNotNull(query.getEmail())&& SUtil.isNotEmpty(query.getEmail()), SysUserAuthentication::getEmail, query.getEmail());
        wrapper.eq(Util.isNotNull(query.getPhone())&& SUtil.isNotEmpty(query.getPhone()), SysUserAuthentication::getPhone, query.getPhone());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysUserAuthenticationVO> save(List<SysUserAuthenticationVO> vo) {
        List<SysUserAuthentication> entityList = BUtil.copyList(vo,SysUserAuthentication.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysUserAuthenticationVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysUserAuthenticationVO vo) {
        SysUserAuthentication entity = BUtil.copy(vo,SysUserAuthentication.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    /**
     * 根据编码
     *
     * @param code {@link com.fast.manage.entity.SysUser#code}
     * @return {@link SysUserAuthenticationVO}
     */
    @Override
    public SysUserAuthenticationVO getByCode(String code) {
        SysUserAuthentication entity = getOne(getWrapper(new SysUserAuthenticationQuery().setUserCode(code)));
        return BUtil.copy(entity, SysUserAuthenticationVO.class);
    }

}