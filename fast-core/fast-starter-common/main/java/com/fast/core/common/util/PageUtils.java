package com.fast.core.common.util;

import com.fast.core.common.domain.page.PageDomain;
import com.fast.core.common.domain.page.TableSupport;
import com.fast.core.common.util.sql.SqlUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 分页工具类
 **/
@Slf4j
public class PageUtils extends PageHelper {
    /**
     * 设置请求分页数据
     */
    public static PageDomain startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (Util.isNotNull(pageNum) && Util.isNotNull(pageSize)) {
            //最多不能超过1000条查询
            if (pageSize > 1000) {
                pageSize = 1000;
            }
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        return pageDomain;
    }

    /**
     * 获取分页参数
     */
    public static PageDomain getPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (Util.isNotNull(pageNum) && Util.isNotNull(pageSize)) {
            //最多不能超过1000条查询
            if (pageSize > 1000) {
                pageDomain.setPageSize(1000);
            }
        }
        return pageDomain;
    }

    /**
     * 处理转换VO分页
     * 避免出现返回数据不一致
     **/
    public static <S, T> List<T> copy(List<S> sourceList, Class<T> targetClazz) {
        if (sourceList instanceof Page) {
            Page pageList = (Page) sourceList;
            Page<T> page = new Page<>();
            page.setTotal(pageList.getTotal());
            page.setPageNum(pageList.getPageNum());
            page.setPages(pageList.getPages());
            page.setPageSize(pageList.getPageSize());
            page.addAll(CUtil.copy(sourceList, targetClazz));
            return page;
        } else {
            if (Util.isNull(sourceList)) {
                return new ArrayList<>();
            }
            return CUtil.copy(sourceList, targetClazz);
        }
    }

    /**
     * 集合分页(不排序)
     *
     * 简单实现PageHelper分页功能
     * int total=15;//总条数
     * int pageSize= 10;//请求页展示条数
     * int pageNum=1;//请求第几页
     * int firstIndex=(pageNum-1)*pageSize;
     * int totalPage = total % pageSize == 0 ? total/pageSize : total/pageSize + 1;
     * System.out.println("起始条数 = " + firstIndex);
     * System.out.println("页数 = " + totalPage);
     **/
    public static <T> List<T> createPage(List<T> list, Integer pageNum, Integer pageSize) {
        if (Util.isNull(pageNum) || Util.isNull(pageSize)) {
            return list;
        }
        Page<T> page = new Page<>();
        int total = list.size();
        //如果总数为0的情况下直接返回list
        if (total == 0) {
            return list;
        }
        //如果现实条数为0 就返回空集合
        if (pageSize == 0) {
            list.clear();
            return list;
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        //计算总页数
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        page.setPageNum(pageNum);
        page.setPages(totalPage);
        page.setPageSize(pageSize);
        page.setTotal(total);
        //请求 起始条数
        int firstIndex = (pageNum - 1) * pageSize;
        //起始条数<总条数
        if (firstIndex < total) {
            list = list.stream().skip(firstIndex).limit(pageSize).collect(Collectors.toList());
        } else {
            int theLastPage = (totalPage - 1) * pageSize;
            list = list.stream().skip(theLastPage).limit(pageSize).collect(Collectors.toList());
        }
        page.addAll(list);
        return page;
    }

    /**
     * 排序并分页
     * 简单实现PageHelper分页功能
     **/
    public static <T> List<T> createPage(List<T> list) {
        PageDomain pageDomain = getPage();
        String orderBy2 = pageDomain.getOrderBy2();
        String orderDirections = pageDomain.orderByColumngetIsAsc();
        if (SUtil.isNotBlank(orderBy2) && SUtil.isNotBlank(orderDirections)) {
            CUtil.sort(list,orderBy2,orderDirections);
        }
        return createPage(list, pageDomain.getPageNum(), pageDomain.getPageSize());
    }

    /**
     * 排序并分页
     * 简单实现PageHelper分页功能
     **/
    public static <T> List<T> createPage(Stream<T> stream) {
        return createPage(stream.collect(Collectors.toList()));
    }

    /**
     * 分页并排序(自定义)
     * 简单实现PageHelper分页功能
     **/
    public static <T, U extends Comparable<? super U>> List<T> createPage(List<T> list,
                                                                          Function<? super T, ? extends U> mapper,
                                                                          boolean desc) {
        CUtil.sort(list,mapper,desc);
        PageDomain pageDomain = getPage();
        return createPage(list, pageDomain.getPageNum(), pageDomain.getPageSize());
    }
}
