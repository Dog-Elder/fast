package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fast.common.dao.SysRequestLogsDao;
import com.fast.common.entity.sys.SysRequestLogs;
import com.fast.common.query.SysRequestLogsQuery;
import com.fast.common.service.ISysRequestLogsService;
import com.fast.common.vo.SysRequestLogsVO;
import com.fast.core.log.event.ApiLogEvent;
import com.fast.core.log.service.LogService;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.safe.util.ManageUtil;
import lombok.RequiredArgsConstructor;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.bean.BUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.SUtil;

import java.util.List;

/**
 * 接口请求日志
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-07-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRequestLogsServiceImpl extends BaseServiceImpl<SysRequestLogsDao, SysRequestLogs> implements ISysRequestLogsService , LogService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysRequestLogsVO> list(SysRequestLogsQuery query) {
        List<SysRequestLogs> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysRequestLogsVO.class);
    }

    private LambdaQueryWrapper<SysRequestLogs> getWrapper(SysRequestLogsQuery query){
        LambdaQueryWrapper<SysRequestLogs> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysRequestLogs::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getIp())&& SUtil.isNotEmpty(query.getIp()), SysRequestLogs::getIp, query.getIp());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    @Override
    public void recordingSave(ApiLogEvent apiLogEvent) {
        SysRequestLogs requestLogs = BUtil.copy(apiLogEvent, SysRequestLogs.class);
        requestLogs.setTake(apiLogEvent.getDuration());
        super.save(requestLogs);
    }
}