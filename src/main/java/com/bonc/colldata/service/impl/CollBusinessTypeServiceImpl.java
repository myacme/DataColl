package com.bonc.colldata.service.impl;

import com.bonc.colldata.entity.CollBusinessType;
import com.bonc.colldata.mapper.CollBusinessTypeDao;
import com.bonc.colldata.service.CollBusinessTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CollBusinessType)表服务实现类
 *
 * @author ljx
 * @since 2021-07-13 16:29:17
 */
@Service("collBusinessTypeService")
public class CollBusinessTypeServiceImpl implements CollBusinessTypeService {
	@Resource
	private CollBusinessTypeDao collBusinessTypeDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public CollBusinessType queryById(String id) {
		return this.collBusinessTypeDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @return 对象列表
	 */
	@Override
	public List<CollBusinessType> queryAll() {
		return this.collBusinessTypeDao.queryAll();
	}

	/**
	 * 新增数据
	 *
	 * @param collBusinessType 实例对象
	 * @return 实例对象
	 */
	@Override
	public int insert(CollBusinessType collBusinessType) {
		return this.collBusinessTypeDao.insert(collBusinessType);
	}

	/**
	 * 修改数据
	 *
	 * @param collBusinessType 实例对象
	 * @return 实例对象
	 */
	@Override
	public int update(CollBusinessType collBusinessType) {
		return this.collBusinessTypeDao.update(collBusinessType);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public int deleteById(String id) {
		return this.collBusinessTypeDao.deleteById(id);
	}
}