package com.fast.manage.controller;

import com.fast.common.controller.WebBaseController;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.vo.UserInfoVO;
import com.fast.core.common.annotation.lov.AutoLov;
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
    @AutoLov
    @GetMapping("/page")
    @ManageCheckPermission(value = "manage.user.page")
    public R<TableDataInfo<SysUserVO>> page(@Validated(Qry.class) SysUserQuery query) {
        startPage();
        return R.success(getDataTable(sysUserService.list(query)));
    }

    /**
     * 信息
     */
    @AutoLov
    @GetMapping("{id}")
    @ManageCheckPermission(value = "manage.user.info")
    public R<SysUserVO> get(@PathVariable("id") String id) {
        SysUser entity = sysUserService.getById(id);
        return R.success(BUtil.copy(entity, SysUserVO.class));
    }

    /**
     * 保存
     */
    @AutoLov
    @PostMapping
    @ManageCheckPermission(value = "manage.user.save")
    public R<List<SysUserVO>> save(@RequestBody @Validated(Save.class) ValidList<SysUserVO> vo) {
        return R.success(sysUserService.save(vo));
    }

    /**
     * 修改密码
     *
     * @param vo 用户信息
     * @return {@link R}
     */
    @PostMapping("/update-password")
    @ManageCheckPermission(value = "manage.user.updatePassword")
    public R updatePassword(@RequestBody UserInfoVO vo) {
        return R.toVersion(sysUserService.updateThePassword(vo));
    }

    /**
     * 更新用户信息
     *
     * @param vo 签证官
     * @return {@link R}
     */
    @PostMapping("/update-info")
    @ManageCheckPermission(value = "manage.user.updateInfo")
    public R updateInfo(@RequestBody UserInfoVO vo) {
        return R.toVersion(sysUserService.updateUserInfo(vo));
    }
}