package com.fast.core.mybatis.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 拓展 MyBatis Plus QueryWrapper 类，主要增加如下功能：
 * <p>
 * 1. 拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 *
 * @param <T> 数据类型
 */
public class BaseLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {

    public BaseLambdaQueryWrapper<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (BaseLambdaQueryWrapper<T>) super.like(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (BaseLambdaQueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        if (!ArrayUtil.isEmpty(values)) {
            return (BaseLambdaQueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (BaseLambdaQueryWrapper<T>) super.eq(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> neIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (BaseLambdaQueryWrapper<T>) super.ne(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (BaseLambdaQueryWrapper<T>) super.gt(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> geIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (BaseLambdaQueryWrapper<T>) super.ge(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (BaseLambdaQueryWrapper<T>) super.lt(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> leIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (BaseLambdaQueryWrapper<T>) super.le(column, val);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (BaseLambdaQueryWrapper<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (BaseLambdaQueryWrapper<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (BaseLambdaQueryWrapper<T>) le(column, val2);
        }
        return this;
    }

    public BaseLambdaQueryWrapper<T> betweenIfPresent(SFunction<T, ?> column, Object[] values) {
        Object val1 = ArrayUtils.get(values, 0);
        Object val2 = ArrayUtils.get(values, 1);
        return betweenIfPresent(column, val1, val2);
    }

    //  ========== 重写父类方法，方便链式调用 ==========

    @Override
    public BaseLambdaQueryWrapper<T> eq(boolean condition, SFunction<T, ?> column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public BaseLambdaQueryWrapper<T> eq(SFunction<T, ?> column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public BaseLambdaQueryWrapper<T> orderByDesc(SFunction<T, ?> column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public BaseLambdaQueryWrapper<T> orderByAsc(SFunction<T, ?> column) {
        super.orderByAsc(true, column);
        return this;
    }

    @Override
    public BaseLambdaQueryWrapper<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public BaseLambdaQueryWrapper<T> in(SFunction<T, ?> column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }

    /**
     * 设置只返回最后一条
     */
    public BaseLambdaQueryWrapper<T> limitN(int n) {
        super.last("LIMIT " + n);
        return this;
    }

}
