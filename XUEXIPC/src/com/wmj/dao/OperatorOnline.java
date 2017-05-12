package com.wmj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wmj.bean.OnlineAnswer;
import com.wmj.bean.OnlineQuestion;
import com.wmj.util.JDBCUtil;

public class OperatorOnline {
	/*
	 *根据老师id或者学生id获取
	 */
	public static List<OnlineQuestion> getOnlineQuestion(int userId ,int typeId) throws Exception{
		Connection conn = null;
		List<OnlineQuestion> list = new ArrayList<OnlineQuestion>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		if(typeId==0){
			sql="select * from online_question where student_id =? order by createtime desc ";
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, userId);
		}else{
			sql="SELECT DISTINCT a.* FROM online_question as a INNER JOIN  online_answer as b ON  a.id = b.online_question_id " 
             +"WHERE  a.answer_id=? AND b.answer_id=? order by a.createtime desc";
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, userId);
			pmt.setInt(2, userId);
		}
		try {
			ResultSet rs = null;
			rs = pmt.executeQuery();
			 while (rs.next()) { 
			   OnlineQuestion question=new OnlineQuestion();
			   question.setId(rs.getInt("id"));
			   question.setStudentId(rs.getInt("student_id"));
			   question.setQuestionTitle(rs.getString("questiontitle"));
			   question.setQuestionContent(rs.getString("questioncontent"));
			   question.setAnswerId(rs.getInt("answer_id"));
			   question.setCreateTime(rs.getTimestamp("createtime"));
               list.add(question);
	         }
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
	return list;
   }
	/*
	 *根据问题id获取所有回复
	 */
	public static List<OnlineAnswer> getQuestionAnswer(int questionId) throws Exception{
		Connection conn = null;
		List<OnlineAnswer> list = new ArrayList<OnlineAnswer>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
	    sql="select * from online_answer where online_question_id=? order by answertime Asc  ";
		
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, questionId);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
			   OnlineAnswer answer=new OnlineAnswer();
			   answer.setId(rs.getInt("id"));
			   answer.setOnlineQuestionId(rs.getInt("online_question_id"));
			   answer.setAnswerId(rs.getInt("answer_id"));
			   answer.setAnswer(rs.getString("answer"));
			   answer.setAnswerTime(rs.getTimestamp("answertime"));
			   answer.setType(rs.getInt("type"));
               list.add(answer);
	         }
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
	return list;
   }
	/*
	 * 学生提问，若提交成功，返回true，否则返回false 
	 */
	public static boolean insertQuestion(OnlineQuestion question) throws Exception{
		boolean result = false;
		 //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		try {
			
            String sql="insert into online_question (student_id,questiontitle,questioncontent,answer_id,createtime,subject_id) values(?,?,?,?,?,?)";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setInt(1, question.getStudentId());
			pmt.setString(2, question.getQuestionTitle());
			pmt.setString(3, question.getQuestionContent());
			pmt.setInt(4, question.getAnswerId());
			pmt.setTimestamp(5, question.getCreateTime());
			pmt.setInt(6, question.getSubjectId());
			if(pmt.executeUpdate()>0)
			   result = true;
		    
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return result;
	}
	/*
	 * 在线答疑，若提交成功，返回true，否则返回false 
	 */
	public static boolean insertAnswer(OnlineAnswer answers) throws Exception{
		boolean result = false;
		 //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		try {
			
            String sql="insert into online_answer (online_question_id,answer_id,answer,answertime,type) values(?,?,?,?,?)";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setInt(1, answers.getOnlineQuestionId());
			pmt.setInt(2, answers.getAnswerId());
			pmt.setString(3, answers.getAnswer());
			pmt.setTimestamp(4, answers.getAnswerTime());
			pmt.setInt(5, answers.getType());
			if(pmt.executeUpdate()>0)
			   result = true;
		    
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return result;
	}
	/*
	 *获取学生或老师某个所在班级的提问
	 */
	public static List<OnlineQuestion> getClassOnlineQuestion(int classId) throws Exception{
		Connection conn = null;
		List<OnlineQuestion> list = new ArrayList<OnlineQuestion>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		sql ="select a.* from online_question as a, teacherclass_relation as b,student_class_relationship as c "
        +" where a.answer_id = b.teacherId and a.student_id= c.studentid "
        +"and b.subjectid =a.subject_id and b.classID =c.classid  "
        +"and  b.classID =?  order by a.createtime desc";
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, classId);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
			   OnlineQuestion question=new OnlineQuestion();
			   question.setId(rs.getInt("id"));
			   question.setStudentId(rs.getInt("student_id"));
			   question.setQuestionTitle(rs.getString("questiontitle"));
			   question.setQuestionContent(rs.getString("questioncontent"));
			   question.setAnswerId(rs.getInt("answer_id"));
			   question.setCreateTime(rs.getTimestamp("createtime"));
               list.add(question);
	         }
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
	return list;
   }
}
