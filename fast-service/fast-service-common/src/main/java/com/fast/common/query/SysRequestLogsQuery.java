package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 接口请求日志查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-07-10
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRequestLogsQuery extends Query {

    /**
     * 来源ip
     */
    @Display("来源ip")
    @TableField("ip")
    private String ip;

}