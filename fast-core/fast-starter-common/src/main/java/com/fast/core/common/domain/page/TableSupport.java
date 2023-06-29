package com.fast.core.common.domain.page;

import com.fast.core.common.constant.Constants;
import com.fast.core.common.util.ServletUtils;
import com.fast.core.common.util.Util;

/**
 * 表格数据处理
 *
 * @author 黄嘉浩
 */
public class TableSupport {
    /**
     * 封装分页对象
     */
    public static com.fast.core.common.domain.page.PageDomain getPageDomain() {
        com.fast.core.common.domain.page.PageDomain pageDomain = new com.fast.core.common.domain.page.PageDomain();
        pageDomain.setPageNum(Util.isNull(ServletUtils.getParameterToInt(Constants.PAGE_NUM)) ? 1 : ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(Util.isNull(ServletUtils.getParameterToInt(Constants.PAGE_SIZE)) ? 500 : ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
