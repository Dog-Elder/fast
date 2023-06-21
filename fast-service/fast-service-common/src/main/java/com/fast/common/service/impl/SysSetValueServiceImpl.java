package com.fast.common.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysSetValueDao;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.query.SysSetValueQuery;
import com.fast.common.service.ISysSetService;
import com.fast.common.service.ISysSetValueService;
import com.fast.common.vo.CustomSetValueVO;
import com.fast.common.vo.SysSetValueVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 值集值Service业务层处理
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

    private LambdaQueryWrapper<SysSetValue> getWrapper(SysSetValueQuery query) {
        LambdaQueryWrapper<SysSetValue> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getSetCode()) && SUtil.isNotEmpty(query.getSetCode()), SysSetValue::getSetCode, query.getSetCode());
        wrapper.eq(Util.isNotNull(query.getSetValueKey()) && SUtil.isNotEmpty(query.getSetValueKey()), SysSetValue::getSetValueKey, query.getSetValueKey());
        wrapper.like(Util.isNotNull(query.getSetValueValue()) && SUtil.isNotEmpty(query.getSetValueValue()), SysSetValue::getSetValueValue, query.getSetValueValue());
        wrapper.eq(Util.isNotNull(query.getSetRelationKey()) && SUtil.isNotEmpty(query.getSetRelationKey()), SysSetValue::getSetRelationKey, query.getSetRelationKey());
        return wrapper;
    }

    /**
     * 查询值集值列表
     *
     * @param query 值集值
     * @return 值集值
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysSetValueVO> list(SysSetValueQuery query) {
        List<SysSetValueVO> vo;
        List<SysSetValue> sysSetValues = list(getWrapper(query));
        //取所有的关联值集值的key
        Set<String> setRelationKeys = sysSetValues.stream().map(SysSetValue::getSetRelationKey).collect(Collectors.toSet());
        vo = PageUtils.copy(sysSetValues, SysSetValueVO.class);
        if (CUtil.isEmpty(setRelationKeys)) {
            return vo;
        }
        //获取当前对象找到关联的值集
        SysSet set = sysSetService.getOne(new LambdaQueryWrapper<SysSet>().eq(SysSet::getSetCode, query.getSetCode()));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysSetValueVO> save(List<SysSetValueVO> vo) {
        List<SysSetValue> entityList = BUtil.copyList(vo, SysSetValue.class);
        checkSet(entityList);
        verifyRepeat(entityList);
        removeCache(entityList);
        saveBatch(entityList);
        return BUtil.copyList(entityList, SysSetValueVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysSetValueVO vo) {
        SysSetValue entity = BUtil.copy(vo, SysSetValue.class);
        verifyRepeat(Collections.singletonList(entity));
        return updateById(entity);
    }

    /**
     * 自定义列表
     * 不能分页
     *
     * @param map K:返回k  V:值集code
     * @return JSONObject k:List<值集值>
     */
    @Override
    public JSONObject customList(Map<String, String> map) {
        if (CUtil.isEmpty(map)) {
            throw new ServiceException("查询值集不能为空");
        }
        Collection<String> values = map.values();
        Optional<String> any = values.stream().filter(SUtil::isBlank).findAny();
        if (any.isPresent()) {
            throw new ServiceException("参数值和参数名不能为空");
        }
        //过滤值集未启用的
        List<SysSet> validSets = sysSetService.list(new LambdaQueryWrapper<SysSet>().select(SysSet::getSetCode).in(SysSet::getSetCode, values).eq(SysSet::getSetState, Constants.Y));
        //获取有效的值集编码
        Set<String> setCodes = CUtil.getPropertySet(validSets.stream().filter(Objects::nonNull), SysSet::getSetCode);
        if (CUtil.isEmpty(setCodes)) {
            return JSONUtil.createObj();
        }
        List<CustomSetValueVO> vos = new ArrayList<>();
        setCodes.forEach(ele -> {
                vos.addAll(qryCacheDataList(new SysSetValueQuery().setSetCode(ele)));
        });
        Map<String, List<CustomSetValueVO>> byCodeGrouping = CUtil.toGrouping(vos, CustomSetValueVO::getSetCode);
        JSONObject jsonObject = JSONUtil.createObj();
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
     * 查询值列表 只查数据库
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
        if (!sysSetService.isEnableBySetCode(req.getSetCode())) {
            return null;
        }
        //查询缓存
        String jsonArrayValue = redis.getHash(CacheConstant.SetValue.SET_VALUE, req.getSetCode());
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
        redis.setHash(CacheConstant.SetValue.SET_VALUE, req.getSetCode(), objects.toString());
        return customSetValueVOS;
    }

    @Override
    public String qryValues(String setCode, String setValueKeys) {
        //查询值集是否已经被删除或关闭
        if (!sysSetService.isEnableBySetCode(setCode)) {
            return null;
        }
        List<String> valueKeys = SUtil.strToList(setValueKeys, ",");

        //查询缓存
        String jsonArrayValue = redis.getHash(CacheConstant.SetValue.SET_VALUE, setCode);
        List<CustomSetValueVO> customSetValueVOS;
        if (SUtil.isNotBlank(jsonArrayValue)) {
            JSONArray jsonArray = JSONUtil.parseArray(jsonArrayValue);
            customSetValueVOS = JSONUtil.toList(jsonArray, CustomSetValueVO.class);
        }

        //查询不到缓存就查询数据库
        else {
            customSetValueVOS = dataList(new SysSetValueQuery().setSetCode(setCode));
            if (CUtil.isEmpty(customSetValueVOS)) {
                return null;
            }
            //这里主要为了获得只根据setCode关联的值集数据
            JSONArray objects = JSONUtil.parseArray(dataList(new SysSetValueQuery().setSetCode(setCode).setDb(Constants.N)));
            redis.setHash(CacheConstant.SetValue.SET_VALUE, setCode, objects.toString());
        }

        List<String> values = customSetValueVOS.stream()
                .filter(ele -> valueKeys.contains(ele.getSetValueKey()))
                .map(CustomSetValueVO::getSetValueValue)
                .collect(Collectors.toList());
        if (CUtil.isEmpty(values)) return null;
        return String.join(",", values);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    private void removeCache(List<SysSetValue> sysSetValue) {
        Set<String> setCodes = CUtil.getPropertySet(sysSetValue, SysSetValue::getSetCode);
        Set<String> fields = new HashSet<>(setCodes);
        //查询所有的
        getRelevanceSetCode(fields, setCodes);
        log.info("清除值集值===要删除的key(SysSet.setCode):" + fields);
        fields.forEach(ele -> {
            redis.deleteHash(CacheConstant.SetValue.SET_VALUE, ele);
        });
    }

    private void getRelevanceSetCode(Set<String> fields, Set<String> setCodes) {
        List<SysSet> list = sysSetService.list(new LambdaQueryWrapper<SysSet>().select(SysSet::getSetCode).in(SysSet::getSetParentCode, setCodes));
        if (CUtil.isNotEmpty(list)) {
            Set<String> setCode = CUtil.getPropertySet(list, SysSet::getSetCode);
            fields.addAll(setCode);
            getRelevanceSetCode(fields, setCode);
        }
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
            throw new ServiceException(sb.toString());
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
        Set<String> ids = req.stream().filter(ele -> Util.isNotNull(ele.getId())).map(SysSetValue::getId).collect(Collectors.toSet());
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
                throw new ServiceException("值集值key重复:" + duplicateElements);
            }
        });
        return reqMap.keySet();
    }
}