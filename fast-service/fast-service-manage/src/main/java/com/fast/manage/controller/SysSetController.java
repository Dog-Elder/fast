package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.common.query.SysSetQuery;
import com.fast.common.query.SysSetValueQuery;
import com.fast.common.service.ISysSetService;
import com.fast.common.service.ISysSetValueService;
import com.fast.common.vo.SysSetVO;
import com.fast.common.vo.SysSetValueVO;
import com.fast.core.common.annotation.lov.AutoLov;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.domain.ValidList;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import lombok.AllArgsConstructor;
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
public class SysSetController extends WebBaseController {
    private final ISysSetService sysSetService;
    private final ISysSetValueService sysSetValueService;
    /**
     * 值集分页
     */
    @AutoLov
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.set.page")
    public R<TableDataInfo> page(@Validated(Qry.class) SysSetQuery query){
        startPage();
        return R.success(getDataTable(sysSetService.list(query)));
    }

    /**
     * 值集信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.set.info")
    public R<SysSetVO> get(@PathVariable("id") String id){
        SysSet entity = sysSetService.getById(id);
        return R.success(BUtil.copy(entity,SysSetVO.class));
    }

    /**
     * 值集保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.set.save")
    public R save(@RequestBody @Validated(Save.class) ValidList<SysSetVO> vo){
        return R.success(sysSetService.save(vo));
    }

    /**
     * 值集修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.set.update")
    public R update(@RequestBody @Validated SysSetVO vo){
        return toVersionAjax(sysSetService.update(vo));
    }

    /**
     * 值集删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.set.delete")
    public R delete(@RequestBody List<String> idList){
        sysSetService.delete(idList);
        return R.success();
    }


    /**
     * 值集值分页
     */
    @GetMapping("/value/page")
    @ManageCheckPermission(value = "manage.setValue.page")
    public R<TableDataInfo> valuePage(@Validated(Qry.class) SysSetValueQuery query){
        startPage();
        return R.success(getDataTable(sysSetValueService.list(query)));
    }

    /**
     * 值集值信息
     */
    @GetMapping("/value/{id}")
    @ManageCheckPermission(value = "manage.setValue.info")
    public R<SysSetValueVO> valueGet(@PathVariable("id") String id){
        SysSetValue entity = sysSetValueService.getById(id);
        return R.success(BUtil.copy(entity,SysSetValueVO.class));
    }

    /**
     * 值集值保存
     */
    @PostMapping("/value")
    @ManageCheckPermission(value = "manage.setValue.save")
    public R valueSave(@RequestBody @Validated(Save.class) ValidList<SysSetValueVO> vo){
        return R.success(sysSetValueService.save(vo));
    }

    /**
     * 值集值修改
     */
    @PutMapping("/value")
    @ManageCheckPermission(value = "manage.setValue.update")
    public R valueUpdate(@RequestBody @Validated(Update.class) SysSetValueVO vo){
        return toVersionAjax(sysSetValueService.update(vo));
    }

    /**
     * 值集值删除
     */
    @DeleteMapping("/value")
    @ManageCheckPermission(value = "manage.setValue.delete")
    public R valueDelete(@RequestBody List<String> idList){
        sysSetValueService.delete(idList);
        return R.success();
    }
}