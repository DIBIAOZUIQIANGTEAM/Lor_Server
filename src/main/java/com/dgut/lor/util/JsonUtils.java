package com.dgut.lor.util;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dgut.lor.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 封装成Json工具类
 */
public class JsonUtils {

	// 把用户信息转换成json
	public static JSONObject toJson(int code, String messege, Object object) {
JSONObject jsonObject = new JSONObject();

		jsonObject.put("code", code);
		jsonObject.put("messege", messege);
		ObjectMapper objectMapper = new ObjectMapper();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(sdf);
		StringWriter str = new StringWriter();
		if (!object.toString().equals("")) {
			try {
				objectMapper.writeValue(str, object);
				jsonObject.put("data", str.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			jsonObject.put("data", null);
		}
		
		
		System.out.println("jsonObject " + jsonObject);
		return jsonObject;

	}

}
