package com.fast.common.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fast.common.dao.SysSetValueDao;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.query.SysSetValueQuery;
import com.fast.common.service.ISysSetService;
import com.fast.common.service.ISysSetValueService;
import com.fast.common.vo.CustomSetValueVO;
import com.fast.common.vo.SysSetValueVO;
import com.fast.core.common.constant.Constants;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.core.common.exception.CustomException;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 值集列Service业务层处理
 *
 * @author @Dog_Elder
 * @date 2022-03-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysSetValueServiceImpl extends BaseServiceImpl<SysSetValueDao, SysSetValue> implements ISysSetValueService {

    private final ISysSetService sysSetService;

    private final FastRedis redis;

    /**
     * 查询值集列
     *
     * @param id 值集列ID
     * @return 值集列
     */
    @Override
    @Transactional()
    public SysSetValue selectById(Long id) {
        return baseMapper.selectSysSetValueById(id);
    }

    /**
     * 查询值集列列表
     *
     * @param sysSetValue 值集列
     * @return 值集列
     */
    @Override
    public List<SysSetValueVO> list(SysSetValue sysSetValue) {
        List<SysSetValueVO> vo;
        List<SysSetValue> sysSetValues = list(new LambdaQueryWrapper<SysSetValue>()
                .eq(Util.isNotNull(sysSetValue.getSetCode()) && SUtil.isNotEmpty(sysSetValue.getSetCode()), SysSetValue::getSetCode, sysSetValue.getSetCode())
                .eq(Util.isNotNull(sysSetValue.getSetValueKey()) && SUtil.isNotEmpty(sysSetValue.getSetValueKey()), SysSetValue::getSetValueKey, sysSetValue.getSetValueKey())
                .eq(Util.isNotNull(sysSetValue.getSetValueValue()) && SUtil.isNotEmpty(sysSetValue.getSetValueValue()), SysSetValue::getSetValueValue, sysSetValue.getSetValueValue())
                .eq(Util.isNotNull(sysSetValue.getSetRelationKey()) && SUtil.isNotEmpty(sysSetValue.getSetRelationKey()), SysSetValue::getSetRelationKey, sysSetValue.getSetRelationKey())
                .eq(Util.isNotNull(sysSetValue.getSetOperate()) && SUtil.isNotEmpty(sysSetValue.getSetOperate()), SysSetValue::getSetOperate, sysSetValue.getSetOperate())
                .eq(Util.isNotNull(sysSetValue.getSetOrder()), SysSetValue::getSetOrder, sysSetValue.getSetOrder())
                .eq(Util.isNotNull(sysSetValue.getVersion()), SysSetValue::getVersion, sysSetValue.getVersion())
        );
        //取所有的关联值集值的key
        Set<String> setRelationKeys = sysSetValues.stream().map(SysSetValue::getSetRelationKey).collect(Collectors.toSet());
        vo = PageUtils.copy(sysSetValues, SysSetValueVO.class);
        if (CUtil.isEmpty(setRelationKeys)) {
            return vo;
        }
        //获取当前对象找到关联的值集
        SysSet set = sysSetService.getOne(new LambdaQueryWrapper<SysSet>().eq(SysSet::getSetCode, sysSetValue.getSetCode()));
        if (Util.isNull(set) || SUtil.isEmpty(set.getSetParentCode())) {
            return vo;
        }
        //找到关联父级值集
        List<SysSetValue> parentSetValues = new LambdaQueryChainWrapper<>(baseMapper).eq(SysSetValue::getSetCode, set.getSetParentCode()).in(SysSetValue::getSetValueKey, setRelationKeys).list();
        Map<String, SysSetValue> parentSetValueMap = CUtil.toMap(parentSetValues, SysSetValue::getSetValueKey);
        vo.forEach(ele -> {
            if (SUtil.isEmpty(ele.getSetRelationKey())) {
                return;
            }
            SysSetValue setValue = parentSetValueMap.get(ele.getSetRelationKey());
            if (Util.isNull(setValue)) {
                return;
            }
            ele.setSetRelationValue(setValue.getSetValueValue());
        });
        return vo;
    }

    /**
     * 自定义列表
     * 不能分页
     * @param map K:返回k  V:值集code
     * @return JSONObject k:List<值集值>
     */
    @Override
    public JSONObject customList(Map<String, String> map) {
        if (CUtil.isEmpty(map)) {
            throw new CustomException("查询值集不能为空");
        }
        Collection<String> values = map.values();
        //过滤值集未启用的
        List<SysSet> validSets = sysSetService.list(new LambdaQueryWrapper<SysSet>().select(SysSet::getSetCode).in(SysSet::getSetCode, values).eq(SysSet::getSetState, Constants.Y));
        //获取有效的值集编码
        Set setCodes = CUtil.getPropertySet(validSets.stream().filter(Objects::nonNull), SysSet::getSetCode);
        //只筛选有效的值集值
        List<SysSetValue> setValues = new LambdaQueryChainWrapper<>(baseMapper)
                .in(SysSetValue::getSetCode, setCodes)
                .list();
        List<CustomSetValueVO> vos = CUtil.copy(setValues, CustomSetValueVO.class);
        Map<String, List<CustomSetValueVO>> byCodeGrouping = CUtil.toGrouping(vos, CustomSetValueVO::getSetCode);
        JSONObject jsonObject = new JSONObject();
        map.forEach((k, v) -> {
            List<CustomSetValueVO> customSetValueVOs = byCodeGrouping.get(v);
            List<CustomSetValueVO> descVO = CUtil.asc(customSetValueVOs, CustomSetValueVO::getSetOrder);
            if (CUtil.isEmpty(descVO)) {
                return;
            }
            jsonObject.set(k, descVO);
        });
        return jsonObject;
    }

    /**
     * 查询值列表
     */
    @Override
    public List<CustomSetValueVO> dataList(SysSetValueQuery req) {
        //查询值集是否已经被删除或关闭
        SysSet set = sysSetService.getOne(new LambdaQueryWrapper<SysSet>().eq(SysSet::getSetCode, req.getSetCode()).eq(SysSet::getSetState, Constants.Y));
        if (Util.isNull(set)) {
            return null;
        }
        if (Constants.Y.equals(req.getDb())) {
            PageUtils.startPage();
        }
        List<SysSetValue> list = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(SysSetValue::getSetCode, req.getSetCode())
                .eq(SUtil.isNotBlank(req.getSetRelationKey()), SysSetValue::getSetRelationKey, req.getSetRelationKey())
                .eq(SUtil.isNotBlank(req.getSetValueKey()), SysSetValue::getSetValueKey, req.getSetValueKey())
                .like(SUtil.isNotBlank(req.getSetValueValue()), SysSetValue::getSetValueValue, req.getSetValueValue())
                .list();
        List<CustomSetValueVO> vos = PageUtils.copy(list, CustomSetValueVO.class);
        //获取关联的上级值集值
        if (SUtil.isBlank(set.getSetParentCode())) {
            return vos;
        }
        //获取上级值集值key
        List<String> parentKeys = CUtil.getPropertyList(vos.stream().filter(ele -> SUtil.isNotBlank(ele.getSetRelationKey())), CustomSetValueVO::getSetRelationKey);
        //查询上级值集值
        List<SysSetValue> parentValues = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(SysSetValue::getSetCode, set.getSetParentCode())
                .in(CUtil.isNotEmpty(parentKeys), SysSetValue::getSetValueKey, parentKeys)
                .list();
        Map<String, SysSetValue> parentValueMap = CUtil.toMap(parentValues, SysSetValue::getSetValueKey);
        CUtil.toGrouping(vos.stream().filter(ele -> SUtil.isNotBlank(ele.getSetRelationKey())), CustomSetValueVO::getSetRelationKey).forEach((k, v) -> {
            if (SUtil.isEmpty(k)) {
                return;
            }
            SysSetValue parentValue = parentValueMap.get(k);
            v.forEach(item -> {
                item.setSetRelationValue(parentValue.getSetValueValue());
            });
        });
        return vos;
    }

    @Override
    public List<CustomSetValueVO> qryCacheDataList(SysSetValueQuery req) {
        //查询值集是否已经被删除或关闭
        SysSet set = sysSetService.getOne(new LambdaQueryWrapper<SysSet>().eq(SysSet::getSetCode, req.getSetCode()).eq(SysSet::getSetState, Constants.Y));
        if (Util.isNull(set)) {
            return null;
        }
        //查询缓存
        String jsonArrayValue = redis.getHash(CacheConstant.SetValue._IN, req.getSetCode());
        if (SUtil.isNotBlank(jsonArrayValue)) {
            JSONArray jsonArray = JSONUtil.parseArray(jsonArrayValue);
            List<CustomSetValueVO> customSetValueVOS = JSONUtil.toList(jsonArray, CustomSetValueVO.class);
            Stream<CustomSetValueVO> qry = customSetValueVOS.stream();
            qry = qry.filter(ele -> SUtil.isBlank(req.getSetValueKey()) || SUtil.contains(ele.getSetValueKey(), req.getSetValueKey()));
            qry = qry.filter(ele -> SUtil.isBlank(req.getSetValueValue()) || SUtil.contains(ele.getSetValueValue(), req.getSetValueValue()));

            return PageUtils.createPage(qry.collect(Collectors.toList()));
        }
        //查询不到缓存就查询数据库
        List<CustomSetValueVO> customSetValueVOS = dataList(req);
        if (CUtil.isEmpty(customSetValueVOS)) {
            return customSetValueVOS;
        }
        //这里主要为了获得只根据setCode关联的值集数据
        JSONArray objects = JSONUtil.parseArray(dataList(new SysSetValueQuery().setSetCode(req.getSetCode()).setDb(Constants.N)));
        redis.setHash(CacheConstant.SetValue._IN, req.getSetCode(), objects.toString());
        return customSetValueVOS;
    }

    @Override
    public String qryValue(String setCode, String setValueKey) {
        return null;
    }

    /**
     * 添加值集列
     *
     * @param sysSetValue 值集列
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<SysSetValue> addSave(List<SysSetValue> sysSetValue) {
        checkSet(sysSetValue);
        verifyRepeat(sysSetValue);
        removeCache(sysSetValue);
        saveBatch(sysSetValue);
        return sysSetValue;
    }

    private void removeCache(List<SysSetValue> sysSetValue) {
        Set<String> setCodes = CUtil.getPropertySet(sysSetValue, SysSetValue::getSetCode);
        Set<String> fields = new HashSet<>(setCodes);
        //查询所有的
        getRelevanceSetCode(fields,setCodes);
        log.info("清除值集值===要删除的key(SysSet.setCode):"+fields);
        fields.forEach(ele->{
            redis.deleteHash(CacheConstant.SetValue._IN, ele);
        });
    }

    private void getRelevanceSetCode(Set<String> fields,Set<String> setCodes){
        List<SysSet> list = sysSetService.list(new LambdaQueryWrapper<SysSet>().select( SysSet::getSetCode).in(SysSet::getSetParentCode, setCodes));
        if (CUtil.isNotEmpty(list)) {
            Set<String> setCode = CUtil.getPropertySet(list, SysSet::getSetCode);
            fields.addAll(setCode);
            getRelevanceSetCode(fields,setCode);
        }
    }

    /**
     * 修改值集列
     *
     * @param sysSetValue 值集列
     * @return 结果
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean update(SysSetValue sysSetValue) {
        verifyRepeat(Collections.singletonList(sysSetValue));
        return updateById(sysSetValue);
    }

    /**
     * 真删除值集列对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids) {
        return baseMapper.deleteSysSetValueByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除值集列信息
     *
     * @param id 值集列ID
     * @return 结果
     */
    @Override
    public int deleteSysSetValueById(Long id) {
        return baseMapper.deleteSysSetValueById(id);
    }

    /**
     * 值集列逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }

    /**
     * 检查要添加的值集值对应的值集是否存在
     **/
    private void checkSet(List<SysSetValue> sysSetValue) {
        //获取要添加值集值对应的值集code
        Set<String> setCodeList = CUtil.getPropertySet(sysSetValue.stream().filter(ele -> SUtil.isNotBlank(ele.getSetCode())), SysSetValue::getSetCode);
        //根据获取的值集code查询
        List<SysSet> list = sysSetService.list(new LambdaQueryWrapper<SysSet>().in(SysSet::getSetCode, setCodeList));
        //找出两个集合的不同元素
        Collection<String> different = CUtil.getDiffByHashSet(setCodeList, CUtil.getPropertyList(list, SysSet::getSetCode));
        if (CUtil.isNotEmpty(different)) {
            StringBuilder sb = new StringBuilder();
            sb.append("对应的值集编码:").append(SUtil.join(different, ",")).append("不存在");
            throw new CustomException(sb.toString());
        }
    }

    /**
     * 校验重复
     **/
    private void verifyRepeat(List<SysSetValue> req) {
        //检查入参中值集值key重复
        Set<String> setCodes = checkRepeat(req);
        //检查库中值集值key重复
        //如果是修改则排除要修改id的
        Set<Long> ids = req.stream().filter(ele -> Util.isNotNull(ele.getId())).map(SysSetValue::getId).collect(Collectors.toSet());
        List<SysSetValue> sysSetValues = new LambdaQueryChainWrapper<>(baseMapper)
                .notIn(CUtil.isNotEmpty(ids), SysSetValue::getId, ids)
                .in(SysSetValue::getSetCode, setCodes).list();
        if (CUtil.isEmpty(sysSetValues)) {
            return;
        }
        List<SysSetValue> list = new ArrayList<>(req);
        list.addAll(sysSetValues);
        checkRepeat(list);
    }

    /**
     * 检查是否重复
     **/
    private Set<String> checkRepeat(List<SysSetValue> req) {
        //检查入参中值集值key重复
        Map<String, List<SysSetValue>> reqMap = req.stream().collect(Collectors.groupingBy(SysSetValue::getSetCode));
        reqMap.forEach((k, v) -> {
            List<String> setValueKeys = v.stream().map(SysSetValue::getSetValueKey).collect(Collectors.toList());
            List<String> duplicateElements = CUtil.getDuplicateElements(setValueKeys);
            if (CUtil.isNotEmpty(duplicateElements)) {
                throw new CustomException("值集值key重复:" + duplicateElements.toString());
            }
        });
        return reqMap.keySet();
    }
}