package com.bonc.colldata.controller;

import com.alibaba.fastjson.JSONObject;
import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.CollDataDictType;
import com.bonc.colldata.entity.CollDataDictValue;
import com.bonc.colldata.service.CollDataDictTypeService;
import com.bonc.colldata.service.CollDataDictValueService;
import com.bonc.utils.CommonUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * (CollDataDictType)表控制层
 *
 * @author ljx
 * @since 2021-07-13 10:08:07
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/dict")
public class CollDataDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private CollDataDictTypeService collDataDictTypeService;
	@Resource
	private CollDataDictValueService collDataDictValueService;

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("查询字典")
	@RequestMapping(method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "字典id", required = true),
	})
	public Object selectOne(String id) {
		CollDataDictType collDataDictType = collDataDictTypeService.queryById(id);
		return new RestRecord(200, "成功", collDataDictType);
	}

	/**
	 * 查询所有数据
	 *
	 * @param name  字典名称
	 * @param state 状态
	 * @return 单条数据
	 */
	@ApiOperation("字典列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "字典名称，搜索参数"),
			@ApiImplicitParam(name = "state", value = "状态，搜索参数"),
	})
	public Object query(String name, String state) {
		List<CollDataDictType> result = collDataDictTypeService.queryAllByLimit(name, state);
		return new RestRecord(200, "查询成功", result);
	}

	/**
	 * 增加字典
	 *
	 * @param collDataDictType 实体类
	 * @return 状态码
	 */
	@ApiOperation("增加字典")
	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestBody CollDataDictType collDataDictType) {
		collDataDictType.setDictCode(CommonUtil.getUUID20());
		collDataDictType.setState("1");
		int result = collDataDictTypeService.insert(collDataDictType);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 修改字典
	 *
	 * @param collDataDictType 实体类
	 * @return 状态码
	 */
	@ApiOperation("修改字典")
	@RequestMapping(method = RequestMethod.PUT)
	public Object update(@RequestBody CollDataDictType collDataDictType) {
		int result = collDataDictTypeService.update(collDataDictType);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 删除字典
	 *
	 * @param id 主键
	 * @return 状态码
	 */
	@ApiOperation("删除字典")
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "字典id", required = true),
	})
	public Object delete(@RequestBody String id) {
		id = JSONObject.parseObject(id).getString("id");
		int result = collDataDictTypeService.deleteById(id);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 查询字典配置项
	 *
	 * @param id       字典id
	 * @param pageable 分页参数
	 * @return 列表
	 */
	@ApiOperation("字典配置项列表")
	@RequestMapping(value = "/data/list", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "字典id", required = true),
	})
	public Object queryDictData(String id, Pageable pageable) {
		PageInfo<CollDataDictValue> pageInfo = collDataDictValueService.queryAllByLimit(id, pageable);
		return new RestRecord(200, "成功", pageInfo);
	}

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("查询字典配置项")
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "字典配置项id", required = true),
	})
	public Object selectOneDictData(String id) {
		CollDataDictValue collDataDictValue = collDataDictValueService.queryById(id);
		return new RestRecord(200, "成功", collDataDictValue);
	}

	/**
	 * 新增字典配置
	 *
	 * @param collDataDictValue 实体类
	 * @return 列表
	 */
	@ApiOperation("增加字典配置项")
	@RequestMapping(value = "/data", method = RequestMethod.POST)
	public Object createDictData(@RequestBody CollDataDictValue collDataDictValue) {
		collDataDictValue.setCodeId(CommonUtil.getUUID20());
		collDataDictValue.setState("1");
		int result = collDataDictValueService.insert(collDataDictValue);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 修改字典配置
	 *
	 * @param collDataDictValue 实体类
	 * @return 列表
	 */
	@ApiOperation("修改字典配置项")
	@RequestMapping(value = "/data", method = RequestMethod.PUT)
	public Object updataDictData(@RequestBody CollDataDictValue collDataDictValue) {
		int result = collDataDictValueService.update(collDataDictValue);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 删除字典配置
	 *
	 * @param ids id列表
	 * @return 列表
	 */
	@ApiOperation("删除字典配置项")
	@RequestMapping(value = "/data", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "字典配置项id列表", required = true),
	})
	public Object deleteDictData(@RequestBody List<Map<String, Object>> ids) {
		int result = collDataDictValueService.deleteById(ids);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 启用字典配置
	 *
	 * @param ids id列表
	 * @return 列表
	 */
	@ApiOperation("启用字典配置项")
	@RequestMapping(value = "/data/use", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "字典配置项id列表", required = true),
	})
	public Object updataDictDataUse(@RequestBody List<Map<String, Object>> ids) {
		int result = collDataDictValueService.updateState(ids, "1");
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 禁用字典配置
	 *
	 * @param ids id列表
	 * @return 列表
	 */
	@ApiOperation("禁用字典配置项")
	@RequestMapping(value = "/data/disuse", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "字典配置项id列表", required = true),
	})
	public Object updataDictDataDisuse(@RequestBody List<Map<String, Object>> ids) {
		int result = collDataDictValueService.updateState(ids, "0");
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}

	/**
	 * 模板下载
	 */
	@ApiOperation("模板下载")
	@RequestMapping(value = "templateDownload", method = RequestMethod.GET)
	public void templateDownload(HttpServletResponse response) {
		collDataDictValueService.templateDownload(response);
	}

	/**
	 * 批量导入
	 */
	@ApiOperation("批量导入")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "字典id", required = true),
	})
	public Object batchImport(@RequestBody MultipartFile file, @RequestParam String id) {
		int result = collDataDictValueService.batchImport(file, id);
		return new RestRecord(result > 0 ? 200 : 400, result > 0 ? "成功" : "失败", result);
	}
}