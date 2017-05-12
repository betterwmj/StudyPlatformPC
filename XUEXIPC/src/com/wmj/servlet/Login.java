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
import com.wmj.util.JSONUtil;


import net.sf.json.JSONObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String name = json.getString("user_number");
		String password = json.getString("password");
		int type=json.getInt("type");
		HttpSession session = request.getSession();
		try {
			Map<String,String> userInfo =OperatorUser.isUserPasswordCorrect(name, password, type);
			if( userInfo.size() == 0 ){
				response.getWriter().append( JSONObject.fromObject(ApiResult.fail("账户或密码错误")).toString());
			}else{
				response.getWriter().append( JSONObject.fromObject(ApiResult.success(userInfo)).toString());
				session.setAttribute("userInfo", userInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append( JSONObject.fromObject(ApiResult.fail("登录失败")).toString());
		}
	}

}
