package com.bonc.colldata.mapper;

import com.bonc.colldata.entity.CollBusinessTableConfig;
import com.bonc.colldata.entity.CollTableData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CollTableData)表数据库访问层
 *
 * @author ljx
 * @since 2021-07-14 11:25:09
 */
@Mapper
public interface CollTableDataDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	CollTableData queryById(String id);


	/**
	 * 查询指定行数据
	 *
	 * @param tableCode 表清单id
	 * @param version   版本
	 * @return 对象列表
	 */
	List<CollTableData> queryAllByLimit(@Param("tableCode") String tableCode, @Param("version") String version);

	/**
	 * 查询数据
	 *
	 * @param list
	 * @param tableCode
	 * @param version
	 * @return
	 */
	List<Map<String, Object>> getDataList(@Param("list") List<CollBusinessTableConfig> list,
	                                      @Param("tableCode") String tableCode,
	                                      @Param("version") String version,
	                                      @Param("dataCode") String dataCode,
	                                      @Param("update") String update,
	                                      @Param("array") String[] array);


	/**
	 * 通过版本所有的数据
	 *
	 * @param verssion 实例对象
	 * @return 对象列表
	 */
	List<CollTableData> queryAll(@Param("version") String verssion);

	/**
	 * 通过版本所有的数据
	 *
	 * @param rportVersion
	 * @param historyVersion
	 * @return 对象列表String rportVersion, String historyVersion
	 */
	List<CollTableData> queryAllOfTowVersion(@Param("rportVersion") String rportVersion,@Param("historyVersion") String historyVersion);
	/**
	 * 通过版本所有的数据
	 *
	 * @param verssion 实例对象
	 * @return 对象列表
	 */
	List<Map<String,Object>> queryMap(@Param("version") String verssion);

	/**
	 * 新增数据
	 *
	 * @param collTableData 实例对象
	 * @return 影响行数
	 */
	int insert(CollTableData collTableData);

	/**
	 * 批量新增数据
	 *
	 * @param list 实例对象列表
	 * @return 实例对象
	 */
	int insertList(@Param("list") List<CollTableData> list);

	/**
	 * 批量修改数据
	 *
	 * @param list 实例对象列表
	 * @return 实例对象
	 */
	int updateList(@Param("list") List<CollTableData> list);

	/**
	 * 修改数据
	 *
	 * @param collTableData 实例对象
	 * @return 影响行数
	 */
	int update(CollTableData collTableData);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);

	/**
	 * 通过ID删除
	 *
	 * @param list 表主键
	 * @return
	 */
	int delete(@Param("list") List<Map<String, Object>> list);
}