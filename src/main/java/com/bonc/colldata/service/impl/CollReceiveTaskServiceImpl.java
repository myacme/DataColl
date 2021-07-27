package com.bonc.colldata.service.impl;

import com.alibaba.fastjson.JSON;
import com.bonc.colldata.config.SystemConfig;
import com.bonc.colldata.entity.CollReceiveTask;
import com.bonc.colldata.entity.CollReceiveTaskTable;
import com.bonc.colldata.mapper.CollReceiveTaskDao;
import com.bonc.colldata.service.CollReceiveTaskService;

import com.bonc.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (CollReceiveTask)表服务实现类
 *
 * @author ljx
 * @since 2021-07-12 16:17:30
 */
@Service("collReceiveTaskService")
public class CollReceiveTaskServiceImpl implements CollReceiveTaskService {
	@Resource
	private CollReceiveTaskDao collReceiveTaskDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public CollReceiveTask queryById(String id) {
		return this.collReceiveTaskDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param pageable 分页参数
	 * @return 对象列表
	 */
	@Override
	public PageInfo<Map<String, Object>> queryAllByLimit(Pageable pageable) {
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		return new PageInfo<>(collReceiveTaskDao.queryAllByLimit());
	}

	/**
	 * 新增数据
	 *
	 * @param
	 * @return 实例对象
	 */
	@Override
	public int insert(MultipartFile multipartFile) {

		CollReceiveTask collReceiveTask=this.readZipAndExcel(multipartFile);
		collReceiveTask.setCreateTime(TimeUtil.getCurrentTime());
		collReceiveTask.setState("1");
		int a = this.collReceiveTaskDao.insert(collReceiveTask);
		int b = this.collReceiveTaskDao.addReceiveTaskTable(collReceiveTask);
		return (a+b);
	}

	/**
	 * 修改数据
	 *
	 * @param collReceiveTask 实例对象
	 * @return 实例对象
	 */
	@Override
	public int update(CollReceiveTask collReceiveTask) {
		return this.collReceiveTaskDao.update(collReceiveTask);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(String id) {
		return this.collReceiveTaskDao.deleteById(id) > 0;
	}

	public CollReceiveTask readZipAndExcel(MultipartFile multipartFile){
		//文件名称 zip规则 部门_任务名称_采集类型_版本号
		String fileName = null;
		try {
			fileName = URLDecoder.decode(multipartFile.getOriginalFilename(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int index = fileName.indexOf("_");
		CollReceiveTask task = new CollReceiveTask();
		task.setSendTaskCollDepartment(fileName.substring(0, index));
		task.setSendTaskName(fileName.substring(index + 1, fileName.indexOf("_", index + 1)));
		index = fileName.indexOf("_", index + 1);
		task.setSendTaskCollType(fileName.substring(index + 1, fileName.indexOf("_", index + 1)));
		index = fileName.indexOf("_", index + 1);
		task.setSendTaskVersion(fileName.substring(index + 1, fileName.indexOf(".", index + 1)));
		String taskCode = CommonUtil.getUUID32();
		task.setSendTaskCode(taskCode);
		task.setCreateTime(CommonUtil.getNowTime());
		task.setState("1");
		List<CollReceiveTaskTable> result = new ArrayList<>();
		//	collReceiveTaskService.insert(task);
		File[] unzip = ZipUtil.unzip(FileUtil.toFile(multipartFile), SystemConfig.getZipPassWord());
		if (unzip != null && unzip.length != 0) {
			for (File file : unzip) {
				if ("txt".equals(FileUtil.getExtensionName(file.getName()))) {
					String s = TxtUtil.readTxtToString(file);
				}
				if ("pdf".equals(FileUtil.getExtensionName(file.getName()))) {
				}
				if ("xlsx".equals(FileUtil.getExtensionName(file.getName()))) {
					List<Map<String, String>> list = ExcelUtil.readExcleCommon(file);
					//将list对象转换
					for (Map<String, String> map : list) {
						CollReceiveTaskTable collReceiveTaskTable = JSON.parseObject(JSON.toJSONString(map), CollReceiveTaskTable.class);
						result.add(collReceiveTaskTable);
					}
				}
			}
		}
		task.setList(result);
		return task;
	}
}