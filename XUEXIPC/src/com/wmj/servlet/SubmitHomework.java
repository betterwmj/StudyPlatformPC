package com.wmj.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wmj.bean.ApiResult;
import com.wmj.bean.HomeworkResult;
import com.wmj.bean.homeworkResultDetail;
import com.wmj.dao.OperatorHomeWork;
import com.wmj.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SubmitHomework
 */
@WebServlet("/SubmitHomework")
public class SubmitHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitHomework() {
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
		JSONObject json = null;
		try {
			json = JSONUtil.parse(request);
		} catch (Exception e1) {
			e1.printStackTrace();
			response.getWriter().append( JSONObject.fromObject(ApiResult.fail("无效的参数")).toString());
		}
		HttpSession session = request.getSession();
		Map<String,String> userInfo=(Map<String, String>) session.getAttribute("userInfo");
		String studentID = userInfo.get("id");
		HomeworkResult homeWork=new HomeworkResult(); 
        JSONObject jo = JSONObject.fromObject(json);
        JSONArray array=jo.getJSONArray("homeworkResult");
        List<homeworkResultDetail> list = new ArrayList<>();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for(int i=0;i<array.size();i++){
        	JSONObject t = JSONObject.fromObject( array.get(i));
        	homeworkResultDetail p = new homeworkResultDetail();
        	p.setQuestionId(Integer.parseInt(t.get("questionID").toString()));
        	p.setAnswer(t.get("answer").toString());
        	list.add(p);
        }
        homeWork.setHomeworkId(Integer.parseInt(jo.getString("homeworkID")));
        homeWork.setStudentId(Integer.parseInt(studentID));
        homeWork.setEvaluation(jo.getString("evaluation"));
        homeWork.setTime(time);
        boolean resultCode;
		try {
			resultCode = OperatorHomeWork.submitHomeWork(list, homeWork);
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
