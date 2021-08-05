package com.bonc.colldata.controller;

import com.alibaba.fastjson.JSONObject;
import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.entity.CollTableData;
import com.bonc.colldata.service.CollTableDataService;
import com.bonc.colldata.service.baseData.CollPersonnelService;
import com.bonc.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CollTableData)表控制层
 *
 * @author ljx
 * @since 2021-07-14 11:25:09
 */
@Api(tags = "数据上报")
@RestController
@RequestMapping("rport")
public class CollDataReportController {
	/**
	 * 服务对象
	 */
	@Resource
	private CollTableDataService collTableDataService;
	@Resource
	private CollPersonnelService collPersonnelService;


	@ApiOperation("导入数据")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", value = "版本", required = true),
			@ApiImplicitParam(name = "rportType", value = "数据类型（本级数据：1，下级数据：0）", required = true),
	})
	public Object inputZip(@RequestBody MultipartFile file, @RequestParam String version, @RequestParam String rportType) {
		Map<String, Object> map = collTableDataService.inputZip(file, version, rportType);
		return new RestRecord(200, "成功", map);
	}

	@ApiOperation("查询数据表数据")
	@RequestMapping(value = "data/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableCode", value = "表id", required = true),
			@ApiImplicitParam(name = "version", value = "版本", required = true),
			@ApiImplicitParam(name = "deptCode", value = "部门", required = true),
	})
	public Object query(String tableCode, String version, String deptCode, Pageable pageable) {
		HashMap<String, Object> dataMap = collTableDataService.queryAllByLimit(tableCode, version, deptCode, pageable);
		return new RestRecord(200, "成功", dataMap);
	}

	@ApiOperation("查询数据表")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", value = "版本", required = true),
	})
	public Object query(String version) {
		List<CollBusinessTableType> tableList = collTableDataService.getTableList(version);
		return new RestRecord(200, "成功", tableList);
	}

	@ApiOperation("导出数据")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", value = "版本", required = true),
	})
	public void downLoadDataZip(HttpServletResponse response, String version) {
		collTableDataService.rportDataZip(response, version);
	}

	@ApiOperation("查询上报版本列表(v开头的版本)")
	@RequestMapping(value = "version", method = RequestMethod.GET)
	@ResponseBody
	public Object queryVersion() {
		List<Map<String, String>> maps = collTableDataService.queryVersion();
		return new RestRecord(200, "成功", maps);
	}

	@ApiOperation("查询上报版本列表(a开头的版本)")
	@RequestMapping(value = "versionOfA", method = RequestMethod.GET)
	@ResponseBody
	public Object queryVersionOfA() {
		List<Map<String, String>> maps = collTableDataService.queryVersionOfA();
		return new RestRecord(200, "成功", maps);
	}

	@ApiOperation("查看数据")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableCode", value = "表id", required = true),
			@ApiImplicitParam(name = "dataCode", value = "数据id", required = true),
			@ApiImplicitParam(name = "version", value = "版本", required = true),
	})
	public Object selectOne(String tableCode, String dataCode, String version) {
		List<Map<String, Object>> maps = collTableDataService.selectOne(tableCode, dataCode, version);
		return new RestRecord(200, "成功", maps);
	}

	@ApiOperation("新增数据")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param", value = "{\"tableCode\":\"表id\",\"deptCode\":\"部门id\",\"version\":\"版本\",\"data\":{\"\":\"\"}}", required = true),
	})
	public Object create(@RequestBody String param) {
		JSONObject paramObject = JSONObject.parseObject(param);
		String tableCode = paramObject.getString("tableCode");
		String version = paramObject.getString("version");
		String deptCode = paramObject.getString("deptCode");
		if ("".equals(version)) {
			version = CommonUtil.getVersionCode();
		}
		Map<String, Object> data = paramObject.getJSONObject("data");
		List<CollTableData> tableDataList = new ArrayList<>();
		String id = CommonUtil.getUUID20();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			CollTableData bean = new CollTableData();
			bean.setBusinessTypeCode("");
			bean.setCreateTime(CommonUtil.getNowTime());
			bean.setDataCode(id);
			bean.setDataValue(entry.getValue().toString());
			bean.setDepartmentCode(deptCode);
			bean.setTableBusinessCode(tableCode);
			bean.setTableConfigCode(entry.getKey());
			bean.setVersion(version);
			bean.setThisUpdate("0");
			tableDataList.add(bean);
		}
		int result = collTableDataService.insertList(tableDataList);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", version);
	}

	@ApiOperation("修改数据")
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param", value = "{\"dataCode\":\"数据id\",\"data\":{\"\":\"\"}}", required = true),
	})
	@Transactional
	public Object updata(@RequestBody String param) {
		JSONObject paramObject = JSONObject.parseObject(param);
		String dataCode = paramObject.getString("dataCode");
		Map<String, Object> data = paramObject.getJSONObject("data");
		int result = 0;
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			CollTableData bean = new CollTableData();
			bean.setDataCode(dataCode);
			bean.setDataValue(entry.getValue().toString());
			bean.setTableConfigCode(entry.getKey());
			result += collTableDataService.update(bean);
		}
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	@ApiOperation("删除数据")
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "数据id列表", required = true),
	})
	public Object delete(@RequestBody List<Map<String, Object>> ids) {
		int result = collTableDataService.delete(ids);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	@ApiOperation("查询人员表数据")
	@RequestMapping(value = "ry/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "deptCode", value = "部门", required = true),
			@ApiImplicitParam(name = "name", value = "姓名（搜索条件）"),
			@ApiImplicitParam(name = "IDcard", value = "身份证（搜索条件）"),
	})
	public Object dataSource(String deptCode, String name, String IDcard, Pageable pageable) {
		Map<String, Object> list = collPersonnelService.getPersonnelByDept(deptCode, name, IDcard,pageable);
		return new RestRecord(200, "成功", list);
	}
	@ApiOperation("查询机构表数据")
	@RequestMapping(value = "jg/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "deptCode", value = "部门"),
			@ApiImplicitParam(name = "name", value = "姓名（搜索条件）"),
			@ApiImplicitParam(name = "IDcard", value = "身份证（搜索条件）"),
	})
	public Object dataSourceOfJg(String deptCode, String name, String IDcard, Pageable pageable) {
		Map<String, Object> list = collPersonnelService.getJgByDept(deptCode, name, IDcard,pageable);
		return new RestRecord(200, "成功", list);
	}
}