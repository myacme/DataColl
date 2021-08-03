package com.bonc.colldata.controller;

import com.bonc.base.RestRecord;
import com.bonc.colldata.service.CollReceiveTaskService;
import com.bonc.colldata.service.impl.CollSendTaskServiceImpl;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 收到的任务<br>
 * 〈〉
 *
 * @author ljx
 * @create 2021/7/8
 * @since 1.0.0
 */

@Api(tags = "收到的任务")
@RequestMapping("/receiveTask")
@RestController
public class CollReceiveTaskController {
	/**
	 * 服务对象
	 */
	@Resource
	private CollReceiveTaskService collReceiveTaskService;
	@Resource
	private CollSendTaskServiceImpl collSendTaskService;

	/**
	 * 接收任务，解压压缩包
	 *
	 * @param multipartFile 压缩文件
	 * @return ResponseEntity
	 */
	@ApiOperation("接收任务")
	@RequestMapping(method = RequestMethod.POST)
	public Object create(@Validated @RequestParam MultipartFile multipartFile) {
		//插入数据
		collReceiveTaskService.insert(multipartFile);
		return new RestRecord(200, "成功", null);
	}

	/**
	 * 分页查询收到的任务
	 *
	 * @param pageable 参数
	 * @return ResponseEntity
	 */
	@ApiOperation("收到任务列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object query(Pageable pageable) {
		PageInfo<Map<String, Object>> pageInfo = collReceiveTaskService.queryAllByLimit(pageable);
		return new RestRecord(200, "成功", pageInfo);
	}

	/**
	 * 分页查询收到的任务
	 *
	 * @param id 任务id
	 * @return ResponseEntity
	 */
	@ApiOperation("查看通知")
	@RequestMapping(value = "/viewNotification", method = RequestMethod.GET)
	public ResponseEntity<Object> queryOne(@RequestParam String id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation("下载模板")
	@RequestMapping(value = "/getTemplate", method = RequestMethod.GET)
	public void getTemplate(HttpServletResponse response, String sendTaskCode) {
		collSendTaskService.getExcelTemplate(response, sendTaskCode, "receive");
	}
}