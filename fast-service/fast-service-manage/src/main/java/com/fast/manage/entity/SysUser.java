package com.fast.manage.entity;

import com.fast.common.entity.base.User;
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
public class SysUser extends User {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员 1 : 是
     */
    private Integer administrator;
    /**
     * 用户状态（1 :正常 0:停封）
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
