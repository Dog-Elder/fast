package com.fast.core.boot.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.exception.BaseException;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.Util;
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

import javax.security.auth.login.AccountExpiredException;
import java.util.List;

/**
 * 全局异常处理器
 *
 * @author @Dog_Elder
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public R baseException(BaseException e) {
        runLog(e);
        return R.error(e.getMessage());
    }

    /**
     * 文件上传异常
     */
    @ExceptionHandler(MultipartException.class)
    public R handleBusinessException(MaxUploadSizeExceededException e) {
        runLog(e);
        return R.error("上传文件失败");
    }

    /**
     * 认证异常
     **/
    @ExceptionHandler(NotLoginException.class)
    public R handlerException(NotLoginException e) {
        runLog(e);
        String type = e.getType();
        switch (type) {
            case NotLoginException.NOT_TOKEN:
            case NotLoginException.INVALID_TOKEN:
            case NotLoginException.TOKEN_TIMEOUT:
                return R.error(R.Type.UNAUTHORIZED);
            case NotLoginException.BE_REPLACED:
                return R.error(R.Type.USER_DISCONNECTED);
            case NotLoginException.KICK_OUT:
                return R.error(R.Type.USER_COMPULSION_LINE);
        }
        return R.error(R.Type.UNAUTHORIZED);
    }

    /**
     * 未授权异常
     **/
    @ExceptionHandler(NotPermissionException.class)
    public R handlerException(NotPermissionException e) {
        runLog(e);
        return R.error(R.Type.FORBIDDEN);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public R businessException(ServiceException e) {
        runLog(e);
        if (Util.isNotNull(e.getCode())) {
            return R.error(e.getMessage());
        }
        return R.error(e.getMessage());
    }

    /**
     * 账户过期
     **/
    @ExceptionHandler(AccountExpiredException.class)
    public R handleAccountExpiredException(AccountExpiredException e) {
        runLog(e);
        return R.error(e.getMessage());
    }


    /**
     * 查询超时
     **/
    @ExceptionHandler(QueryTimeoutException.class)
    public R queryTimeoutException(QueryTimeoutException e) {
        runLog(e);
        return R.error();
    }

    /**
     * 查询超时
     **/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R httpMessageNotReadableException(HttpMessageNotReadableException e) {
        runLog(e);
        return R.error(R.Type.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        runLog(e);
        return R.error();
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public R validatedBindException(BindException bindException) {
        runLog(bindException);
        Object target = bindException.getTarget();
        FieldError fieldError = bindException.getFieldError();
        return WebUtil.dealError(target, fieldError);
    }

    /**
     * 入参校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validExceptionHandler(MethodArgumentNotValidException mde) {
        runLog(mde);
        Object target = mde.getBindingResult().getTarget();
        List<ObjectError> errors = mde.getBindingResult().getAllErrors();
        FieldError fieldError = errors.isEmpty() ? null : (FieldError) errors.get(0);
        return WebUtil.dealError(target, fieldError);
    }

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
