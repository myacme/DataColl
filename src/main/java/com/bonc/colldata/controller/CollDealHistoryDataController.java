package com.bonc.colldata.controller;


import com.bonc.base.RestRecord;
import com.bonc.colldata.service.CollTableDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 〈基于历史数据维护〉
 *
 * @author ljx
 * @create 2021/7/16 9:33
 * @since 1.0.0
 */

@Api(tags = "基于历史数据维护")
@RequestMapping("history")
@RestController
public class CollDealHistoryDataController {
	/**
	 * 服务对象
	 */
	@Resource
	private CollTableDataService collTableDataService;

	@ApiOperation("导出数据")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "version", value = "版本", required = true),
			@ApiImplicitParam(name = "isTemplet", value = "是否仅模板，1：仅模板，0：数据", required = true),
	})
	public void downLoadDataZip(HttpServletResponse response, @RequestParam("version") String version, @RequestParam(value = "isTemplet", defaultValue = "0") String isTemplet) {
		collTableDataService.downloadDataZip(response, version, isTemplet);
	}


	@ApiOperation("查询历史版本列表")
	@RequestMapping(value = "version", method = RequestMethod.GET)
	@ResponseBody
	public Object queryVersion() {
		List<Map<String, String>> maps = collTableDataService.queryVersionOfHistory();
		return new RestRecord(200, "成功", maps);
	}

	@ApiOperation("复制数据")
	@RequestMapping(value = "copy", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "rportVerion", value = "上报版本", required = true),
			@ApiImplicitParam(name = "historyVersion", value = "历史版本", required = true),
	})
	public Object copyDataToNewVersion(String rportVerion, String historyVersion) {
		int result = collTableDataService.copyDataToNewVersion(rportVerion, historyVersion);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}
}