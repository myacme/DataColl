package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollBasicPersonnelConfig;
import com.bonc.colldata.entity.CollPersonnelMaintain;
import com.bonc.colldata.entity.QueryParam;
import com.bonc.colldata.mapper.baseData.CollPersonnelMapper;
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
		Map<String, Object> map=new HashMap<>();
		map.put("list",list);
		return collPersonnelMapper.getPersonnelByList(map);
	}

	@Override
	public int updatePersonnel(CollPersonnelMaintain collPersonnelMaintain) {
		return collPersonnelMapper.updatePersonnel(collPersonnelMaintain);
	}

	@Override
	public int deletePersonnelById(List<String> id) {
		Map<String,Object> map=new HashMap<>();
		map.put("list",id);
		return collPersonnelMapper.deletePersonnelById(map);
	}

	@Override
	public List<Map<String, Object>> getTableDesc() {
		return collPersonnelMapper.getTableDesc();
	}

}
