package com.fast.core.common.domain.page;

import lombok.Data;

/**
 * @Program: fast
 * @Description:
 * @Author: 黄嘉浩
 * @Create: 2023-06-12 23:06
 **/
@Data
public class Query {
    /** 当前记录起始索引 */
    private Integer pageNum;
    /** 每页显示记录数 */
    private Integer pageSize;
    /** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;

}
