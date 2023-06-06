package com.fast.manage.controller;

import com.fast.core.common.domain.controller.WebBaseController;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.manage.entity.SysMenu;
import com.fast.manage.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 *
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/manage-api/menu")
public class SysMenuController extends WebBaseController {

    private final ISysMenuService sysMenuService;


    /**
     * 查询菜单权限列表
     */
    @PostMapping("/list")
    public TableDataInfo list(SysMenu sysMenu) {
        startPage();
        List<SysMenu> list = sysMenuService.list(sysMenu);
        return getDataTable(list);
    }


    /**
     * 新增保存菜单权限
     */
    @PostMapping("/add")
    public R<Boolean> addSave(SysMenu sysMenu) {
        return toAjax(sysMenuService.save(sysMenu));
    }


    /**
     * 修改保存菜单权限
     */
    @PostMapping("/edit")
    public R editSave(SysMenu sysMenu) {
        return toAjax(sysMenuService.update(sysMenu));
    }

    /**
     * 真批量删除菜单权限
     */
    @PostMapping("/remove")
    public R remove(String ids) {
        return toAjax(sysMenuService.deleteByIds(ids));
    }

    /**
     * 逻辑删除菜单权限
     */
    @PostMapping("/logic-remove")
    public R logicRemove(String ids) {
        return toAjax(sysMenuService.logicRemove(ids));
    }
}