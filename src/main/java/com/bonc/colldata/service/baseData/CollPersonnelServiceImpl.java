package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.*;
import com.bonc.colldata.mapper.baseData.CollDepartmentMapper;
import com.bonc.colldata.mapper.baseData.CollPersonnelMapper;
import com.bonc.colldata.mapper.baseData.SysConfigDao;
import com.bonc.colldata.service.CollTableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:10:42
 */
@Service
public class CollPersonnelServiceImpl implements CollPersonnelService {
	@Resource
	private CollPersonnelMapper collPersonnelMapper;
	@Resource
	private SysConfigDao sysConfigDao;
	@Resource
	private CollDepartmentServiceImpl collDepartmentService;
	@Autowired
	private CollDepartmentMapper collDepartmentMapper;
	@Autowired
	private CollTableDataService collTableDataService;



	@Override
	public int addPersonnelData(RYKB rykb) {
		return collPersonnelMapper.addPersonnelData(rykb);
	}

	@Override
	public RYKB checkById(String id) {
		return collPersonnelMapper.checkById(id);
	}

	@Override
	public List<RYKB> getPersonnelByList(String xm,String szdwcjid) {
		Map<String, Object> map = new HashMap<>();
		map.put("xm", xm);
		map.put("szdwcjid",szdwcjid);
		return collPersonnelMapper.getPersonnelByList(map);
	}

	@Override

	public List<RYKB> getPersonnelByDept(String deptCode, String name, String IDcard) {
		//获取本级及下级部门id
		JGKB collDepartment = collDepartmentMapper.checkDepartmentById(deptCode);
		List<JGKB> list = collTableDataService.checkCollDepartmentTree(collDepartment);
		List<String> idList = collDepartmentService.getAllNode(list);
		idList.add(deptCode);
		String[] ids = idList.toArray(new String[idList.size()]);
		return collPersonnelMapper.getPersonnelByDept(ids,name,IDcard);
	}

	@Override
	public int updatePersonnel(RYKB rykb) {
		return collPersonnelMapper.updatePersonnel(rykb);
	}

	@Override
	public int deletePersonnelById(List<String> id) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", id);
		return collPersonnelMapper.deletePersonnelById(map);
	}
	@Override
	public String getSystemName() {
		return sysConfigDao.queryById("sys_name").getConfigValue();
	}
}
