package com.fast.core.service;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.core.entity.sys.SysSetValue;
import com.fast.core.req.SysSetValueReq;
import com.fast.core.vo.CustomSetValueVO;
import com.fast.core.vo.SysSetValueVO;

import java.util.List;
import java.util.Map;

/**
 * 值集列Service接口
 *
 * @author @Dog_Elder
 * @date 2022-03-25
 */
public interface ISysSetValueService extends IService<SysSetValue> {
    /**
     * 查询值集列
     *
     * @param id 值集列ID
     * @return 值集列
     */
    public SysSetValue selectById(Long id);

    /**
     * 查询值集列列表
     *
     * @param sysSetValue 值集列
     * @return 值集列集合
     */
    public List<SysSetValueVO> list(SysSetValue sysSetValue);

    /**
     * 自定义列表
     *
     * @param map<String,String> K:返回k  V:值集code
     * @return 值集列集合
     */
    public JSONObject customList(Map<String, String> map);

    /**
     * 添加值集列
     *
     * @param sysSetValue 值集列
     * @return 结果
     */
    public List<SysSetValue> addSave(List<SysSetValue> sysSetValue);

    /**
     * 修改值集列
     *
     * @param sysSetValue 值集列
     * @return 结果
     */
    public boolean update(SysSetValue sysSetValue);

    /**
     * 真批量删除值集列
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除值集列信息
     *
     * @param id 值集列ID
     * @return 结果
     */
    public int deleteSysSetValueById(Long id);

    /**
     * 值集列逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);

    /**
     * 查询值列表
     */
    List<CustomSetValueVO> dataList(SysSetValueReq req);

    /**
     * 查询值列表(从缓存中获取数据)
     */
    List<CustomSetValueVO> qryCacheDataList(SysSetValueReq req);

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