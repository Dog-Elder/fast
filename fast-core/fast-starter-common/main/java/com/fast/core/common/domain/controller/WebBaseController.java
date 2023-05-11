package com.fast.core.common.domain.controller;

import com.fast.core.common.domain.domain.R;
import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @program: xxxxx
 * @description: web层通用数据处理
 * @author: @Dog_Elder
 * @create: 2021-04-01 14:43
 **/
//@Log4j2
public class WebBaseController {

    private static List<Object> defaultList = Collections.emptyList();

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        if (CUtil.isEmpty(list)) {
            list = defaultList;
        }
        rspData.setRows(list);
        PageInfo pageInfo = new PageInfo(list);
        rspData.setPages(pageInfo.getPages());
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 响应返回结果(针对乐观锁修改)
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R toVersionAjax(boolean result) {
        return result ? success() : R.errorVersion();
    }

    /**
     * 返回成功
     */
    public R success() {
        return R.success();
    }

    /**
     * 返回失败消息
     */
    public R error() {
        return R.error();
    }

    /**
     * 返回成功消息
     */
    public R success(String message) {
        return R.success(message);
    }

    /**
     * 返回失败消息
     */
    public R error(String message) {
        return R.error(message);
    }

    /**
     * 返回错误码消息
     */
    public R error(R.Type type, String message) {
        return new R(type, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return SUtil.format("redirect:{}", url);
    }
}
