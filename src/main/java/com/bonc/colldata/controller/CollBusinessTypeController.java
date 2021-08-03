package com.bonc.colldata.controller;

import com.alibaba.fastjson.JSONObject;
import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.CollBusinessType;
import com.bonc.colldata.service.CollBusinessTypeService;
import com.bonc.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CollBusinessType)表控制层
 *
 * @author ljx
 * @since 2021-07-13 16:29:17
 */
@Api(tags = "业务类型")
@RestController
@RequestMapping("/business")
public class CollBusinessTypeController {
	/**
	 * 服务对象
	 */
	@Resource
	private CollBusinessTypeService collBusinessTypeService;

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("查询业务类型")
	@RequestMapping(method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "业务类型id", required = true),
	})
	public Object selectOne(String id) {
		CollBusinessType collBusinessType = collBusinessTypeService.queryById(id);
		return new RestRecord(200, "成功", collBusinessType);
	}

	/**
	 * 查询所有数据
	 *
	 * @return 单条数据
	 */
	@ApiOperation("业务类型列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object query() {
		List<CollBusinessType> list = collBusinessTypeService.queryAll();
		return new RestRecord(200, "成功", list);
	}

	/**
	 * 增加字典
	 *
	 * @param collBusinessType 实体类
	 * @return 状态码
	 */
	@ApiOperation("增加业务类型")
	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestBody CollBusinessType collBusinessType) {
		collBusinessType.setBusinessCode(CommonUtil.getUUID20());
		int insert = collBusinessTypeService.insert(collBusinessType);
		return new RestRecord(200, "成功", insert);
	}

	/**
	 * 修改字典
	 *
	 * @param collBusinessType 实体类
	 * @return 状态码
	 */
	@ApiOperation("修改业务类型")
	@RequestMapping(method = RequestMethod.PUT)
	public Object update(@RequestBody CollBusinessType collBusinessType) {
		int update = collBusinessTypeService.update(collBusinessType);
		return new RestRecord(200, "成功", update);
	}

	/**
	 * 删除字典
	 *
	 * @param id 主键
	 * @return 状态码
	 */
	@ApiOperation("删除业务类型")
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "业务类型id", required = true),
	})
	public Object delete(@RequestBody String id) {
		id = JSONObject.parseObject(id).getString("id");
		int result = collBusinessTypeService.deleteById(id);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}
}