package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollDepartment;
import com.bonc.colldata.entity.JGKB;
import com.bonc.colldata.entity.UserManager;
import com.bonc.colldata.mapper.baseData.CollDepartmentMapper;
import com.bonc.utils.SessionUtiil;
import com.bonc.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CollDepartmentServiceImpl implements CollDepartmentService {
	@Autowired
	private CollDepartmentMapper collDepartmentMapper;

	@Override
	public int addDepartment(JGKB collDepartment) {
		return collDepartmentMapper.addDepartment(collDepartment);
	}

	@Override
	public List<JGKB> checkCollDepartmentList(Map<String, Object> map) {
		return collDepartmentMapper.checkCollDepartmentList(map);
	}

	@Override
	public List<JGKB> checkCollDepartmentTree(Map<String, Object> map) {
		UserManager user = SessionUtiil.getUserInfo();
		//判断用户是否是超级管理员
		String isAdmin = user.getIsAdmin();
		if ("1".equals(isAdmin)) {
			return collDepartmentMapper.checkCollDepartmentTree(map);
		} else {
			//如果不是超级管理员那么只能看部门及以下的数据
			if (map == null) {
				map = new HashMap<>();
			}
			String deptId = user.getDeptId();
			JGKB jgkb = collDepartmentMapper.checkDepartmentById(deptId);
			//获取本级的父节点
			String parentCode = jgkb.getFdwid();
			map.put("pid", parentCode);
			return collDepartmentMapper.checkCollDepartmentTree(map);
		}

	}

	@Override
	public int updateDepartment(JGKB collDepartment) {
		return collDepartmentMapper.updateDepartment(collDepartment);
	}

	@Override
	public int deleteDepartment(String instiutionsId) {
		Map<String, Object> map = new HashMap<>();
		String pCode = StringUtils.isBlank(instiutionsId) == true ? "0" : instiutionsId;
		map.put("pid", pCode);
		List<JGKB> list = collDepartmentMapper.checkCollDepartmentTree(map);
		List<String> idList = this.getAllNode(list);
		idList.add(pCode);
		map.put("list", idList);
		return collDepartmentMapper.deleteDepartment(map);
	}

	@Override
	public JGKB checkDepartmentById(String id) {
		return collDepartmentMapper.checkDepartmentById(id);
	}

	public List<JGKB> treeToList(List<JGKB> list) {
		if (list != null && list.size() > 0) {
			//结果集
			List<JGKB> result = new ArrayList<>();
			for (JGKB collDepartment : list) {
				result.add(collDepartment);
				List<JGKB> childList = collDepartment.getList();
				if (childList != null && childList.size() > 0) {
					List<JGKB> listList = this.treeToList(childList);
					result.addAll(listList);
				}
			}
			if (result.size() > 0) {
				for (JGKB collDepartment : result) {
					collDepartment.setList(null);
				}
			}
			return result;
		} else {
			return null;
		}

	}

	public List<String> getAllNode(List<JGKB> list) {
		List<String> strList = new ArrayList<>();
		List<JGKB> result = this.treeToList(list);
		System.out.println(result);
		//获取该节点下的所有节点id(包括自己)
		if (result != null && result.size() > 0) {
			for (JGKB collDepartment : result) {
				strList.add(collDepartment.getId());
			}
		}
		return strList;
	}
}
