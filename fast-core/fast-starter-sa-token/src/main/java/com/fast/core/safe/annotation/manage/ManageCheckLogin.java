package com.fast.core.safe.annotation.manage;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.fast.core.safe.util.ManageUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 管理端检查登陆
 * 登录认证(后台)：只有登录之后才能进入该方法
 * <p> 可标注在函数、类上（效果等同于标注在此类的所有方法上）
 *
 * @author 黄嘉浩
 * @date 2024/03/13
 */
@SaCheckLogin(type = ManageUtil.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE})
public @interface ManageCheckLogin {
    
}