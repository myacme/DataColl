package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollDataDictValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CollDataDictValue)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-13 10:17:35
 */
@Mapper
public interface CollDataDictValueDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param codeId 主键
	 * @return 实例对象
	 */
	CollDataDictValue queryById(String codeId);

	/**
	 * 查询指定行数据
	 *
	 * @param id 字典id
	 * @return 对象列表
	 */
	List<CollDataDictValue> queryAllByLimit(@Param("id") String id);


	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param collDataDictValue 实例对象
	 * @return 对象列表
	 */
	List<CollDataDictValue> queryAll(CollDataDictValue collDataDictValue);

	/**
	 * 新增数据
	 *
	 * @param collDataDictValue 实例对象
	 * @return 影响行数
	 */
	int insert(CollDataDictValue collDataDictValue);

	/**
	 * 批量新增数据
	 *
	 * @param list 实例对象
	 * @return 影响行数
	 */
	int insertList(@Param("list") List<CollDataDictValue> list);

	/**
	 * 修改数据
	 *
	 * @param collDataDictValue 实例对象
	 * @return 影响行数
	 */
	int update(CollDataDictValue collDataDictValue);

	/**
	 * 修改数据
	 *
	 * @return 影响行数
	 */
	int updateState(@Param("list") List<Map<String, Object>> ids, @Param("state") String state);

	/**
	 * 通过主键删除数据
	 *
	 * @param ids 主键
	 * @return 影响行数
	 */
	int deleteById(@Param("list") List<Map<String, Object>> ids);
}