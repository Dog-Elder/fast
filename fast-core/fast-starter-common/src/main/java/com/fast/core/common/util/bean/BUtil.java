package com.fast.core.common.util.bean;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用BeanUtils.copyProperties有一个问题就是当src对象的键值为Null时就会把target对象的对应键值覆盖成空，这不科学，本实现类在继承BeanUtils
 * 的基础上对此做了扩展
 * 增加 获取Baen对象
 */
@Component
@RequiredArgsConstructor
public class BUtil extends BeanUtils {

    private final ApplicationContext applicationContext;

    /**
     * 浅拷贝
     * 创建一个targetClazz的实例（利用其默认构造），然后将source的非null的属性复制到target上的同名属性
     */
    public static <S, T> T copy(S source, Class<T> targetClazz) {
        if (ObjectUtils.isEmpty(source)) {
            throw new IllegalArgumentException("Source object must not be null");
        }
        if (ObjectUtils.isEmpty(targetClazz)) {
            throw new IllegalArgumentException("Target class must not be null");
        }

        Class<?> sourceClass = source.getClass();
        HashMap<PropertyDescriptor, PropertyDescriptor> pdMap = getPropertyDescriptorsMap(sourceClass, targetClazz);
        try {
            T target = targetClazz.getDeclaredConstructor().newInstance();
            copy(source, target, pdMap, true);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 浅拷贝或创建空
     * 创建一个targetClazz的实例（利用其默认构造），然后将source的非null的属性复制到target上的同名属性
     *
     * @param source      源
     * @param targetClazz 目标 Clazz
     * @return {@link T}
     */
    public static <S, T> T copyOrCreateEmpty(S source, Class<T> targetClazz) {
        if (ObjectUtils.isEmpty(targetClazz)) {
            throw new IllegalArgumentException("Target class must not be null");
        }

        T target;
        try {
            // 即使 source 为 null，也创建 targetClazz 的一个实例
            target = targetClazz.getDeclaredConstructor().newInstance();

            if (!ObjectUtils.isEmpty(source)) {
                Class<?> sourceClass = source.getClass();
                HashMap<PropertyDescriptor, PropertyDescriptor> pdMap = getPropertyDescriptorsMap(sourceClass, targetClazz);
                copy(source, target, pdMap, true);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create instance of class: " + targetClazz.getName(), e);
        }

        return target;
    }
    // 浅拷贝


    public static <S> S copy(S source) {
        return (S) copy(source, source.getClass());
    }

    public static HashMap<PropertyDescriptor, PropertyDescriptor> getPropertyDescriptorsMap(Class<?> sourceClass,
                                                                                            Class<?> targetClazz) {
        PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClazz);
        HashMap<PropertyDescriptor, PropertyDescriptor> pdMap = new HashMap<>();
        for (PropertyDescriptor targetPd : targetPds) {
            pdMap.put(targetPd, getPropertyDescriptor(sourceClass, targetPd.getName()));
        }
        return pdMap;
    }

    /**
     * 将source的非null的属性复制到target上的同名属性
     */
    public static <S, T> T copy(S source, T target) {
        HashMap<PropertyDescriptor, PropertyDescriptor> pdMap = getPropertyDescriptorsMap(source.getClass(), target.getClass());
        try {
            copy(source, target, pdMap, true);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, T> void copy(S source, T target, HashMap<PropertyDescriptor, PropertyDescriptor> pdMap, boolean ignoreNull) {
        pdMap.forEach((PropertyDescriptor targetPd, PropertyDescriptor sourcePd) -> {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && sourcePd != null) { //  && (ignoreList == null ||
                //  !ignoreList.contains(targetPd.getName()))) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod != null
                        && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (ignoreNull && value == null) {
                            return; //  忽略掉值为null的
                        }
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    } catch (Throwable ex) {
                        throw new FatalBeanException(
                                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                    }
                }
            }
        });
    }


    /**
     * 拷贝转List
     */
    public static <S, T> List<T> copyList(Collection<S> sources, Class<T> targetClazz) {
        return sources.stream().map(source -> copy(source, targetClazz)).collect(Collectors.toList());
    }

    // 以下是深拷贝 ------------

    /**
     * 复制对象到指定类（深度拷贝）
     *
     * @param object
     * @param destclas 指定类
     * @param <T>
     * @return
     */
    public static <T> T clone(final Object object, Class<T> destclas) {
        if (object == null) {
            return null;
        }
        String json = JSON.toJSONString(object);
        return JSON.parseObject(json, destclas);
    }

    /**
     * 复制集合到指定类（深度拷贝）
     *
     * @param object
     * @param destclas 指定类
     * @param <T>
     * @return
     */
    public static <T> List<T> cloneList(List<?> object, Class<T> destclas) {
        if (object == null) {
            return new ArrayList<>();
        }
        String json = JSON.toJSONString(object);
        return JSON.parseArray(json, destclas);
    }


    /**
     * 获取 Bean
     *
     * @param clazz 类
     * @return {@link T}
     */
    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
