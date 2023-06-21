package com.fast.manage.controller;

import com.fast.common.entity.verification.Qry;
import com.fast.core.common.util.bean.BUtil;
import lombok.AllArgsConstructor;
import com.fast.core.common.domain.domain.R;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.common.controller.WebBaseController;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import com.fast.core.common.domain.domain.ValidList;
import com.fast.manage.entity.SysTest;
import com.fast.manage.service.ISysTestService;
import com.fast.manage.query.SysTestQuery;
import com.fast.manage.vo.SysTestVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 测试
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-21
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/test")
public class SysTestController extends WebBaseController{
    private final ISysTestService sysTestService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.test.page")
    public R<TableDataInfo<SysTestVO>> page(@Validated(Qry.class) SysTestQuery query){
        startPage();
        return R.success(getDataTable(sysTestService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.test.info")
    public R<SysTestVO> get(@PathVariable("id") String id){
        SysTest entity = sysTestService.getById(id);
        return R.success(BUtil.copy(entity,SysTestVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.test.save")
    public R<List<SysTestVO>> save(@RequestBody @Validated(Save.class) ValidList<SysTestVO> vo){
        return R.success(sysTestService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.test.update")
    public R update(@RequestBody @Validated(Update.class) SysTestVO vo){
        return R.toVersion(sysTestService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.test.delete")
    public R delete(@RequestBody List<String> idList){
        sysTestService.delete(idList);
        return R.success();
    }
}