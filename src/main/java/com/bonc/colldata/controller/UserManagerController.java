package com.bonc.colldata.controller;

import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.UserManager;
import com.bonc.colldata.service.impl.UserManagerServiceImpl;
import com.bonc.utils.PasswordUtil;
import com.bonc.utils.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/19
 * Time:11:19
 * todo:用户管理
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping(value = "/userManger")
@RequiredArgsConstructor
public class UserManagerController {
	private final UserManagerServiceImpl userManagerService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation("新增用户")
	public Object addUserManager(@RequestBody UserManager userManager) {
		int result = userManagerService.addUserManager(userManager);
		return new RestRecord(200, "新增成功", result);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation("列表查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "int", paramType = "query", required = true),
			@ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "电话", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "state", value = "状态", dataType = "String", paramType = "query"),
	})
	public Object checkList(int pageSize, int pageNum, String userName, String name, String phone, String state) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserManager> list = userManagerService.checkList(userName, name, phone, state);
		PageInfo pageInfo = new PageInfo(list);
		return new RestRecord(200, "查询成功", pageInfo);
	}

	@RequestMapping(value = "/checkInfo", method = RequestMethod.GET)
	@ApiOperation("查询用户详情")
	public Object checkUserById(String userId) {
		UserManager userManager = userManagerService.checkUserById(userId);
		return new RestRecord(200, "查询成功", userManager);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation("修改用户")
	public Object updateUser(@RequestBody UserManager userManager) {
		int result = userManagerService.updateUser(userManager);
		return new RestRecord(200, "修改成功", result);
	}

	@RequestMapping(value = "/checkState", method = RequestMethod.GET)
	@ApiOperation("禁用或者启用状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "list", value = "用户id列表", dataType = "List", allowMultiple = true, paramType = "query", required = true),
			@ApiImplicitParam(name = "state", value = "状态", dataType = "String", paramType = "query", required = true)
	})
	public Object checkState(@RequestParam(value = "list") List<String> list, String state) {
		int result = userManagerService.checkState(list, state);
		return new RestRecord(200, "修改状态成功", result);
	}

	@RequestMapping(value = "/updatePd", method = RequestMethod.GET)
	@ApiOperation("口令生成")
	public Object updatePassword(String userId) {
		//默认生成密码
		//String code="Bonc_qA*9"; e
		String code = PasswordUtil.createPassWord(8);
		int result = userManagerService.updatePassword(userId, code);
		return new RestRecord(200, "修改密码成功", result);
	}

	@ApiOperation("获取用户编码")
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public Object getRoleCode() {
		String code = TimeUtil.getCode();
		return new RestRecord(200, "查询成功", "user_" + code);
	}
}
