package com.bonc.colldata.mapper.baseData;

import com.bonc.colldata.entity.CollDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:10:16
 * todo:机构维护
 */
@Mapper
public interface CollDepartmentMapper {
	/***
	 * todo:新增部门
	 * @param collDepartment
	 * @return
	 */
	int addDepartment(CollDepartment collDepartment);

	/****
	 * todo:查询机构列表
	 * @param map
	 * @return
	 */
	List<CollDepartment> checkCollDepartmentList(Map<String, Object> map);

	/****
	 * todo:查询机构列表树
	 * @param map
	 * @return
	 */
	List<CollDepartment> checkCollDepartmentTree(Map<String, Object> map);

	/***
	 * todo:查看部门详情
	 * @param id
	 * @return
	 */
	CollDepartment checkDepartmentById(@Param("id") String id);

	/****
	 * todo:修改机构数据
	 * @param collDepartment
	 * @return
	 */
	int updateDepartment(CollDepartment collDepartment);

	/***
	 * todo:删除机构
	 * @param map
	 * @return
	 */
	int deleteDepartment(Map<String, Object> map);

	/***
	 * todo:获取表字段描述
	 * @return
	 */
	List<Map<String, Object>> getTableHead();
}
