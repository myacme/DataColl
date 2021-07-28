package com.bonc.colldata.service.impl;

import com.alibaba.fastjson.JSON;
import com.bonc.colldata.config.SystemConfig;
import com.bonc.colldata.entity.*;
import com.bonc.colldata.mapper.CollSendTaskDao;
import com.bonc.colldata.service.CollReceiveTaskService;
import com.bonc.colldata.service.CollSendTaskService;
import com.bonc.colldata.service.CollTableDataService;
import com.bonc.colldata.service.baseData.CollDepartmentServiceImpl;
import com.bonc.colldata.service.baseData.CollPersonnelService;

import com.bonc.utils.*;

import org.apache.poi.ss.usermodel.*;
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

	@Override
	public List<CollTask> checkCollTasks() {
		return collSendTaskDao.checkCollTasks();
	}

	@Override
	public int addCollTask(CollTask collTask) {
		String uuid = CommonUtil.getUUID32();
		collTask.setCollTaskCode(uuid);
		return collSendTaskDao.addCollTask(collTask);
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
		String uuid = CommonUtil.getUUID32();
		collReceiveTask.setSendTaskCode(uuid);
		collReceiveTask.setCreateTime(TimeUtil.getCurrentTime());
		collReceiveTask.setState("1");
		int resultTask = collSendTaskDao.addSendTask(collReceiveTask);
		int resultTable = collSendTaskDao.addSendTaskTable(collReceiveTask);
		return ("插入任务表数据条数:" + resultTask + ";插入关联表数据:" + resultTable);
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
		//任务名称
		String taskName = collReceiveTask.getSendTaskName();
		//是否仅模板
		String ifTemp = collReceiveTask.getSendIfTemp();
		//数据版本
		String vsersion = collReceiveTask.getSendTaskVersion();
		//构建表格
		List<CollReceiveTaskTable> listTable = this.getTaskTables(sendTaskCode);
		System.out.println(listTable.size());
		List<Map<String, Object>> list = new ArrayList<>();
		list = this.getTaskTemplate(listTable);
		if ("cjlx002".equals(taskCollType)) {
			list.add(this.getBaseTemplate( deptId, ifTemp));
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
		String deptName=collDepartmentService.checkDepartmentById(deptId).getInstiutionsName();
		//任务名称
		String taskName = collReceiveTask.getSendTaskName();
		//是否仅模板
		String ifTemp = collReceiveTask.getSendIfTemp();
		//数据版本
		String vsersion = collReceiveTask.getSendTaskVersion();
		//构建表格
		List<CollReceiveTaskTable> listTable = this.getTaskTables(sendTaskCode);
		List<Map<String, Object>> list = new ArrayList<>();
		list = this.getTaskTemplate(listTable);
		if ("cjlx002".equals(taskCollType)) {
			list.add(this.getBaseTemplate( deptId, ifTemp));
		}

		String fileName = deptName + "_" + taskName + "_" + taskCollType + "_" + vsersion;
		ArrayList<File> fileList = new ArrayList<File>();
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
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> nameMap = new HashMap<>();
		Map<String, Object> p = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		param.put("dept_id", deptId);
		//获取基础数据
		List<CollBasicPersonnelConfig> listHead = collPersonnelService.getTableHead();
		//获取表字段字段信息
		List<Map<String, Object>> listDesc = collPersonnelService.getTableDesc();
		//获取数据
		List<QueryParam> listp = new ArrayList<>();
		QueryParam queryParam = new QueryParam();
		queryParam.setCode("dept_id");
		queryParam.setType(1);
		queryParam.setValue(deptId);
		listp.add(queryParam);
		List<CollPersonnelMaintain> listData = collPersonnelService.getPersonnelByList(listp);

		for (Map<String, Object> m : listDesc) {

				String key = String.valueOf(m.get("name"));
				String value = key;
				for (CollBasicPersonnelConfig collBasicPersonnelConfig : listHead) {
					if (collBasicPersonnelConfig.getPersonnelConfigValue().toLowerCase().equals(key.toLowerCase())) {
						value = collBasicPersonnelConfig.getPersonnelConfigName() + "(" + key + ")";
						break;
					} else {
						value = key + "(" + key + ")";
					}
				}
				nameMap.put(key, value);
		}
		System.out.println(nameMap);
		p.put("nameMap", nameMap);
		p.put("name", "coll_personnel_maintain");
		p.put("tableName", "人员基本信息");
		if ("1".equals(ifTemp)) {
			p.put("data", null);
		} else {
			if (listData != null && listData.size() > 0) {
				for (CollPersonnelMaintain collPersonnelMaintain : listData) {
					list.add(JSON.parseObject(JSON.toJSONString(collPersonnelMaintain), Map.class));
				}
				p.put("data", list);
			} else {
				p.put("data", null);
			}

		}
		return p;
	}

	public List<Map<String, Object>> getTaskTemplate(List<CollReceiveTaskTable> listTable) {
		System.out.println(listTable.size());
		List<Map<String, Object>> list = new ArrayList<>();
		for (CollReceiveTaskTable collReceiveTaskTable : listTable) {
			//
			String tableCode = collReceiveTaskTable.getSendTaskTableCode();
			String tableName = collReceiveTaskTable.getSendTaskTableName();
			List<CollBusinessTableConfig> fieldList = this.getTableFieldConfig(tableCode);
			//封装数据标题数据
			Map<String, String> map = new LinkedHashMap<>();
			for (CollBusinessTableConfig collBusinessTableConfig : fieldList) {
				map.put(collBusinessTableConfig.getTableConfigCode(), collBusinessTableConfig.getTableConfigName() + "(" + collBusinessTableConfig.getTableConfigCode() + ")");
			}
			Map<String, Object> p = new HashMap<>();
			p.put("nameMap", map);
			p.put("data", null);
			p.put("name", tableCode);
			p.put("tableName",tableName);
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
			String deptName=collDepartmentService.checkDepartmentById(deptId).getInstiutionsName();
			//任务名称
			String taskName = collReceiveTask.getSendTaskName();
			//是否仅模板
			String ifTemp = collReceiveTask.getSendIfTemp();
			//历史数据版本
			String vsersion = collReceiveTask.getSendTaskDataAgo();
			//构建表格
			String fileName = deptName + "_" + taskName + "_" + taskCollType + "_" + vsersion;

			List<CollReceiveTaskTable> listTable = this.getTaskTables(sendTaskCode);
			List<Map<String, Object>> list = new ArrayList<>();
			list = this.getTaskTemplate(listTable);
			if ("cjlx002".equals(taskCollType)) {
				list.add(this.getBaseTemplate(deptId, ifTemp));
			}
			for(Map<String,Object> map:list){
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
	public File getText(HttpServletResponse response, String ids) {
		//根据部门查询该部门下的所有
		StringBuffer sb = new StringBuffer();
		sb.append("text");
		File file = TxtUtil.writrTxt(sb.toString(), "账密");
		/*try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("账密.txt").getBytes(), "iso-8859-1"));
			OutputStreamWriter write = null;
			write = new OutputStreamWriter(response.getOutputStream(), "utf-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(sb + "\r\n");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

		}*/

		return file;
	}

}