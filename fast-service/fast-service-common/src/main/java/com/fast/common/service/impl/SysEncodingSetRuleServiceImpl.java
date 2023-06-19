package com.fast.common.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysEncodingSetRuleDao;
import com.fast.common.dto.SysCreateCode;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.query.SysEncodingSetRuleQuery;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingSetRuleVO;
import com.fast.common.vo.SysEncodingSetVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.CustomException;
import com.fast.core.common.util.*;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.FastRedis;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 编码段
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysEncodingSetRuleServiceImpl extends BaseServiceImpl<SysEncodingSetRuleDao, SysEncodingSetRule> implements ISysEncodingSetRuleService {
    private final FastRedis redis;
    @Lazy
    @Resource
    private ISysEncodingSetService encodingSetService;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysEncodingSetRuleVO> list(SysEncodingSetRuleQuery query) {
        SysEncodingSetRule entity = BUtil.copy(query, SysEncodingSetRule.class);
        //从Redis中取
        List<SysEncodingSetRule> sysEncodingSetRules = getCacheList(entity);
        //查询不为空则分页返回
        if (CUtil.isNotEmpty(sysEncodingSetRules)) {
            Stream<SysEncodingSetRule> qry = sysEncodingSetRules.stream();
            qry = qry.filter(ele -> SUtil.filterContains(ele.getSysEncodingCode(), query.getSysEncodingCode()));
            qry = qry.filter(ele -> SUtil.filterContains(ele.getSysEncodingSetCode(), query.getSysEncodingSetCode()));
            qry = qry.filter(ele -> SUtil.filterContains(ele.getSysEncodingSetRuleType(), query.getSysEncodingSetRuleType()));
            return PageUtils.createPage(qry, SysEncodingSetRuleVO.class);
        }
        String ruleIn = SUtil.format(CacheConstant.SysSetRule._IN, query.getSysEncodingSetCode(), query.getSysEncodingSetCode());
        //存放Redis
        taskExecutor.execute(() -> {
            List<SysEncodingSetRule> list = list(new LambdaQueryWrapper<SysEncodingSetRule>()
                    .eq(SUtil.isNotEmpty(query.getSysEncodingCode()), SysEncodingSetRule::getSysEncodingCode, query.getSysEncodingCode())
                    .eq(SUtil.isNotEmpty(query.getSysEncodingSetCode()), SysEncodingSetRule::getSysEncodingSetCode, query.getSysEncodingSetCode()));
            list.forEach(ele -> {
                JSONObject ruleJsonObject = JSONUtil.parseObj(ele);
                redis.setHash(ruleIn, String.valueOf(ele.getId()), ruleJsonObject.toString());
            });
        });
        //启动分页
        PageUtils.startPage();
        List<SysEncodingSetRule> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList, SysEncodingSetRuleVO.class);
    }

    private LambdaQueryWrapper<SysEncodingSetRule> getWrapper(SysEncodingSetRuleQuery query) {
        LambdaQueryWrapper<SysEncodingSetRule> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId()) && SUtil.isNotEmpty(query.getId()), SysEncodingSetRule::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getSysEncodingCode()) && SUtil.isNotEmpty(query.getSysEncodingCode()), SysEncodingSetRule::getSysEncodingCode, query.getSysEncodingCode());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetCode()) && SUtil.isNotEmpty(query.getSysEncodingSetCode()), SysEncodingSetRule::getSysEncodingSetCode, query.getSysEncodingSetCode());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetRuleNumber()), SysEncodingSetRule::getSysEncodingSetRuleNumber, query.getSysEncodingSetRuleNumber());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysEncodingSetRuleVO save(SysEncodingSetRuleVO vo) {
        SysEncodingSetRule entity = BUtil.copy(vo, SysEncodingSetRule.class);
        SysEncodingSet sysEncodingSet = checkToUse(entity);
        //查询序号是否已经存在
        SysEncodingSetRule setRule = getOne(new QueryWrapper<SysEncodingSetRule>().lambda()
                .eq(SysEncodingSetRule::getSysEncodingSetRuleNumber, entity.getSysEncodingSetRuleNumber())
                .eq(SysEncodingSetRule::getSysEncodingCode, entity.getSysEncodingCode())
                .eq(SysEncodingSetRule::getSysEncodingSetCode, entity.getSysEncodingSetCode())
        );
        Util.isNotNull(setRule, "序号已存在");
        //匹配规则
        matchingRule(entity);
        //保存规则
        save(entity);
        if (!encodingSetService.update(BUtil.copy(sysEncodingSet, SysEncodingSetVO.class))) {
            throw new CustomException("编码集版本不一致请重新提交");
        }
        //存放Redis
        String ruleIn = SUtil.format(CacheConstant.SysSetRule._IN, entity.getSysEncodingSetCode(), entity.getSysEncodingSetCode());
        JSONObject ruleJsonObject = JSONUtil.parseObj(entity);
        redis.setHash(ruleIn, entity.getId(), ruleJsonObject.toString());
        return BUtil.copy(entity, SysEncodingSetRuleVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingSetRuleVO vo) {
        SysEncodingSetRule entity = BUtil.copy(vo, SysEncodingSetRule.class);
        SysEncodingSet sysEncodingSet = checkToUse(entity);
        //查询序号是否已经存在
        SysEncodingSetRule setRule = getOne(new QueryWrapper<SysEncodingSetRule>().lambda()
                .eq(SysEncodingSetRule::getSysEncodingSetRuleNumber, entity.getSysEncodingSetRuleNumber())
                .eq(SysEncodingSetRule::getSysEncodingCode, entity.getSysEncodingCode())
                .eq(SysEncodingSetRule::getSysEncodingSetCode, entity.getSysEncodingSetCode())
                .ne(SysEncodingSetRule::getId, entity.getId())
        );
        Util.isNotNull(setRule, "序号已存在");
        //匹配规则
        matchingRule(entity);
        if (!encodingSetService.update(BUtil.copy(sysEncodingSet,SysEncodingSetVO.class))) {
            throw new CustomException("编码集版本不一致请重新提交");
        }
        //存放Redis
        String ruleIn = SUtil.format(CacheConstant.SysSetRule._IN, entity.getSysEncodingSetCode(), entity.getSysEncodingSetCode());
        JSONObject ruleJsonObject = JSONUtil.parseObj(entity);
        redis.setHash(ruleIn, entity.getId(), ruleJsonObject.toString());
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    /**
     * 获取编码
     *
     * @param req: 生成编码请求对象
     * @Date: 2022/9/25 22:23
     * @return: java.lang.String 编码
     **/
    public String createCode(SysCreateCode req) {
        //使用分布式锁 避免脏数据
        String uuid = IdUtil.simpleUUID();
        String lockKey = SUtil.format(CacheConstant.SysLock._CODE_IN, req.getSysEncodingCode(), req.getSysEncodingSetCode());
        redis.retryTheLockLasting(lockKey, uuid, 1000, 0);

        //业务代码-开始----------------------
        StringBuilder codeStr = new StringBuilder();
        try {
            //查询编码集状态是否关闭
            String ruleStatusIn = SUtil.format(CacheConstant.SysSetRule._STATUS, req.getSysEncodingCode(), req.getSysEncodingSetCode());
            String ruleStatus = redis.getString(ruleStatusIn);
            //编码集不存在
            if (Util.isNull(ruleStatus)) {
                log.error("获取编码异常,编码集不存在! 规则代码:{} 编码值:{}", req.getSysEncodingCode(), req.getSysEncodingSetCode());
                throw new CustomException("编码集不存在,请联系管理员!");
            }
            //编码集已关闭
            if (SUtil.equals(ruleStatus, Constants._N)) {
                log.error("获取编码异常,编码集状态已关闭! 规则代码:{} 编码值:{}", req.getSysEncodingCode(), req.getSysEncodingSetCode());
                throw new CustomException("编码集已关闭,请联系管理员!");
            }

            //从Redis中查询是否已经使用
            String ruleOpen = SUtil.format(CacheConstant.SysSetRule._USE_OPEN, req.getSysEncodingCode(), req.getSysEncodingSetCode());
            //编码集使用状态
            String sysEncodingSetStatus = redis.getString(ruleOpen);
            //编码集状态不存在
            if (SUtil.isBlank(sysEncodingSetStatus)) {
                //编码集未关闭
                disposeRule(codeStr, req);
                //处理编码集 状态
                SysEncodingSet sysEncodingSet = encodingSetService.getOne(new QueryWrapper<SysEncodingSet>().lambda()
                        .eq(SysEncodingSet::getSysEncodingCode, req.getSysEncodingCode())
                        .eq(SysEncodingSet::getSysEncodingSetCode, req.getSysEncodingSetCode())
                );
                boolean isSetRes = encodingSetService.updateById(sysEncodingSet.setSysEncodingSetUseStatus(Constants._Y));
                if (!isSetRes) {
                    //释放分布式锁
                    redis.releaseTheLock(lockKey, uuid);
                    throw new CustomException("生成编码操作失败,版本不一致!");
                }
                //Redis处理编码集 状态
                redis.setString(ruleOpen, Constants._Y);
                return codeStr.toString();
            }
            //编码集状态存在 获取code
            disposeRule(codeStr, req);
            //业务代码-结束----------------------
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        } finally {
            //释放分布式锁
            redis.releaseTheLock(lockKey, uuid);
        }
        return codeStr.toString();
    }

    //获取规则 处理规则
    private void disposeRule(StringBuilder codeStr, SysCreateCode req) {
        SysEncodingSetRule sysEncodingSetRuleDTO = new SysEncodingSetRule()
                .setSysEncodingCode(req.getSysEncodingCode())
                .setSysEncodingSetCode(req.getSysEncodingSetCode());
        //从Redis中取 编码段集合
        List<SysEncodingSetRule> sysEncodingSetRules = getCacheList(sysEncodingSetRuleDTO);
        sysEncodingSetRules = CUtil.asc(sysEncodingSetRules, SysEncodingSetRule::getSysEncodingSetRuleNumber);
        //匹配规则 拼接编码
        sysEncodingSetRules.forEach(ele -> {
            matchGenerateCode(codeStr, req, ele);
        });
        //TODO 作者:@Dog_Elder 2022/9/28 0:41 处理Redis值和数据库值 未来放到队列中不影响正常业务也避免了并发问题
        Optional<SysEncodingSetRule> optional = sysEncodingSetRules.stream().filter(ele -> "NUMBER".equals(ele.getSysEncodingSetRuleType())).findAny();
        if (optional.isPresent()) {
            SysEncodingSetRule numberRule = optional.get();
            //TODO 作者:@Dog_Elder  [54.19% 3.7237ms ] com.xxxxx.service.impl.SysEncodingSetRuleServiceImpl:updateById() #146
            updateById(numberRule);
            //修改Redis
            String ruleIn = SUtil.format(CacheConstant.SysSetRule._IN, req.getSysEncodingSetCode(), req.getSysEncodingSetCode());
            JSONObject ruleJsonObject = JSONUtil.parseObj(numberRule);
            redis.setHash(ruleIn, numberRule.getId(), ruleJsonObject.toString());
        }
    }

    //从缓存中获取
    private List<SysEncodingSetRule> getCacheList(SysEncodingSetRule req) {
        //从Redis中取
        String ruleIn = SUtil.format(CacheConstant.SysSetRule._IN, req.getSysEncodingSetCode(), req.getSysEncodingSetCode());
        List<String> hvals = redis.getAllHashValues(ruleIn);
        return CUtil.jsonListStrToList(hvals, SysEncodingSetRule.class);
    }

    /**
     * 查询编码集是否已经使用
     * 返回对象主要是防止并发,利用乐观锁重新更新一次
     **/
    public SysEncodingSet checkToUse(SysEncodingSetRule sysEncodingSetRule) {
        //查询编码集是否已经使用
        SysEncodingSet sysEncodingSet = encodingSetService.getOne(new QueryWrapper<SysEncodingSet>().lambda()
                .eq(SysEncodingSet::getSysEncodingCode, sysEncodingSetRule.getSysEncodingCode())
                .eq(SysEncodingSet::getSysEncodingSetCode, sysEncodingSetRule.getSysEncodingSetCode())
        );
        if (SUtil.equals(sysEncodingSet.getSysEncodingSetUseStatus(), Constants._Y)) {
            Util.isNotNull(sysEncodingSet, "编码已经使用无法操作");
        }
        return sysEncodingSet;
    }


    //匹配规则 生成编码
    private void matchGenerateCode(StringBuilder codeStr, SysCreateCode req, SysEncodingSetRule ele) {
        switch (ele.getSysEncodingSetRuleType()) {
            //常量
            case "CONSTANT":
                codeStr.append(ele.getSysEncodingSetRuleSectionCode());
                break;
            //序列
            case "NUMBER":
                String ruleNumberIn = SUtil.format(CacheConstant.SysSetRule._NUMBER, req.getSysEncodingCode(), req.getSysEncodingSetCode());
                long number = getNumber(ruleNumberIn, ele);
                //根据位数前置前置补零
                codeStr.append(SUtil.zeroPr(number, ele.getSysEncodingSetRuleDigit()));
                ele.setSysEncodingSetNowValue(number);
                break;
            //UUID(8位数)
            case "UUID_8":
                codeStr.append(UUIDDigitUtil.generateString(8));
                break;
            //UUID(16位数)
            case "UUID_16":
                codeStr.append(UUIDDigitUtil.generateString(16));
                break;
            //UUID(22位数)
            case "UUID_22":
                codeStr.append(UUIDDigitUtil.generateString(22));
                break;
            //UUID(32位数)
            case "UUID_32":
                codeStr.append(UUIDDigitUtil.generateString(32));
                break;
            //日期(yyyyMMdd)
            case "DATE_yyyyMMdd":
                codeStr.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                break;
            //日期(yyyyMM)
            case "DATE_yyyyMM":
                codeStr.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
                break;
            //日期(yyyy)
            case "DATE_yyyy":
                codeStr.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));
                break;
            default:
                log.error("获取编码异常,未匹配到对应的规则段类型! 规则代码:{} 编码值:{}", req.getSysEncodingCode(), req.getSysEncodingSetCode());
                throw new CustomException("未匹配到对应的规则段类型!");
        }
    }

    //获取序列
    private long getNumber(String ruleNumberIn, SysEncodingSetRule rule) {
        //查询序列值开始值是否存在
        Boolean exists = redis.exists(ruleNumberIn);
        Long number = null;
        //不存在
        if (Boolean.FALSE.equals(exists)) {
            //将开始值存入
            redis.setString(ruleNumberIn, rule.getSysEncodingSetInitialValue().toString());
            //直接 取开始值 不进行自增
            number = rule.getSysEncodingSetInitialValue();
        }
        //假如 初始值是1 那就从1开始使用
        if (Util.isNull(number)) {
            number = redis.incrementBy(ruleNumberIn, 1);
        }
        return number;
    }

    //匹配规则
    private void matchingRule(SysEncodingSetRule rule) {
        switch (rule.getSysEncodingSetRuleType()) {
            //常量
            case "CONSTANT":
                checkConstant(rule);
                break;
            //序列
            case "NUMBER":
                disposeNumber(rule);
                break;
            //UUID(8位数)
            case "UUID_8":
                rule.setSysEncodingSetRuleDigit(8);
                break;
            //UUID(16位数)
            case "UUID_16":
                rule.setSysEncodingSetRuleDigit(16);
                break;
            //UUID(22位数)
            case "UUID_22":
                rule.setSysEncodingSetRuleDigit(22);
                break;
            //UUID(32位数)
            case "UUID_32":
                rule.setSysEncodingSetRuleDigit(32);
                break;
            //日期(yyyyMMdd)
            case "DATE_yyyyMMdd":
                rule.setSysEncodingSetRuleDateMask("yyyyMMdd");
                break;
            //日期(yyyyMM)
            case "DATE_yyyyMM":
                rule.setSysEncodingSetRuleDateMask("yyyyMM");
                break;
            //日期(yyyy)
            case "DATE_yyyy":
                rule.setSysEncodingSetRuleDateMask("yyyy");
                break;
            default:
                throw new CustomException("未匹配到对应的规则段类型!");
        }
    }
    //处理序列规则
    private void disposeNumber(SysEncodingSetRule rule) {
        //序列规则类型只能添加一个
        SysEncodingSetRule number = getOne(new QueryWrapper<SysEncodingSetRule>().lambda()
                .eq(SysEncodingSetRule::getSysEncodingCode, rule.getSysEncodingCode())
                .eq(SysEncodingSetRule::getSysEncodingSetCode, rule.getSysEncodingSetCode())
                //规则段类型 NUMBER	序列
                .eq(SysEncodingSetRule::getSysEncodingSetRuleType, "NUMBER")
        );

        if (Util.isNotNull(rule.getId()) && number.getId().equals(rule.getId())
                || Util.isNull(rule.getId()) && Util.isNotNull(number)) {
            throw new CustomException("序列规则类型只能有一个");
        }
        //位数
        Integer ruleDigit = rule.getSysEncodingSetRuleDigit();
        //检查位数
        if (Util.isNull(ruleDigit) || ruleDigit <= 0) {
            throw new CustomException("位数不能为空或小于0");
        }
        //开始值
        Long initialValue = rule.getSysEncodingSetInitialValue();
        //检查位数
        if (Util.isNull(initialValue) || initialValue <= 0) {
            throw new CustomException("开始值不能为空或小于0");
        }
    }

    //检查常量规则
    private void checkConstant(SysEncodingSetRule rule) {
        String sysEncodingSetRuleSectionCode = rule.getSysEncodingSetRuleSectionCode();
        //检查段值
        if (SUtil.isBlank(sysEncodingSetRuleSectionCode)) {
            throw new CustomException("常量值不能为空");
        }
    }
}