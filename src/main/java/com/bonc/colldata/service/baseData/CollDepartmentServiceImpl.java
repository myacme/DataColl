package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollDepartment;
import com.bonc.colldata.mapper.baseData.CollDepartmentMapper;
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
	public int addDepartment(CollDepartment collDepartment) {
		return collDepartmentMapper.addDepartment(collDepartment);
	}

	@Override
	public List<CollDepartment> checkCollDepartmentList(Map<String, Object> map) {
		return collDepartmentMapper.checkCollDepartmentList(map);
	}

	@Override
	public List<CollDepartment> checkCollDepartmentTree(Map<String, Object> map) {

		return collDepartmentMapper.checkCollDepartmentTree(map);
	}

	@Override
	public int updateDepartment(CollDepartment collDepartment) {
		return collDepartmentMapper.updateDepartment(collDepartment);
	}

	@Override
	public int deleteDepartment(String instiutionsId) {
		Map<String,Object> map=new HashMap<>();
		String pCode = StringUtils.isBlank(instiutionsId) == true ? "0" : instiutionsId;
		map.put("pid", pCode);
		List<CollDepartment> list = collDepartmentMapper.checkCollDepartmentTree(map);
		List<String> idList = this.getAllNode(list);
		idList.add(pCode);
		map.put("list", idList);
		return collDepartmentMapper.deleteDepartment(map);
	}

	@Override
	public CollDepartment checkDepartmentById(String id) {
		return collDepartmentMapper.checkDepartmentById(id);
	}

	public List<CollDepartment> treeToList(List<CollDepartment> list) {
		if (list != null && list.size() > 0) {
			//结果集
			List<CollDepartment> result = new ArrayList<>();
			for (CollDepartment collDepartment : list) {
				result.add(collDepartment);
				List<CollDepartment> childList = collDepartment.getList();
				if (childList != null && childList.size() > 0) {
					List<CollDepartment> listList = this.treeToList(childList);
					result.addAll(listList);
				}
			}
			if (result.size() > 0) {
				for (CollDepartment collDepartment : result) {
					collDepartment.setList(null);
				}
			}
			return result;
		} else {
			return null;
		}

	}

	public List<String> getAllNode(List<CollDepartment> list){
		List<String> strList=new ArrayList<>();
		List<CollDepartment> result=this.treeToList(list);
		System.out.println(result);
		//获取该节点下的所有节点id(包括自己)
		if(result!=null&&result.size()>0){
			for(CollDepartment collDepartment:result){
				strList.add(collDepartment.getInstiutionsId());
			}
		}
		return strList;
	}
}
