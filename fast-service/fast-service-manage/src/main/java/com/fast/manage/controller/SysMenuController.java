package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.core.common.annotation.lov.AutoLov;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.domain.ValidList;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.query.SysMenuQuery;
import com.fast.manage.service.ISysMenuService;
import com.fast.manage.vo.SysMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 *
 * @author 黄嘉浩
 * @date 2021-06-29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("${fast.api-prefix.manage}/menu")
public class SysMenuController extends WebBaseController {

    private final ISysMenuService sysMenuService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.menu.page")
    public R<TableDataInfo<SysMenuVO>> page(@Validated(Qry.class) SysMenuQuery query) {
        startPage();
        return R.success(getDataTable(sysMenuService.list(query)));
    }

    /**
     * 树
     */
    @AutoLov
    @GetMapping("/tree")
    @ManageCheckPermission(value = "manage.menu.tree")
    public R<List<SysMenuVO>> tree(@Validated(Qry.class) SysMenuQuery query) {
        return R.success(sysMenuService.tree(query));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.menu.info")
    public R<SysMenuVO> get(@PathVariable("id") String id) {
        SysMenu entity = sysMenuService.getById(id);
        return R.success(BUtil.copy(entity, SysMenuVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.menu.save")
    public R save(@RequestBody @Validated(Save.class) ValidList<SysMenuVO> vo) {
        return R.success(sysMenuService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.menu.update")
    public R update(@RequestBody @Validated SysMenuVO vo) {
        return R.toVersion(sysMenuService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.menu.delete")
    public R delete(@RequestBody List<String> idList) {
        sysMenuService.delete(idList);
        return R.success();
    }
}