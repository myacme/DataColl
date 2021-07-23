package com.bonc.colldata.service.impl;

import com.bonc.colldata.entity.UserRole;
import com.bonc.colldata.mapper.UserRoleDao;
import com.bonc.colldata.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:15:26
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleDao userRoleDao;
	@Override
	public List<UserRole> getRoleList(String roleId, String roleName, String state) {
		return userRoleDao.getRoleList(roleId,roleName,state);
	}

	@Override
	public int addRole(UserRole userRole) {
		return userRoleDao.addRole(userRole);
	}

	@Override
	public int updateState(List<String> list, String state) {
		Map<String,Object> map=new HashMap<>();
		map.put("list",list);
		map.put("state",state);
		return userRoleDao.updateState(map);
	}
}
