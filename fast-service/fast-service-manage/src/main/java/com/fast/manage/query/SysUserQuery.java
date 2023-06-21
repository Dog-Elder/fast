package com.fast.manage.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import com.fast.core.common.domain.page.Query;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
* 后台用户查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserQuery extends Query {
    /**
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

    /**
     * 用户编码
     */
    @Display("用户编码")
    @TableField("code")
    private String code;

    /**
     * 用户名
     */
    @Display("用户名")
    @TableField("username")
    private String username;

    /**
     * 昵称
     */
    @Display("昵称")
    @TableField("nickname")
    private String nickname;

    /**
     * 邮箱
     */
    @Display("邮箱")
    @TableField("email")
    private String email;

    /**
     * 姓名
     */
    @Display("姓名")
    @TableField("real_name")
    private String realName;

    /**
     * 性别
     */
    @Display("性别")
    @TableField("sex")
    private Integer sex;

    /**
     * 手机号
     */
    @Display("手机号")
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 管理员 1 : 是
     */
    @Display("管理员 1 : 是")
    @TableField("administrator")
    private Integer administrator;

    /**
     * 状态 1 :正常 0:停封 
     */
    @Display("状态 1 :正常 0:停封 ")
    @TableField("status")
    private Integer status;

}