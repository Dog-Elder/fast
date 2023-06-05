package com.fast.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.manage.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 后台用户Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-18
 */
@Mapper
public interface SysAdminMapper extends BaseMapper<SysUser>
{
    /**
     * 查询后台用户
     *
     * @param id 后台用户ID
     * @return 后台用户
     */
    public SysUser selectSysAdminById(Long id);

    /**
     * 查询后台用户列表
     *
     * @param sysUser 后台用户
     * @return 后台用户集合
     */
    public List<SysUser> selectSysAdminList(SysUser sysUser);

    /**
     * 新增后台用户
     *
     * @param sysUser 后台用户
     * @return 结果
     */
    public int insertSysAdmin(SysUser sysUser);

    /**
     * 修改后台用户
     *
     * @param sysUser 后台用户
     * @return 结果
     */
    public int updateSysAdmin(SysUser sysUser);

    /**
     * 删除后台用户
     *
     * @param id 后台用户ID
     * @return 结果
     */
    public int deleteSysAdminById(Long id);

    /**
     * 批量删除后台用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAdminByIds(String[] ids);
}
