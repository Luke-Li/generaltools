package com.luke.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

	/**
	 * Object 转 json string
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * json string 转 object
	 * @param str
	 * @return
	 */
	public static Object stringToObject(String str){
		return JSON.parse(str);
	}
}
