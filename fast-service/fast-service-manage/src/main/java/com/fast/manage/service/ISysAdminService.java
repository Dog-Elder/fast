package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.manage.entity.SysAdmin;

import java.util.List;

/**
 * 后台用户Service接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-18
 */
public interface ISysAdminService extends IService<SysAdmin>
{
    /**
     * 查询后台用户
     *
     * @param id 后台用户ID
     * @return 后台用户
     */
    public SysAdmin selectById(Long id);

    /**
     * 查询后台用户列表
     *
     * @param sysAdmin 后台用户
     * @return 后台用户集合
     */
    public List<SysAdmin> list(SysAdmin sysAdmin);

    /**
     * 新增后台用户
     *
     * @param sysAdmin 后台用户
     * @return 结果
     */
    public boolean save(SysAdmin sysAdmin);

    /**
     * 修改后台用户
     *
     * @param sysAdmin 后台用户
     * @return 结果
     */
    public boolean update(SysAdmin sysAdmin);

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
