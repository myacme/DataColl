package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollBusinessType;

import java.util.List;

/**
 * (CollBusinessType)表服务接口
 *
 * @author ljx
 * @since 2021-07-13 16:29:17
 */
public interface CollBusinessTypeService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollBusinessType queryById(String id);

	/**
	 * 查询多条数据
	 *
	 * @return 对象列表
	 */
	List<CollBusinessType> queryAll();

	/**
	 * 新增数据
	 *
	 * @param collBusinessType 实例对象
	 * @return 实例对象
	 */
	int insert(CollBusinessType collBusinessType);

	/**
	 * 修改数据
	 *
	 * @param collBusinessType 实例对象
	 * @return 实例对象
	 */
	int update(CollBusinessType collBusinessType);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	int deleteById(String id);
}