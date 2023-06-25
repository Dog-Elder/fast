package com.fast.core.common.util;


import com.fast.core.common.executor.LogicExecutor;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 反射工具类
 */
@Slf4j
public class RUtil {
    // 缓存对象的运行时类
    private static final Map<Object, Class<?>> classCache = new ConcurrentHashMap<>();
    // 缓存类的注解
    private static final Map<Class<?>, Map<Class<? extends Annotation>, Annotation>> annotationCache = new HashMap<>();
    // 缓存类的字段
    private static final Map<Class<?>, Map<String, Field>> fieldCache = new HashMap<>();
    // 缓存类的所有字段，包括父类的字段
    private static final Map<Class<?>, List<Field>> allFieldsCache = new ConcurrentHashMap<>();

    /**
     * 创建指定类的新实例
     *
     * @param clazz 类型
     * @param <T>   泛型参数
     * @return 类的新实例
     * @throws NoSuchMethodException     如果找不到无参构造函数
     * @throws IllegalAccessException    如果无法访问构造函数
     * @throws InvocationTargetException 如果构造函数调用发生异常
     * @throws InstantiationException    如果实例化失败
     */
    public static <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * 获取对象的所有字段及相应值（包括父类的字段），枚举类型返回ordinal值，忽略静态字段和BigDecimal类型的null值
     *
     * @param bean 对象实例
     * @return 包含字段名和对应值的映射
     */
    public static Map<String, Object> getAllFields(Object bean) {
        return getAllFields(bean, null);
    }

    /**
     * 获取对象的所有字段及相应值（包括父类的字段），枚举类型返回ordinal值，不忽略任何值
     *
     * @param bean 对象实例
     * @return 包含字段名和对应值的映射
     */
    public static Map<String, Object> getAllFieldsForBatchAdd(Object bean) {
        Map<String, Object> ret = new HashMap<>();
        allFields(bean.getClass(), field -> {
            if (ret.containsKey(field.getName())) {
                return; // 避免将值覆盖为null，因为在继承关系中，如果存在同名的属性，则通过反射从父类获取的是null
            }
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                return; // 忽略静态字段
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
            if (val instanceof Enum<?>) { // 枚举对象取ordinal值
                Enum<?> en = (Enum<?>) val;
                val = en.ordinal();
            }
            ret.put(field.getName(), val);
        });

        return ret;
    }

    /**
     * 获取对象指定属性的字段及相应值（包括父类的字段），枚举类型返回ordinal值
     *
     * @param bean            对象实例
     * @param targetPropList  只反射指定的属性列表
     * @return 包含字段名和对应值的映射
     */
    public static Map<String, Object> getAllFields(Object bean, List<String> targetPropList) {
        Map<String, Object> ret = new HashMap<>();
        allFields(bean.getClass(), field -> {
            if (ret.containsKey(field.getName())) {
                return; // 避免将值覆盖为null，因为在继承关系中，如果存在同名的属性，则通过反射从父类获取的是null
            }
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                return; // 忽略静态字段
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
                if (val instanceof Enum<?>) { // 枚举对象取ordinal值
                    Enum<?> en = (Enum<?>) val;
                    val = en.ordinal();
                }
                ret.put((item != null && item.isPresent()) ? item.get() : field.getName(), val);
            }
        });

        return ret;
    }

    /**
     * 遍历类的所有字段（包括父类的字段）
     *
     * @param clazz          类型
     * @param fieldConsumer  字段消费者
     */
    public static void allFields(Class<?> clazz, Consumer<Field> fieldConsumer) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                fieldConsumer.accept(field);
            }
        }
    }

    /**
     * 遍历类的所有字段（包括父类的字段），通过回调函数判断是否继续循环
     *
     * @param clazz 类型
     * @param fn    回调函数，返回false时停止循环
     */
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
     * 遍历类的所有方法（包括父类的方法）
     *
     * @param clazz 类型
     * @param fn    方法消费者
     */
    public static void allMethods(Class<?> clazz, Consumer<Method> fn) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method item : methods) {
                fn.accept(item);
            }
        }
    }

    /**
     * 遍历类的所有方法（包括父类的方法），通过回调函数判断是否继续循环
     *
     * @param clazz 类型
     * @param fn    回调函数，返回false时停止循环
     */
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
     * 获取注解指定属性的值
     *
     * @param annotation  注解实例
     * @param memberName  属性名称
     * @return 属性的值
     */
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

    /**
     * 获取字段的类型
     *
     * @param aClass       类型
     * @param propertyName 字段名
     * @return 字段的类型
     */
    public static Class<?> getFieldClass(Class<?> aClass, String propertyName) {
        Class<?> ret = null;

        Field field = getField(aClass, propertyName);
        if (field != null) {
            ret = field.getType();
        }

        return ret;
    }

    /**
     * 获取字段实例
     *
     * @param clazz        类型
     * @param propertyName 字段名
     * @return 字段实例
     */
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
                //如果找不到则继续向父类查找
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
            LogUtil.err(e);
        }
        return null;
    }

    /**
     * 获取类的所有字段（包括父类的字段）
     *
     * @param clazz 类型
     * @return 包含所有字段的列表
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = allFieldsCache.get(clazz);
        if (fields != null) {
            return fields;
        }
        final List<Field> fieldList = new ArrayList<>(); // 使用 final 声明列表变量
        allFields(clazz, field -> {
            fieldList.add(field);
            return true;
        });
        allFieldsCache.put(clazz, fieldList); // 缓存字段列表
        return fieldList;
    }


    /**
     * 获取类的所有字段（包括父类的字段）
     *
     * @param object 类(运行时)
     * @return 包含所有字段的列表
     */
    private static List<Field> getFields(Object object) {
        Class<?> clazz = getClass(object);
        List<Field> fields = getAllFields(clazz);
        return fields;
    }

    /**
     * 获取指定类型的注解
     *
     * @param clazz      类型
     * @param annotation 注解类型
     * @param <A>        注解类型参数
     * @return 注解实例
     */
    public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotation) {
        Map<Class<? extends Annotation>, Annotation> annotationMap = annotationCache.get(clazz);
        if (annotationMap != null && annotationMap.containsKey(annotation)) {
            return (A) annotationMap.get(annotation);
        }

        A ret = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            ret = clazz.getAnnotation(annotation);
            if (ret != null) {
                if (annotationMap == null) {
                    annotationMap = new HashMap<>();
                    annotationCache.put(clazz, annotationMap);
                }
                annotationMap.put(annotation, ret);
                break;
            }
        }

        return ret;
    }

    /**
     * 判断类是否包含指定注解
     *
     * @param clazz      类型
     * @param annotation 注解类型
     * @return true-包含指定注解；false-不包含指定注解
     */
    public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return getAnnotation(clazz, annotation) != null;
    }

    /**
     * 获取类的运行时类型
     *
     * @param object 实例对象
     * @return 运行时类型
     */
    public static Class<?> getClass(Object object) {
        Class<?> clazz = classCache.get(object);
        if (clazz == null) {
            clazz = object.getClass();
            classCache.put(object, clazz);
        }
        return clazz;
    }

    /**
     * 处理对象的字段上指定注解的逻辑，并根据提供的业务逻辑执行器执行相应操作。
     *
     * @param object            对象实例
     * @param annotationClass   注解类型
     * @param executor          业务逻辑执行器
     * @param <T>               泛型参数
     */
    public static <T> void processAnnotations(Object object, Class<? extends Annotation> annotationClass, LogicExecutor<T> executor) {
        List<Field> fields = getFields(object);
        for (Field field : fields) {
            //TODO 作者:黄嘉浩 可以进行优化 使用本地缓存
            Annotation annotation = field.getDeclaredAnnotation(annotationClass);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    Object value = executor.execute(field, annotation);
                    field.set(object, value);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
