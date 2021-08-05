package com.bonc.colldata.service.baseData;

import com.bonc.colldata.entity.RYKB;
import org.springframework.data.domain.Pageable;

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

	int addPersonnelData(RYKB rykb);

	RYKB checkById(String id);

	List<RYKB> getPersonnelByList(String xm, String szdwcjid);

	int updatePersonnel(RYKB rykb);

	Map<String, Object> getPersonnelByDept(String deptCode, String name, String IDcard, Pageable pageable);

	Map<String, Object> getJgByDept(String deptCode, String name, String IDcard, Pageable pageable);

	int deletePersonnelById(List<String> id);

	String getSystemName();
}
