package com.bonc.colldata.service.impl;

import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.mapper.CollBusinessTableTypeDao;
import com.bonc.colldata.service.CollBusinessTableTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CollBusinessTableType)表服务实现类
 *
 * @author ljx
 * @since 2021-07-13 16:57:12
 */
@Service("collBusinessTableTypeService")
public class CollBusinessTableTypeServiceImpl implements CollBusinessTableTypeService {
	@Resource
	private CollBusinessTableTypeDao collBusinessTableTypeDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public CollBusinessTableType queryById(String id) {
		return this.collBusinessTableTypeDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param typeCode 业务类型编码
	 * @param name     表清单名称
	 * @param code     表清单编码
	 * @return 对象列表
	 */
	@Override
	public List<CollBusinessTableType> queryAllByLimit(String typeCode, String name, String code) {
		return collBusinessTableTypeDao.queryAllByLimit(typeCode, name, code);
	}

	/**
	 * 新增数据
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 实例对象
	 */
	@Override
	public int insert(CollBusinessTableType collBusinessTableType) {
		return this.collBusinessTableTypeDao.insert(collBusinessTableType);
	}

	/**
	 * 修改数据
	 *
	 * @param collBusinessTableType 实例对象
	 * @return 实例对象
	 */
	@Override
	public int update(CollBusinessTableType collBusinessTableType) {
		return this.collBusinessTableTypeDao.update(collBusinessTableType);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public int deleteById(String id) {
		return this.collBusinessTableTypeDao.deleteById(id);
	}
}