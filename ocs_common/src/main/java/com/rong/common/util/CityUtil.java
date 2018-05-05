package com.rong.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jfinal.kit.PathKit;

public class CityUtil {
	@SuppressWarnings("unchecked")
	public static List<String> getProvince() {
	        String path = PathKit.getWebRootPath()+("\\city.json");
	        List<String> returnList = new ArrayList<>();
	        try {
	            String input = FileUtils.readFileToString(new File(path), "UTF-8");
	            JsonArray jsonArray = (JsonArray) GsonUtil.fromJson(input, JsonArray.class);
	            for (JsonElement json : jsonArray) {
	            	Map<String, Object> map = (Map<String, Object>)GsonUtil.fromJson(json.toString(), Map.class);
	            	returnList.add(((String)map.get("name")));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return returnList;
	    }
	
	public static void main(String[] args) {
		getProvince();
	}
}

