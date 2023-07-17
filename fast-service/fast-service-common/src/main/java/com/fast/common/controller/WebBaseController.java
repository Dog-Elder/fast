package com.fast.common.controller;

import com.fast.core.common.domain.page.TableDataInfo;
import com.fast.core.common.util.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @program: xxxxx
 * @description: web层通用数据处理
 * @author: 黄嘉浩
 * @create: 2021-04-01 14:43
 **/

public class WebBaseController {


    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //  Date 类型转换
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
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected <T>TableDataInfo<T> getDataTable(List<T> list) {
        TableDataInfo rspData = new TableDataInfo();
        if (CUtil.isEmpty(list)) {
            list = new ArrayList<T>();
        }
        rspData.setRows(list);
        PageInfo pageInfo = new PageInfo(list);
        rspData.setPages(pageInfo.getPages());
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }
}
