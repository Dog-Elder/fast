package com.fast.core.common.domain.domain;//package com.fast.core.common.domain.domain;
//
//import com.fast.core.common.validate.annotation.Display;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.experimental.Accessors;
//
//@Data
//@ToString(callSuper = true)
//@Accessors(chain = true)
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonInclude(value=JsonInclude.Include.NON_NULL)
//public class Attach extends BaseEntity{
//
//
//    /** id */
//    @Display("id")
//    @TableField("`id`")
//    private Long id;
//
//    /** 附件CODE */
//    @Display("附件CODE")
//    @TableField("`attach_code`")
//    private String attachCode;
//
//    /** 附件唯一标识 */
//    @Display("附件唯一标识")
//    @TableField("`attach_uuid`")
//    private String attachUuid;
//
//    /** 附件名 */
//    @Display("附件名")
//    @TableField("`attach_name`")
//    private String attachName;
//
//    /** 附件别名(持久化名) */
//    @Display("附件别名(持久化名)")
//    @TableField("`attach_alias`")
//    private String attachAlias;
//
//    /** 附件类型 */
//    @Display("附件类型")
//    @TableField("`attach_suffix`")
//    private String attachSuffix;
//
//    /** 删除标志（1:删除） */
//    @Display("删除标志（1:删除）")
//    @TableField("`del_flag`")
//    private String delFlag;
//
//    /** 附件名URL */
//    @Display("附件名URL")
//    @TableField("`attach_url`")
//    private String attachUrl;
//
//    /** 附件存放地址 */
//    @Display("附件存放地址")
//    @TableField("`attach_address`")
//    private String attachAddress;
//
//
//    /** 附件大小 */
//    @Display("附件大小")
//    @TableField("`attach_size`")
//    private Long attachSize;
//}
