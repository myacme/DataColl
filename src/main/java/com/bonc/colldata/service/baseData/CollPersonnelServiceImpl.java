package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollBasicPersonnelConfig;
import com.bonc.colldata.entity.CollPersonnelMaintain;
import com.bonc.colldata.mapper.baseData.CollPersonnelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
	public List<CollPersonnelMaintain> getPersonnelByList(Map<String, Object> map) {
		return collPersonnelMapper.getPersonnelByList(map);
	}

	@Override
	public int updatePersonnel(CollPersonnelMaintain collPersonnelMaintain) {
		return collPersonnelMapper.updatePersonnel(collPersonnelMaintain);
	}

	@Override
	public int deletePersonnelById(String id) {
		return collPersonnelMapper.deletePersonnelById(id);
	}

	@Override
	public List<Map<String, Object>> getTableDesc() {
		return collPersonnelMapper.getTableDesc();
	}

}
