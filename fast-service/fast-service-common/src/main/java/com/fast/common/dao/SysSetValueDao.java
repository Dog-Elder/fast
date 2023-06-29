package com.fast.common.dao;

import com.fast.common.entity.sys.SysSetValue;
import com.fast.core.mybatis.dao.BaseDao;

import java.util.List;

/**
 * 值集值Mapper接口
 * 
 * @author 黄嘉浩
 * @date 2022-03-24
 */
public interface SysSetValueDao extends BaseDao<SysSetValue>
{
    /**
     * 查询值集值
     * 
     * @param id 值集值ID
     * @return 值集值
     */
    public SysSetValue selectSysSetValueById(Long id);

    /**
     * 查询值集值列表
     * 
     * @param sysSetValue 值集值
     * @return 值集值集合
     */
    public List<SysSetValue> selectSysSetValueList(SysSetValue sysSetValue);

    /**
     * 新增值集值
     * 
     * @param sysSetValue 值集值
     * @return 结果
     */
    public int insertSysSetValue(SysSetValue sysSetValue);

    /**
     * 修改值集值
     * 
     * @param sysSetValue 值集值
     * @return 结果
     */
    public int updateSysSetValue(SysSetValue sysSetValue);

    /**
     * 删除值集值
     * 
     * @param id 值集值ID
     * @return 结果
     */
    public int deleteSysSetValueById(Long id);

    /**
     * 批量删除值集值
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSetValueByIds(String[] ids);
}
