package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.UserManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:10:08
 * todo:用户管理
 */
@Mapper
public interface UserManagerDao {
	/****
	 * todo:查询用户列表
	 * @param userName
	 * @param name
	 * @param phone
	 * @param state
	 * @return
	 */
	List<UserManager> checkList(@Param("userName") String userName, @Param("name") String name, @Param("phone") String phone, @Param("state") String state);

	/***
	 * todo:查询用户详情
	 * @param userId
	 * @return
	 */
	UserManager checkUserById(@Param("userId") String userId);

	UserManager checkUserByName(@Param("userName") String userName);

	/****
	 * todo: 修改用户
	 * @param userManager
	 * @return
	 */
	int updateUser(UserManager userManager);

	/***
	 * todo:禁用或启用账号
	 * @param map

	 * @return
	 */
	int checkState(Map<String, Object> map);

	/***
	 * todo:修改密码
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	int updatePassword(@Param("userId") String userId, @Param("newPassword") String newPassword, @Param("passwordEncode") String passwordEncode);

	/***
	 * todo:新增用户
	 * @param userManager
	 * @return
	 */
	int addUserManager(UserManager userManager);
}
