package com.fast.common.controller;

import com.fast.common.entity.sys.SysRequestLogs;
import com.fast.common.entity.verification.Qry;
import com.fast.common.query.SysRequestLogsQuery;
import com.fast.common.service.ISysRequestLogsService;
import com.fast.common.vo.SysRequestLogsVO;
import com.fast.core.common.util.bean.BUtil;
import lombok.AllArgsConstructor;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 接口请求日志
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-07-10
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/request-logs")
public class SysRequestLogsController extends WebBaseController{
    private final ISysRequestLogsService sysRequestLogsService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.requestLogs.page")
    public R<TableDataInfo<SysRequestLogsVO>> page(@Validated(Qry.class) SysRequestLogsQuery query){
        startPage();
        return R.success(getDataTable(sysRequestLogsService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.requestLogs.info")
    public R<SysRequestLogsVO> get(@PathVariable("id") String id){
        SysRequestLogs entity = sysRequestLogsService.getById(id);
        return R.success(BUtil.copy(entity,SysRequestLogsVO.class));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.requestLogs.delete")
    public R delete(@RequestBody List<String> idList){
        sysRequestLogsService.delete(idList);
        return R.success();
    }
}