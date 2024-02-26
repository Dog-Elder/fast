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
import com.fast.manage.entity.TestBodyWeigh;
import com.fast.manage.query.TestBodyWeighQuery;
import com.fast.manage.service.ITestBodyWeighService;
import com.fast.manage.vo.TestBodyWeighVO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 体重记录
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2024-02-20
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/weigh")
public class TestBodyWeighController extends WebBaseController {
    private final ITestBodyWeighService testBodyWeighService;

    /**
     * 分页
     */
    @GetMapping("/page")
    public R<TableDataInfo<TestBodyWeighVO>> page(@Validated(Qry.class) TestBodyWeighQuery query) {
        return R.success(getDataTable(testBodyWeighService.page(query)));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R<List<TestBodyWeighVO>> list(@Validated(Qry.class) TestBodyWeighQuery query) {
        return R.success(testBodyWeighService.list(query));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    public R<TestBodyWeighVO> get(@PathVariable("id") String id) {
        TestBodyWeigh entity = testBodyWeighService.getById(id);
        return R.success(BUtil.copy(entity, TestBodyWeighVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    public R<List<TestBodyWeighVO>> save(@RequestBody @Validated(Save.class) ValidList<TestBodyWeighVO> vo){
        return R.success(testBodyWeighService.save(vo));
    }

    /**
     * 保存
     */
    @PostMapping("/saveOne")
    public R<TestBodyWeighVO> saveOne(@RequestBody @Validated(Save.class) TestBodyWeighVO vo){
        return R.success(testBodyWeighService.saveOne(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    public R update(@RequestBody @Validated(Update.class) TestBodyWeighVO vo){
        return R.toVersion(testBodyWeighService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.weigh.delete")
    public R delete(@RequestBody List<String> idList){
        testBodyWeighService.delete(idList);
        return R.success();
    }
}