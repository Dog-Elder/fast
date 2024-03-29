package com.fast.core.boot.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountExpiredException;
import java.util.List;

/**
 * 全局异常处理程序
 *
 * @author 黄嘉浩
 * @date 2023/07/28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 文件上传异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(MultipartException.class)
    public R fileUploadException(MaxUploadSizeExceededException e) {
        runLog(e);
        return R.error("上传文件失败");
    }

    /**
     * 未找到处理程序异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R noHandlerFoundException(NoHandlerFoundException e) {
        runLog(e);
        return R.error((R.Type.NOT_FOUND));
    }

    /**
     * 认证异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(NotLoginException.class)
    public R authenticationAnomaly(NotLoginException e) {
        runLog(e);
        String type = e.getType();
        return switch (type) {
            case NotLoginException.BE_REPLACED -> R.error(R.Type.USER_DISCONNECTED);
            case NotLoginException.KICK_OUT -> R.error(R.Type.USER_COMPULSION_LINE);
            default -> R.error(R.Type.UNAUTHORIZED);
        };
    }

    /**
     * 未授权异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(NotPermissionException.class)
    public R unauthorizedException(NotPermissionException e) {
        runLog(e);
        return R.error(R.Type.FORBIDDEN);
    }

    /**
     * 服务异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(ServiceException.class)
    public R serviceException(ServiceException e) {
        runLog(e);
        return R.error(e.getMessage());
    }


    /**
     * 处理账户过期异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(AccountExpiredException.class)
    public R handleAccountExpiredException(AccountExpiredException e) {
        runLog(e);
        return R.error(e.getMessage());
    }


    /**
     * 查询超时异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(QueryTimeoutException.class)
    public R queryTimeoutException(QueryTimeoutException e) {
        runLog(e);
        return R.error();
    }

    /**
     * http消息不可读例外
     * 错误的请求
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R httpMessageNotReadableException(HttpMessageNotReadableException e) {
        runLog(e);
        return R.error(R.Type.BAD_REQUEST);
    }


    /**
     * 系统异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(Exception.class)
    public R systemAbnormality(Exception e) {
        runLog(e);
        return R.error();
    }

    /**
     * 验证绑定异常
     *
     * @param bindException 绑定异常
     * @return {@link R}
     */
    @ExceptionHandler(BindException.class)
    public R validatedBindException(BindException bindException) {
        runLog(bindException);
        Object target = bindException.getTarget();
        FieldError fieldError = bindException.getFieldError();
        return WebUtil.dealError(target, fieldError);
    }

    /**
     * 有效异常处理程序
     * 入参校验异常
     *
     * @param mde 身边
     * @return {@link R}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validExceptionHandler(MethodArgumentNotValidException mde) {
        runLog(mde);
        Object target = mde.getBindingResult().getTarget();
        List<ObjectError> errors = mde.getBindingResult().getAllErrors();
        FieldError fieldError = errors.isEmpty() ? null : (FieldError) errors.get(0);
        return WebUtil.dealError(target, fieldError);
    }

    /**
     * 运行日志
     *
     * @param e e
     */
    public void runLog(Exception e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        String className = stackTraceElement.getClassName();
        String fileName = stackTraceElement.getFileName();
        int lineNumber = stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();
        log.error("类名:{},文件名:{},行数:{},方法名:{}", className, fileName, lineNumber, methodName);
        log.error(e.getMessage(), e);
    }
}
