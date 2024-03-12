package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.common.query.SysEncodingQuery;
import com.fast.common.query.SysEncodingSetQuery;
import com.fast.common.query.SysEncodingSetRuleQuery;
import com.fast.common.service.ISysEncodingService;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingSetRuleVO;
import com.fast.common.vo.SysEncodingSetVO;
import com.fast.common.vo.SysEncodingVO;
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
* 编码
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@RestController
@AllArgsConstructor
@RequestMapping("${fast.api-prefix.manage}/encoding")
public class SysEncodingController extends WebBaseController{
    private final ISysEncodingService sysEncodingService;
    private final ISysEncodingSetService sysEncodingSetService;
    private final ISysEncodingSetRuleService sysEncodingSetRuleService;
    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.encoding.page")
    public R<TableDataInfo<SysEncodingVO>> page(@Validated(Qry.class) SysEncodingQuery query){
        startPage();
        return R.success(getDataTable(sysEncodingService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.encoding.info")
    public R<SysEncodingVO> get(@PathVariable("id") String id){
        SysEncoding entity = sysEncodingService.getById(id);
        return R.success(BUtil.copy(entity,SysEncodingVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.encoding.save")
    public R save(@RequestBody @Validated ValidList<SysEncodingVO> vo){
        return R.success(sysEncodingService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.encoding.update")
    public R<Boolean> update(@RequestBody @Validated SysEncodingVO vo){
        return R.toVersion(sysEncodingService.update(vo));
    }

    /**
     * 编码集分页
     * @folder 编码/编码集
     */
    @AutoLov
    @GetMapping("/set/page")
    @ManageCheckPermission(value = "manage.encodingSet.page")
    public R<TableDataInfo<SysEncodingSetVO>> setPage(@Validated(Qry.class) SysEncodingSetQuery query){
        startPage();
        return R.success(getDataTable(sysEncodingSetService.list(query)));
    }

    /**
     * 编码集信息
     * @folder 编码/编码集
     */
    @AutoLov
    @GetMapping("/set/{id}")
    @ManageCheckPermission(value = "manage.encodingSet.info")
    public R<SysEncodingSetVO> setGet(@PathVariable("id") String id){
        SysEncodingSet entity = sysEncodingSetService.getById(id);
        return R.success(BUtil.copy(entity,SysEncodingSetVO.class));
    }

    /**
     * 编码集保存
     * @folder 编码/编码集
     */
    @AutoLov
    @PostMapping("/set")
    @ManageCheckPermission(value = "manage.encodingSet.save")
    public R<List<SysEncodingSetVO>> setSave(@RequestBody @Validated(Save.class) ValidList<SysEncodingSetVO> vo){
        return R.success(sysEncodingSetService.save(vo));
    }

    /**
     * 编码集修改
     * @folder 编码/编码集
     */
    @PutMapping("/set")
    @ManageCheckPermission(value = "manage.encodingSet.update")
    public R setUpdate(@RequestBody @Validated(Update.class) SysEncodingSetVO vo){
        return R.toVersion(sysEncodingSetService.update(vo));
    }

    /**
     * 刷新缓存
     * @folder 编码
     */
    @PutMapping("/refreshCache/{id}")
    @ManageCheckPermission(value = "manage.encodingSet.refreshCache")
    public R refreshCache(@PathVariable("id") String id){
        sysEncodingSetService.refreshCache(id);
        return R.success();
    }

    /**
     * 编码集删除
     * @folder 编码/编码集
     */
    @DeleteMapping("/set")
    @ManageCheckPermission(value = "manage.encodingSet.delete")
    public R setDelete(@RequestBody List<String> idList){
        sysEncodingSetService.delete(idList);
        return R.success();
    }


    /**
     * 编码段分页
     * @folder 编码/编码集/编码段
     */
    @AutoLov
    @GetMapping("/rule/page")
    @ManageCheckPermission(value = "manage.encodingSetRule.page")
    public R<TableDataInfo<SysEncodingSetRuleVO>> rulePage(@Validated(Qry.class) SysEncodingSetRuleQuery query){
        startPage();
        return R.success(getDataTable(sysEncodingSetRuleService.list(query)));
    }

    /**
     * 编码段信息
     * @folder 编码/编码集/编码段
     */
    @AutoLov
    @GetMapping("/rule/{id}")
    @ManageCheckPermission(value = "manage.encodingSetRule.info")
    public R<SysEncodingSetRuleVO> ruleGet(@PathVariable("id") String id){
        SysEncodingSetRule entity = sysEncodingSetRuleService.getById(id);
        return R.success(BUtil.copy(entity,SysEncodingSetRuleVO.class));
    }

    /**
     * 编码段保存
     * @folder 编码/编码集/编码段
     */
    @AutoLov
    @PostMapping("/rule")
    @ManageCheckPermission(value = "manage.encodingSetRule.save")
    public R<SysEncodingSetRuleVO> ruleSave(@RequestBody @Validated(Update.class) SysEncodingSetRuleVO vo){
        return R.success(sysEncodingSetRuleService.save(vo));
    }

    /**
     * 编码段修改
     * @folder 编码/编码集/编码段
     */
    @PutMapping("/rule")
    @ManageCheckPermission(value = "manage.encodingSetRule.update")
    public R ruleUpdate(@RequestBody @Validated SysEncodingSetRuleVO vo){
        return R.toVersion(sysEncodingSetRuleService.update(vo));
    }

    /**
     * 编码段删除
     * @folder 编码/编码集/编码段
     */
    @DeleteMapping("/rule")
    @ManageCheckPermission(value = "manage.encodingSetRule.delete")
    public R ruleDelete(@RequestBody List<String> idList){
        sysEncodingSetRuleService.delete(idList);
        return R.success();
    }
}