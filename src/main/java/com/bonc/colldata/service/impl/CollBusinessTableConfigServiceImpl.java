package com.bonc.colldata.service.impl;

import com.alibaba.fastjson.JSON;
import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.mapper.CollBusinessTableConfigDao;
import com.bonc.colldata.service.CollBusinessTableConfigService;
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
 * (CollBusinessTableConfig)表服务实现类
 *
 * @author ljx
 * @since 2021-07-14 10:15:24
 */
@Service("collBusinessTableConfigService")
public class CollBusinessTableConfigServiceImpl implements CollBusinessTableConfigService {
    @Resource
    private CollBusinessTableConfigDao collBusinessTableConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param  id 主键
     * @return 实例对象
     */
    @Override
    public CollBusinessTableConfig queryById(String id ) {
        return this.collBusinessTableConfigDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param id 表清单id
     * @param pageable 分页
     * @return 对象列表
     */
    @Override
    public PageInfo<CollBusinessTableConfig> queryAllByLimit(String id, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<CollBusinessTableConfig> list = this.collBusinessTableConfigDao.queryAllByLimit(id);
        return new PageInfo<>(list);
    }

    /**
     * 新增数据
     *
     * @param collBusinessTableConfig 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(CollBusinessTableConfig collBusinessTableConfig) {
        return this.collBusinessTableConfigDao.insert(collBusinessTableConfig);
    }

    /**
     * 修改数据
     *
     * @param collBusinessTableConfig 实例对象
     * @return 实例对象
     */
    @Override
    public int update(CollBusinessTableConfig collBusinessTableConfig) {
        return this.collBusinessTableConfigDao.update(collBusinessTableConfig);
    }

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(List<Map<String, Object>> ids) {
        return this.collBusinessTableConfigDao.deleteById(ids);
    }


    /**
     * 模板下载
     *
     * @param response
     */
    @Override
    public void templateDownload(HttpServletResponse response){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("表字段名称", "table_config_name");
        map.put("表字段代码", "table_config_name_code");
        map.put("表字段类型", "table_config_type");
        map.put("表字段长度", "table_config_size");
        map.put("表字段精度", "table_config_precision");
        map.put("表字段刻度", "table_config_calibration");
        map.put("表字段是否可为空（0/1）", "table_config_ifnull");
        map.put("注释", "remarks");
        Workbook wb = ExcelUtil.generateXSLX(null, map, null);
        try (OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("表清单配置项导入模板.xlsx", "utf-8"));
            wb.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量导入
     *
     * @param file excle
     * @param id 表id
     */
    @Override
    public int batchImport(MultipartFile file, String id){
        List<CollBusinessTableConfig> datalist = new ArrayList<>();
        List<Map<String, String>> list = ExcelUtil.readExcleOfCommon(FileUtil.toFile(file));
        if (list != null){
            list.forEach(map -> {
                CollBusinessTableConfig bean = JSON.parseObject(JSON.toJSONString(map), CollBusinessTableConfig.class);
                bean.setTableConfigCode(CommonUtil.getUUID32());
                bean.setTableConfigTableCode(id);
                datalist.add(bean);
            });
            return collBusinessTableConfigDao.insertList(datalist);
        }else {
            return 0;
        }
    }
}