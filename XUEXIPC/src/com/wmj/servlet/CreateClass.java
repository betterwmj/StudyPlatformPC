package com.wmj.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wmj.bean.ApiResult;
import com.wmj.bean.Classes;
import com.wmj.dao.OperatorClass;



import net.sf.json.JSONObject;

/**
 * Servlet implementation class CreateClass
 */
@WebServlet("/CreateClass")
public class CreateClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateClass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String cname = request.getParameter("className").trim();
		 String subjectID = request.getParameter("subjectID").trim();
	     int subjectId=Integer.parseInt(subjectID);
	     HttpSession session = request.getSession();
         Map<String,String> userInfo=(Map<String, String>) session.getAttribute("userInfo");	
         String teacherID= userInfo.get("id");
         int teacherId=Integer.parseInt(teacherID);
	     Classes classes=new Classes();
		 classes.setClassName(cname);
	     try {
	    	    boolean resultCode=OperatorClass.insertClass(classes, teacherId,subjectId);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
