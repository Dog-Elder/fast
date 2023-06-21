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
import com.fast.manage.entity.SysUser;
import com.fast.manage.query.SysUserQuery;
import com.fast.manage.service.ISysUserService;
import com.fast.manage.vo.SysUserVO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 后台用户
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-20
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/user")
public class SysUserController extends WebBaseController {
    private final ISysUserService sysUserService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.user.page")
    public R<TableDataInfo<SysUserVO>> page(@Validated(Qry.class) SysUserQuery query){
        startPage();
        return R.success(getDataTable(sysUserService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.user.info")
    public R<SysUserVO> get(@PathVariable("id") String id){
        SysUser entity = sysUserService.getById(id);
        return R.success(BUtil.copy(entity,SysUserVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.user.save")
    public R<List<SysUserVO>> save(@RequestBody @Validated(Save.class) ValidList<SysUserVO> vo){
        return R.success(sysUserService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.user.update")
    public R update(@RequestBody @Validated(Update.class) SysUserVO vo){
        return R.toVersion(sysUserService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.user.delete")
    public R delete(@RequestBody List<String> idList){
        sysUserService.delete(idList);
        return R.success();
    }
}