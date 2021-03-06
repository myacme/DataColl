package com.bonc.colldata.mapper.baseData;

import com.bonc.colldata.entity.CollBasicPersonnelConfig;
import com.bonc.colldata.entity.CollPersonnelMaintain;
import com.bonc.colldata.entity.RYKB;
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
	 * todo:新增人员数据
	 * @param
	 * @return
	 */
	int addPersonnelData(RYKB rykb);

	/***
	 * todo:批量新增人员数据
	 * @param list
	 * @return
	 */
	int insertPersonnelData(@Param("list") List<RYKB> list);

	/***
	 * todo:查看某个人员详情
	 * @param id
	 * @return
	 */
	RYKB checkById(@Param("id") String id);

	/****
	 * todo:查询人员列表
	 * @param map
	 * @return
	 */
	List<RYKB> getPersonnelByList(Map<String, Object> map);

	/****
	 * todo:查询人员列表
	 * @param array
	 * @return
	 */
	List<RYKB> getPersonnelByDept(@Param("array") String[] array, @Param("name") String name, @Param("IDcard") String IDcard);

	/***
	 * todo:修改人员信息
	 * @param rykb
	 * @return
	 */
	int updatePersonnel(RYKB rykb);

	/****
	 * todo:人员信息
	 * @param
	 * @return
	 */
	int deletePersonnelById(Map<String, Object> map);

}
