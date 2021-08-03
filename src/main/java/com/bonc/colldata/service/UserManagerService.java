package com.bonc.colldata.service;

import com.bonc.colldata.entity.UserManager;

import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:11:05
 */
public interface UserManagerService {

	List<UserManager> checkList(String userName, String name, String phone, String state);


	UserManager checkUserById(String userId);

	UserManager checkUserByName(String userName);

	int updateUser(UserManager userManager);


	int checkState(List<String> userIdList, String state);


	int updatePassword(String userId, String newPassword);


	int addUserManager(UserManager userManager);
}
