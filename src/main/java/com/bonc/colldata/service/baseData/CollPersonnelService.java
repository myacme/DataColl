package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.CollBasicPersonnelConfig;
import com.bonc.colldata.entity.CollPersonnelMaintain;
import com.bonc.colldata.entity.QueryParam;
import com.bonc.colldata.entity.RYKB;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/13
 * Time:16:42
 * todo:人员维护服务接口
 */

public interface CollPersonnelService {

	List<CollBasicPersonnelConfig> getTableHead();


	int addPersonnelData(CollPersonnelMaintain collPersonnelMaintain);


	CollPersonnelMaintain checkById(@Param("id") String id);


	List<CollPersonnelMaintain> getPersonnelByList(List<QueryParam> list);
	List<RYKB> getPersonnelByDept(String deptCode, String name, String IDcard);


	int updatePersonnel(CollPersonnelMaintain collPersonnelMaintain);


	int deletePersonnelById(List<String> id);

	List<Map<String,Object>> getTableDesc();

	String getSystemName();
}
