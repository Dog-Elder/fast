package com.fast.service.controller;

import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.util.FastRedis;
import com.fast.service.entity.sys.SysSet;
import com.fast.service.service.ISysSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: fast
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 17:10
 **/
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController extends BaseController {
    private final FastRedis fastRedis;

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
    public TableDataInfo list(SysSet sysSet) {
        startPage();
        List<SysSet> list = sysSetService.list(sysSet);
        return getDataTable(list);
    }
}
