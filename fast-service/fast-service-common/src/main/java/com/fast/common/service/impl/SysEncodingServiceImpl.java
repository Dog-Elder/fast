package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.dao.SysEncodingDao;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.query.SysEncodingQuery;
import com.fast.common.service.ISysEncodingService;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.CustomException;
import com.fast.core.common.util.CUtil;
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
    private final ISysEncodingSetService encodingSetService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysEncodingVO> list(SysEncodingQuery query) {
        List<SysEncoding> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList, SysEncodingVO.class);
    }

    private LambdaQueryWrapper<SysEncoding> getWrapper(SysEncodingQuery query) {
        LambdaQueryWrapper<SysEncoding> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId()) && SUtil.isNotEmpty(query.getId()), SysEncoding::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getSysEncodingCode()) && SUtil.isNotEmpty(query.getSysEncodingCode()), SysEncoding::getSysEncodingCode, query.getSysEncodingCode());
        wrapper.like(Util.isNotNull(query.getSysEncodingName()) && SUtil.isNotEmpty(query.getSysEncodingName()), SysEncoding::getSysEncodingName, query.getSysEncodingName());
        wrapper.like(Util.isNotNull(query.getRemark()) && SUtil.isNotEmpty(query.getRemark()), SysEncoding::getRemark, query.getRemark());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEncodingVO> save(List<SysEncodingVO> vo) {
        List<SysEncoding> entityList = BUtil.copyList(vo, SysEncoding.class);
        List<String> codeList = CUtil.getPropertyList(entityList, SysEncoding::getSysEncodingCode);
        //查询入参重复
        List<String> repeatCode = CUtil.getDuplicateElements(codeList);
        if (CUtil.isNotEmpty(repeatCode)) {
            throw new CustomException("存在规则代码:" + repeatCode);
        }
        //查询库中是否存在
        List<SysEncoding> entityExist = list(new LambdaQueryWrapper<SysEncoding>().in(SysEncoding::getSysEncodingCode, codeList).select(SysEncoding::getSysEncodingCode));
        List<String> existCode = CUtil.getPropertyList(entityExist, SysEncoding::getSysEncodingCode);
        if (CUtil.isNotEmpty(existCode)) {
            throw new CustomException("规则代码已存在:" + existCode);
        }
        saveBatch(entityList);
        return BUtil.copyList(entityList, SysEncodingVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingVO vo) {
        SysEncoding entity = BUtil.copy(vo, SysEncoding.class);
        //查询库中数据
        SysEncoding existing = getOne(new QueryWrapper<SysEncoding>().lambda().eq(SysEncoding::getId,entity.getId()));
        Util.isNull(existing, "数据不存在!");
        //查询编码集中是否有已经启用的或者已开启的
        long validCount = encodingSetService.count(new QueryWrapper<SysEncodingSet>().lambda()
                .eq(SysEncodingSet::getSysEncodingCode, existing.getSysEncodingCode())
                .and(qry -> qry
                        .eq(SysEncodingSet::getSysEncodingSetStatus, Constants._Y)
                        .or()
                        .eq(SysEncodingSet::getSysEncodingSetUseStatus, Constants._Y)
                )
        );
        boolean b = validCount > 0 && !SUtil.equals(entity.getSysEncodingCode(), existing.getSysEncodingCode());
        Util.isTrue(b, "编码值已生效,不能修改规则代码!");
        return updateById(entity);
    }


}