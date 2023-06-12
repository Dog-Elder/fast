package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.entity.verification.Qry;
import com.fast.common.query.SysEncodingSetRuleQuery;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.vo.SysEncodingSetRuleVO;
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
* 编码段
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/encodingSetRule")
public class SysEncodingSetRuleController extends WebBaseController{
    private final ISysEncodingSetRuleService sysEncodingSetRuleService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.encodingSetRule.page")
    public R<TableDataInfo> page(@Validated(Qry.class) SysEncodingSetRuleQuery query){
        startPage();
        return R.success(getDataTable(sysEncodingSetRuleService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.encodingSetRule.info")
    public R<SysEncodingSetRuleVO> get(@PathVariable("id") String id){
        SysEncodingSetRule entity = sysEncodingSetRuleService.getById(id);
        return R.success(BUtil.copy(entity,SysEncodingSetRuleVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.encodingSetRule.save")
    public R save(@RequestBody @Validated ValidList<SysEncodingSetRuleVO> vo){
        return R.success(sysEncodingSetRuleService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.encodingSetRule.update")
    public R update(@RequestBody @Validated SysEncodingSetRuleVO vo){
        return toVersionAjax(sysEncodingSetRuleService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.encodingSetRule.delete")
    public R delete(@RequestBody List<String> idList){
        sysEncodingSetRuleService.delete(idList);
        return R.success();
    }
}