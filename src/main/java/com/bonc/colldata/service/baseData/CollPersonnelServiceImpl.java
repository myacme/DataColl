package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.*;
import com.bonc.colldata.mapper.baseData.CollPersonnelMapper;
import com.bonc.colldata.mapper.baseData.SysConfigDao;
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

	@Override
	public List<CollBasicPersonnelConfig> getTableHead() {
		return collPersonnelMapper.getTableHead();
	}

	@Override
	public int addPersonnelData(CollPersonnelMaintain collPersonnelMaintain) {
		return collPersonnelMapper.addPersonnelData(collPersonnelMaintain);
	}

	@Override
	public CollPersonnelMaintain checkById(String id) {
		return collPersonnelMapper.checkById(id);
	}

	@Override
	public List<CollPersonnelMaintain> getPersonnelByList(List<QueryParam> list) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		return collPersonnelMapper.getPersonnelByList(map);
	}

	@Override
	public List<CollPersonnelMaintain> getPersonnelByDept(String deptCode) {
		//获取本级及下级部门id
		Map<String, Object> map = new HashMap<>(2);
		map.put("pid", deptCode);
		List<JGKB> list = collDepartmentService.checkCollDepartmentTree(map);
		List<String> idList = collDepartmentService.getAllNode(list);
		idList.add(deptCode);
		String[] ids = idList.toArray(new String[idList.size()]);
		return collPersonnelMapper.getPersonnelByDept(ids);
	}

	@Override
	public int updatePersonnel(CollPersonnelMaintain collPersonnelMaintain) {
		return collPersonnelMapper.updatePersonnel(collPersonnelMaintain);
	}

	@Override
	public int deletePersonnelById(List<String> id) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", id);
		return collPersonnelMapper.deletePersonnelById(map);
	}

	@Override
	public List<Map<String, Object>> getTableDesc() {
		return collPersonnelMapper.getTableDesc();
	}

	@Override
	public String getSystemName() {
		return sysConfigDao.queryById("sys_name").getConfigValue();
	}
}
