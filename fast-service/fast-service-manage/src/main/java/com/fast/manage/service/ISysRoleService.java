package com.fast.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.manage.entity.SysRole;

import java.util.List;
import java.util.Set;

/**
 * 角色Service接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
public interface ISysRoleService extends IService<SysRole>
{
    /**
     * 查询角色
     * 
     * @param id 角色ID
     * @return 角色
     */
    public SysRole selectById(String id);

    /**
     * 查询角色列表
     * 
     * @param sysRole 角色
     * @return 角色集合
     */
    public List<SysRole> list(SysRole sysRole);



    /**
     * 真批量删除角色
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByIds(String ids);

    /**
     * 真删除角色信息
     * 
     * @param id 角色ID
     * @return 结果
     */
    public int deleteSysRoleById(String id);

    /**
     * 角色逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public boolean logicRemove(String ids);

    /**
     * @Description: 根据用户id查询角色
     * @Author: Dog_Elder
     * @Date:  17:03
     * @param id : 用户id
     * @return: 角色集合
     **/
    public Set<SysRole> qryRolesByUser(String id, Integer roleType);

    /**
     * @Description: 根据用户id,类型获取角色ids
     * @Author: Dog_Elder
     * @Date:  17:44
     * @param id: 用户id
     * @param roleType: 用户类型
     * @return: RoleIds
     **/
    public Set<String> qryRoleIdsByUser(String id,Integer roleType);

    /**
     * 根据用户id查询该用户下的所有角色表示(key)
     **/
    public Set<String> qryRoleKeysByUser(String id, Integer roleType);

    /**
     * 根据用户id查询权限
     *
     * @param userId:用户id
     * @return: java.util.List<java.lang.String>
     **/
    List<String> qryPermsById(String userId);

    List<String> qryRoleById(String userId);
}
