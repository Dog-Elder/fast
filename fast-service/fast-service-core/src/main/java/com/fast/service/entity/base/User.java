package com.fast.service.entity.base;


import com.fast.core.mybatis.base.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @program: fast
 * @description: 用户基础类
 * @author: @黄嘉浩
 * @create: 2023-05-11 16:31
 **/
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User extends BaseVersionEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
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
}
