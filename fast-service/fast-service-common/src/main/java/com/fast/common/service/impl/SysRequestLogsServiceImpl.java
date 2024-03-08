package com.fast.common.service.impl;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.config.secure.BaseAuthUtil;
import com.fast.common.dao.SysRequestLogsDao;
import com.fast.common.entity.sys.SysRequestLogs;
import com.fast.common.query.SysRequestLogsQuery;
import com.fast.common.service.ISysRequestLogsService;
import com.fast.common.vo.SysRequestLogsVO;
import com.fast.core.common.constant.StringPool;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void recordingSave(ApiLogEvent apiLogEvent) {
        SysRequestLogs requestLogs = BUtil.copy(apiLogEvent, SysRequestLogs.class);

        requestLogs.setTake(apiLogEvent.getDuration());

        JSONObject headerJson = requestLogs.getRequestHeaderJson();

        // 状态码
        if (SUtil.isNotBlank(apiLogEvent.getResponderBody())) {
            String code = JSONUtil.parseObj(apiLogEvent.getResponderBody()).getStr(StringPool.CODE);
            requestLogs.setResponseCode(code);
        }

        if (Util.isNull(headerJson)) {
            super.save(requestLogs);
            return;
        }

        String tokenValue = headerJson.getStr(BaseAuthUtil.getLowerCaseTokenName());
        if (SUtil.isBlank(tokenValue) || !SysRequestLogsServiceImpl.verifyToken(tokenValue)) {
            super.save(requestLogs);
            return;
        }

        // 该方法是在公共项目中,并且异步状态下无法获取当前操作人,所以需要根据token
        Object loginIdByToken = BaseAuthUtil.getLoginIdByToken(tokenValue);
        if (ObjectUtil.isEmpty(loginIdByToken)) {
            super.save(requestLogs);
            return;
        }

        String loginId = null;
        if (loginIdByToken != null) {
            loginId = loginIdByToken.toString();
        }
        String loginAccountType = BaseAuthUtil.getLoginAccountType(tokenValue);
        requestLogs.setCreateBy(loginId);
        requestLogs.setCreateByType(loginAccountType);

        super.save(requestLogs);
    }

    /**
     * 将JWT字符串拆分为3部分，无加密算法则最后一部分是""
     *
     * @param token JWT Token
     * @return boolean
     */
    private static boolean verifyToken(String token) {
        final List<String> tokens = StrUtil.split(token, CharUtil.DOT);
        if (3 != tokens.size()) {
            return false;
        }
        return true;
    }

}