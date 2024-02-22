package com.fast.manage.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.query.BaseLambdaQueryWrapper;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.dao.TestBodyWeighDao;
import com.fast.manage.entity.TestBodyWeigh;
import com.fast.manage.entity.TestBodyWeighUser;
import com.fast.manage.query.TestBodyWeighQuery;
import com.fast.manage.query.TestBodyWeighUserQuery;
import com.fast.manage.service.ITestBodyWeighService;
import com.fast.manage.service.ITestBodyWeighUserService;
import com.fast.manage.vo.TestBodyWeighUserVO;
import com.fast.manage.vo.TestBodyWeighVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 体重记录
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
@Service
@RequiredArgsConstructor
public class TestBodyWeighServiceImpl extends BaseServiceImpl<TestBodyWeighDao, TestBodyWeigh> implements ITestBodyWeighService {

    private final ITestBodyWeighUserService weighUserService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TestBodyWeighVO> list(TestBodyWeighQuery query) {
        List<TestBodyWeighUserVO> list = weighUserService.list(new TestBodyWeighUserQuery().setName(query.getName()));
        if (CUtil.isEmpty(list)) {
            return ListUtil.empty();
        }
        PageUtils.startPage();
        List<TestBodyWeigh> entityList = list(getWrapper(query).in(TestBodyWeigh::getTestBodyWeighUserId, CUtil.getPropertySet(list, TestBodyWeighUserVO::getId)));
        Map<String, TestBodyWeighUserVO> map = CUtil.toMap(list, TestBodyWeighUserVO::getId);
        List<TestBodyWeighVO> vos = PageUtils.copy(entityList, TestBodyWeighVO.class);
        vos.forEach(ele -> {
            TestBodyWeighUserVO userVO = Optional.ofNullable(map.get(ele.getTestBodyWeighUserId())).orElse(new TestBodyWeighUserVO());
            ele.setName(userVO.getName());
        });
        return vos;
    }

    private BaseLambdaQueryWrapper<TestBodyWeigh> getWrapper(TestBodyWeighQuery query) {
        BaseLambdaQueryWrapper<TestBodyWeigh> wrapper = new BaseLambdaQueryWrapper<>();
        return wrapper;
    }

    private BaseLambdaQueryWrapper<TestBodyWeigh> getWrapper() {
        return getWrapper(new TestBodyWeighQuery());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestBodyWeighVO> save(List<TestBodyWeighVO> vo) {
        List<TestBodyWeigh> entityList = BUtil.copyList(vo, TestBodyWeigh.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList, TestBodyWeighVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(TestBodyWeighVO vo) {
        TestBodyWeigh entity = BUtil.copy(vo, TestBodyWeigh.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    @Override
    public TestBodyWeighVO saveOne(TestBodyWeighVO vo) {
        // 获取当前时间
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atStartOfDay().plusDays(1).minusNanos(1);

        // 查询今天数据
        TestBodyWeigh one = getOne(Wrappers.<TestBodyWeigh>lambdaQuery()
                .eq(TestBodyWeigh::getTestBodyWeighUserId, vo.getTestBodyWeighUserId())
                .between(TestBodyWeigh::getCreateTime, startOfDay, endOfDay)
        );
        // 新增或修改今日数据
        TestBodyWeigh testBodyWeigh = Optional.ofNullable(one).orElse(new TestBodyWeigh());
        BUtil.copy(vo, testBodyWeigh);
        saveOrUpdate(testBodyWeigh);

        // 处理用户数据
        processingOfUserData(vo);
        return BUtil.copy(testBodyWeigh, TestBodyWeighVO.class);
    }

    private void processingOfUserData(TestBodyWeighVO vo) {
        TestBodyWeighUser user = weighUserService.getById(vo.getTestBodyWeighUserId());
        user.setLatestBodyWeight(vo.getBodyWeight());
        TestBodyWeigh maximum = getOne(getWrapper()
                .eq(TestBodyWeigh::getTestBodyWeighUserId, vo.getTestBodyWeighUserId())
                .orderByDesc(TestBodyWeigh::getBodyWeight)
                .limitN(1)
        );

        TestBodyWeigh smallest = getOne(getWrapper()
                .eq(TestBodyWeigh::getTestBodyWeighUserId, vo.getTestBodyWeighUserId())
                .orderByAsc(TestBodyWeigh::getBodyWeight)
                .limitN(1)
        );
        user.setSupremeBodyWeight(maximum.getBodyWeight());
        user.setMinimumBodyWeight(smallest.getBodyWeight());
        // 获取昨天的日期
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // 获取昨天的起始时间和结束时间
        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
        LocalDateTime endOfYesterday = yesterday.atStartOfDay().plusDays(1).minusNanos(1);

        // 查询昨天数据
        TestBodyWeigh testBodyWeigh = getOne(Wrappers.<TestBodyWeigh>lambdaQuery()
                .eq(TestBodyWeigh::getTestBodyWeighUserId, vo.getTestBodyWeighUserId())
                .between(TestBodyWeigh::getCreateTime, startOfYesterday, endOfYesterday)
        );

        if (Util.isNotNull(testBodyWeigh) || user.getContinuousRecording().equals(0)) {
            user.setContinuousRecording(user.getContinuousRecording() + 1);
        }

        weighUserService.updateById(user);
    }
}