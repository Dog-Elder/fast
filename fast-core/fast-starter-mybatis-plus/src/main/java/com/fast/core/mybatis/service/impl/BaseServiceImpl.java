package com.fast.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.core.common.annotation.encode.Code;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.RUtil;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 所有Service都要继承
 *
 * @author 黄嘉浩
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize  ignore
     * @return ignore
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        for (T entity : entityList) {
            RUtil.processAnnotations(entity, Code.class, (field, annotation) -> {
                Code code = (Code) annotation;
                String value = code.value();
                // 执行自定义业务逻辑，并返回对应的数据
                // 例如，根据注解中的值查询数据库，然后返回结果填充到属性中
                return 11;
            });
        }
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }


}