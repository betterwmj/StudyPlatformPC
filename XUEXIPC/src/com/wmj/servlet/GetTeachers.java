package com.wmj.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmj.bean.ApiResult;
import com.wmj.bean.Students;
import com.wmj.bean.Teachers;
import com.wmj.dao.OperatorUser;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetTeachers
 */
@WebServlet("/GetTeachers")
public class GetTeachers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTeachers() {
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
		String userId =request.getParameter("userID");
		System.out.print("userId"+userId);
		 try {
			    List<Teachers> list =null;
			    if(userId !=null){
			    	int userid = Integer.parseInt(userId);
			    	list = OperatorUser.getTeachers(userid);
				}else{
					list = OperatorUser.getTeachers(-1);
				}
				ApiResult result = new ApiResult();
				result.setCode(0);
				result.setData(list);
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
