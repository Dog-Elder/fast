package com.fast.manage.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 体重记录查询
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.manage.entity.TestBodyWeigh
 * @since 1.0.0 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestBodyWeighQuery extends Query {
    /**
     * 体重记录用户ID
     */
    @Display("体重记录用户ID")
    @TableField("test_body_weigh_user_id")
    private String testBodyWeighUserId;


    private String name;
}