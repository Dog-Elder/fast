package com.fast.core.mybatis.service;

/**
 * @author 黄嘉浩
 */
public interface IEventService<T> {
    /**
     * 填充Code
     **/
    void paddingCode(T entity);


}
