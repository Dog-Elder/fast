package com.fast.common.service;


import cn.hutool.json.JSONObject;
import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.query.SysSetValueQuery;
import com.fast.common.vo.CustomSetValueVO;
import com.fast.common.vo.SysSetValueVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 值集值Service接口
 *
 * @author @Dog_Elder
 * @date 2022-03-25
 */
public interface ISysSetValueService extends BaseService<SysSetValue> {

    List<SysSetValueVO> list(SysSetValueQuery query);

    List<SysSetValueVO> save(List<SysSetValueVO> vo);

    boolean update(SysSetValueVO vo);

    boolean delete(List<String> idList);

    /**
     * 自定义列表
     *
     * @param map<String,String> K:返回k  V:值集code
     * @return 值集值集合
     */
    public JSONObject customList(Map<String, String> map);

    /**
     * 查询值列表
     */
    List<CustomSetValueVO> dataList(SysSetValueQuery req);

    /**
     * 查询值列表(从缓存中获取数据)
     */
    List<CustomSetValueVO> qryCacheDataList(SysSetValueQuery req);

    /**
     * 根据值集编码 和值集值key获取一个 值含义
     *
     * @param setCode:     值集编码
     * @param setValueKey: 值集值key
     * @Date: 2022/8/24 0:22
     * @return: java.lang.String 值含义
     **/
    String qryValue(String setCode, String setValueKey);

}