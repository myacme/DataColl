package com.bonc.colldata.service;

import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.entity.CollDataDictValue;
import com.bonc.colldata.entity.CollReceiveTask;
import com.bonc.colldata.entity.CollReceiveTaskTable;

import java.util.List;
import java.util.Map;

/**
todo:下发任务
 */


public interface CollSendTaskService {

	List<CollReceiveTask> getSendTaskList(int pageSize, int pageNum);

	String addSendTask(CollReceiveTask collReceiveTask);

	List<CollDataDictValue> getCollType();

	List<Map<String,Object>> getBeforeVersion(String departmentCode);
	/****
	 * todo:获取派发任务详情
	 * @param sendTaskCode
	 * @return
	 */
	CollReceiveTask getSendTaskById(String sendTaskCode);

	/****
	 * todo:获取表列表
	 * @param sendTaskCode
	 * @return
	 */
	List<CollReceiveTaskTable> getTaskTables(String sendTaskCode);


	List<CollBusinessTableConfig> getTableFieldConfig(String tableCode);
}