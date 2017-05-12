package com.wmj.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class JSONUtil {
	public static JSONObject parse(HttpServletRequest request) throws Exception{
		JSONObject json = null;
		String acceptjson = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(  (ServletInputStream) request.getInputStream(), "utf-8"));  
        StringBuffer sb = new StringBuffer("");  
        String temp;  
        while ((temp = br.readLine()) != null) {  
            sb.append(temp);  
        }  
        br.close();  
        acceptjson = sb.toString();
        json = JSONObject.fromObject(acceptjson);
		return json;
	}
}
