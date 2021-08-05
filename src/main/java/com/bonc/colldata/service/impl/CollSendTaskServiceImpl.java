package com.bonc.colldata.service.impl;

import com.alibaba.fastjson.JSON;
import com.bonc.colldata.config.SystemConfig;
import com.bonc.colldata.entity.*;
import com.bonc.colldata.mapper.CollBusinessTableConfigDao;
import com.bonc.colldata.mapper.CollSendTaskDao;
import com.bonc.colldata.mapper.CollTableDataDao;
import com.bonc.colldata.service.CollReceiveTaskService;
import com.bonc.colldata.service.CollSendTaskService;
import com.bonc.colldata.service.CollTableDataService;
import com.bonc.colldata.service.baseData.CollDepartmentServiceImpl;
import com.bonc.colldata.service.baseData.CollPersonnelService;
import com.bonc.utils.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * (CollReceiveTask)表服务实现类
 *
 * @author ljx
 * @since 2021-07-12 16:17:30
 */
@Service
public class CollSendTaskServiceImpl implements CollSendTaskService {
	@Resource
	private CollSendTaskDao collSendTaskDao;

	@Resource
	private CollTableDataService collTableDataService;
	@Resource
	private CollPersonnelService collPersonnelService;
	@Resource
	private CollReceiveTaskService collReceiveTaskService;
	@Resource
	private CollDepartmentServiceImpl collDepartmentService;

	@Resource
	private CollBusinessTableConfigDao collBusinessTableConfigDao;

	@Override
	public List<CollTask> checkCollTasks() {
		return collSendTaskDao.checkCollTasks();
	}

	@Override
	public String addCollTask(CollTask collTask) {
		String uuid = CommonUtil.getUUID20();
		collTask.setCollTaskCode(uuid);
		collTask.setCreateTime(TimeUtil.getCurrentTime());
		int result = collSendTaskDao.addCollTask(collTask);
		return uuid;
	}

	@Override
	public CollTask checkCollTaskById(String code) {
		return collSendTaskDao.checkCollTaskById(code);
	}

	@Override
	public List<CollReceiveTask> getSendTaskList(int pageSize, int pageNum) {
		return collSendTaskDao.getSendTaskList(pageSize, pageNum);
	}

	@Override
	public String addSendTask(CollReceiveTask collReceiveTask) {
		//插入表
		//获取任务下发编号
		String uuid = CommonUtil.getUUID20();
		collReceiveTask.setSendTaskCode(uuid);
		collReceiveTask.setCreateTime(TimeUtil.getCurrentTime());
		collReceiveTask.setState("1");
		int resultTask = collSendTaskDao.addSendTask(collReceiveTask);
		int resultTable = collSendTaskDao.addSendTaskTable(collReceiveTask);
		//	return ("插入任务表数据条数:" + resultTask + ";插入关联表数据:" + resultTable);
		return uuid;
	}

	@Override
	public List<CollDataDictValue> getCollType() {
		return collSendTaskDao.getCollType();
	}

	@Override
	public List<Map<String, Object>> getBeforeVersion(String departmentCode) {
		return collSendTaskDao.getBeforeVersion(departmentCode);
	}

	@Override
	public CollReceiveTask getSendTaskById(String sendTaskCode) {
		return collSendTaskDao.getSendTaskById(sendTaskCode);
	}

	@Override
	public List<CollReceiveTaskTable> getTaskTables(String sendTaskCode) {
		return collSendTaskDao.getTaskTables(sendTaskCode);
	}

	@Override
	public List<CollBusinessTableConfig> getTableFieldConfig(String tableCode) {
		return collSendTaskDao.getTableFieldConfig(tableCode);
	}

	/***
	 * todo:处理pdf预览
	 * @param response
	 * @param isOnLine  true 为预览，false为下载
	 * @param sendTaskCode  下发任务编号
	 * @throws IOException
	 */
	public void scanPdf(HttpServletResponse response, boolean isOnLine, String sendTaskCode) throws IOException {
		String filePath = "e:/pdf/Test111.pdf";
		System.out.println("filePath:" + filePath);
		File f = new File(filePath);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] bs = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
			URL u = new URL("file:///" + filePath);
			String contentType = u.openConnection().getContentType();
			System.out.println(contentType);
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "inline;filename=" + sendTaskCode);
			// 文件名应该编码成utf-8，注意：使用时，我们可忽略这句
		} else {
			// 纯下载方式
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=" + sendTaskCode);
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(bs)) > 0) {
			out.write(bs, 0, len);
		}
		out.flush();
		out.close();
		br.close();
	}

	/***
	 * todo:生成excel
	 * @param response
	 * @param sendTaskCode
	 */
	public void getExcelTemplate(HttpServletResponse response, String sendTaskCode, String type) {
		//根据派发任务编号 获取派发任务详情
		CollReceiveTask collReceiveTask = null;
		if ("send".toLowerCase().equals(type.toLowerCase())) {
			collReceiveTask = this.getSendTaskById(sendTaskCode);
		} else if ("receive".toLowerCase().equals(type.toLowerCase())) {
			collReceiveTask = collReceiveTaskService.queryById(sendTaskCode);
		}
		//业务数据类型  001是业务数据 002 时候业务+基础
		String taskCollType = collReceiveTask.getSendTaskCollType();
		//下发部门
		String deptId = collReceiveTask.getSendTaskCollDepartment();
		String deptName = collReceiveTask.getSendTaskCollDepartmentName();
		//任务名称
		String taskName = collReceiveTask.getSendTaskName();
		//是否仅模板
		String ifTemp = collReceiveTask.getSendIfTemp();
		//数据版本
		String vsersion = collReceiveTask.getSendTaskVersion();
		String befVsersion = collReceiveTask.getSendTaskDataAgo();
		//构建表格
		List<CollReceiveTaskTable> listTable = this.getTaskTables(sendTaskCode);
		System.out.println(listTable.size());
		List<Map<String, Object>> list = new ArrayList<>();
		list = this.getTaskTemplate(listTable, befVsersion, deptId);
		if ("cjlx002".equals(taskCollType)) {
			list.add(this.getBaseTemplate(deptId, ifTemp));
			list.add(this.getBaseDeptTemplate(deptId, ifTemp));
		}
		Workbook wb = ExcelUtil.createXSLXTemplateList(list);
		try {
			//this.excelToHtml(wb, response);
			//    String html=ExcelTOHtml.excelWriteToHtml(wb);
			//System.out.println(html);
			OutputStream outputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(taskName + ".xlsx", "utf-8"));
			wb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getZipTemplate(HttpServletResponse response, String sendTaskCode, String type) {
		//根据派发任务编号 获取派发任务详情
		CollReceiveTask collReceiveTask = null;
		if ("send".toLowerCase().equals(type.toLowerCase())) {
			collReceiveTask = this.getSendTaskById(sendTaskCode);
		} else if ("receive".toLowerCase().equals(type.toLowerCase())) {
			collReceiveTask = collReceiveTaskService.queryById(sendTaskCode);
		}
		//业务数据类型  001是业务数据 002 时候业务+基础
		String taskCollType = collReceiveTask.getSendTaskCollType();
		//下发部门
		String deptId = collReceiveTask.getSendTaskCollDepartment();
		//获取部门名称
		String deptName = collDepartmentService.checkDepartmentById(deptId).getJgmc();
		//任务名称
		String taskName = collReceiveTask.getSendTaskName();
		//是否仅模板
		String ifTemp = collReceiveTask.getSendIfTemp();
		//数据版本
		String vsersion = collReceiveTask.getSendTaskVersion();
		String befVersion = collReceiveTask.getSendTaskDataAgo();
		//构建表格
		List<CollReceiveTaskTable> listTable = this.getTaskTables(sendTaskCode);
		List<Map<String, Object>> list = new ArrayList<>();
		list = this.getTaskTemplate(listTable, befVersion, deptId);
		if ("cjlx002".equals(taskCollType)) {
			list.add(this.getBaseTemplate(deptId, ifTemp));
			list.add(this.getBaseDeptTemplate(deptId, ifTemp));
		}
		String fileName = deptName + "_" + taskName + "_" + taskCollType + "_" + vsersion;
		ArrayList<File> fileList = new ArrayList<File>();
		File fileTxt = this.getText(collReceiveTask);
		fileList.add(fileTxt);
		for (Map<String, Object> map : list) {
			Workbook wb = ExcelUtil.createXSLXTemplate(map);
			//zip 或者文件名称 zip规则 部门_任务名称_采集类型_版本号
			File file = new File(ZipUtil.getProjectPath(), map.get("tableName").toString() + ".xlsx");
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				wb.write(outputStream);
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
		}
		ZipUtil.zipDownload(response, fileList, fileName + ".zip", SystemConfig.getZipPassWord());
		fileList.forEach(file1 -> file1.delete());
	}

	/***
	 * todo：基础数据管理
	 * @param
	 * @param deptId
	 */
	public Map<String, Object> getBaseTemplate(String deptId, String ifTemp) {
		Map<String, Object> nameMap = null;
		Map<String, Object> p = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		//获取基础数据
		//获取表字段字段信息
		List<RYKB> listData = collPersonnelService.getPersonnelByList(null, deptId);
		nameMap = PersonEnum.payTypeMap;
		System.out.println(nameMap);
		p.put("nameMap", nameMap);
		p.put("name", "t_zb_rykb");
		p.put("tableName", "人员基本信息");
		if ("1".equals(ifTemp)) {
			p.put("data", null);
		} else {
			if (listData != null && listData.size() > 0) {
				for (RYKB collPersonnelMaintain : listData) {
					list.add(JSON.parseObject(JSON.toJSONString(collPersonnelMaintain), Map.class));
				}
				p.put("data", list);
			} else {
				p.put("data", null);
			}
		}
		return p;
	}

	/***
	 *
	 * @param deptId
	 * @param ifTemp
	 * @return
	 */
	public Map<String, Object> getBaseDeptTemplate(String deptId, String ifTemp) {
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> nameMap = null;
		Map<String, Object> p = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		param.put("pid", deptId);
		//获取基础数据
		//获取表字段字段信息
		List<JGKB> listData = collDepartmentService.checkCollDepartmentList(param);
		nameMap = DepartEnum.payTypeMap;
		System.out.println(nameMap);
		p.put("nameMap", nameMap);
		p.put("name", "t_zb_jgkb");
		p.put("tableName", "机构信息表");
		if ("1".equals(ifTemp)) {
			p.put("data", null);
		} else {
			if (listData != null && listData.size() > 0) {
				for (JGKB jgkb : listData) {
					list.add(JSON.parseObject(JSON.toJSONString(jgkb), Map.class));
				}
				p.put("data", list);
			} else {
				p.put("data", null);
			}
		}
		return p;
	}

	public List<Map<String, Object>> getTaskTemplate(List<CollReceiveTaskTable> listTable, String version, String deptId) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		param.put("pid", deptId);
		List<String> deptList = collDepartmentService.getAllNode(collDepartmentService.checkCollDepartmentTree(param));
         deptList.add(deptId);
		for (CollReceiveTaskTable collReceiveTaskTable : listTable) {
			String tableCode = collReceiveTaskTable.getSendTaskTableCode();
			String tableName = collReceiveTaskTable.getSendTaskTableName();
			//List<CollBusinessTableConfig> fieldList = this.getTableFieldConfig(tableCode);
			List<Map<String, Object>> tableDataList = collTableDataService.getTableDataList(deptList, version, tableCode);
			//封装数据标题数据
			Map<String, String> map = TableFieldEnum.payTypeMap;
			/*for (CollBusinessTableConfig collBusinessTableConfig : fieldList) {
				map.put(collBusinessTableConfig.getTableConfigCode(), collBusinessTableConfig.getTableConfigName() + "(" + collBusinessTableConfig.getTableConfigCode() + ")");
			}*/
			Map<String, Object> p = new HashMap<>();
			p.put("nameMap", map);
			if (tableDataList != null && tableDataList.size() > 0) {
				p.put("data", tableDataList);
			} else {
				p.put("data", null);
			}
			p.put("name", tableCode);
			p.put("tableName", tableName);
			list.add(p);
		}
		return list;
	}

	/***
	 * todo:生成批量压缩包
	 */
	public void getZipMore(HttpServletResponse response, List<String> list1) {
		ArrayList<File> filed = new ArrayList<File>();
		for (String sendTaskCode : list1) {
			ArrayList<File> fileList = new ArrayList<File>();
			//根据派发任务编号 获取派发任务详情
			CollReceiveTask collReceiveTask = this.getSendTaskById(sendTaskCode);
			//业务数据类型  001是业务数据 002 时候业务+基础
			String taskCollType = collReceiveTask.getSendTaskCollType();
			//下发部门
			String deptId = collReceiveTask.getSendTaskCollDepartment();
			//获取部门名称
			String deptName = collDepartmentService.checkDepartmentById(deptId).getJgmc();
			//任务名称
			String taskName = collReceiveTask.getSendTaskName();
			//是否仅模板
			String ifTemp = collReceiveTask.getSendIfTemp();
			//历史数据版本
			String befvsersion = collReceiveTask.getSendTaskDataAgo();
			String vsersion = collReceiveTask.getSendTaskVersion();
			//构建表格
			String fileName = deptName + "_" + taskName + "_" + taskCollType + "_" + vsersion;
			List<CollReceiveTaskTable> listTable = this.getTaskTables(sendTaskCode);
			List<Map<String, Object>> list = new ArrayList<>();
			list = this.getTaskTemplate(listTable, befvsersion, deptId);
			if ("cjlx002".equals(taskCollType)) {
				list.add(this.getBaseTemplate(deptId, ifTemp));
			}
			for (Map<String, Object> map : list) {
				Workbook wb = ExcelUtil.createXSLXTemplate(map);
				File file = new File(ZipUtil.getProjectPath(), map.get("tableName") + ".xlsx");
				FileOutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(file);
					wb.write(outputStream);
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
			}
			String path = ZipUtil.zipTodir(fileList, fileName, SystemConfig.getZipPassWord());
			File f = new File(path);
			filed.add(f);
			fileList.forEach(file1 -> file1.delete());
		}
		ZipUtil.zipDownload(response, filed, "数据.zip", SystemConfig.getZipPassWord());
		filed.forEach(f -> f.delete());
	}

	/**
	 * 导出zip
	 *
	 * @param ids
	 */
	public void downloadZip(String ids) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			CollReceiveTask task = collSendTaskDao.getSendTask(id);
			List<Map<String, Object>> tables = collSendTaskDao.getSendTaskTable(id);
			tables.forEach(table -> {
				collTableDataService.reportDataExcle(table.get("").toString(), task.getSendTaskVersion(), "0");
			});
		}
	}

	/***
	 * todo:生成账密文件.txt
	 * @param
	 */
	public File getText(CollReceiveTask collReceiveTask) {
		//根据部门查询该部门下的所有
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = JSON.parseObject(JSON.toJSONString(collReceiveTask));
		sb.append(map.toString());
		File file = TxtUtil.writrTxt(sb.toString(), collReceiveTask.getSendTaskName());
		return file;
	}
}