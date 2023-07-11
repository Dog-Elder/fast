package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import com.fast.core.common.domain.page.Query;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

    /**
     * 来源ip
     */
    @Display("来源ip")
    @TableField("ip")
    private String ip;

}