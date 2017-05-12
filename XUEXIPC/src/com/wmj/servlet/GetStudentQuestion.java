package com.wmj.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wmj.bean.ApiResult;
import com.wmj.bean.OnlineQuestion;
import com.wmj.dao.OperatorOnline;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetStudentQuestion
 */
@WebServlet("/GetStudentQuestion")
public class GetStudentQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetStudentQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		Map<String, String> userInfo = (Map<String, String>) session.getAttribute("userInfo");
		String id = userInfo.get("id");
		String type = userInfo.get("type");
		//String type=request.getParameter("type");
		int typeId = Integer.parseInt(type);
		//String id=request.getParameter("userID");
		int userId = Integer.parseInt(id);
		try {
			List<OnlineQuestion> list = OperatorOnline.getOnlineQuestion(userId, typeId);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
