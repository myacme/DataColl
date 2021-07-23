package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollBusinessTableType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CollBusinessTableType)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-13 16:57:12
 */
@Mapper
public interface CollBusinessTableTypeDao {

	/**
	 * 通过版本查询所有的表
	 * @param version
	 * @return
	 */
	List<CollBusinessTableType> getTableList(@Param("version") String version);

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollBusinessTableType queryById(String id);

	/**
	 * 查询指定行数据
	 *
	 * @param typeCode 业务类型编码
	 * @param name     表清单名称
	 * @param code     表清单编码
	 * @return 对象列表
	 */
	List<CollBusinessTableType> queryAllByLimit(@Param("typeCode") String typeCode,
	                                            @Param("name") String name, @Param("code") String code);


	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 对象列表
	 */
	List<CollBusinessTableType> queryAll(CollBusinessTableType collBusinessTableType);

	/**
	 * 新增数据
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 影响行数
	 */
	int insert(CollBusinessTableType collBusinessTableType);

	/**
	 * 修改数据
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 影响行数
	 */
	int update(CollBusinessTableType collBusinessTableType);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);
}