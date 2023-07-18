package com.fast.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.config.secure.BaseAuthUtil;
import com.fast.common.dao.SysRequestLogsDao;
import com.fast.common.entity.sys.SysRequestLogs;
import com.fast.common.query.SysRequestLogsQuery;
import com.fast.common.service.ISysRequestLogsService;
import com.fast.common.vo.SysRequestLogsVO;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.log.event.ApiLogEvent;
import com.fast.core.log.service.LogService;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class SysRequestLogsServiceImpl extends BaseServiceImpl<SysRequestLogsDao, SysRequestLogs> implements ISysRequestLogsService, LogService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysRequestLogsVO> list(SysRequestLogsQuery query) {
        List<SysRequestLogs> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList, SysRequestLogsVO.class);
    }

    private LambdaQueryWrapper<SysRequestLogs> getWrapper(SysRequestLogsQuery query) {
        LambdaQueryWrapper<SysRequestLogs> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId()) && SUtil.isNotEmpty(query.getId()), SysRequestLogs::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getIp()) && SUtil.isNotEmpty(query.getIp()), SysRequestLogs::getIp, query.getIp());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    /**
     * 记录保存
     *
     * @param apiLogEvent api日志事件
     */
    @Override
    public void recordingSave(ApiLogEvent apiLogEvent) {
        SysRequestLogs requestLogs = BUtil.copy(apiLogEvent, SysRequestLogs.class);
        requestLogs.setTake(apiLogEvent.getDuration());

        JSONObject headerJson = requestLogs.getRequestHeaderJson();

        String tokenValue = headerJson.getStr(BaseAuthUtil.getLowerCaseTokenName());
        if (SUtil.isBlank(tokenValue)) {
            super.save(requestLogs);
            return;
        }

        // 该方法是在公共项目中,并且异步状态下无法获取当前操作人,所以需要根据token
        Object loginIdByToken = BaseAuthUtil.getLoginIdByToken(tokenValue);
        if (ObjectUtil.isEmpty(loginIdByToken)) {
            super.save(requestLogs);
            return;
        }

        String loginId = loginIdByToken.toString();
        String loginAccountType = BaseAuthUtil.getLoginAccountType(tokenValue);
        requestLogs.setCreateBy(loginId);
        requestLogs.setCreateByType(loginAccountType);

        super.save(requestLogs);
    }
}