package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollDepartment;
import com.bonc.colldata.entity.JGKB;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:10:40
 */
public interface CollDepartmentService {

	int addDepartment(JGKB jgkb);

	List<JGKB> checkCollDepartmentList(Map<String, Object> map);

	List<JGKB> checkCollDepartmentTree(Map<String, Object> map);

	int updateDepartment(JGKB jgkb);

	int deleteDepartment(String instiutionsId);
	JGKB checkDepartmentById(String id);
}
