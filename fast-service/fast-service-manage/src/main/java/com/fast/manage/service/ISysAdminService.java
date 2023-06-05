package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.manage.entity.SysUser;

import java.util.List;

/**
 * 后台用户Service接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-18
 */
public interface ISysAdminService extends IService<SysUser>
{
    /**
     * 查询后台用户
     *
     * @param id 后台用户ID
     * @return 后台用户
     */
    public SysUser selectById(Long id);

    /**
     * 查询后台用户列表
     *
     * @param sysUser 后台用户
     * @return 后台用户集合
     */
    public List<SysUser> list(SysUser sysUser);

    /**
     * 新增后台用户
     *
     * @param sysUser 后台用户
     * @return 结果
     */
    public boolean save(SysUser sysUser);

    /**
     * 修改后台用户
     *
     * @param sysUser 后台用户
     * @return 结果
     */
    public boolean update(SysUser sysUser);

    /**
     * 真批量删除后台用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除后台用户信息
     *
     * @param id 后台用户ID
     * @return 结果
     */
    public int deleteSysAdminById(Long id);

    /**
     * 后台用户逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);
}
