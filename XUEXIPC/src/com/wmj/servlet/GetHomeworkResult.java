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

import com.wmj.dao.OperatorHomeWork;
import com.wmj.dao.OperatorTestPaper;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetHomeworkResult
 */
@WebServlet("/GetHomeworkResult")
public class GetHomeworkResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHomeworkResult() {
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
		HttpSession session = request.getSession();
        Map<String,String> userInfo=(Map<String, String>) session.getAttribute("userInfo");	
        String studentID= userInfo.get("id");
        String typeID= userInfo.get("type");
        int studentId=Integer.parseInt(studentID);
        int typeId=Integer.parseInt(typeID);
	
		try {
			List<Map<String,Object>> HomeWorkResultMap=null;
			if(typeId==0){
				HomeWorkResultMap= OperatorHomeWork.getHomeWorkResult(typeId,studentId);
			}else{
				String homeworkID=request.getParameter("homeworkID");
				int homeworkId=Integer.parseInt(homeworkID);
				HomeWorkResultMap = OperatorHomeWork.getHomeWorkResult(typeId,homeworkId);
			}
			ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(HomeWorkResultMap);
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
