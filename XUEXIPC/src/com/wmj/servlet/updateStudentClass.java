package com.wmj.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmj.bean.ApiResult;
import com.wmj.dao.OperatorClass;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class DeleteStudentClas
 */
@WebServlet("/updateStudentClass")
public class updateStudentClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateStudentClass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String studentId=request.getParameter("studentId");
		int studentid=Integer.parseInt(studentId);
		String classId=request.getParameter("classId");
		int classid=Integer.parseInt(classId);
		// 0--表示删除 1--表示更新
		String typeId=request.getParameter("typeId");
		int typeid=Integer.parseInt(typeId);
	    try {
	    	boolean resultData =false;
	    	if(typeid ==0)
		        resultData = OperatorClass.deleteStudentClass(studentid, classid);
	    	else
	    		resultData = OperatorClass.updateStudentClassId(studentid, classid);
			ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(resultData);
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
		doGet(request, response);
	}

}
