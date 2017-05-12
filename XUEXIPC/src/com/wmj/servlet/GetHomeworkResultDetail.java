package com.wmj.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmj.bean.ApiResult;
import com.wmj.dao.OperatorHomeWork;


import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetHomeworkResultDetail
 */
@WebServlet("/GetHomeworkResultDetail")
public class GetHomeworkResultDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHomeworkResultDetail() {
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
		String homeworkResultID=request.getParameter("homeworkResultID");
		int homeworkResultId=Integer.parseInt(homeworkResultID);
		try {
			List<Map<String,Object>> HomeWorkResultDetailMap = OperatorHomeWork.getHomeWorkResultDetail(homeworkResultId);
			ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(HomeWorkResultDetailMap);
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
