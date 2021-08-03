package com.bonc.colldata.service.impl;

import com.alibaba.fastjson.JSON;
import com.bonc.colldata.entity.CollDataDictValue;
import com.bonc.colldata.mapper.CollDataDictValueDao;
import com.bonc.colldata.service.CollDataDictValueService;
import com.bonc.utils.CommonUtil;
import com.bonc.utils.ExcelUtil;
import com.bonc.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * (CollDataDictValue)表服务实现类
 *
 * @author ljx
 * @since 2021-07-13 10:17:35
 */
@Service("collDataDictValueService")
public class CollDataDictValueServiceImpl implements CollDataDictValueService {
	@Resource
	private CollDataDictValueDao collDataDictValueDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param codeId 主键
	 * @return 实例对象
	 */
	@Override
	public CollDataDictValue queryById(String codeId) {
		return this.collDataDictValueDao.queryById(codeId);
	}

	/**
	 * 查询多条数据
	 *
	 * @param id id
	 * @return 对象列表
	 */
	@Override
	public PageInfo<CollDataDictValue> queryAllByLimit(String id, Pageable pageable) {
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
		return new PageInfo<>(collDataDictValueDao.queryAllByLimit(id));
	}

	/**
	 * 新增数据
	 *
	 * @param collDataDictValue 实例对象
	 * @return 实例对象
	 */
	@Override
	public int insert(CollDataDictValue collDataDictValue) {
		return this.collDataDictValueDao.insert(collDataDictValue);
	}

	/**
	 * 修改数据
	 *
	 * @param collDataDictValue 实例对象
	 * @return 实例对象
	 */
	@Override
	public int update(CollDataDictValue collDataDictValue) {
		return this.collDataDictValueDao.update(collDataDictValue);
	}

	/**
	 * 修改数据
	 *
	 * @param ids   实例对象
	 * @param state 状态
	 * @return 实例对象
	 */
	@Override
	public int updateState(List<Map<String, Object>> ids, String state) {
		return this.collDataDictValueDao.updateState(ids, state);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param ids 主键
	 * @return 是否成功
	 */
	@Override
	public int deleteById(List<Map<String, Object>> ids) {
		return this.collDataDictValueDao.deleteById(ids);
	}

	/**
	 * 模板下载
	 *
	 * @param response
	 */
	@Override
	public void templateDownload(HttpServletResponse response) {
		Map<String, String> map = new LinkedHashMap<>(4);
		map.put("code_name", "配置项名称");
		map.put("code_value", "配置项值");
		Workbook wb = ExcelUtil.generateXSLX(null, map, null);
		try (OutputStream outputStream = response.getOutputStream()) {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("字典配置项导入模板.xlsx", "utf-8"));
			wb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量导入
	 *
	 * @param file excle
	 * @param id   字典id
	 */
	@Override
	public int batchImport(MultipartFile file, String id) {
		List<CollDataDictValue> datalist = new ArrayList<>();
		List<Map<String, String>> list = ExcelUtil.readExcleOfCommon(FileUtil.toFile(file));
		if (list != null) {
			list.forEach(map -> {
				CollDataDictValue bean = JSON.parseObject(JSON.toJSONString(map), CollDataDictValue.class);
				bean.setCodeId(CommonUtil.getUUID20());
				bean.setCodeType(id);
				bean.setState("1");
				datalist.add(bean);
			});
			return collDataDictValueDao.insertList(datalist);
		} else {
			return 0;
		}
	}
}