package com.fast.common.dao;

import com.fast.common.entity.sys.SysSetValue;
import com.fast.core.mybatis.dao.BaseDao;

import java.util.List;

/**
 * 值集列Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2022-03-24
 */
public interface SysSetValueDao extends BaseDao<SysSetValue>
{
    /**
     * 查询值集列
     * 
     * @param id 值集列ID
     * @return 值集列
     */
    public SysSetValue selectSysSetValueById(Long id);

    /**
     * 查询值集列列表
     * 
     * @param sysSetValue 值集列
     * @return 值集列集合
     */
    public List<SysSetValue> selectSysSetValueList(SysSetValue sysSetValue);

    /**
     * 新增值集列
     * 
     * @param sysSetValue 值集列
     * @return 结果
     */
    public int insertSysSetValue(SysSetValue sysSetValue);

    /**
     * 修改值集列
     * 
     * @param sysSetValue 值集列
     * @return 结果
     */
    public int updateSysSetValue(SysSetValue sysSetValue);

    /**
     * 删除值集列
     * 
     * @param id 值集列ID
     * @return 结果
     */
    public int deleteSysSetValueById(Long id);

    /**
     * 批量删除值集列
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSetValueByIds(String[] ids);
}
