package com.fast.core.mybatis.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.fast.core.mybatis.annotation.AutoFill;
import lombok.Data;
import lombok.ToString;

/**
 * 安全基类
 * 要维护了version实现乐观锁
 * {@link  com.fast.core.mybatis.annotation.AutoFill} defaultVersion 默认为false ,设置为true时可以在便于添加操作时自动维护version=1
 *
 * @author 黄嘉浩
 **/
@Data
@AutoFill(defaultVersion = true)
@ToString(callSuper = true)
public class BaseVersionEntity extends BaseEntity {
    @Version
    @TableField(value = "`version`", fill = FieldFill.INSERT)
    private Integer version;
}