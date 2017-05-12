package com.wmj.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.wmj.bean.ApiResult;
import com.wmj.bean.PaperResult;
import com.wmj.bean.PaperResultDetail;
import com.wmj.dao.OperatorTestPaper;
import com.wmj.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SubmitPaper
 */
@WebServlet("/SubmitPaper")
public class SubmitPaper extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitPaper() {
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
		PaperResult paper=new PaperResult(); 
        JSONObject jo = JSONObject.fromObject(json);
        JSONArray array=jo.getJSONArray("paperresult");
        List<PaperResultDetail> list = new ArrayList<>();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for(int i=0;i<array.size();i++){
        	JSONObject t = JSONObject.fromObject( array.get(i));
        	PaperResultDetail p = new PaperResultDetail();
        	p.setQuestionId(Integer.parseInt(t.get("questionID").toString()));
        	p.setAnswer(t.get("answer").toString());
        	list.add(p);
        }
        paper.setPaperId(Integer.parseInt(jo.getString("paperID")));
        paper.setStudentId(Integer.parseInt(jo.getString("studentID")));
        paper.setScore(Integer.parseInt(jo.getString("score")));
        paper.setTime(time);
        boolean resultCode;
		try {
			resultCode = OperatorTestPaper.submitTestPaper(list,paper);
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
