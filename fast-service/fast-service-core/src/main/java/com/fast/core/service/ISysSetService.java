package com.fast.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.core.entity.sys.SysSet;

import java.util.List;

/**
 * 值集Service接口
 * 
 * @author @Dog_Elder
 * @date 2022-03-24
 */
public interface ISysSetService extends IService<SysSet>
{
    /**
     * 查询值集
     * 
     * @param id 值集ID
     * @return 值集
     */
    public SysSet selectById(Long id);

    /**
     * 查询值集列表
     * 
     * @param sysSet 值集
     * @return 值集集合
     */
    public List<SysSet> list(SysSet sysSet);

    /**
     * @Description: 批量添加
     * @Author: Dog_Elder
     * @Date: 2022/3/24 14:31
     * @param sysSets: 值集对象
     * @return: java.util.List<com.xxxxx.domain.sys.SysSet>
     **/
    public List<SysSet> addSave(List<SysSet> sysSets);

    /**
     * 修改值集
     * 
     * @param sysSet 值集
     * @return 结果
     */
    public boolean update(SysSet sysSet);

    /**
     * 真批量删除值集
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除值集信息
     * 
     * @param id 值集ID
     * @return 结果
     */
    public int deleteSysSetById(Long id);

    /**
     * 值集逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);
}
