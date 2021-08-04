package com.bonc.utils;

import com.bonc.colldata.entity.PersonEnum;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/8/4
 * Time:15:37
 * todo:枚举类装map
 */
public class Enum2Map {
	public static <T> Map<String, Object> EnumToMap(Class<T> enumT,
	                                                String... methodNames) {
		Map<String, Object> enummap = new HashMap<String, Object>();
		if (!enumT.isEnum()) {
			return enummap;
		}
		T[] enums = enumT.getEnumConstants();
		if (enums == null || enums.length <= 0) {
			return enummap;
		}
		int count = methodNames.length;
		/**默认接口value方法*/
		String valueMathod = "getCode";
		/**默认接口typeName方法*/
		String desMathod = "getName";
		/**扩展方法*/
		if (count >= 1 && !"".equals(methodNames[0])) {
			valueMathod = methodNames[0];
		}
		if (count == 2 && !"".equals(methodNames[1])) {
			desMathod = methodNames[1];
		}
		for (int i = 0, len = enums.length; i < len; i++) {
			T tobj = enums[i];
			try {
				/**获取value值*/
				String resultValue = getMethodValue(valueMathod, tobj);
				if ("".equals(resultValue)) {
					continue;
				}
				/**获取typeName描述值*/
				Object resultDes = getMethodValue(desMathod, tobj);
				/**如果描述不存在获取属性值*/
				if ("".equals(resultDes)) {
					resultDes = tobj;
				}
				enummap.put(resultValue, resultDes + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return enummap;
	}

	private static <T> String getMethodValue(String methodName, T obj, Object... args) {
		String resut = "";
		try {
			/********************************* start *****************************************/
			/**获取方法数组，这里只要共有的方法*/
			Method[] methods = obj.getClass().getMethods();
			if (methods.length <= 0) {
				return resut;
			}
			Method method = null;
			for (int i = 0, len = methods.length; i < len; i++) {
				/**忽略大小写取方法*/
				if (methods[i].getName().equalsIgnoreCase(methodName)) {
					/**如果存在，则取出正确的方法名称*/
					methodName = methods[i].getName();
					method = methods[i];
					break;
				}
			}
			/*************************** end ***********************************************/
			if (method == null) {
				return resut;
			}
			/**方法执行*/
			resut = (String)method.invoke(obj, args);
			if (resut == null) {
				resut = "";
			}
			/**返回结果*/
			return resut;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resut;

	}

	public static void main(String[] args) {
		Map<String,Object> map=Enum2Map.EnumToMap(PersonEnum.class);
		System.out.println(map);
	}
}
