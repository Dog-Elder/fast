package com.fast.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.manage.entity.SysRole;

import java.util.List;

/**
 * 角色Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface SysRoleMapper extends BaseMapper<SysRole>
{
    /**
     * 查询角色
     * 
     * @param id 角色ID
     * @return 角色
     */
    public SysRole selectSysRoleById(String id);

    /**
     * 查询角色列表
     * 
     * @param sysRole 角色
     * @return 角色集合
     */
    public List<SysRole> selectSysRoleList(SysRole sysRole);

    /**
     * 新增角色
     * 
     * @param sysRole 角色
     * @return 结果
     */
    public int insertSysRole(SysRole sysRole);

    /**
     * 修改角色
     * 
     * @param sysRole 角色
     * @return 结果
     */
    public int updateSysRole(SysRole sysRole);

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return 结果
     */
    public int deleteSysRoleById(String id);

    /**
     * 批量删除角色
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRoleByIds(String[] ids);
}
