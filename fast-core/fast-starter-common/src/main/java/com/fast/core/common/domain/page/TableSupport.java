package com.fast.core.common.domain.page;

import com.fast.core.common.constant.Constants;
import com.fast.core.common.util.ServletUtils;

/**
 * 表格数据处理
 * 
 * @author @Dog_Elder
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static com.fast.core.common.domain.page.PageDomain getPageDomain()
    {
        com.fast.core.common.domain.page.PageDomain pageDomain = new com.fast.core.common.domain.page.PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
