package com.fast.manage.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.core.common.util.SUtil;
import com.fast.manage.dao.SysAdminMapper;
import com.fast.manage.entity.SysAdmin;
import com.fast.manage.service.ISysAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台用户Service业务层处理
 * 
 * @author @Dog_Elder
 * @date 2021-06-18
 */
@Service
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements ISysAdminService
{
    @Autowired
    private SysAdminMapper sysAdminMapper;

    /**
     * 查询后台用户
     *
     * @param id 后台用户ID
     * @return 后台用户
     */
    @Override
    public SysAdmin selectById(Long id)
    {
        return super.getById(id);
    }

    /**
     * 查询后台用户列表
     *
     * @param sysAdmin 后台用户
     * @return 后台用户
     */
    @Override
    public List<SysAdmin> list(SysAdmin sysAdmin)
    {
        return sysAdminMapper.selectSysAdminList(sysAdmin);
    }

    /**
     * 新增后台用户
     *
     * @param sysAdmin 后台用户
     * @return 结果
     */
    @Transactional
    @Override
    public boolean save(SysAdmin sysAdmin)
    {
        return super.save(sysAdmin);
    }

    /**
     * 修改后台用户
     *
     * @param sysAdmin 后台用户
     * @return 结果
     */
    @Transactional
    @Override
    public boolean update(SysAdmin sysAdmin)
    {
        return super.updateById(sysAdmin);
    }

    /**
     * 真删除后台用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids)
    {
        return sysAdminMapper.deleteSysAdminByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除后台用户信息
     *
     * @param id 后台用户ID
     * @return 结果
     */
    @Override
    public int deleteSysAdminById(Long id)
    {
        return sysAdminMapper.deleteSysAdminById(id);
    }

    /**
     * 后台用户逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }

}
