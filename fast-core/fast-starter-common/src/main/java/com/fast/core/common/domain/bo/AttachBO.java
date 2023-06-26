package com.fast.core.common.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class AttachBO {

    /** 附件名 */
    private String attachName;

    /** 附件别名(持久化名) */
    private String attachAlias;

    /** 附件类型 */
    private String attachSuffix;

    /** 附件URL */
    private String attachUrl;

    /** 附件存放地址 */
    private String attachAddress;

    /** 附件大小 */
    private Integer attachSize;
}
