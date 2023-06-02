package com.fast.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.manage.entity.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole>
{
    /**
     * 查询用户和角色关联
     * 
     * @param userId 用户和角色关联ID
     * @return 用户和角色关联
     */
    public SysUserRole selectSysUserRoleById(Long userId);

    /**
     * 查询用户和角色关联列表
     * 
     * @param sysUserRole 用户和角色关联
     * @return 用户和角色关联集合
     */
    public List<SysUserRole> selectSysUserRoleList(SysUserRole sysUserRole);

    /**
     * 新增用户和角色关联
     * 
     * @param sysUserRole 用户和角色关联
     * @return 结果
     */
    public int insertSysUserRole(SysUserRole sysUserRole);

    /**
     * 修改用户和角色关联
     * 
     * @param sysUserRole 用户和角色关联
     * @return 结果
     */
    public int updateSysUserRole(SysUserRole sysUserRole);

    /**
     * 删除用户和角色关联
     * 
     * @param userId 用户和角色关联ID
     * @return 结果
     */
    public int deleteSysUserRoleById(Long userId);

    /**
     * 批量删除用户和角色关联
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserRoleByIds(String[] userIds);
}
