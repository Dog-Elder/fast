package com.fast.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息VO
 *
 * @author 黄嘉浩
 * @date 2023/07/31
 */
@Data
@Accessors(chain = true)
public class UserInfoVO {
    /**
     * 用户id
     **/
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 新密码
     */
    private String nowPassword;
    /**
     * 原始密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 头像URL
     */
    private String headPortrait;
    /**
     * 版本号
     **/
    private Integer version;

}
