package com.fast.core.common.exception;


/**
 * 404异常
 *
 * @author 黄嘉浩
 * @date 2023/07/26
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
