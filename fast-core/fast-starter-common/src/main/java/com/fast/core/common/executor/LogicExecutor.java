package com.fast.core.common.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 逻辑执行器
 * 业务逻辑执行器接口，用于执行处理注解的逻辑并返回结果。
 * @Author: 黄嘉浩
 * @Date: 2023-06-21 17:01
 **/
public interface LogicExecutor<T> {
    /**
     * 执行处理注解的逻辑并返回结果。
     *
     * @param field      字段对象
     * @param annotation 注解对象
     * @return 处理结果
     * @throws IllegalAccessException    如果访问字段时发生非法访问异常
     * @throws InvocationTargetException 如果调用执行方法时发生异常
     * @throws NoSuchMethodException     如果找不到指定方法
     */
    T execute(Field field, Annotation annotation) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
