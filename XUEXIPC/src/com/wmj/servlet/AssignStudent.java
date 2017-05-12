package com.wmj.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmj.bean.ApiResult;
import com.wmj.bean.Students;
import com.wmj.dao.OperatorClass;
import com.wmj.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AssignStudent
 */
@WebServlet("/AssignStudent")
public class AssignStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignStudent() {
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
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			JSONObject json = null;
			try {
				json = JSONUtil.parse(request);
			} catch (Exception e1) {
				e1.printStackTrace();
				response.getWriter().append( JSONObject.fromObject(ApiResult.fail("无效的登录参数")).toString());
			}
		    int classid=json.getInt("classId");
	        JSONArray array=json.getJSONArray("studentIds");
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
