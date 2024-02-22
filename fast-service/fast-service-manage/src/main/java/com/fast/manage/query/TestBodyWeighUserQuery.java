package com.fast.manage.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 体重记录用户查询
 *
 * @see com.fast.manage.entity.TestBodyWeighUser
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TestBodyWeighUserQuery extends Query {
    /**
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

    /**
     * 名字
     */
    @Display("名字")
    @TableField("name")
    private String name;

}