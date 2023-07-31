package com.fast.common.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysEncodingDao;
import com.fast.common.dto.SysCreateCode;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.enums.code.AttachEnum;
import com.fast.common.query.SysEncodingQuery;
import com.fast.common.query.SysEncodingSetQuery;
import com.fast.common.service.ISysEncodingService;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.*;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 编码
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysEncodingServiceImpl extends ServiceImpl<SysEncodingDao, SysEncoding> implements ISysEncodingService {
    private final ISysEncodingSetService encodingSetService;
    private final ISysEncodingSetRuleService encodingSetRuleService;
    private final FastRedis redis;

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
        // 查询入参重复
        List<String> repeatCode = CUtil.getDuplicateElements(codeList);
        if (CUtil.isNotEmpty(repeatCode)) {
            throw new ServiceException("存在规则代码:" + repeatCode);
        }
        // 查询库中是否存在
        List<SysEncoding> entityExist = list(new LambdaQueryWrapper<SysEncoding>().in(SysEncoding::getSysEncodingCode, codeList).select(SysEncoding::getSysEncodingCode));
        List<String> existCode = CUtil.getPropertyList(entityExist, SysEncoding::getSysEncodingCode);
        if (CUtil.isNotEmpty(existCode)) {
            throw new ServiceException("规则代码已存在:" + existCode);
        }
        saveBatch(entityList);
        return BUtil.copyList(entityList, SysEncodingVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingVO vo) {
        SysEncoding entity = BUtil.copy(vo, SysEncoding.class);
        // 查询库中数据
        SysEncoding existing = getOne(new QueryWrapper<SysEncoding>().lambda().eq(SysEncoding::getId, entity.getId()));
        Util.isNull(existing, "数据不存在!");
        // 查询编码集中是否有已经启用的或者已开启的
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


    @Override
    public String createCode(AttachEnum attachEnum) {
        return createCode(attachEnum.getEncodingCode(), attachEnum.getEncodingSetCode());
    }

    @Override
    public String createCode(String encodingCode, String encodingSetCode) {
        return createCode(new SysCreateCode().setSysEncodingCode(encodingCode).setSysEncodingSetCode(encodingSetCode));
    }

    /**
     * 获取编码
     *
     * @param req: 生成编码请求对象
     * @Date: 2022/9/25 22:23
     * @return: java.lang.String 编码
     **/
    @Override
    public String createCode(SysCreateCode req) {
        // 使用分布式锁 避免脏数据
        String uuid = IdUtil.simpleUUID();
        String lockKey = SUtil.format(CacheConstant.SysLock.LOCK_CODE, req.getSysEncodingCode(), req.getSysEncodingSetCode());
        redis.retryTheLockLasting(lockKey, uuid, 1000, 0);

        // 业务代码-开始----------------------
        StringBuilder codeStr = new StringBuilder();
        try {
            // 检查编码集状态
            checkTheEncodingSetStatus(req);

            // 从Redis中查询是否已经使用
            String ruleOpen = SUtil.format(CacheConstant.SysSetRule.CODE_USE_OPEN, req.getSysEncodingCode(), req.getSysEncodingSetCode());

            // 编码集使用状态
            String sysEncodingSetStatus = redis.getString(ruleOpen);

            // 编码集状态不存在
            if (SUtil.isBlank(sysEncodingSetStatus)) {
                // 编码集未关闭
                disposeRule(codeStr, req);
                // 处理编码集 状态
                SysEncodingSet sysEncodingSet = encodingSetService.getOne(new QueryWrapper<SysEncodingSet>().lambda()
                        .eq(SysEncodingSet::getSysEncodingCode, req.getSysEncodingCode())
                        .eq(SysEncodingSet::getSysEncodingSetCode, req.getSysEncodingSetCode())
                );
                boolean isSetRes = encodingSetService.updateById(sysEncodingSet.setSysEncodingSetUseStatus(Constants._Y));
                if (!isSetRes) {
                    // 释放分布式锁
                    redis.releaseTheLock(lockKey, uuid);
                    throw new ServiceException("生成编码操作失败,版本不一致!");
                }
                // Redis处理编码集 状态
                redis.setString(ruleOpen, Constants._Y);
                return codeStr.toString();
            }

            // 编码集状态存在 获取code
            disposeRule(codeStr, req);
            // 业务代码-结束----------------------
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            // 释放分布式锁
            redis.releaseTheLock(lockKey, uuid);
        }
        return codeStr.toString();
    }

    /**
     * 检查编码设置状态
     *
     * @param req 要求事情
     */
    private void checkTheEncodingSetStatus(SysCreateCode req) {
        // 查询编码集状态是否关闭
        String ruleStatusIn = SUtil.format(CacheConstant.SysSetRule.CODE_STATUS, req.getSysEncodingCode(), req.getSysEncodingSetCode());
        String ruleStatus = redis.getString(ruleStatusIn);

        // 编码集不存在
        if (SUtil.isBlank(ruleStatus)) {
            // 避免编码集不存在频繁调用数据库增加 编码集不存在缓存查询
            String doesNotExistKeyPath = SUtil.format(CacheConstant.SysSetRule.CODE_STATUS_NO_EXIST, req.getSysEncodingCode(), req.getSysEncodingSetCode());
            String doesNotExist = redis.getString(doesNotExistKeyPath);
            // 编码集不存在
            if (SUtil.equals(Constants._Y, doesNotExist)) {
                SysEncodingServiceImpl.encodingException(req);
            }
            SysEncodingSet sysEncodingSet = encodingSetService.get(new SysEncodingSetQuery().setSysEncodingCode(req.getSysEncodingCode()).setSysEncodingSetCode(req.getSysEncodingSetCode()));
            if (Util.isNull(sysEncodingSet)) {
                // 避免编码集不存在频繁调用数据库增加 编码集不存在增加缓存
                redis.setString(doesNotExistKeyPath, Constants._Y);
                SysEncodingServiceImpl.encodingException(req);
            }
            // 刷新规则编码
            encodingSetService.refreshCache(sysEncodingSet.getId());
            return;
        }

        // 编码集已关闭
        if (SUtil.equals(ruleStatus, Constants._N)) {
            SysEncodingServiceImpl.encodingException(req);
        }
    }

    /**
     * 编码异常
     *
     * @param req 要求事情
     */
    private static void encodingException(SysCreateCode req) {
        SysEncodingServiceImpl.log.error("获取编码异常,编码集状态已关闭! 规则代码:{} 编码值:{}", req.getSysEncodingCode(), req.getSysEncodingSetCode());
        throw new ServiceException("编码集已关闭,请联系管理员!");
    }

    // 获取规则 处理规则
    private void disposeRule(StringBuilder codeStr, SysCreateCode req) {
        SysEncodingSetRule sysEncodingSetRuleDTO = new SysEncodingSetRule()
                .setSysEncodingCode(req.getSysEncodingCode())
                .setSysEncodingSetCode(req.getSysEncodingSetCode());
        //  获取编码段集合
        List<SysEncodingSetRule> sysEncodingSetRules = getCodeSnippetList(sysEncodingSetRuleDTO);
        sysEncodingSetRules = CUtil.asc(sysEncodingSetRules, SysEncodingSetRule::getSysEncodingSetRuleNumber);
        // 匹配规则 拼接编码
        sysEncodingSetRules.forEach(ele -> {
            matchGenerateCode(codeStr, req, ele);
        });
        // TODO 作者:黄嘉浩 2022/9/28 0:41 处理Redis值和数据库值 未来放到队列中不影响正常业务也避免了并发问题
        Optional<SysEncodingSetRule> optional = sysEncodingSetRules.stream().filter(ele -> "NUMBER".equals(ele.getSysEncodingSetRuleType())).findAny();
        if (optional.isPresent()) {
            SysEncodingSetRule numberRule = optional.get();
            // TODO 作者:黄嘉浩  [54.19% 3.7237ms ] com.xxxxx.service.impl.SysEncodingSetRuleServiceImpl:updateById() #146
            encodingSetRuleService.updateById(numberRule);
            // 修改Redis
            String ruleIn = SUtil.format(CacheConstant.SysSetRule.CODE_RULE, req.getSysEncodingCode(), req.getSysEncodingSetCode());
            JSONObject ruleJsonObject = JSONUtil.parseObj(numberRule);
            redis.setHash(ruleIn, numberRule.getId(), ruleJsonObject.toString());
        }
    }

    // 获取编码片段集合
    private List<SysEncodingSetRule> getCodeSnippetList(SysEncodingSetRule req) {
        // 从Redis中取
        String ruleIn = SUtil.format(CacheConstant.SysSetRule.CODE_RULE, req.getSysEncodingCode(), req.getSysEncodingSetCode());
        List<String> hvals = redis.getAllHashValues(ruleIn);
        // 如果redis中没有数据 查库并更新redis
        if (CUtil.isEmpty(hvals)) {
            List<SysEncodingSetRule> list = encodingSetRuleService.list(new LambdaQueryWrapper<SysEncodingSetRule>()
                    .eq(SUtil.isNotEmpty(req.getSysEncodingCode()), SysEncodingSetRule::getSysEncodingCode, req.getSysEncodingCode())
                    .eq(SUtil.isNotEmpty(req.getSysEncodingSetCode()), SysEncodingSetRule::getSysEncodingSetCode, req.getSysEncodingSetCode()));
            list.forEach(ele -> {
                // 针对缓存丢失清空 需要把原有的缓存序列更新为最新值 如果没有使用 则还用起始值
                ele.setSysEncodingSetInitialValue(Optional.ofNullable(ele.getSysEncodingSetNowValue()).orElse(ele.getSysEncodingSetInitialValue()));
                JSONObject ruleJsonObject = JSONUtil.parseObj(ele);
                redis.setHash(ruleIn, String.valueOf(ele.getId()), ruleJsonObject.toString());
            });
            return list;
        }
        return CUtil.jsonListStrToList(hvals, SysEncodingSetRule.class);
    }

    // 匹配规则 生成编码
    private void matchGenerateCode(StringBuilder codeStr, SysCreateCode req, SysEncodingSetRule ele) {
        switch (ele.getSysEncodingSetRuleType()) {
            // 常量
            case "CONSTANT":
                codeStr.append(ele.getSysEncodingSetRuleSectionCode());
                break;
            // 序列
            case "NUMBER":
                String ruleNumberIn = SUtil.format(CacheConstant.SysSetRule.CODE_NUMBER, req.getSysEncodingCode(), req.getSysEncodingSetCode());
                long number = getNumber(ruleNumberIn, ele);
                // 根据位数前置前置补零
                codeStr.append(SUtil.zeroPr(number, ele.getSysEncodingSetRuleDigit()));
                ele.setSysEncodingSetNowValue(number);
                break;
            // UUID(8位数)
            case "UUID_8":
                codeStr.append(UUIDDigitUtil.generateString(8));
                break;
            // UUID(16位数)
            case "UUID_16":
                codeStr.append(UUIDDigitUtil.generateString(16));
                break;
            // UUID(22位数)
            case "UUID_22":
                codeStr.append(UUIDDigitUtil.generateString(22));
                break;
            // UUID(32位数)
            case "UUID_32":
                codeStr.append(UUIDDigitUtil.generateString(32));
                break;
            // 日期(yyyyMMdd)
            case "DATE_yyyyMMdd":
                codeStr.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                break;
            // 日期(yyyyMM)
            case "DATE_yyyyMM":
                codeStr.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
                break;
            // 日期(yyyy)
            case "DATE_yyyy":
                codeStr.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));
                break;
            default:
                SysEncodingServiceImpl.log.error("获取编码异常,未匹配到对应的规则段类型! 规则代码:{} 编码值:{}", req.getSysEncodingCode(), req.getSysEncodingSetCode());
                throw new ServiceException("未匹配到对应的规则段类型!");
        }
    }

    // 获取序列
    private long getNumber(String ruleNumberIn, SysEncodingSetRule rule) {
        // 查询序列值开始值是否存在
        Boolean exists = redis.exists(ruleNumberIn);
        Long number = null;
        // 不存在
        if (Boolean.FALSE.equals(exists)) {
            // 将开始值存入
            redis.setString(ruleNumberIn, rule.getSysEncodingSetInitialValue().toString());
            // 直接 取开始值 不进行自增
            number = rule.getSysEncodingSetInitialValue();
        }
        // 假如 初始值是1 那就从1开始使用
        if (Util.isNull(number)) {
            number = redis.incrementBy(ruleNumberIn, 1);
        }
        return number;
    }
}