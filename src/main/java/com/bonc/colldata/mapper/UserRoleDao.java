package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:14:43
 */
@Mapper
public interface UserRoleDao {
	/***
	 * todo:角色列表
	 * @param roleId
	 * @param roleName
	 * @param state
	 * @return
	 */
	List<UserRole> getRoleList(@Param("roleId") String roleId, @Param("roleName") String roleName, @Param("state") String state);

	/***
	 * todo:新增用户
	 * @param userRole
	 * @return
	 */
	int addRole(UserRole userRole);

	/***
	 * todo:修改状态
	 * @param map
	 * @return
	 */
	int updateState(Map<String, Object> map);
}
