package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollTableConfigDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CollTableConfigDataSource)表数据库访问层
 *
 * @author ljx
 * @since 2021-08-01 10:02:44
 */
@Mapper
public interface CollTableConfigDataSourceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param dataSourceCode 主键
     * @return 实例对象
     */
    CollTableConfigDataSource queryById(String dataSourceCode);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CollTableConfigDataSource> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param collTableConfigDataSource 实例对象
     * @return 对象列表
     */
    List<CollTableConfigDataSource> queryAll(CollTableConfigDataSource collTableConfigDataSource);

    /**
     * 新增数据
     *
     * @param collTableConfigDataSource 实例对象
     * @return 影响行数
     */
    int insert(CollTableConfigDataSource collTableConfigDataSource);
    /**
     * 新增数据
     *
     * @param list 实例对象列表
     * @return 影响行数
     */
    int insertList(@Param("list") List<CollTableConfigDataSource> list);

    /**
     * 修改数据
     *
     * @param collTableConfigDataSource 实例对象
     * @return 影响行数
     */
    int update(CollTableConfigDataSource collTableConfigDataSource);

    /**
     * 通过主键删除数据
     *
     * @param list 主键
     * @return 影响行数
     */
    int deleteById(@Param("list") List<Map<String, Object>> list);

}