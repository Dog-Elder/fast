package com.fast.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.query.SysSetQuery;
import com.fast.common.vo.SysSetVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 值集Service接口
 * 
 * @author @Dog_Elder
 * @date 2022-03-24
 */
public interface ISysSetService extends BaseService<SysSet>
{
    List<SysSetVO> list(SysSetQuery query);
    List<SysSetVO> save(List<SysSetVO> vo);
    boolean update(SysSetVO vo);
    boolean delete(List<String> idList);
    /**
     * 是否启用
     *
     * @param setCode:     值集编码
     **/
    Boolean isEnableBySetCode(String setCode);
}
