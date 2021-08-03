package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * todo:下发任务
 */

@Mapper
public interface CollSendTaskDao {
	/***
	 * todo：新建任务列表
	 * @return
	 */
	List<CollTask> checkCollTasks();

	/**
	 * todo:新增任务
	 *
	 * @param collTask
	 * @return
	 */
	int addCollTask(CollTask collTask);

	/***
	 * todo:查询任务
	 * @param code
	 * @return
	 */
	CollTask checkCollTaskById(@Param("code") String code);

	/***
	 * todo:获取下发任务列表
	 * @return
	 */
	List<CollReceiveTask> getSendTaskList(int pageSize, int pageNum);

	/***
	 * todo:获取下发任务列表
	 * @return
	 */
	CollReceiveTask getSendTask(@Param("id") String id);

	/***
	 * todo:数据插入下发任务表
	 * @param collReceiveTask
	 * @return
	 */
	int addSendTask(CollReceiveTask collReceiveTask);

	/***
	 * todo:数据插入下发任务关联表
	 * @param collReceiveTask
	 * @return
	 */
	int addSendTaskTable(CollReceiveTask collReceiveTask);

	/***
	 * todo:查询下发任务关联表
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getSendTaskTable(@Param("id") String id);

	/***
	 * todo:下发采集类型
	 * @return
	 */
	List<CollDataDictValue> getCollType();

	/***
	 * todo:获取历史版本编号
	 * @return
	 */
	List<Map<String, Object>> getBeforeVersion(@Param("departmentCode") String departmentCode);

	/****
	 * todo:获取派发任务详情
	 * @param sendTaskCode
	 * @return
	 */
	CollReceiveTask getSendTaskById(@Param("sendTaskCode") String sendTaskCode);

	/****
	 * todo:获取表列表
	 * @param sendTaskCode
	 * @return
	 */
	List<CollReceiveTaskTable> getTaskTables(@Param("sendTaskCode") String sendTaskCode);

	/***
	 * todo:获取表字段列表
	 * @param tableCode
	 * @return
	 */
	List<CollBusinessTableConfig> getTableFieldConfig(@Param("tableCode") String tableCode);
}