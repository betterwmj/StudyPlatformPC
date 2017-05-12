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
import com.wmj.dao.OperatorUser;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetUserInfoByName
 */
@WebServlet("/GetUserInfoByName")
public class GetUserInfoByName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfoByName() {
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
//		HttpSession session = request.getSession();
//        Map<String,String> userInfo=(Map<String, String>) session.getAttribute("userInfo");	
//        String typeString= userInfo.get("type");
//        String name= userInfo.get("teacher_number");
//        int type=Integer.parseInt(typeString);
//		//String type=request.getParameter("type");
//        //int type=Integer.parseInt(typeString);
//      //String typeString=request.getParameter("type");
		String user_number=request.getParameter("user_number");
		String typeString=request.getParameter("type");
		int type=Integer.parseInt(typeString);	
		try {
			Object userInfos=OperatorUser.getUserInfo(user_number, type);
			ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(userInfos);
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
