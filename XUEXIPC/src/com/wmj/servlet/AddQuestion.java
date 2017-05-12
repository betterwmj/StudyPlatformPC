package com.wmj.servlet;

import java.io.IOException;
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
import com.wmj.bean.Title;
import com.wmj.dao.OperatorQuestion;
import com.wmj.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AddQuestion
 */
@WebServlet("/AddQuestion")
public class AddQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQuestion() {
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
        String id= userInfo.get("id");
        int teacherId=Integer.parseInt(id);
        int subjectid=json.getInt("subjectId");
        JSONArray array=json.getJSONArray("titles");
        int resultCount = 0;
        List<Title> list=new ArrayList<>();
        for(int i=0;i<array.size();i++){
        	JSONObject t = JSONObject.fromObject( array.get(i));
        	Title title=new Title();
        	String titles = t.getString("title");
       		String answer = t.getString("answer");
       		int type=t.getInt("type");
        	title.setOptionA(t.get("optionA").toString());
        	title.setOptionB(t.get("optionB").toString());
        	title.setOptionC(t.get("optionC").toString());
        	title.setOptionD(t.get("optionD").toString());
        	title.setTitle(titles);
            title.setType(type);
            title.setAnswer(answer);
            title.setSubjectId(subjectid);
            title.setTeacherId(teacherId);
            try {
				title = OperatorQuestion.AddQuestion(title);
				if(title!=null){
				   resultCount++;	
				   list.add(title);
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
        
        if( array.size() == resultCount ){
        	ApiResult result = new ApiResult();
			result.setCode(0);
			result.setData(list);
			response.getWriter().append(JSONObject.fromObject(result).toString());
        }else{
        	ApiResult result = new ApiResult();
			result.setCode(-1);
			result.setMessage("添加试题失败");
			response.getWriter().append(JSONObject.fromObject(result).toString());
        }
	}

}
