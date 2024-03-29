package com.fast.common.controller;

import com.fast.common.entity.sys.SysSet;
import com.fast.common.service.ISysSetService;
import com.fast.core.annotation.Cache;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.safe.annotation.manage.ManageCheckLogin;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试控制器
 *
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 17:10
 **/
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController extends WebBaseController {
    private final FastRedis fastRedis;

    @ManageCheckLogin
    @GetMapping
    public String test() {
        String string = fastRedis.getString("111");
        System.out.println("string = " + string);

        return "ok";
    }

    @Autowired
    private ISysSetService sysSetService;

    /**
     * @folder 一级目录/二级目录
     * 查询值集值表
     */
    @GetMapping
    @RequestMapping("/set")
    @ManageCheckPermission("user.add")
    public R<TableDataInfo> list(SysSet sysSet) {
        startPage();
        List<SysSet> list = sysSetService.list();
        return R.success(getDataTable(list));
    }

    /**
     * 查询值集值表
     */
    @PostMapping("/set2")
    @ManageCheckPermission(value = "user.delete")
    public R<TableDataInfo> list2(@RequestBody SysSet sysSet) {
        startPage();
        List<SysSet> list = sysSetService.list();
        return R.success(getDataTable(list));
    }

    /**
     * 查询值集值表
     */
    @GetMapping("/set3/{v2}")
    @Cache(value = "test@ttl=30")
    public R<List<SysSet>> list3(@RequestParam("v1") String aa, @PathVariable("v2") String v2) {
//         List<SysSet> list = );
        return R.success(sysSetService.list());
    }
}
