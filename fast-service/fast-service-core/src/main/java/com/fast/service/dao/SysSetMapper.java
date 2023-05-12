package com.fast.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.service.entity.sys.SysSet;

import java.util.List;

/**
 * 值集Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2022-03-24
 */
public interface SysSetMapper extends BaseMapper<SysSet>
{
    /**
     * 查询值集
     * 
     * @param id 值集ID
     * @return 值集
     */
    public SysSet selectSysSetById(Long id);

    /**
     * 查询值集列表
     * 
     * @param sysSet 值集
     * @return 值集集合
     */
    public List<SysSet> selectSysSetList(SysSet sysSet);

    /**
     * 新增值集
     * 
     * @param sysSet 值集
     * @return 结果
     */
    public int insertSysSet(SysSet sysSet);

    /**
     * 修改值集
     * 
     * @param sysSet 值集
     * @return 结果
     */
    public int updateSysSet(SysSet sysSet);

    /**
     * 删除值集
     * 
     * @param id 值集ID
     * @return 结果
     */
    public int deleteSysSetById(Long id);

    /**
     * 批量删除值集
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSetByIds(String[] ids);
}
