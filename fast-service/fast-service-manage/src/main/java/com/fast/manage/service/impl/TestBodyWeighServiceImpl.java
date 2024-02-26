package com.fast.manage.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
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
    public List<TestBodyWeighVO> page(TestBodyWeighQuery query) {
        List<TestBodyWeighUserVO> userList = weighUserService.list(new TestBodyWeighUserQuery().setName(query.getName()));
        if (CUtil.isEmpty(userList)) {
            return ListUtil.empty();
        }

        PageUtils.startPage();

        List<TestBodyWeigh> entityList = list(getWrapper(query).in(TestBodyWeigh::getTestBodyWeighUserId, CUtil.getPropertySet(userList, TestBodyWeighUserVO::getId)));
        if (CUtil.isEmpty(entityList)) {
            return ListUtil.empty();
        }

        Map<String, TestBodyWeighUserVO> userMap = CUtil.toMap(userList, TestBodyWeighUserVO::getId);
        List<TestBodyWeighVO> vos = PageUtils.copy(entityList, TestBodyWeighVO.class);

        vos.forEach(vo -> {
            TestBodyWeighUserVO userVO = userMap.getOrDefault(vo.getTestBodyWeighUserId(), new TestBodyWeighUserVO());
            vo.setName(userVO.getName());
        });

        return vos;
    }

    @Override
    public List<TestBodyWeighVO> list(TestBodyWeighQuery query) {
        return PageUtils.copy(list(getWrapper(query)), TestBodyWeighVO.class);
    }

    private BaseLambdaQueryWrapper<TestBodyWeigh> getWrapper(TestBodyWeighQuery query) {
        BaseLambdaQueryWrapper<TestBodyWeigh> wrapper = new BaseLambdaQueryWrapper<>();
        wrapper.eqIfPresent(TestBodyWeigh::getTestBodyWeighUserId, query.getTestBodyWeighUserId());
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
    @Transactional(rollbackFor = Exception.class)
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
        processingOfUserData(testBodyWeigh);
        return BUtil.copy(testBodyWeigh, TestBodyWeighVO.class);
    }

    private void processingOfUserData(TestBodyWeigh bodyWeigh) {
        TestBodyWeighUser user = weighUserService.getById(bodyWeigh.getTestBodyWeighUserId());
        user.setLatestBodyWeight(bodyWeigh.getBodyWeight());
        TestBodyWeigh maximum = getOne(getWrapper()
                .eq(TestBodyWeigh::getTestBodyWeighUserId, bodyWeigh.getTestBodyWeighUserId())
                .orderByDesc(TestBodyWeigh::getBodyWeight)
                .limitN(1)
        );

        TestBodyWeigh smallest = getOne(getWrapper()
                .eq(TestBodyWeigh::getTestBodyWeighUserId, bodyWeigh.getTestBodyWeighUserId())
                .orderByAsc(TestBodyWeigh::getBodyWeight)
                .limitN(1)
        );
        user.setSupremeBodyWeight(maximum.getBodyWeight());
        user.setMinimumBodyWeight(smallest.getBodyWeight());
        weighUserService.updateById(user);
    }
}