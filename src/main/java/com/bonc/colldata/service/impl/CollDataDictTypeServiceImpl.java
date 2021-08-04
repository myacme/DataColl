package com.bonc.colldata.service.impl;

import com.bonc.colldata.entity.CollDataDictType;
import com.bonc.colldata.entity.ZgGn;
import com.bonc.colldata.mapper.CollDataDictTypeDao;
import com.bonc.colldata.service.CollDataDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CollDataDictType)表服务实现类
 *
 * @author ljx
 * @since 2021-07-13 10:08:07
 */
@Service("collDataDictTypeService")
public class CollDataDictTypeServiceImpl implements CollDataDictTypeService {
	@Resource
	private CollDataDictTypeDao collDataDictTypeDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public CollDataDictType queryById(String id) {
		return this.collDataDictTypeDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param name  字典名称
	 * @param state 状态
	 * @return 对象列表
	 */
	@Override
	public List<ZgGn> queryAllByLimit(String name, String state) {
//		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		return collDataDictTypeDao.queryAllByLimit(name, state);
	}

	/**
	 * 新增数据
	 *
	 * @param collDataDictType 实例对象
	 * @return int
	 */
	@Override
	public int insert(CollDataDictType collDataDictType) {
		return this.collDataDictTypeDao.insert(collDataDictType);
	}

	/**
	 * 修改数据
	 *
	 * @param collDataDictType 实例对象
	 * @return int
	 */
	@Override
	public int update(CollDataDictType collDataDictType) {
		return this.collDataDictTypeDao.update(collDataDictType);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public int deleteById(String id) {
		return this.collDataDictTypeDao.deleteById(id);
	}
}