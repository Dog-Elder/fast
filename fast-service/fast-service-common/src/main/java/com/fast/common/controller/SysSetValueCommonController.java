package com.fast.common.controller;

import com.fast.common.dto.SysCreateCode;
import com.fast.common.entity.verification.Qry;
import com.fast.common.query.SysSetValueQuery;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.service.ISysSetValueService;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
* 公共服务
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-13
*/
@RestController
@AllArgsConstructor
@RequestMapping("/common")
public class SysSetValueCommonController extends WebBaseController{
    private final ISysSetValueService sysSetValueService;
    private final ISysEncodingSetRuleService sysEncodingSetRuleService;

    /**
     * 定制查询多值集值列表(不分页)
     * 注意尽量避免返回大量的值集值
     * @folder 公共服务/值集
     */
    @GetMapping("/set-value/custom-list")
    public R customList(@RequestParam Map<String,String> map) {
        return R.success(sysSetValueService.customList(map));
    }

    /**
     * 通过缓存查询值列表
     * @folder 公共服务/值集
     */
    @GetMapping("/set-value/cache-data-list")
    public R<TableDataInfo> qryCacheDataList(@Validated(value = Qry.class) SysSetValueQuery req) {
        return R.success(getDataTable(sysSetValueService.qryCacheDataList(req)));
    }

    /**
     * 获取值集含义
     * @folder 公共服务/值集
     */
    @GetMapping("/set-value/get-value/{setCode}/{setValueKey}")
    public R<String> qryValue(@PathVariable("setCode") String setCode,@PathVariable("setValueKey") String setValueKey) {
        return R.success(sysSetValueService.qryValues(setCode,setValueKey));
    }

    /**
     * 获取编码
     * @folder 公共服务/编码
     * @param req: 生成编码请求对象
     * @Date: 2022/9/25 22:23
     * @return: java.lang.String 编码
     **/
    @GetMapping("/get-code")
    @ResponseBody
    public R<String> createCode(@Validated(Qry.class) SysCreateCode req) {
        String code = sysEncodingSetRuleService.createCode(req);
        return R.success(code);
    }
}