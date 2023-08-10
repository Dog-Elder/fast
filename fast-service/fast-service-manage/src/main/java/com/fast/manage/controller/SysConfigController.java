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
import com.fast.manage.entity.SysConfig;
import com.fast.manage.query.SysConfigQuery;
import com.fast.manage.service.ISysConfigService;
import com.fast.manage.vo.SysConfigVO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 系统参数配置
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-08-09
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/config")
public class SysConfigController extends WebBaseController{
    private final ISysConfigService sysConfigService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.config.page")
    public R<TableDataInfo<SysConfigVO>> page(@Validated(Qry.class) SysConfigQuery query){
        startPage();
        return R.success(getDataTable(sysConfigService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.config.info")
    public R<SysConfigVO> get(@PathVariable("id") String id){
        SysConfig entity = sysConfigService.getById(id);
        return R.success(BUtil.copy(entity,SysConfigVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.config.save")
    public R<List<SysConfigVO>> save(@RequestBody @Validated(Save.class) ValidList<SysConfigVO> vo){
        return R.success(sysConfigService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.config.update")
    public R update(@RequestBody @Validated(Update.class) SysConfigVO vo){
        return R.toVersion(sysConfigService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.config.delete")
    public R delete(@RequestBody List<String> idList){
        sysConfigService.delete(idList);
        return R.success();
    }
}