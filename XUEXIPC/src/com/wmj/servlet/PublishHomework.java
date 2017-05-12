package com.wmj.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmj.bean.ApiResult;
import com.wmj.bean.Classes;
import com.wmj.bean.HomeWork;
import com.wmj.bean.PaperDetail;
import com.wmj.dao.OperatorHomeWork;
import com.wmj.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**发布作业
 * Servlet implementation class PublishHomework
 */
@WebServlet("/PublishHomework")
public class PublishHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishHomework() {
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
		    JSONObject json = null;
			try {
				json = JSONUtil.parse(request);
			} catch (Exception e1) {
				e1.printStackTrace();
				response.getWriter().append( JSONObject.fromObject(ApiResult.fail("无效的参数")).toString());
			}
			JSONArray array=json.getJSONArray("classesID");
		    List<Classes> list = new ArrayList<>();
			for(int i=0;i<array.size();i++){
	        	JSONObject t = JSONObject.fromObject( array.get(i));
	        	Classes classes= new Classes();
	        	classes.setClassId(Integer.parseInt(t.get("classId").toString()));
	        	list.add(classes);
	        }
//		    String homeworkString=request.getParameter("homeworkID");
		
            int homeworkId=json.getInt("homeworkID");
		    try {
		      Boolean responseData = OperatorHomeWork.updateHomeWork(homeworkId,list);
		      ApiResult result = new ApiResult();
		      result.setCode(0);
		      result.setData(responseData);
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
