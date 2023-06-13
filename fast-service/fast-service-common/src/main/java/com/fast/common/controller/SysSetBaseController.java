package com.fast.common.controller;

import com.fast.common.entity.sys.SysSet;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.query.SysSetQuery;
import com.fast.common.service.ISysSetService;
import com.fast.common.vo.SysSetVO;
import com.fast.core.common.util.bean.BUtil;
import lombok.AllArgsConstructor;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import com.fast.core.common.domain.domain.ValidList;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 值集
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-13
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/set-value")
public class SysSetBaseController extends WebBaseController{
    private final ISysSetService sysSetService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.set.page")
    public R<TableDataInfo> page(@Validated(Qry.class) SysSetQuery query){
        startPage();
        return R.success(getDataTable(sysSetService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.set.info")
    public R<SysSetVO> get(@PathVariable("id") String id){
        SysSet entity = sysSetService.getById(id);
        return R.success(BUtil.copy(entity,SysSetVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.set.save")
    public R save(@RequestBody @Validated(Save.class) ValidList<SysSetVO> vo){
        return R.success(sysSetService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.set.update")
    public R update(@RequestBody @Validated SysSetVO vo){
        return toVersionAjax(sysSetService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.set.delete")
    public R delete(@RequestBody List<String> idList){
        sysSetService.delete(idList);
        return R.success();
    }
}