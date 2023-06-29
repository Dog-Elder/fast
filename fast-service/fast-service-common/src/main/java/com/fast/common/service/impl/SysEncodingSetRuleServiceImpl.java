package com.fast.common.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysEncodingSetRuleDao;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.query.SysEncodingSetRuleQuery;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingSetRuleVO;
import com.fast.common.vo.SysEncodingSetVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Stream;

/**
 * 编码段
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysEncodingSetRuleServiceImpl extends ServiceImpl<SysEncodingSetRuleDao, SysEncodingSetRule> implements ISysEncodingSetRuleService {
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
        String ruleIn = SUtil.format(CacheConstant.SysSetRule.CODE_RULE, query.getSysEncodingSetCode(), query.getSysEncodingSetCode());
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
        updateAndCache(entity, sysEncodingSet);
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
        updateAndCache(entity, sysEncodingSet);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    private void updateAndCache(SysEncodingSetRule entity, SysEncodingSet sysEncodingSet) {
        if (!encodingSetService.update(BUtil.copy(sysEncodingSet, SysEncodingSetVO.class))) {
            throw new ServiceException("编码集版本不一致请重新提交");
        }
        //存放Redis
        String ruleIn = SUtil.format(CacheConstant.SysSetRule.CODE_RULE, entity.getSysEncodingSetCode(), entity.getSysEncodingSetCode());
        JSONObject ruleJsonObject = JSONUtil.parseObj(entity);
        redis.setHash(ruleIn, entity.getId(), ruleJsonObject.toString());
    }

    //从缓存中获取
    private List<SysEncodingSetRule> getCacheList(SysEncodingSetRule req) {
        //从Redis中取
        String ruleIn = SUtil.format(CacheConstant.SysSetRule.CODE_RULE, req.getSysEncodingSetCode(), req.getSysEncodingSetCode());
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
                throw new ServiceException("未匹配到对应的规则段类型!");
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
            throw new ServiceException("序列规则类型只能有一个");
        }
        //位数
        Integer ruleDigit = rule.getSysEncodingSetRuleDigit();
        //检查位数
        if (Util.isNull(ruleDigit) || ruleDigit <= 0) {
            throw new ServiceException("位数不能为空或小于0");
        }
        //开始值
        Long initialValue = rule.getSysEncodingSetInitialValue();
        //检查位数
        if (Util.isNull(initialValue) || initialValue <= 0) {
            throw new ServiceException("开始值不能为空或小于0");
        }
    }

    //检查常量规则
    private void checkConstant(SysEncodingSetRule rule) {
        String sysEncodingSetRuleSectionCode = rule.getSysEncodingSetRuleSectionCode();
        //检查段值
        if (SUtil.isBlank(sysEncodingSetRuleSectionCode)) {
            throw new ServiceException("常量值不能为空");
        }
    }
}