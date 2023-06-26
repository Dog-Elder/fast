package com.fast.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fast.core.common.constant.AttachConstant;
import com.fast.core.common.domain.vo.AttachVO;
import com.fast.core.common.util.*;
import lombok.RequiredArgsConstructor;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.entity.SysAttach;
import com.fast.manage.query.SysAttachQuery;
import com.fast.manage.vo.SysAttachVO;
import com.fast.manage.dao.SysAttachDao;
import com.fast.manage.service.ISysAttachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统附件
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-26
 */
@Service
@RequiredArgsConstructor
public class SysAttachServiceImpl extends BaseServiceImpl<SysAttachDao, SysAttach> implements ISysAttachService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysAttachVO> list(SysAttachQuery query) {
        List<SysAttach> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysAttachVO.class);
    }

    private LambdaQueryWrapper<SysAttach> getWrapper(SysAttachQuery query){
        LambdaQueryWrapper<SysAttach> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysAttach::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getAttachCode())&& SUtil.isNotEmpty(query.getAttachCode()), SysAttach::getAttachCode, query.getAttachCode());
        wrapper.eq(Util.isNotNull(query.getAttachUuid())&& SUtil.isNotEmpty(query.getAttachUuid()), SysAttach::getAttachUuid, query.getAttachUuid());
        wrapper.like(Util.isNotNull(query.getAttachName()) && SUtil.isNotEmpty(query.getAttachName()), SysAttach::getAttachName, query.getAttachName());
        wrapper.like(Util.isNotNull(query.getAttachAlias()) && SUtil.isNotEmpty(query.getAttachAlias()), SysAttach::getAttachAlias, query.getAttachAlias());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysAttachVO> save(List<SysAttachVO> vo) {
        List<SysAttach> entityList = BUtil.copyList(vo,SysAttach.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysAttachVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysAttachVO vo) {
        SysAttach entity = BUtil.copy(vo,SysAttach.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttachVO<SysAttach> upload(MultipartFile[] files, String attachCode) {
        List<AttachVO> vos = FileUploader.upload(files, AttachConstant.SYS, AttachConstant.CURRENCY);
        if (SUtil.isBlank(attachCode)) {
            //TODO 待完成 [Huangjh,  2023-2-7 15:10]  code获取
            attachCode = UUIDDigitUtil.generateString(32);
        }
        List<SysAttach> sysAttaches = BUtil.cloneList(vos, SysAttach.class);
        for (SysAttach ele : sysAttaches) {
            ele.setAttachUuid(UUIDDigitUtil.generateString(32));
            ele.setAttachCode(attachCode);
        }
        saveBatch(sysAttaches);
        return new AttachVO<SysAttach>().setAttachCode(attachCode).setAttach(sysAttaches);
    }
}