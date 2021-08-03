package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.entity.CollTableConfigDataSource;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * (CollBusinessTableConfig)表服务接口
 *
 * @author ljx
 * @since 2021-07-14 10:15:24
 */
public interface CollBusinessTableConfigService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollBusinessTableConfig queryById(String id);

	/**
	 * 查询多条数据
	 *
	 * @param id       表清单id
	 * @param pageable 分页参数
	 * @return 对象列表
	 */
	PageInfo<CollBusinessTableConfig> queryAllByLimit(String id, Pageable pageable);

	/**
	 * 新增数据
	 *
	 * @param collBusinessTableConfig 实例对象
	 * @return 实例对象
	 */
	int insert(CollBusinessTableConfig collBusinessTableConfig);

	/**
	 * 修改数据
	 *
	 * @param collBusinessTableConfig 实例对象
	 * @return 实例对象
	 */
	int update(CollBusinessTableConfig collBusinessTableConfig);

	/**
	 * 通过主键删除数据
	 *
	 * @param ids 主键
	 * @return 是否成功
	 */
	int deleteById(List<Map<String, Object>> ids);

	/**
	 * 模板下载
	 *
	 * @param response
	 */
	void templateDownload(HttpServletResponse response);

	/**
	 * 批量导入
	 *
	 * @param file excle
	 * @param id   表id
	 */
	int batchImport(MultipartFile file, String id);

	/**
	 * 数据绑定
	 *
	 * @param tableName 表名称
	 */
	List<Map<String, Object>> queryDataSourceField(String tableName);

	/**
	 * 数据绑定
	 *
	 * @param list 对象列表
	 */
	int dataBinding(List<CollTableConfigDataSource> list);
}