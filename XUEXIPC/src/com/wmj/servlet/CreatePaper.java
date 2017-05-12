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
import com.wmj.bean.Paper;
import com.wmj.bean.PaperDetail;
import com.wmj.dao.OperatorSubject;
import com.wmj.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class InsertTestPaper
 */
@WebServlet("/CreatePaper")
public class CreatePaper extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePaper() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
        String id= userInfo.get("id");
        int userId=Integer.parseInt(id);
       
        int subjectid=json.getInt("subjectId");
        Paper paper=new Paper(); 
        JSONArray array=json.getJSONArray("papertitles");
        List<PaperDetail> list = new ArrayList<>();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for(int i=0;i<array.size();i++){
        	JSONObject t = JSONObject.fromObject( array.get(i));
        	PaperDetail p = new PaperDetail();
        	p.setTitleId(Integer.parseInt(t.get("itemId").toString()));
        	p.setScore(Integer.parseInt(t.get("score").toString()));
        	list.add(p);
        }
//        int userId=json.getInt("id");
//        int subjectId=json.getInt("suid");
        paper.setTestName(json.getString("papername"));
        paper.setSubjectID(subjectid);
        paper.setUserId(userId);
        paper.setCreateTime(time);
        boolean resultCode;
		try {
			resultCode = OperatorSubject.insertTestPaper(list,paper);
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
