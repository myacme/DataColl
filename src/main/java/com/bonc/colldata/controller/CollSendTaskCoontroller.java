package com.bonc.colldata.controller;

import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.CollDataDictValue;
import com.bonc.colldata.entity.CollReceiveTask;
import com.bonc.colldata.service.impl.CollSendTaskServiceImpl;
import com.bonc.utils.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/14
 * Time:10:13
 * todo:下发任务管理类
 */
@RestController
@RequestMapping(value = "/sendTask")
@Api(tags = "下发任务管理")
public class CollSendTaskCoontroller {
	@Resource
	private CollSendTaskServiceImpl collSendTaskService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@ApiOperation("下发任务列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageSize",value = "分页大小",dataType = "int",required = true),
			@ApiImplicitParam(name = "pageNum",value = "当前页码",dataType = "int",required = true),
	})
	public Object sendTaskList(int pageSize,int pageNum){
		PageHelper.startPage(pageNum,pageSize);
		List<CollReceiveTask> list=collSendTaskService.getSendTaskList(pageSize,pageNum);
		PageInfo pageInfo=new PageInfo(list);
		return new RestRecord(200,"查询成功",pageInfo);
	}
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ApiOperation("新增下发任务")
	public Object addSendTask(@RequestBody CollReceiveTask collReceiveTask){
		String result=collSendTaskService.addSendTask(collReceiveTask);
		return new RestRecord(200,"新增成功",result);
	}
	@RequestMapping(value = "/getVersion",method = RequestMethod.GET)
	@ApiOperation("获取下发任务版本号")
	public Object getSendVersion(){
		String version=TimeUtil.getVersion();
		return  new RestRecord(200,"版本号",version);
	}
	@RequestMapping(value = "/scan",method = RequestMethod.GET)
	@ApiOperation("pdf预览")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isOnline",value = "true为预览，false为下载",required = true),
			@ApiImplicitParam(name = "sendTaskCode",value = "下发任务编号",required = true),
	})
	public void scanPdf( HttpServletResponse response, boolean isOnLine, String sendTaskCode) throws IOException{
		collSendTaskService.scanPdf(response,isOnLine,sendTaskCode);
	}
	@RequestMapping(value = "/collType",method = RequestMethod.GET)
	@ApiOperation("查询采集类型")
	public Object getCollType(){
		List<CollDataDictValue> list=collSendTaskService.getCollType();
		return new RestRecord(200,"查询成功",list);
	}

	@RequestMapping(value = "/getBeforeVersion",method = RequestMethod.GET)
	@ApiOperation("参考数据")
	public Object getVersion(String departmentCode){
		List<Map<String,Object>> list=collSendTaskService.getBeforeVersion(departmentCode);
		return new RestRecord(200,"查询成功",list);
	}
	@RequestMapping(value = "/getExcelTemplate",method = RequestMethod.GET)
	@ApiOperation("生成excl")
	public  void getExcelTemplate(HttpServletResponse response,String sendTaskCode){

		collSendTaskService.getExcelTemplate(response,sendTaskCode,false,"send");
	}
	@RequestMapping(value = "/getTaskZip",method = RequestMethod.GET)
	@ApiOperation("生成压缩包")
	public void getSendTaskZip(HttpServletResponse response,String sendTaskCode){
		collSendTaskService.getExcelTemplate(response,sendTaskCode,true,"send");
	}
	@RequestMapping(value = "/getExport",method = RequestMethod.GET)
	@ApiOperation("导出")
	public void exportZip(HttpServletResponse response,@RequestParam List<String> sendTaskCode){
		collSendTaskService.getZipMore(response,sendTaskCode);
	}

	@RequestMapping(value = "/downloadZip",method = RequestMethod.GET)
	@ApiOperation("下载zip")
	public Object downloadZip(String ids){
		collSendTaskService.downloadZip(ids);
		return new RestRecord(200,"成功","");
	}
}
