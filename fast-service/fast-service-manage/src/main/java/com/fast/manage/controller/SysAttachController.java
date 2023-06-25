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
import com.fast.manage.entity.SysAttach;
import com.fast.manage.query.SysAttachQuery;
import com.fast.manage.service.ISysAttachService;
import com.fast.manage.vo.SysAttachVO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 系统附件
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-26
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/sys-attach")
public class SysAttachController extends WebBaseController{
    private final ISysAttachService sysAttachService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.sysAttach.page")
    public R<TableDataInfo<SysAttachVO>> page(@Validated(Qry.class) SysAttachQuery query){
        startPage();
        return R.success(getDataTable(sysAttachService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.sysAttach.info")
    public R<SysAttachVO> get(@PathVariable("id") String id){
        SysAttach entity = sysAttachService.getById(id);
        return R.success(BUtil.copy(entity,SysAttachVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.sysAttach.save")
    public R<List<SysAttachVO>> save(@RequestBody @Validated(Save.class) ValidList<SysAttachVO> vo){
        return R.success(sysAttachService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.sysAttach.update")
    public R update(@RequestBody @Validated(Update.class) SysAttachVO vo){
        return R.toVersion(sysAttachService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.sysAttach.delete")
    public R delete(@RequestBody List<String> idList){
        sysAttachService.delete(idList);
        return R.success();
    }
}