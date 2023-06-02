package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.manage.dao.SysRoleMapper;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.manage.entity.SysRole;
import com.fast.manage.entity.SysUserRole;
import com.fast.manage.service.ISysRoleService;
import com.fast.manage.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色Service业务层处理
 *
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    private final ISysUserRoleService userRoleService;

    /**
     * 查询角色
     *
     * @param id 角色ID
     * @return 角色
     */
    @Override

    public SysRole selectById(Long id) {
        return baseMapper.selectSysRoleById(id);
    }

    /**
     * 查询角色列表
     *
     * @param sysRole 角色
     * @return 角色
     */
    @Override
    public List<SysRole> list(SysRole sysRole) {
        return baseMapper.selectSysRoleList(sysRole);
    }


    /**
     * 真删除角色对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids) {
        return baseMapper.deleteSysRoleByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除角色信息
     *
     * @param id 角色ID
     * @return 结果
     */
    @Override
    public int deleteSysRoleById(Long id) {
        return baseMapper.deleteSysRoleById(id);
    }

    /**
     * 角色逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }


    @Override
    public Set<SysRole> qryRolesByUser(Long id, Integer roleType) {
        if (Util.isNull(id)) {
            return new HashSet<>();
        }
        Set<Long> roleIds = userRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, id)
                        .eq(roleType != null, SysUserRole::getType, roleType))
                .stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        return new HashSet<>(list(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, roleIds)));
    }

    @Override
    public Set<Long> qryRoleIdsByUser(Long id, Integer roleType) {
        return qryRolesByUser(id, roleType).stream().map(SysRole::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<String> qryRoleKeysByUser(Long id, Integer roleType) {
        return qryRolesByUser(id, roleType).stream().map(SysRole::getKey).collect(Collectors.toSet());
    }

}
