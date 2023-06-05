package com.fast.common.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.json.JSONUtil;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.service.ISysSetService;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.safe.annotation.manage.ManageCheckLogin;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import com.fast.core.safe.util.ManageUtil;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 测试控制器
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 17:10
 **/
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController extends BaseController {
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
     * 查询值集列表
     */
    @GetMapping
    @RequestMapping("/set")
    @ManageCheckPermission("user.add")
    public R<TableDataInfo> list(SysSet sysSet) {
        startPage();
        List<SysSet> list = sysSetService.list(sysSet);
        return R.success(getDataTable(list));
    }
    /**
     * 查询值集列表
     */
    @PostMapping("/set2")
    @ManageCheckPermission(value = "user.delete")
    public R<TableDataInfo> list2(@RequestBody SysSet sysSet) {
        startPage();
        List<SysSet> list = sysSetService.list(sysSet);
        return R.success(getDataTable(list));
    }
    /**
     * 查询值集列表
     */
    @GetMapping("/set3/{v2}")
    public R<String> list3(@RequestParam("v1")String aa,@PathVariable("v2") String v2) {
        return R.success(aa+"   "+v2);
    }

    @PostMapping("/login")
    public R doLogin(@RequestParam("username")String username,@RequestParam("password")String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            ManageUtil.login(10001);
            // 第2步，获取 Token  相关参数
            SaTokenInfo tokenInfo = ManageUtil.getTokenInfo();
            // 第3步，返回给前端
            return R.success(JSONUtil.parseObj(tokenInfo));
        }
        return R.success("登录失败");
    }
    @PostMapping("/ee")
    @ResponseBody
    public R<String> ee(@RequestParam("file") MultipartFile[] file) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        System.out.println(file);
        return R.success("登录失败");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + ManageUtil.isLogin();
    }
}
