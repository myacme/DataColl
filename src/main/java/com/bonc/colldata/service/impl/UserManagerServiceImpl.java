package com.bonc.colldata.service.impl;

import com.bonc.colldata.entity.UserManager;
import com.bonc.colldata.mapper.UserManagerDao;
import com.bonc.colldata.service.UserManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:11:07
 */
@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService {

	private final UserManagerDao userManagerDao;
	@Override
	public List<UserManager> checkList(String userName, String name, String phone, String state) {
		return userManagerDao.checkList(userName,name,phone,state);
	}

	@Override
	public UserManager checkUserById(String userId) {
		return userManagerDao.checkUserById(userId);
	}

	@Override
	public UserManager checkUserByName(String userName) {
		 return userManagerDao.checkUserByName(userName);
	}

	@Override
	public int updateUser(UserManager userManager) {

		return userManagerDao.updateUser(userManager);
	}

	@Override
	public int checkState(List<String> userIdList, String state) {
		Map<String,Object> map=new HashMap<>();
		map.put("state",state);
		map.put("list",userIdList);
		return userManagerDao.checkState(map);
	}

	@Override
	public int updatePassword(String userId, String newPassword) {
		String passwordEncode=newPassword;

		return userManagerDao.updatePassword(userId,newPassword,passwordEncode);
	}

	@Override
	public int addUserManager(UserManager userManager) {
		//默认明码bonc_1234
		String code="bonc_1234";
		userManager.setPasswordCode(code);
		userManager.setPassword(code);
		userManager.setEnabled("1");
		return userManagerDao.addUserManager(userManager);
	}
}
