package com.fast.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.service.ISysSetValueService;
import com.fast.core.common.annotation.lov.Lov;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.RUtil;
import com.fast.core.common.util.SUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 值集切面，用于解析值集注解并进行翻译
 **/
@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class AutoLovAspect {

    // 用于缓存类和字段信息，避免重复反射获取
    private static ConcurrentHashMap<Class<?>, List<Field>> classFieldMap = new ConcurrentHashMap<>();

    private final ISysSetValueService sysSetValueService;

    // 定义切点，匹配带有 @AutoLov 注解的方法
    @Pointcut("@annotation(com.fast.core.common.annotation.lov.AutoLov)")
    public void execute() {
    }

    @Around("execute()")
    public Object lovManage(ProceedingJoinPoint pjp) throws Throwable {
        // 执行原方法，并获取返回结果
        Object proceed = pjp.proceed();
        // 解析翻译值集文本
        parseLovText(proceed);
        return proceed;
    }

    /**
     * 解析值集文本
     *
     * @param proceed 方法执行结果
     */
    private void parseLovText(Object proceed) {

        if (proceed instanceof R) {
            R r = (R) proceed;
            if (r.isSuccess()) {
                Object data = r.getData();
                // 分页数据
                if (data instanceof TableDataInfo) {
                    List<?> rows = ((TableDataInfo) data).getRows();
                    convertList(rows);
                }
                // 常规数据
                else if (data instanceof List) {
                    List<?> list = (List<?>) data;
                    convertList(list);
                } else if (data != null) {
                    convertList(Collections.singletonList(data));
                }
            }
        }
    }

    /**
     * 对列表数据进行值集翻译
     *
     * @param list 列表数据
     */
    private void convertList(List<?> list) {
        if (CUtil.isEmpty(list)) {
            return;
        }
        Class<?> runClass = list.get(0).getClass();
        List<Field> fields = classFieldMap.computeIfAbsent(runClass, key -> RUtil.getAllFields(runClass));
        list.forEach(ele -> convertLov(ele, fields));
    }

    /**
     * 对单个对象进行值集翻译
     *
     * @param object 对象
     * @param fields 字段列表
     */
    private void convertLov(Object object, List<Field> fields) {
        if (CUtil.isEmpty(fields)) {
            return;
        }
        Class<?> aClass = object.getClass();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (Objects.isNull(field.get(object))) {
                    continue;
                }
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                Lov annotation = field.getAnnotation(Lov.class);
                if (annotation == null) {
                    continue;
                }
                //当前字段值
                String currentFieldValue = String.valueOf(jsonObject.get(field.getName()));
                if (SUtil.isBlank(currentFieldValue)) {
                    continue;
                }
                //值集code
                String setCode = annotation.setCode();
                if (SUtil.isBlank(setCode)) {
                    continue;
                }
                //获取翻译值
                String valueMeaning = getValueMeaning(setCode, currentFieldValue);
                putLovMap(aClass, object, field, valueMeaning);
                //获取翻译值
                if (SUtil.isEmpty(valueMeaning)) {
                    continue;
                }
                String setMethodName;
                if (SUtil.isBlank(annotation.decipherField())) {
                    continue;
                }
                //存放在自定义的属性中
                setMethodName = "set" + annotation.decipherField().substring(0, 1).toUpperCase() + annotation.decipherField().substring(1);
                Method method = aClass.getMethod(setMethodName, String.class);
                method.invoke(object, valueMeaning);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("尝试放入lovMap失败", e);
            } catch (NoSuchMethodException e) {
                log.error("尝试放入lovMap失败!类:{}中putLovMap方法不存在!",aClass.getName());
            }
        }
    }

    /**
     * 存放在map中
     **/
    private void putLovMap(Class<?> aClass, Object object, Field field, String valueMeaning) {
        try {
            //尝试放入lovMap
            Method putLovMapMethod = aClass.getMethod("putLovMap", String.class, Object.class);
            putLovMapMethod.invoke(object, field.getName(), valueMeaning);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("尝试放入lovMap失败", e);
        } catch (NoSuchMethodException e) {
            log.error("尝试放入lovMap失败!类:{}中putLovMap方法不存在!",aClass.getName());
        }
    }


    /**
     * 根据值集编码和值集值获取值集含义
     *
     * @param setCode           值集编码
     * @param currentFieldValue 值集值
     * @return 值集含义
     */
    private String getValueMeaning(String setCode, String currentFieldValue) {
        return sysSetValueService.qryValues(setCode, currentFieldValue);
    }
}
