package com.bonc.colldata.service.impl;

import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.entity.CollTableData;
import com.bonc.colldata.mapper.CollBusinessTableConfigDao;
import com.bonc.colldata.mapper.CollBusinessTableTypeDao;
import com.bonc.colldata.mapper.CollReceiveTaskDao;
import com.bonc.colldata.mapper.CollTableDataDao;
import com.bonc.colldata.service.CollTableDataService;

import com.bonc.utils.CommonUtil;
import com.bonc.utils.ExcelUtil;
import com.bonc.utils.ZipUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (CollTableData)表服务实现类
 *
 * @author ljx
 * @since 2021-07-14 11:25:09
 */
@Service("collTableDataService")
public class CollTableDataServiceImpl implements CollTableDataService {
	@Resource
	private CollTableDataDao collTableDataDao;
	@Resource
	private CollBusinessTableConfigDao collBusinessTableConfigDao;
	@Resource
	private CollBusinessTableTypeDao collBusinessTableTypeDao;
	@Resource
	private CollReceiveTaskDao collReceiveTaskDao;

	/**
	 * 通过版本查询所有的表
	 *
	 * @param version
	 * @return
	 */
	@Override
	public List<CollBusinessTableType> getTableList(String version) {
		return collBusinessTableTypeDao.getTableList(version);
	}

	/**
	 * 通过导出的表数据
	 *
	 * @param tableCode
	 * @param version
	 * @param isTemplet
	 * @return
	 */
	@Override
	public File reportDataExcle(String tableCode, String version, String isTemplet) {
		CollBusinessTableType table = collBusinessTableTypeDao.queryById(tableCode);
		FileOutputStream outputStream = null;
		File file = null;
		try {
			file = new File(ZipUtil.getProjectPath(), table.getBusinessTypeTableName() + ".xlsx");
			outputStream = new FileOutputStream(file);
			List<CollBusinessTableConfig> configList = collBusinessTableConfigDao.getConfigList(table.getBusinessTypeTableCode());
			List<Map<String, Object>> tableDataList = this.collTableDataDao.getDataList(configList, table.getBusinessTypeTableCode(), version, null, "this_update");
			if ("0".equals(isTemplet)) {
				Workbook wb = ExcelUtil.generateXSLX(tableDataList, configList, table);
				wb.write(outputStream);
			}
			if ("1".equals(isTemplet)) {
				Workbook wb = ExcelUtil.generateXSLX(null, configList, table);
				wb.write(outputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 通过版本导出所有的表数据
	 *
	 * @param response
	 * @param version
	 * @param isTemplet
	 * @return
	 */
	@Override
	public void downloadDataZip(HttpServletResponse response, String version, String isTemplet) {
		List<CollBusinessTableType> tableList = collBusinessTableTypeDao.getTableList(version);
		ArrayList<File> fileList = new ArrayList<File>();
		tableList.forEach(table -> {
			FileOutputStream outputStream = null;
			try {
				File file = new File(ZipUtil.getProjectPath(), table.getBusinessTypeTableName() + ".xlsx");
				outputStream = new FileOutputStream(file);
				List<CollBusinessTableConfig> configList = collBusinessTableConfigDao.getConfigList(table.getBusinessTypeTableCode());
				List<Map<String, Object>> tableDataList = this.collTableDataDao.getDataList(configList, table.getBusinessTypeTableCode(), version, null, "this_update");
				if ("0".equals(isTemplet)) {
					Workbook wb = ExcelUtil.generateXSLX(tableDataList, configList, table);
					wb.write(outputStream);
				}
				if ("1".equals(isTemplet)) {
					Workbook wb = ExcelUtil.generateXSLX(null, configList, table);
					wb.write(outputStream);
				}
				fileList.add(file);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ZipUtil.zipDownload(response, fileList, "数据.zip", "123");
		fileList.forEach(file -> file.delete());
		collReceiveTaskDao.report(version);
	}

	/**
	 * 查询上报版本号
	 *
	 * @return
	 */
	@Override
	public List<Map<String, String>> queryVersion() {
		return collReceiveTaskDao.queryVersionOfnew();
	}

	/**
	 * 查询历史版本号
	 *
	 * @return
	 */
	@Override
	public List<Map<String, String>> queryVersionOfHistory() {
		return collReceiveTaskDao.queryVersionOfnew();
	}

	/**
	 * 通过ID查询单条数据
	 *
	 * @param tableCode 表主键
	 * @param dataCode  行数据主键
	 * @param version   版本
	 * @return 实例对象
	 */
	@Override
	public List<Map<String, Object>> selectOne(String tableCode, String dataCode, String version) {
		List<CollBusinessTableConfig> configList = collBusinessTableConfigDao.getConfigList(tableCode);
		List<Map<String, Object>> tableDataList = this.collTableDataDao.getDataList(configList, tableCode, version, dataCode, "lower_update");
		return tableDataList;
	}

	/**
	 * 查询多条数据
	 *
	 * @param tableCode 表清单id
	 * @param version   版本
	 * @return 对象列表
	 */
	@Override
	public HashMap<String, Object> queryAllByLimit(String tableCode, String version, Pageable pageable) {
		List<CollBusinessTableConfig> configList = collBusinessTableConfigDao.getConfigList(tableCode);
		Map<String, String> nameMap = new HashMap<>();
		configList.forEach(bean -> {
			nameMap.put(bean.getTableConfigCode(), bean.getTableConfigName());
		});
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		List<Map<String, Object>> tableDataList = this.collTableDataDao.getDataList(configList, tableCode, version, null, "lower_update");
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(tableDataList);
		HashMap<String, Object> dataMap = new HashMap<>();
		dataMap.put("name", nameMap);
		dataMap.put("data", pageInfo);
		return dataMap;
	}

	/**
	 * 查询多条数据不分页
	 *
	 * @param tableCode 表清单id
	 * @param version   版本
	 * @return 对象列表
	 */
	@Override
	public HashMap<String, Object> queryAll(String tableCode, String version) {
		List<CollBusinessTableConfig> configList = collBusinessTableConfigDao.getConfigList(tableCode);
		Map<String, String> nameMap = new HashMap<>();
		configList.forEach(bean -> {
			nameMap.put(bean.getTableConfigCode(), bean.getTableConfigName());
		});
		List<Map<String, Object>> tableDataList = this.collTableDataDao.getDataList(configList, tableCode, version, null, "lower_update");
		HashMap<String, Object> dataMap = new HashMap<>();
		dataMap.put("name", nameMap);
		dataMap.put("data", tableDataList);
		return dataMap;
	}

	/**
	 * 新增数据
	 *
	 * @param collTableData 实例对象
	 * @return 实例对象
	 */
	@Override
	public int insert(CollTableData collTableData) {
		return this.collTableDataDao.insert(collTableData);
	}

	/**
	 * 批量新增数据
	 *
	 * @param list 实例对象列表
	 * @return 实例对象
	 */
	@Override
	public int insertList(List<CollTableData> list) {
		return this.collTableDataDao.insertList(list);
	}

	/**
	 * 批量新增数据
	 *
	 * @param rportVersion   上报版本
	 * @param historyVersion 历史版本
	 * @return 实例对象
	 */
	@Override
	public int copyDataToNewVersion(String rportVersion, String historyVersion) {
		List<CollTableData> historyDataList = collTableDataDao.queryAll(historyVersion);
		List<CollTableData> newDataList = new ArrayList<>();
		Map<String, List<CollTableData>> collect = historyDataList.stream().collect(Collectors.groupingBy(CollTableData::getDataCode));
		collect.forEach((k, list) -> {
			String id = CommonUtil.getUUID32();
			list.forEach(collTableData -> {
				collTableData.setVersion(rportVersion);
				collTableData.setDataCode(id);
				newDataList.add(collTableData);
			});
		});
		return collTableDataDao.insertList(newDataList);
	}

	/**
	 * 批量修改数据
	 *
	 * @param list 实例对象列表
	 * @return 实例对象
	 */
	@Override
	public int updateList(List<CollTableData> list) {
		return this.collTableDataDao.updateList(list);
	}

	/**
	 * 修改数据
	 *
	 * @param collTableData 实例对象
	 * @return 实例对象
	 */
	@Override
	public int update(CollTableData collTableData) {
		return this.collTableDataDao.update(collTableData);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public int deleteById(String id) {
		return this.collTableDataDao.deleteById(id);
	}

	/**
	 * 通过ID删除
	 *
	 * @param tableCode 表主键
	 * @param dataCode  行数据主键
	 * @param version   版本
	 * @return
	 */
	@Override
	public int delete(String tableCode, String dataCode, String version) {
		return this.collTableDataDao.delete(tableCode, dataCode, version);
	}
}