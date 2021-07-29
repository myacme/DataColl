package com.bonc.colldata.controller;

import com.alibaba.fastjson.JSONObject;
import com.bonc.base.RestRecord;
import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.entity.CollBusinessTableType;
import com.bonc.colldata.service.CollBusinessTableConfigService;
import com.bonc.colldata.service.CollBusinessTableTypeService;
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
 * (CollBusinessTableType)表控制层
 *
 * @author ljx
 * @since 2021-07-13 16:57:12
 */
@Api(tags = "表清单")
@RestController
@RequestMapping("/table")
public class CollBusinessTableController {
	/**
	 * 服务对象
	 */
	@Resource
	private CollBusinessTableTypeService collBusinessTableTypeService;
	@Resource
	private CollBusinessTableConfigService collBusinessTableConfigService;

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("查询表清单")
	@RequestMapping(method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "表清单id", required = true),
	})
	public Object selectOne(String id) {
		CollBusinessTableType collBusinessTableType = collBusinessTableTypeService.queryById(id);
		return new RestRecord(200, "成功", collBusinessTableType);
	}

	/**
	 * 查询所有数据
	 *
	 * @param typeCode 业务类型编码
	 * @param name     表清单名称
	 * @param code     表清单编码
	 * @return 单条数据
	 */
	@ApiOperation("表清单列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "typeCode", value = "业务类型id", required = true),
			@ApiImplicitParam(name = "name", value = "表清单名称，搜索使用"),
			@ApiImplicitParam(name = "code", value = "表清单编码，搜索使用"),
	})
	public Object query(String typeCode, String name, String code) {
		List<CollBusinessTableType> pageInfo = collBusinessTableTypeService.queryAllByLimit(typeCode, name, code);
		return new RestRecord(200, "成功", pageInfo);
	}

	/**
	 * 增加表清单
	 *
	 * @param collBusinessTableType 实体类
	 * @return 状态码
	 */
	@ApiOperation("增加表清单")
	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestBody CollBusinessTableType collBusinessTableType) {
		collBusinessTableType.setBusinessTypeTableCode(CommonUtil.getUUID20());
		int result = collBusinessTableTypeService.insert(collBusinessTableType);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result>0?collBusinessTableType:null);
	}

	/**
	 * 修改表清单
	 *
	 * @param collBusinessTableType 实体类
	 * @return 状态码
	 */
	@ApiOperation("修改表清单")
	@RequestMapping(method = RequestMethod.PUT)
	public Object update(@RequestBody CollBusinessTableType collBusinessTableType) {
		int result = collBusinessTableTypeService.update(collBusinessTableType);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}

	/**
	 * 删除表清单
	 *
	 * @param id 主键
	 * @return 状态码
	 */
	@ApiOperation("删除表清单")
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "表清单id", required = true),
	})
	public Object delete(@RequestBody String id) {
		id = JSONObject.parseObject(id).getString("id");
		int result = collBusinessTableTypeService.deleteById(id);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}


	/**
	 * 查询表清单配置项
	 *
	 * @param id       表清单id
	 * @param pageable 分页参数
	 * @return 列表
	 */
	@ApiOperation("表清单配置项列表")
	@RequestMapping(value = "/config/list", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "表清单id", required = true),
	})
	public Object queryTableData(String id, Pageable pageable) {
		PageInfo<CollBusinessTableConfig> pageInfo = collBusinessTableConfigService.queryAllByLimit(id, pageable);
		return new RestRecord(200, "成功", pageInfo);
	}

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("查询表清单配置项")
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "表清单配置项id", required = true),
	})
	public Object selectOneTableData(String id) {
		CollBusinessTableConfig collBusinessTableConfig = collBusinessTableConfigService.queryById(id);
		return new RestRecord(200, "成功", collBusinessTableConfig);
	}

	/**
	 * 新增表清单配置
	 *
	 * @param collBusinessTableConfig 实体类
	 * @return 列表
	 */
	@ApiOperation("增加表清单配置项")
	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public Object createTableData(@RequestBody CollBusinessTableConfig collBusinessTableConfig) {
		collBusinessTableConfig.setTableConfigCode(CommonUtil.getUUID20());
		int result = collBusinessTableConfigService.insert(collBusinessTableConfig);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}

	/**
	 * 修改表清单配置
	 *
	 * @param collBusinessTableConfig 实体类
	 * @return 列表
	 */
	@ApiOperation("修改表清单配置项")
	@RequestMapping(value = "/config", method = RequestMethod.PUT)
	public Object updataTableData(@RequestBody CollBusinessTableConfig collBusinessTableConfig) {
		int result = collBusinessTableConfigService.update(collBusinessTableConfig);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}

	/**
	 * 删除表清单配置
	 *
	 * @param ids id列表
	 * @return 列表
	 */
	@ApiOperation("删除表清单配置项")
	@RequestMapping(value = "/data", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "表清单配置项id列表", required = true),
	})
	public Object deleteTableData(@RequestBody List<Map<String, Object>> ids) {
		int result = collBusinessTableConfigService.deleteById(ids);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}

	/**
	 * 模板下载
	 *
	 */
	@ApiOperation("模板下载")
	@RequestMapping(value = "templateDownload", method = RequestMethod.GET)
	public void templateDownload(HttpServletResponse response) {
		collBusinessTableConfigService.templateDownload(response);
	}

	/**
	 * 批量导入
	 *
	 */
	@ApiOperation("批量导入")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "表清单id", required = true),
	})
	public Object batchImport(@RequestBody MultipartFile file, @RequestParam String id) {
		int result = collBusinessTableConfigService.batchImport(file,id);
		return new RestRecord(result>0?200:400, result>0?"成功":"失败", result);
	}



}