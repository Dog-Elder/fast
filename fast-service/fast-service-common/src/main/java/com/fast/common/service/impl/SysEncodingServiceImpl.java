package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.dao.SysEncodingDao;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.query.SysEncodingQuery;
import com.fast.common.service.ISysEncodingService;
import com.fast.common.vo.SysEncodingVO;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 编码
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Service
@AllArgsConstructor
public class SysEncodingServiceImpl extends BaseServiceImpl<SysEncodingDao, SysEncoding> implements ISysEncodingService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysEncodingVO> list(SysEncodingQuery query) {
        List<SysEncoding> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysEncodingVO.class);
    }

    private LambdaQueryWrapper<SysEncoding> getWrapper(SysEncodingQuery query){
        LambdaQueryWrapper<SysEncoding> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysEncoding::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getSysEncodingCode())&& SUtil.isNotEmpty(query.getSysEncodingCode()), SysEncoding::getSysEncodingCode, query.getSysEncodingCode());
        wrapper.like(Util.isNotNull(query.getSysEncodingName()) && SUtil.isNotEmpty(query.getSysEncodingName()), SysEncoding::getSysEncodingName, query.getSysEncodingName());
        wrapper.like(Util.isNotNull(query.getRemark()) && SUtil.isNotEmpty(query.getRemark()), SysEncoding::getRemark, query.getRemark());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEncodingVO> save(List<SysEncodingVO> vo) {
        List<SysEncoding> entityList = BUtil.copyList(vo,SysEncoding.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysEncodingVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingVO vo) {
        SysEncoding entity = BUtil.copy(vo,SysEncoding.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

}