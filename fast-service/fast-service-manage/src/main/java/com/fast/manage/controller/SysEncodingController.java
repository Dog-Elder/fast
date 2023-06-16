package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.entity.verification.Qry;
import com.fast.common.query.SysEncodingQuery;
import com.fast.common.service.ISysEncodingService;
import com.fast.common.vo.SysEncodingVO;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.domain.ValidList;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.safe.annotation.manage.ManageCheckPermission;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 编码
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@RestController
@AllArgsConstructor
@RequestMapping("/manage-api/encoding")
public class SysEncodingController extends WebBaseController{
    private final ISysEncodingService sysEncodingService;

    /**
     * 分页
     */
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.encoding.page")
    public R<TableDataInfo> page(@Validated(Qry.class) SysEncodingQuery query){
        startPage();
        return R.success(getDataTable(sysEncodingService.list(query)));
    }

    /**
     * 信息
     */
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.encoding.info")
    public R<SysEncodingVO> get(@PathVariable("id") String id){
        SysEncoding entity = sysEncodingService.getById(id);
        return R.success(BUtil.copy(entity,SysEncodingVO.class));
    }

    /**
     * 保存
     */
    @PostMapping
    @ManageCheckPermission(value = "manage.encoding.save")
    public R save(@RequestBody @Validated ValidList<SysEncodingVO> vo){
        return R.success(sysEncodingService.save(vo));
    }

    /**
     * 修改
     */
    @PutMapping
    @ManageCheckPermission(value = "manage.encoding.update")
    public R<Boolean> update(@RequestBody @Validated SysEncodingVO vo){
        return R.toVersion(sysEncodingService.update(vo));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ManageCheckPermission(value = "manage.encoding.delete")
    public R delete(@RequestBody List<String> idList){
        sysEncodingService.delete(idList);
        return R.success();
    }
}