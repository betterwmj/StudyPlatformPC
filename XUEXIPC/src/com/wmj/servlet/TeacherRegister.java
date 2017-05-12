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

import com.wmj.bean.Teachers;
import com.wmj.dao.OperatorUser;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class TeacherRegister
 */
@WebServlet("/TeacherRegister")
public class TeacherRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherRegister() {
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
		String name=request.getParameter("teacher_number");
		String realName=request.getParameter("realName");
		String password=request.getParameter("password");
		Teachers teacher=new Teachers();
		teacher.setTeacher_number(name);
		teacher.setRealName(realName);
		teacher.setPassword(password);
		HttpSession session=request.getSession();
		Map<String,String> userInfo=(Map<String, String>) session.getAttribute("userInfo");
        String typeString= userInfo.get("type");
        int type=Integer.parseInt(typeString);
		try {
			boolean resultCode =false;
			if(type ==2)
			   resultCode=OperatorUser.addTeachers(teacher);
			else
			   resultCode=OperatorUser.insertTeacher(teacher);
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
