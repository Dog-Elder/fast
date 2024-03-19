package com.fast.manage.dto.user;

import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * SYS 用户密码 DTO
 *
 * @author 黄嘉浩
 * @date 2024/03/12
 */
@Data
public class SysUserPasswordDTO {

    /**
     * 用户id
     **/
    @Display("id")
    @NotBlank(message = Com.Require)
    private String id;
    /**
     * 原始密码
     **/
    @Display("原始密码")
    @NotBlank(message = Com.Require)
    private String oldPassword;
    /**
     * 新密码
     **/
    @Display("新密码")
    @NotBlank(message = Com.Require)
    private String newPassword;
}
