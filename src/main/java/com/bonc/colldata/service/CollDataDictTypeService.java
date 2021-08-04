package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollDataDictType;
import com.bonc.colldata.entity.ZgGn;

import java.util.List;

/**
 * (CollDataDictType)表服务接口
 *
 * @author ljx
 * @since 2021-07-13 10:08:07
 */
public interface CollDataDictTypeService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollDataDictType queryById(String id);

	/**
	 * 查询多条数据
	 *
	 * @param name  字典名称
	 * @param state 状态
	 * @return 对象列表
	 */
	List<ZgGn> queryAllByLimit(String name, String state);

	/**
	 * 新增数据
	 *
	 * @param collDataDictType 实例对象
	 * @return int
	 */
	int insert(CollDataDictType collDataDictType);

	/**
	 * 修改数据
	 *
	 * @param collDataDictType 实例对象
	 * @return int
	 */
	int update(CollDataDictType collDataDictType);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	int deleteById(String id);
}