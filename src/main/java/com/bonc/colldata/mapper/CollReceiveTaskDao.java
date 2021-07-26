package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollReceiveTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CollReceiveTask)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-12 16:10:54
 */

@Mapper
public interface CollReceiveTaskDao {

	/**
	 * 查询所有未上报的版本
	 *
	 * @return 实例对象
	 */
	List<Map<String, String>> queryVersionOfnew();

	/**
	 * 查询所有历史版本
	 *
	 * @return 实例对象
	 */
	List<Map<String, String>> queryVersionOfHistory();

	/**
	 * 数据上报修改状态
	 *
	 * @param version
	 * @return int
	 */
	int report(@Param("version") String version);

	/**
	 * 根据版本号查询
	 *
	 * @param version
	 * @return int
	 */
	CollReceiveTask getByVersion(@Param("version") String version);

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollReceiveTask queryById(String id);

	/**
	 * 查询指定行数据
	 *
	 * @return 对象列表
	 */
	List<Map<String, Object>> queryAllByLimit();


	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param collReceiveTask 实例对象
	 * @return 对象列表
	 */
	List<CollReceiveTask> queryAll(CollReceiveTask collReceiveTask);

	/**
	 * 新增数据
	 *
	 * @param collReceiveTask 实例对象
	 * @return 影响行数
	 */
	int insert(CollReceiveTask collReceiveTask);

	int addReceiveTaskTable(CollReceiveTask collReceiveTask);
	/**
	 * 修改数据
	 *
	 * @param collReceiveTask 实例对象
	 * @return 影响行数
	 */
	int update(CollReceiveTask collReceiveTask);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);
}