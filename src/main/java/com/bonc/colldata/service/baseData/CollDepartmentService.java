package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollDepartment;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:10:40
 */
public interface CollDepartmentService {

	int addDepartment(CollDepartment collDepartment);

	List<CollDepartment> checkCollDepartmentList(Map<String, Object> map);

	List<CollDepartment> checkCollDepartmentTree(Map<String, Object> map);

	int updateDepartment(CollDepartment collDepartment);

	int deleteDepartment(String instiutionsId);
	CollDepartment checkDepartmentById(String id);
}
