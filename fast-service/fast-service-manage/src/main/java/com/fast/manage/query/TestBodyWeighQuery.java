package com.fast.manage.query;

import com.fast.core.common.domain.page.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 体重记录查询
 *
 * @see com.fast.manage.entity.TestBodyWeigh
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestBodyWeighQuery extends Query {
    private String name;
}