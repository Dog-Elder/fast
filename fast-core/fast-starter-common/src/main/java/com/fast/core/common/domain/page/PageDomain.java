package com.fast.core.common.domain.page;


import com.fast.core.common.util.SUtil;

/**
 * 分页数据
 * 
 * @author 黄嘉浩
 */
public class PageDomain
{
    /** 当前记录起始索引 */
    private Integer pageNum;
    /** 每页显示记录数 */
    private Integer pageSize;
    /** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;

    // 数据库层面用法
    public String getOrderBy()
    {
        if (SUtil.isEmpty(orderByColumn))
        {
            return "";
        }
        return SUtil.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    // list数据处理
    public String getOrderBy2()
    {
        if (SUtil.isEmpty(orderByColumn))
        {
            return "";
        }
        return orderByColumn;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn()
    {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn)
    {
        this.orderByColumn = orderByColumn;
    }

    public String orderByColumngetIsAsc()
    {
        return isAsc;
    }

    public void setIsAsc(String isAsc)
    {
        this.isAsc = isAsc;
    }
}
