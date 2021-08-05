package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.*;
import com.bonc.colldata.mapper.baseData.CollDepartmentMapper;
import com.bonc.colldata.mapper.baseData.CollPersonnelMapper;
import com.bonc.colldata.mapper.baseData.SysConfigDao;
import com.bonc.colldata.service.CollTableDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	public List<RYKB> getPersonnelByList(String xm, String szdwcjid) {
		Map<String, Object> map = new HashMap<>();
		map.put("xm", xm);
		map.put("szdwcjid", szdwcjid);
		return collPersonnelMapper.getPersonnelByList(map);
	}

	@Override
	public Map<String, Object> getPersonnelByDept(String deptCode, String name, String IDcard, Pageable pageable) {
		Map<String, Object> result = new HashMap<>();
		//获取本级及下级部门id
		JGKB collDepartment = collDepartmentMapper.checkDepartmentById(deptCode);
		List<JGKB> list = collTableDataService.checkCollDepartmentTree(collDepartment);
		List<String> idList = new ArrayList<>();
		list.forEach(l->{
			idList.add(l.getId());
		});
		String[] ids = idList.toArray(new String[idList.size()]);
		List<RYKB> personnelByDept = collPersonnelMapper.getPersonnelByDept(ids, name, IDcard);
		//分页
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		PageInfo<RYKB> pageInfo = new PageInfo<>(personnelByDept);
		result.put("data", pageInfo);
		//表头
		List<Map<String, Object>> tableHead = new ArrayList<>();
		for (PersonEnum personEnum : PersonEnum.values()) {
			HashMap<String, Object> map = new HashMap<>(4);
			map.put("name", personEnum.name);
			map.put("code", personEnum.code);
			tableHead.add(map);
		}
		result.put("name", tableHead);
		return result;
	}

	@Override
	public Map<String, Object> getJgByDept(String deptCode, String name, String IDcard, Pageable pageable) {
		//获取本级及下级部门id
		Map<String, Object> result = new HashMap<>();
		List<JGKB> jgkbs = collDepartmentMapper.checkCollDepartmentList(null);
		//分页
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		PageInfo<JGKB> pageInfo = new PageInfo<>(jgkbs);
		result.put("data", pageInfo);
		//表头
		List<Map<String, Object>> tableHead = new ArrayList<>();
		for (DepartEnum departEnum : DepartEnum.values()) {
			HashMap<String, Object> map = new HashMap<>(4);
			map.put("name", departEnum.name);
			map.put("code", departEnum.code);
			tableHead.add(map);
		}
		result.put("name", tableHead);
		return result;
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
