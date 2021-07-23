package com.bonc.colldata.service;

import com.bonc.colldata.entity.UserRole;

import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:11:05
 */
public interface UserRoleService {

	List<UserRole> getRoleList(String roleId, String roleName, String state);


	int addRole(UserRole userRole);

	int updateState(List<String> list, String state);
}
