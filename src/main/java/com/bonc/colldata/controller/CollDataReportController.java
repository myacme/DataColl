package com.bonc.colldata.controller;

import com.alibaba.fastjson.JSONObject;
import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.entity.CollTableData;
import com.bonc.colldata.service.CollTableDataService;
import com.bonc.utils.ExcelUtil;
import com.bonc.utils.CommonUtil;
import com.bonc.utils.FileUtil;
import com.bonc.utils.ZipUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.Instant;
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


	@ApiOperation("导入数据")

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", value = "版本", required = true),
	})
	public Object inputZip(@RequestBody MultipartFile file, @RequestParam String version) {
		List<CollTableData> tableDataList = new ArrayList<>();
		File[] files = ZipUtil.unzip(FileUtil.toFile(file), "123");
		int result = 0;
		if (files != null && files.length != 0) {
			for (File excle : files) {
				List<List<CollTableData>> lists = ExcelUtil.readExcle(excle);
				if (lists != null) {
					for (List<CollTableData> list : lists) {
						String id = CommonUtil.getUUID32();
						list.forEach(bean -> {
							bean.setDataCode(id);
							bean.setBusinessTypeCode("");
							bean.setDepartmentCode("");
							bean.setVersion(version);
							bean.setCreateTime(String.valueOf(Instant.now().toEpochMilli()));
						});
						tableDataList.addAll(list);
					}
				}
				excle.delete();
			}
			if (tableDataList.size() > 0) {
				result = collTableDataService.insertList(tableDataList);
			}
		}
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}

	@ApiOperation("查询数据表数据")

	@RequestMapping(value = "data/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tableCode", value = "表id", required = true),
			@ApiImplicitParam(name = "version", value = "版本", required = true),
	})
	public Object query(String tableCode, String version, Pageable pageable) {
		HashMap<String, Object> dataMap = collTableDataService.queryAllByLimit(tableCode, version, pageable);
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
	public void downLoadDataZip(HttpServletResponse response, @RequestParam("version") String version) {
		collTableDataService.downloadDataZip(response, version, "0");
	}

	@ApiOperation("查询上报版本列表")

	@RequestMapping(value = "version", method = RequestMethod.GET)
	@ResponseBody
	public Object queryVersion() {
		List<Map<String, String>> maps = collTableDataService.queryVersion();
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

	@ApiOperation("修改数据")

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param", value = "{\"tableCode\":\"表id\",\"dataCode\":\"数据id\",\"version\":\"版本\",\"data\":{\"\":\"\"}}", required = true),
	})
	public Object updata(@RequestBody String param) {

		JSONObject paramObject = JSONObject.parseObject(param);
		String tableCode = paramObject.getString("tableCode");
		String dataCode = paramObject.getString("dataCode");
		String version = paramObject.getString("version");
		Map<String, Object> data = paramObject.getJSONObject("data");

		List<CollTableData> tableDataList = new ArrayList<>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			CollTableData bean = new CollTableData();
			bean.setBusinessTypeCode("");
			bean.setCreateTime(String.valueOf(Instant.now().toEpochMilli()));
			bean.setDataCode(dataCode);
			bean.setDataValue(entry.getValue().toString());
			bean.setDepartmentCode("");
			bean.setTableBusinessCode(tableCode);
			bean.setTableConfigCode(entry.getKey());
			bean.setVersion(version);
			tableDataList.add(bean);
		}
		int result = collTableDataService.updateList(tableDataList);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}

	@ApiOperation("删除数据")

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param", value = "{\"tableCode\":\"表id\",\"dataCode\":\"数据id\",\"version\":\"版本\"}", required = true),
	})
	public Object delete(@RequestBody String param) {
		JSONObject paramObject = JSONObject.parseObject(param);
		String tableCode = paramObject.getString("tableCode");
		String dataCode = paramObject.getString("dataCode");
		String version = paramObject.getString("version");
		int result = collTableDataService.delete(tableCode, dataCode, version);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}
}