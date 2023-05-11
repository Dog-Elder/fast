package com.fast.core.mybatis.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;import com.fast.core.mybatis.base.annotation.AutoFill;
import lombok.Data;
import lombok.ToString;

/**
 * @description: 安全基类
 * 之所以叫安全基类是因为继承公共类库中
 * 安全基类了需要维护version便于实现乐观锁
 * @AutoFill defaultVersion 默认为false ,设置为true时可以在便于添加操作时自动维护version=1
 **/
@Data
@AutoFill(defaultVersion = true)
@ToString(callSuper = true)
public class BaseVersionEntity extends BaseEntity {
    @Version
    @TableField(value="`version`",fill = FieldFill.INSERT)
    private Integer version;
}