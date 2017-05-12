package com.wmj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wmj.bean.Title;
import com.wmj.util.JDBCUtil;

public class OperatorQuestion {
	/*
	 * 用户的插入，若插入成功，返回true，否则返回false //title为需要插入的题目
	 */
	public static Title AddQuestion(Title title) throws Exception{
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
		ResultSet rs=null;
		PreparedStatement pmt = null; 
		try {
			
            String sql="insert into title (title,type,optionA,optionB,optionC,optionD,answer,SubjectID,teacherid) values(?,?,?,?,?,?,?,?,?)";
        	pmt=JDBCUtil.getPreparedStatement(conn, sql,Statement.RETURN_GENERATED_KEYS); 
			pmt.setString(1, title.getTitle());
			pmt.setInt(2, title.getType());
			pmt.setString(3, title.getOptionA());
			pmt.setString(4, title.getOptionB());
			pmt.setString(5, title.getOptionC());
			pmt.setString(6, title.getOptionD());
			pmt.setString(7, title.getAnswer());
			pmt.setInt(8, title.getSubjectId());
			pmt.setInt(9, title.getTeacherId());
			if(pmt.executeUpdate()>0){
			   rs = pmt.getGeneratedKeys(); 
			   if (rs.next()) {
				   int titleId=rs.getInt(1);
				   title.setItemId(titleId);
				   return title;
			   }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return null;
	}
	
}
