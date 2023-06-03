package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.core.common.util.SUtil;
import com.fast.manage.dao.SysUserRoleMapper;
import com.fast.manage.entity.SysUserRole;
import com.fast.manage.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户和角色关联Service业务层处理
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService
{
    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 查询用户和角色关联
     * 
     * @param userId 用户和角色关联ID
     * @return 用户和角色关联
     */
    @Override
    public SysUserRole selectById(Long userId)
    {
        return sysUserRoleMapper.selectSysUserRoleById(userId);
    }

    /**
     * 查询用户和角色关联列表
     * 
     * @param sysUserRole 用户和角色关联
     * @return 用户和角色关联
     */
    @Override
    public List<SysUserRole> list(SysUserRole sysUserRole)
    {
        return sysUserRoleMapper.selectSysUserRoleList(sysUserRole);
    }



    /**
     * 修改用户和角色关联
     * 
     * @param sysUserRole 用户和角色关联
     * @return 结果
     */
    @Transactional
    @Override
    public boolean update(SysUserRole sysUserRole)
    {
        return updateById(sysUserRole);
    }

    /**
     * 真删除用户和角色关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids)
    {
        return sysUserRoleMapper.deleteSysUserRoleByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除用户和角色关联信息
     * 
     * @param userId 用户和角色关联ID
     * @return 结果
     */
    @Override
    public int deleteSysUserRoleById(Long userId)
    {
        return sysUserRoleMapper.deleteSysUserRoleById(userId);
    }

    /**
     * 用户和角色关联逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }

}
