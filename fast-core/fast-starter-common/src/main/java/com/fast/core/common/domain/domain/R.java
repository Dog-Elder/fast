package com.fast.core.common.domain.domain;


import java.io.Serializable;
import java.util.List;

/**
 * 操作消息提醒
 *
 * @param <T> 数据类型
 */
/**
 * 操作消息提醒
 *
 * @param <T> 数据类型
 */
public class R<T> implements Serializable{
    private int code;
    private boolean success;
    private String msg;
    private T data;

    public enum Type {
        SUCCESS(200, "成功"),
        CREATED(201, "已创建"),
        ACCEPTED(202, "已接受"),
        BAD_REQUEST(400, "错误的请求"),
        UNAUTHORIZED(401, "未经授权"),
        FORBIDDEN(403, "禁止访问"),
        NOT_FOUND(404, "未找到资源"),
        SERVER_ERROR(500, "服务器错误"),
        USER_COMPULSION_LINE(1002, "该账号已已被强行下线"),
        USER_DISCONNECTED(1001, "该账号已在别处登录");

        private final int value;
        private final String description;

        Type(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int value() {
            return this.value;
        }

        public String description() {
            return this.description;
        }
    }

    public R() {
    }

    public R(Type type, String msg, T data) {
        this.code = type.value();
        this.msg = msg;
        this.data = data;
        this.success = (type == Type.SUCCESS);
    }

    public R(Type type, String msg) {
        this.code = type.value();
        this.msg = msg;
        this.success = (type == Type.SUCCESS);
    }

    public R(Type type) {
        this.code = type.value();
        this.msg = type.description();
        this.success = (type == Type.SUCCESS);
    }

    public static <T extends Serializable> R<T> success() {
        return new R<>(Type.SUCCESS);
    }

    public static <T extends Serializable> R<T> success(T data) {
        return new R<>(Type.SUCCESS, "操作成功", data);
    }

    public static <T extends Serializable> R<T> success(String msg, T data) {
        return new R<>(Type.SUCCESS, msg, data);
    }

    public static <T extends Serializable> R<List<T>> success(List<T> data) {
        return new R<>(Type.SUCCESS, "操作成功", data);
    }

    public static <T extends Serializable> R<List<T>> success(String msg, List<T> data) {
        return new R<>(Type.SUCCESS, msg, data);
    }

    public static <T extends Serializable> R<T> error() {
        return new R<>(Type.SERVER_ERROR, Type.SERVER_ERROR.description);
    }
    /**
     * 响应返回结果(针对乐观锁修改)
     *
     * @param result 结果
     * @return 操作结果
     */
    public static <T extends Serializable> R<T> toVersion(boolean result) {
        return result ? success() : R.errorVersion();
    }

    public static <T extends Serializable> R<T> errorVersion() {
        return new R<>(Type.SERVER_ERROR, "操作失败,版本不一致!");
    }

    public static <T extends Serializable> R<T> error(String msg) {
        return new R<>(Type.SERVER_ERROR, msg);
    }

    public static <T extends Serializable> R<T> error(Type type) {
        return new R<>(type);
    }

    public static <T extends Serializable> R<T> error(Type type, String msg) {
        return new R<>(type, msg);
    }

    public static <T extends Serializable> R<T> error(String msg, T data) {
        return new R<>(Type.SERVER_ERROR, msg, data);
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return code==200;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}