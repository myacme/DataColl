package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollDataDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CollDataDictType)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-13 10:08:07
 */
@Mapper
public interface CollDataDictTypeDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollDataDictType queryById(@Param("id") String id);

	/**
	 * 查询数据
	 *
	 * @param name  字典名称
	 * @param state 状态
	 * @return 对象列表
	 */
	List<CollDataDictType> queryAllByLimit(@Param("name") String name, @Param("state") String state);

	/**
	 * 新增数据
	 *
	 * @param collDataDictType 实例对象
	 * @return 影响行数
	 */
	int insert(CollDataDictType collDataDictType);

	/**
	 * 修改数据
	 *
	 * @param collDataDictType 实例对象
	 * @return 影响行数
	 */
	int update(CollDataDictType collDataDictType);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);
}