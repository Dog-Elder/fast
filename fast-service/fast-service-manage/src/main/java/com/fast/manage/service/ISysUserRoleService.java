package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联Service接口
 * 
 * @author 黄嘉浩
 * @date 2021-06-29
 */
public interface ISysUserRoleService extends BaseService<SysUserRole>
{
    /**
     * 查询用户和角色关联
     * 
     * @param userId 用户和角色关联ID
     * @return 用户和角色关联
     */
    public SysUserRole selectById(Long userId);

    /**
     * 查询用户和角色关联列表
     * 
     * @param sysUserRole 用户和角色关联
     * @return 用户和角色关联集合
     */
    public List<SysUserRole> list(SysUserRole sysUserRole);


    /**
     * 修改用户和角色关联
     * 
     * @param sysUserRole 用户和角色关联
     * @return 结果
     */
    public boolean update(SysUserRole sysUserRole);

    /**
     * 真批量删除用户和角色关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除用户和角色关联信息
     * 
     * @param userId 用户和角色关联ID
     * @return 结果
     */
    public int deleteSysUserRoleById(Long userId);

    /**
     * 用户和角色关联逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);
}
