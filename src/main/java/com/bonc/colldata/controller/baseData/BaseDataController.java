package com.bonc.colldata.controller.baseData;

import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.*;
import com.bonc.colldata.service.baseData.CollDepartmentServiceImpl;
import com.bonc.colldata.service.baseData.CollPersonnelService;
import com.bonc.utils.CommonUtil;
import com.bonc.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:10:44
 * todo:基础数据控制类
 */
@RestController
@RequestMapping(value = "/baseData")
@Api(tags = "基础数据维护")
public class BaseDataController {
	@Resource
	private CollDepartmentServiceImpl collDepartmentService;
	@Resource
	private CollPersonnelService collPersonnelService;

	@ApiOperation("新增机构维护")
	@RequestMapping(value = "/depart/add", method = RequestMethod.POST)
	public Object addDepartment(@RequestBody JGKB collDepartment) {
		collDepartmentService.addDepartment(collDepartment);
		return new RestRecord(200, "新增成功", 1);
	}

	@ApiOperation("查询机构列表")
	@RequestMapping(value = "/depart/list", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "机构ID", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "jgmc", value = "机构名称", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pid", value = "节点编号", dataType = "String", paramType = "query")
	})
	public Object departmentList(String id, String jgmc,  String pid, int pageSize, int pageNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("pid", pid);
		map.put("id", id);
		map.put("jgmc", jgmc);
		PageHelper.startPage(pageNum, pageSize);
		List<JGKB> result = collDepartmentService.checkCollDepartmentList(map);
		PageInfo pageInfo = new PageInfo(result);
		return new RestRecord(200, "查询成功", pageInfo);
	}

	@ApiOperation("查询机构树")
	@RequestMapping(value = "/depart/tree", method = RequestMethod.POST)
	public Object departmentTree() {
		Map<String, Object> map = new HashMap<>();
		map.put("pid","0");
		List<JGKB> list = collDepartmentService.checkCollDepartmentTree(map);
		return new RestRecord(200, "查询成功", list);
	}

	@ApiOperation("查询机构详情")
	@RequestMapping(value = "/depart/detail", method = RequestMethod.GET)
	public Object departmentDetail(String id) {
		JGKB collDepartment = collDepartmentService.checkDepartmentById(id);
		return new RestRecord(200, "查询成功", collDepartment);
	}

	@ApiOperation("修改机构")
	@RequestMapping(value = "/depart/update", method = RequestMethod.POST)
	public Object updateDepartment(@RequestBody JGKB collDepartment) {
		int result = collDepartmentService.updateDepartment(collDepartment);
		return new RestRecord(200, "修改成功", result);
	}

	@ApiOperation("删除机构")
	@RequestMapping(value = "/depart/delete", method = RequestMethod.GET)
	public Object deleteDepartment(String instiutionsId) {
		int result = collDepartmentService.deleteDepartment(instiutionsId);
		return new RestRecord(200, "删除成功", result);
	}

	/********************************************************
	 *                    人员                              *
	 *                    数据                              *
	 *                    维护                              *
	 *                                                      *
	 ********************************************************/


	@ApiOperation("新增人员数据")
	@RequestMapping(value = "/personnel/add", method = RequestMethod.POST)
	public Object addPersonnel(@RequestBody RYKB rykb) {
		//获取数据id
		System.out.println();
		String uuid = CommonUtil.getUUID20();
		rykb.setId(uuid);
		collPersonnelService.addPersonnelData(rykb);
		return new RestRecord(200, "查询成功", rykb);
	}

	@ApiOperation("查询人员数据详情")
	@RequestMapping(value = "/personnel/check", method = RequestMethod.GET)
	public Object checkPersonnel(String id) {
		//获取数据id
		RYKB collPersonnelMaintain = collPersonnelService.checkById(id);
		return new RestRecord(200, "查询成功", collPersonnelMaintain);
	}

	@ApiOperation("修改人员数据详情")
	@RequestMapping(value = "/personnel/update", method = RequestMethod.POST)
	public Object updatePersonnel(@RequestBody RYKB collPersonnelMaintain) {
		//获取数据id
		collPersonnelService.updatePersonnel(collPersonnelMaintain);
		return new RestRecord(200, "查询成功", collPersonnelMaintain);
	}

	@ApiOperation("删除人员数据详情")
	@RequestMapping(value = "/personnel/delete", method = RequestMethod.GET)
	public Object deletePersonnel(@RequestParam(value = "id") List<String> id) {

		//获取数据id
		collPersonnelService.deletePersonnelById(id);
		return new RestRecord(200, "查询成功", id);
	}

	@ApiOperation("人员数据列表")
	@RequestMapping(value = "/personnel/list", method = RequestMethod.POST)
	public Object listPersonnel(@RequestBody QueryList queryList) {
		PageHelper.startPage(queryList.getPageNum(), queryList.getPageSize());
		//获取数据id
		List<RYKB> list = collPersonnelService.getPersonnelByList(queryList.getXm(),queryList.getSzdwcjid());
		PageInfo pageInfo = new PageInfo(list);
		return new RestRecord(200, "查询成功", pageInfo);
	}

	@ApiOperation("获取系统名称")
	@RequestMapping(value = "/sysName", method = RequestMethod.GET)
	public Object getSystemName() {
		String systemName = collPersonnelService.getSystemName();
		return new RestRecord(200, "查询成功", systemName);
	}
}

