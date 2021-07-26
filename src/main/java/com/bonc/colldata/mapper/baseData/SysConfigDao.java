package com.bonc.colldata.mapper.baseData;

import com.bonc.colldata.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysConfig)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-26 17:04:44
 */
@Mapper
public interface SysConfigDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param code 主键
	 * @return 实例对象
	 */
	SysConfig queryById(String code);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<SysConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param sysConfig 实例对象
	 * @return 对象列表
	 */
	List<SysConfig> queryAll(SysConfig sysConfig);

	/**
	 * 新增数据
	 *
	 * @param sysConfig 实例对象
	 * @return 影响行数
	 */
	int insert(SysConfig sysConfig);

	/**
	 * 修改数据
	 *
	 * @param sysConfig 实例对象
	 * @return 影响行数
	 */
	int update(SysConfig sysConfig);

	/**
	 * 通过主键删除数据
	 *
	 * @param code 主键
	 * @return 影响行数
	 */
	int deleteById(String code);
}