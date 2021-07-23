package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollReceiveTask;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * (CollReceiveTask)表服务接口
 *
 * @author ljx
 * @since 2021-07-12 16:17:30
 */
public interface CollReceiveTaskService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollReceiveTask queryById(String id);

	/**
	 * 查询多条数据
	 *
	 * @param pageable 分页参数
	 * @return 对象列表
	 */
	PageInfo<Map<String, Object>> queryAllByLimit(Pageable pageable);

	/**
	 * 新增数据
	 *
	 * @param  实例对象
	 * @return int
	 */
	int insert(MultipartFile multipartFile);

	/**
	 * 修改数据
	 *
	 * @param collReceiveTask 实例对象
	 * @return int
	 */
	int update(CollReceiveTask collReceiveTask);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(String id);
}