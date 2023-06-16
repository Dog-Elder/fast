package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.entity.verification.Qry;
import com.fast.common.query.SysEncodingSetQuery;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingSetVO;
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
* 编码集
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/encoding-set")
public class SysEncodingSetController extends WebBaseController{
    private final ISysEncodingSetService sysEncodingSetService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.encodingSet.page")
    public R<TableDataInfo<SysEncodingSetVO>> page(@Validated(Qry.class) SysEncodingSetQuery query){
        startPage();
        return R.success(getDataTable(sysEncodingSetService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.encodingSet.info")
    public R<SysEncodingSetVO> get(@PathVariable("id") String id){
        SysEncodingSet entity = sysEncodingSetService.getById(id);
        return R.success(BUtil.copy(entity,SysEncodingSetVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.encodingSet.save")
    public R<List<SysEncodingSetVO>> save(@RequestBody @Validated ValidList<SysEncodingSetVO> vo){
        return R.success(sysEncodingSetService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.encodingSet.update")
    public R update(@RequestBody @Validated SysEncodingSetVO vo){
        return R.toVersion(sysEncodingSetService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.encodingSet.delete")
    public R delete(@RequestBody List<String> idList){
        sysEncodingSetService.delete(idList);
        return R.success();
    }
}