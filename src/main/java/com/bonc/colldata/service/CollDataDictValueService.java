package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollDataDictValue;
import com.bonc.colldata.entity.ZgGn;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * (CollDataDictValue)表服务接口
 *
 * @author ljx
 * @since 2021-07-13 10:17:35
 */
public interface CollDataDictValueService {

//	/**
//	 * 通过ID查询单条数据
//	 *
//	 * @param codeId 主键
//	 * @return 实例对象
//	 */
//	CollDataDictValue queryById(String codeId);
	/**
	 * 通过ID查询单条数据
	 *
	 * @param codeId 主键
	 * @return 实例对象
	 */
	ZgGn queryById(String codeId);

//	/**
//	 * 查询多条数据
//	 *
//	 * @param id 字典id
//	 * @return 对象列表
//	 */
//	PageInfo<CollDataDictValue> queryAllByLimit(String id, Pageable pageable);
	/**
	 * 查询多条数据
	 *
	 * @param id 字典id
	 * @return 对象列表
	 */
	List<ZgGn> queryAllByLimit(String id);

	/**
	 * 新增数据
	 *
	 * @param collDataDictValue 实例对象
	 * @return 实例对象
	 */
	int insert(CollDataDictValue collDataDictValue);

	/**
	 * 修改数据
	 *
	 * @param collDataDictValue 实例对象
	 * @return 实例对象
	 */
	int update(CollDataDictValue collDataDictValue);

	/**
	 * 修改数据
	 *
	 * @param ids
	 * @param state
	 * @return 实例对象
	 */
	int updateState(List<Map<String, Object>> ids, String state);

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
	 * @param id   字典id
	 */
	int batchImport(MultipartFile file, String id);
}