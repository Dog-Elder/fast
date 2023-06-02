package com.fast.manage.entity;

import com.fast.core.common.util.Util;
import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 后台用户对象 sys_admin
 *
 * @author @Dog_Elder
 * @Date 2021-06-18
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysAdmin extends BaseVersionEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 父级id
     */
    private Long parentId;
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
    private String sex;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 头像URL
     */
    private String headPortrait;
    /**
     * 管理员 1 : 是
     */
    private Integer administrator;
    /**
     * 用户状态（0代表存在 2代表删除）
     */
    private String status;
    /**
     * 最近操作
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recentOperation;

    public boolean isGod() {
        if (Util.isNull(administrator)) {
            return false;
        }
        return administrator == 1;
    }


}
