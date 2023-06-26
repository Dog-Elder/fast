package com.fast.core.common.domain.vo;

import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class AttachVO<T> implements Serializable {
    /** 附件CODE */
    @Display("附件CODE")
    private String attachCode;

    private List<T> attach;
}
