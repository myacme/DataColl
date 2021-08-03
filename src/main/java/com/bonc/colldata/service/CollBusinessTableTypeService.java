package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollBusinessTableType;

import java.util.List;

/**
 * (CollBusinessTableType)表服务接口
 *
 * @author ljx
 * @since 2021-07-13 16:57:12
 */
public interface CollBusinessTableTypeService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollBusinessTableType queryById(String id);

	/**
	 * 查询多条数据
	 *
	 * @param typeCode 业务类型编码
	 * @param name     表清单名称
	 * @param code     表清单编码
	 * @return 对象列表
	 */
	List<CollBusinessTableType> queryAllByLimit(String typeCode, String name, String code);

	/**
	 * 新增数据
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 实例对象
	 */
	int insert(CollBusinessTableType collBusinessTableType);

	/**
	 * 修改数据
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 实例对象
	 */
	int update(CollBusinessTableType collBusinessTableType);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	int deleteById(String id);
}