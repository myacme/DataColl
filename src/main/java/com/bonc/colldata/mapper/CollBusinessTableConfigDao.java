package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollBusinessTableConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CollBusinessTableConfig)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-14 10:15:24
 */
@Mapper
public interface CollBusinessTableConfigDao {

	/**
	 * 通过表id查询所有的字段
	 * @param tableCode
	 * @return
	 */
	List<CollBusinessTableConfig> getConfigList(@Param("tableCode") String tableCode);
	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollBusinessTableConfig queryById(String id);

	/**
	 * 查询指定行数据
	 *
	 * @param id 表清单id
	 * @return 对象列表
	 */
	List<CollBusinessTableConfig> queryAllByLimit(@Param("id") String id);


	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param collBusinessTableConfig 实例对象
	 * @return 对象列表
	 */
	List<CollBusinessTableConfig> queryAll(CollBusinessTableConfig collBusinessTableConfig);

	/**
	 * 新增数据
	 *
	 * @param collBusinessTableConfig 实例对象
	 * @return 影响行数
	 */
	int insert(CollBusinessTableConfig collBusinessTableConfig);

	/**
	 * 批量新增数据
	 *
	 * @param list 实例对象
	 * @return 影响行数
	 */
	int insertList(@Param("list") List<CollBusinessTableConfig> list);

	/**
	 * 修改数据
	 *
	 * @param collBusinessTableConfig 实例对象
	 * @return 影响行数
	 */
	int update(CollBusinessTableConfig collBusinessTableConfig);

	/**
	 * 通过主键删除数据
	 *
	 * @param list 主键
	 * @return 影响行数
	 */
	int deleteById(@Param("list") List<Map<String, Object>> list);
}