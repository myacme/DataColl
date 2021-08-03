package com.bonc.colldata.controller;

import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.UserRole;
import com.bonc.colldata.service.UserRoleService;
import com.bonc.utils.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:15:28
 * todo:角色管理
 */
@RestController
@RequestMapping(value = "/userRole")
@Api(tags = "角色管理")
public class UserRoleController {
	@Resource
	private UserRoleService userRoleService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation("分页查询角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "roleName", value = "角色名", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "状态", dataType = "String", paramType = "query")
	})
	public Object getRoleList(String roleId, String roleName, String state, int pageSize, int pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserRole> list = userRoleService.getRoleList(roleId, roleName, state);
		PageInfo pageInfo = new PageInfo(list);
		return new RestRecord(200, "查询成功", pageInfo);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation("查询所有角色")
	public Object getRoleListAll() {
		List<UserRole> list = userRoleService.getRoleList(null, null, null);
		return new RestRecord(200, "查询成功", list);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation("角色新增")
	public Object addRole(@RequestBody UserRole userRole) {
		int result = userRoleService.addRole(userRole);
		return new RestRecord(200, "新增成功", result);
	}

	@RequestMapping(value = "/checkState", method = RequestMethod.GET)
	@ApiOperation("禁用或者启用状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "list", value = "角色id列表", dataType = "List", allowMultiple = true, paramType = "query", required = true),
			@ApiImplicitParam(name = "state", value = "状态", dataType = "String", paramType = "query", required = true)
	})
	public Object updateState(@RequestParam List<String> userIdList, String state) {
		int result = userRoleService.updateState(userIdList, state);
		return new RestRecord(200, "修改成功", result);
	}

	@ApiOperation("获取角色编码")
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public Object getRoleCode() {
		String code = TimeUtil.getCode();
		return new RestRecord(200, "查询成功", "role_" + code);
	}
}
