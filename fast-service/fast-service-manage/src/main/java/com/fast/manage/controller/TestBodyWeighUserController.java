package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.domain.ValidList;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import com.fast.manage.entity.TestBodyWeighUser;
import com.fast.manage.query.TestBodyWeighUserQuery;
import com.fast.manage.service.ITestBodyWeighUserService;
import com.fast.manage.vo.TestBodyWeighUserVO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 体重记录用户
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2024-02-20
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/weigh-user")
public class TestBodyWeighUserController extends WebBaseController{
    private final ITestBodyWeighUserService testBodyWeighUserService;

    /**
     * 分页
     */
    @GetMapping("/page")
    public R<TableDataInfo<TestBodyWeighUserVO>> page(@Validated(Qry.class) TestBodyWeighUserQuery query){
        startPage();
        return R.success(getDataTable(testBodyWeighUserService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    public R<TestBodyWeighUserVO> get(@PathVariable("id") String id) {
        TestBodyWeighUser entity = testBodyWeighUserService.getById(id);
        return R.success(BUtil.copy(entity, TestBodyWeighUserVO.class));
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R<TestBodyWeighUserVO> getInfo(@PathVariable("id") String id) {
        TestBodyWeighUser entity = testBodyWeighUserService.getInfo(id);
        return R.success(BUtil.copy(entity, TestBodyWeighUserVO.class));
    }


    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.weighUser.save")
    public R<List<TestBodyWeighUserVO>> save(@RequestBody @Validated(Save.class) ValidList<TestBodyWeighUserVO> vo) {
        return R.success(testBodyWeighUserService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.weighUser.update")
    public R update(@RequestBody @Validated(Update.class) TestBodyWeighUserVO vo){
        return R.toVersion(testBodyWeighUserService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.weighUser.delete")
    public R delete(@RequestBody List<String> idList){
        testBodyWeighUserService.delete(idList);
        return R.success();
    }
}