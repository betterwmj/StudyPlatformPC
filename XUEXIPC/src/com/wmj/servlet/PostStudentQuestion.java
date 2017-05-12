package com.wmj.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmj.bean.ApiResult;
import com.wmj.bean.OnlineQuestion;
import com.wmj.dao.OperatorOnline;
import com.wmj.util.JSONUtil;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class PostStudentQuestion
 */
@WebServlet("/PostStudentQuestion")
public class PostStudentQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostStudentQuestion() {
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
			response.getWriter().append( JSONObject.fromObject(ApiResult.fail("无效的参数")).toString());
		}
		String title = json.getString("title");
		String content = json.getString("content");
		int studentId=json.getInt("studentId");
		int teacherID=json.getInt("teacherID");
		int subjectID=json.getInt("subjectID");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		OnlineQuestion question=new OnlineQuestion();
	    question.setStudentId(studentId);
	    question.setQuestionTitle(title);
	    question.setQuestionContent(content);
	    question.setAnswerId(teacherID);
	    question.setCreateTime(time);
	    question.setSubjectId(subjectID);
		boolean resultCode;
		try {
			resultCode = OperatorOnline.insertQuestion(question);
			ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(resultCode);
			response.getWriter().append(JSONObject.fromObject(result).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ApiResult result = new ApiResult();
			result.setCode(-1);
			result.setMessage(e.getMessage());
			response.getWriter().append(JSONObject.fromObject(result).toString());
		}
	}

}
