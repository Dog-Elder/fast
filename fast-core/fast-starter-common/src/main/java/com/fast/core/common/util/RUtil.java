package com.fast.core.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * 反射工具
 */
@Slf4j
public class RUtil {
    private static final Map<Class<?>, Map<Class<? extends Annotation>, Annotation>> annotationCache = new HashMap<>();
    private static Map<Class<?>, Map<String, Field>> fieldCache = new HashMap<>();

    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * 反射获取对象所有属性及相应值，包括所有上级类，其中枚举取ordinal值
     * 忽略 静态属性 和 类型非BigDecimal的null值
     */
    public static Map<String, Object> getAllFields(Object bean) {
        return getAllFields(bean, null);
    }

    /**
     * 反射获取对象所有属性及相应值，包括所有上级类，其中枚举取ordinal值
     * 不忽略任何值
     */
    public static Map<String, Object> getAllFieldsForBatchAdd(Object bean) {
        Map<String, Object> ret = new HashMap<>();
        allFields(bean.getClass(), field -> {
            if (ret.containsKey(field.getName())) {
                return; // 为了避免把值覆盖为null, 因为:继承如果存在同名的属性,则通过反射从父类获取的是null
            }
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                return; // 忽略静态属性
            }
            field.setAccessible(true);
            Object val = null;
            try {
                val = field.get(bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (val == null) {
                if (field.getType() == BigDecimal.class) { // BigDecimal 默认为 0
                    val = BigDecimal.ZERO;
                }
            }
            if (val instanceof Enum<?>) { // 枚举对象取ordinal
                Enum<?> en = (Enum<?>) val;
                val = en.ordinal();
            }
            ret.put(field.getName(), val);
        });

        return ret;
    }

    /**
     * 反射获取对象属性及相应值，包括所有上级类，其中枚举取ordinal值
     *
     * @param targetPropList 只反射这些指定的属性
     */
    public static Map<String, Object> getAllFields(Object bean, List<String> targetPropList) {
        Map<String, Object> ret = new HashMap<>();
        allFields(bean.getClass(), field -> {
            if (ret.containsKey(field.getName())) {
                return; // 为了避免把值覆盖为null, 因为:继承如果存在同名的属性,则通过反射从父类获取的是null
            }
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                return; // 忽略静态属性
            }
            boolean isOk = targetPropList == null;
            Optional<String> item = Optional.empty();
            if (!isOk) {
                item = targetPropList.stream().filter(a -> StringUtils.equalsIgnoreCase(a, field.getName()))
                        .findFirst();
                isOk = item.isPresent();
            }

            if (isOk) {
                field.setAccessible(true);
                Object val = null;
                try {
                    val = field.get(bean);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (val == null) {
                    if (field.getType() == BigDecimal.class) { // BigDecimal 默认为 0
                        val = BigDecimal.ZERO;
                    } else {
                        return;
                    }
                }
                if (val instanceof Enum<?>) { // 枚举对象取rodinal
                    Enum<?> en = (Enum<?>) val;
                    val = en.ordinal();
                }
                ret.put((item != null && item.isPresent()) ? item.get() : field.getName(), val);
            }
        });

        return ret;
    }

    /**
     * 反射获取对象所有属性（包括父类）
     **/
    public static void allFields(Class<?> clazz, Consumer<Field> fieldConsumer) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                fieldConsumer.accept(field);
            }
        }
    }

    /**
     * 反射获取对象所有属性（包括父类），fn若返回false则停止循环
     **/
    public static void allFields(Class<?> clazz, Function<Field, Boolean> fn) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Boolean b = fn.apply(field);
                if (!b) {
                    break;
                }
            }
        }
    }

    /**
     * 反射获取对象所有方法（包括父类）
     **/
    public static void allMethods(Class<?> clazz, Consumer<Method> fn) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method item : methods) {
                fn.accept(item);
            }
        }
    }

    /**
     * desc 反射获取对象所有方法（包括父类），fn若返回false则停止循环
     **/
    public static void allMethods(Class<?> clazz, Function<Method, Boolean> fn) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method item : methods) {
                Boolean b = fn.apply(item);
                if (!b) {
                    break;
                }
            }
        }
    }

    /**
     * desc 反射获取注解指定属性的值
     **/
    public static Object annoValue(Annotation annotation, String memberName) {
        try {
            InvocationHandler h = Proxy.getInvocationHandler(annotation);
            Field hField = h.getClass().getDeclaredField("memberValues");
            hField.setAccessible(true);
            Map memberValues = (Map) hField.get(h);
            return memberValues.get(memberName);
        } catch (Exception e) {
            LogUtil.err(e);
            return null;
        }
    }

    public static Class<?> getFieldClass(Class<?> aClass, String propertyName) {
        Class<?> ret = null;

        Field field = getField(aClass, propertyName);
        if (field != null) {
            ret = field.getType();
        }

        return ret;
    }

    public static Field getField(Class<?> clazz, String propertyName) {
        Map<String, Field> classFieldCache = fieldCache.get(clazz);
        if (classFieldCache != null && classFieldCache.containsKey(propertyName)) {
            return classFieldCache.get(propertyName);
        }

        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(propertyName);
                field.setAccessible(true);
                if (classFieldCache == null) {
                    classFieldCache = new HashMap<>();
                    fieldCache.put(clazz, classFieldCache);
                }
                classFieldCache.put(propertyName, field);
                break;
            } catch (NoSuchFieldException e) {
                //如果找不到则继续向父类找
            }
        }

        return field;
    }

    /**
     * desc 通过反射获取值
     **/
    public static Object getValue(Object object, String propertyName) {
        Field field = getField(object.getClass(), propertyName);
        try {
            if (field != null) {
                return field.get(object);
            }
        } catch (Exception e) {
            LogFactory.getLog(new CurrentClassGetter().getCurrentClass()).error(e.getMessage(), e);
        }
        return null;
    }

    private static class CurrentClassGetter extends SecurityManager {
        public Class getCurrentClass() {
            Class[] arr = getClassContext();
            int i = 2;
            for (i = 2; i < arr.length; i++) {
                if (arr[i] != LogUtil.class) {
                    break;
                }
            }
            return arr[i];
        }
    }

    /**
     * 获取类注解
     **/
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
        Map<Class<? extends Annotation>, Annotation> classCache = annotationCache.get(clazz);
        if (classCache != null && classCache.containsKey(annotationClass)) {
            return annotationClass.cast(classCache.get(annotationClass));
        }

        T annotation = clazz.getAnnotation(annotationClass);

        if (annotation != null) {
            if (classCache == null) {
                classCache = new HashMap<>();
                annotationCache.put(clazz, classCache);
            }
            classCache.put(annotationClass, annotation);
        }

        return annotation;
    }
}
