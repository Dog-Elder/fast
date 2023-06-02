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
 * 菜单权限Controller
 *
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/manage-api/menu")
public class SysMenuController extends WebBaseController {
    private String prefix = "xxxxx/menu";

    private final ISysMenuService sysMenuService;

    @GetMapping()
    public String menu() {
        return prefix + "/menu";
    }

    /**
     * 查询菜单权限列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysMenu sysMenu) {
        startPage();
        List<SysMenu> list = sysMenuService.list(sysMenu);
        return getDataTable(list);
    }


    /**
     * 新增菜单权限
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存菜单权限
     */
    @PostMapping("/add")
    @ResponseBody
    public R<Boolean> addSave(SysMenu sysMenu) {
        return toAjax(sysMenuService.save(sysMenu));
    }

    /**
     * 修改菜单权限
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysMenu sysMenu = sysMenuService.selectById(id);
        mmap.put("sysMenu", sysMenu);
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单权限
     */
    @PostMapping("/edit")
    @ResponseBody
    public R editSave(SysMenu sysMenu) {
        return toAjax(sysMenuService.update(sysMenu));
    }

    /**
     * 真批量删除菜单权限
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(String ids) {
        return toAjax(sysMenuService.deleteByIds(ids));
    }

    /**
     * 逻辑删除菜单权限
     */
    @PostMapping("/logic-remove")
    @ResponseBody
    public R logicRemove(String ids) {
        return toAjax(sysMenuService.logicRemove(ids));
    }
}