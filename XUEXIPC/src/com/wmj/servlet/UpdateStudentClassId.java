package com.wmj.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.wmj.bean.ApiResult;

import com.wmj.bean.Students;
import com.wmj.dao.OperatorClass;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UpdateStudentClassId
 */
@WebServlet("/UpdateStudentClassId")
public class UpdateStudentClassId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateStudentClassId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acceptjson="";
		BufferedReader br = new BufferedReader(new InputStreamReader(  (ServletInputStream) request.getInputStream(), "utf-8"));  
        StringBuffer sb = new StringBuffer("");  
        String temp;  
        while ((temp = br.readLine()) != null) {  
            sb.append(temp);  
        }  
        br.close();  
        acceptjson = sb.toString();
        JSONObject jo = JSONObject.fromObject(acceptjson);
        int classid=(int) jo.get("classId");
        JSONArray array=jo.getJSONArray("studentIds");
        List<Students> list = new ArrayList<>();
        for(int i=0;i<array.size();i++){
       
        	Students student=new Students();
        	student.setUserID(array.getInt(i));
        	list.add(student);
        	
        }
        try {
        	Object resultCode=OperatorClass.updateClassId(list,classid);
			ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(resultCode);
			response.getWriter().append(JSONObject.fromObject(result).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApiResult result = new ApiResult();
			result.setCode(-1);
			result.setMessage(e.getMessage());
			response.getWriter().append(JSONObject.fromObject(result).toString());
		}
       
        
              
	}
}
