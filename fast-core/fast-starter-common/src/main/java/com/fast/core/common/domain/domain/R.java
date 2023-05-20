package com.fast.core.common.domain.domain;


import java.io.Serializable;

/**
 * 操作消息提醒
 *
 * @param <T> 数据类型
 */
public class R<T extends Serializable> {
    /** 状态码 */
    private int code;
    /** 返回内容 */
    private String msg;
    /** 数据对象 */
    private T data;

    /**
     * 状态类型
     */
    public enum Type {
        /** 成功 */
        SUCCESS(200),
        /** 错误 */
        ERROR(500);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 初始化一个新创建的 R 对象，使其表示一个空消息。
     */
    public R() {
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     */
    public R(Type type, String msg) {
        this.code = type.value();
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public R(Type type, String msg, T data) {
        this.code = type.value();
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T extends Serializable> R<T> success() {
        return R.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T extends Serializable> R<T> success(T data) {
        return R.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T extends Serializable> R<T> success(String msg) {
        return R.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T extends Serializable> R<T> success(String msg, T data) {
        return new R<>(Type.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static <T extends Serializable> R<T> error() {
        return R.error("操作失败");
    }

    /**
     * 返回错误消息:版本不一致
     *
     * @return 错误消息
     */
    public static <T extends Serializable> R<T> errorVersion() {
        return R.error("操作失败,版本不一致!");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static <T extends Serializable> R<T> error(String msg) {
        return R.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static <T extends Serializable> R<T> error(String msg, T data) {
        return new R<>(Type.ERROR, msg, data);
    }


    public int getCode() {
        return code;
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
