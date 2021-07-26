package com.bonc.colldata.mapper.baseData;

import com.bonc.colldata.entity.CollBasicPersonnelConfig;
import com.bonc.colldata.entity.CollPersonnelMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:16:22
 * todo:人员数据维护
 */
@Mapper
public interface CollPersonnelMapper {
	/***
	 * todo:获取人员数据表配置项
	 * @return
	 */
	List<CollBasicPersonnelConfig> getTableHead();

	/***
	 * todo:新增人员数据
	 * @param collPersonnelMaintain
	 * @return
	 */
	int addPersonnelData(CollPersonnelMaintain collPersonnelMaintain);

	/***
	 * todo:批量新增人员数据
	 * @param collPersonnelMaintain
	 * @return
	 */
	int insertPersonnelData(@Param("list") List<CollPersonnelMaintain> collPersonnelMaintain);

	/***
	 * todo:查看某个人员详情
	 * @param id
	 * @return
	 */
	CollPersonnelMaintain checkById(@Param("id") String id);

	/****
	 * todo:查询人员列表
	 * @param map
	 * @return
	 */
	List<CollPersonnelMaintain> getPersonnelByList(Map<String,Object> map);

	/***
	 * todo:修改人员信息
	 * @param collPersonnelMaintain
	 * @return
	 */
	int updatePersonnel(CollPersonnelMaintain collPersonnelMaintain);

	/****
	 * todo:人员信息
	 * @param id
	 * @return
	 */
	int deletePersonnelById(Map<String,Object> map);

	/***
	 * todo:获取表字段描述
	 * @return
	 */
	List<Map<String,Object>> getTableDesc();
}
