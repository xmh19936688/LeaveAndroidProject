package com.xmh.leave.utils;

import com.google.gson.Gson;
import com.xmh.leave.vo.Ask;

import java.lang.reflect.Type;
import java.util.List;


public class JsonUtils {

	/**
	 * 从jsonstring转换到指定类型
	 * @param jsonAskList jsonstring
	 * @param type 目标类型
	 * @return
	 */
	public static Object StringToObjet(String jsonAskList, Type type) {
		Gson gson = new Gson();
		List<Ask> list = gson.fromJson(jsonAskList, type);
		return list;
	}

}
