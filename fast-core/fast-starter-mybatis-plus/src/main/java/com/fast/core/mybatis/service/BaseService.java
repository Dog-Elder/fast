package com.fast.core.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fast.core.mybatis.model.BaseVersionEntity;

/**
 * 基础服务接口，所有Service接口都要继承
 *
 * @author 黄嘉浩
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 按 ID 更新，无需版本
     * 应用内调用 , 方法实现逻辑
     * 1. Entity 对象必须继承 {@link com.fast.core.mybatis.model.BaseVersionEntity}
     * 2. 根据Id查询数据 赋值版本号 {@link com.baomidou.mybatisplus.extension.service.IService#getById(java.io.Serializable)}
     * 3. 然后调用方法 {@link com.baomidou.mybatisplus.extension.service.IService#updateById(java.lang.Object)}
     *
     * @param entity 实体对象
     **/
    default boolean updateByIdWithoutVersion(T entity) {
        if (entity instanceof BaseVersionEntity versionEntity) {
            T oldEntity = getById(versionEntity.getId());
            if (oldEntity != null) {
                versionEntity.setVersion(((BaseVersionEntity) oldEntity).getVersion());
                return SqlHelper.retBool(getBaseMapper().updateById(entity));
            }
        }
        throw new IllegalArgumentException("Entity must extends BaseVersionEntity");
    }

}