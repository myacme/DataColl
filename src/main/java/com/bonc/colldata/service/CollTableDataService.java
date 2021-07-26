package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.entity.CollTableData;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CollTableData)表服务接口
 *
 * @author ljx
 * @since 2021-07-14 11:25:09
 */
public interface CollTableDataService {

	/**
	 * 导入zip
	 * @param file
	 * @param version
	 * @param rportType
	 * @return
	 */
	int inputZip(MultipartFile file, String version, String rportType);
	/**
	 * 通过版本查询所有的表
	 *
	 * @param version
	 * @return
	 */
	List<CollBusinessTableType> getTableList(String version);

	/**
	 * 通过导出的表数据
	 *
	 * @param tableCode
	 * @param version
	 * @param isTemplet
	 * @return
	 */
	File reportDataExcle(String tableCode, String version, String isTemplet);

	/**
	 * 通过版本导出所有的表数据
	 *
	 * @param response
	 * @param version
	 * @param isTemplet 是否仅模板 1：仅模板，0：数据
	 * @return
	 */
	void downloadDataZip(HttpServletResponse response, String version, String isTemplet);


	/**
	 * 通过版本导出所有的表数据（纵表）
	 *
	 * @param response
	 * @param version
	 * @return
	 */
	void rportDataZip(HttpServletResponse response, String version);

	/**
	 * 查询上报版本号
	 *
	 * @return
	 */
	List<Map<String, String>> queryVersion();

	/**
	 * 查询历史版本号
	 *
	 * @return
	 */
	List<Map<String, String>> queryVersionOfHistory();


	/**
	 * 通过ID查询单条数据
	 *
	 * @param tableCode 表主键
	 * @param dataCode  行数据主键
	 * @param version   版本
	 * @return 实例对象
	 */
	List<Map<String, Object>> selectOne(String tableCode, String dataCode, String version);

	/**
	 * 查询多条数据
	 *
	 * @param tableCode 表清单id
	 * @param version   版本
	 * @param deptCode  分页
	 * @param pageable  分页
	 * @return 对象列表
	 */
	HashMap<String, Object> queryAllByLimit(String tableCode, String version, String deptCode, Pageable pageable);

	/**
	 * 查询多条数据不分页
	 *
	 * @param tableCode 表清单id
	 * @param version   版本
	 * @return 对象列表
	 */
	HashMap<String, Object> queryAll(String tableCode, String version);

	/**
	 * 新增数据
	 *
	 * @param collTableData 实例对象
	 * @return 实例对象
	 */
	int insert(CollTableData collTableData);

	/**
	 * 批量新增数据
	 *
	 * @param list 实例对象列表
	 * @return 实例对象
	 */
	int insertList(List<CollTableData> list);


	/**
	 * 批量新增数据
	 *
	 * @param rportVersion   上报版本
	 * @param historyVersion 历史版本
	 * @return 实例对象
	 */
	int copyDataToNewVersion(String rportVersion, String historyVersion);


	/**
	 * 批量修改数据
	 *
	 * @param list 实例对象列表
	 * @return 实例对象
	 */
	int updateList(List<CollTableData> list);

	/**
	 * 修改数据
	 *
	 * @param collTableData 实例对象
	 * @return 实例对象
	 */
	int update(CollTableData collTableData);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	int deleteById(String id);

	/**
	 * 通过ID删除
	 *
	 * @param list 表主键
	 * @return 实例对象
	 */
	int delete(List<Map<String, Object>> list);
}