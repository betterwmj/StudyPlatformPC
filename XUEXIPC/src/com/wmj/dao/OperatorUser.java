package com.wmj.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wmj.bean.Students;
import com.wmj.bean.Teachers;
import com.wmj.util.JDBCUtil;

/*
 * 对用户信息进行操作的数据库类
 */
public class OperatorUser {
	/*
	 * 查询用户名是否重复，返回true，否则返回false 
	 */
	public static Object getUserInfo(String userNumber,int type) throws Exception{
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
            String sql=null;
			if(type==0)
				sql ="select * from students where school_number = ? ";
			else if(type==1 || type==2)
				sql ="select * from teachers where teacher_number = ? "; 
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setString(1, userNumber);
			rs = pmt.executeQuery();
			 if (rs.next()) {
				if(type==0){
					Students student=new Students();
					student.setUserID(rs.getInt("UserID"));
					student.setSchool_number(rs.getString("school_number"));
					student.setRealName(rs.getString("realName"));
					student.setPassword(rs.getString("password"));
					student.setSchool(rs.getString("school"));
					student.setTelephone(rs.getString("telephone"));
					return student;
				}		
				else {
					Teachers teacher=new Teachers();
					teacher.setUserID(rs.getInt("UserID"));
					teacher.setTeacher_number(rs.getString("teacher_number"));
					teacher.setRealName(rs.getString("realName"));
					teacher.setPassword(rs.getString("password"));
					return teacher;
					
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
	/*
	 * 学生用户注册，若注册成功，返回true，否则返回false 
	 */
	public static boolean insertStudent(Students student) throws Exception{
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
		ResultSet rs= null;
		try {
			String sql1="select * from  students where school_number = ? and  realName=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql1); 
			pmt.setString(1, student.getSchool_number());
			pmt.setString(2, student.getRealName());
	        rs=pmt.executeQuery();
			if(rs.next()){
				String sql2="update students set password=?, school=? ,telephone= ? where school_number = ? and  realName=? ";
				pmt=JDBCUtil.getPreparedStatement(conn, sql2); 
				pmt.setString(4, student.getSchool_number());
				pmt.setString(5, student.getRealName());
				pmt.setString(1, student.getPassword());
				pmt.setString(2, student.getSchool());
				pmt.setString(3, student.getTelephone());
				if(pmt.executeUpdate()>0)
				   result = true;
		   }else{
			   result = false;
		   }   
	  
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return result;
	}
	public static boolean insertTeacher (Teachers teacher) throws Exception{
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
		ResultSet rs=null;
		try {
		    String sql1="select * from  teachers where teacher_number = ? and  realName=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql1); 
			pmt.setString(1, teacher.getTeacher_number());
			pmt.setString(2, teacher.getRealName());
	        rs=pmt.executeQuery();
			if(rs.next()){
				String sql="update teachers set password=? where teacher_number = ? and  realName=?";
				pmt=JDBCUtil.getPreparedStatement(conn, sql); 
				pmt.setString(2, teacher.getTeacher_number());
				pmt.setString(3, teacher.getRealName());
				pmt.setString(1, teacher.getPassword());
				if(pmt.executeUpdate()>0)
				result = true;
			}else{
			   result = false;
		   }  
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return result;
	}
	//管理员添加学生
	public static boolean addStudent(Students student) throws Exception{
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
			
				String sql="insert into students (school_number,realName,school,password,telephone) values(?,?,?,?,?) ";
				pmt=JDBCUtil.getPreparedStatement(conn, sql); 
				pmt.setString(1, student.getSchool_number());
				pmt.setString(2, student.getRealName());
				pmt.setString(3, student.getPassword());
				pmt.setString(4, student.getSchool());
				pmt.setString(5, student.getTelephone());
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
	//管理员添加老师
		public static boolean addTeachers(Teachers teacher) throws Exception{
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
				
					String sql="insert into teachers(teacher_number,realName,password) values(?,?,?)";
					pmt=JDBCUtil.getPreparedStatement(conn, sql); 
					pmt.setString(1, teacher.getTeacher_number());
					pmt.setString(2, teacher.getRealName());
					pmt.setString(3, teacher.getPassword());
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
	// 判断用户名密码是否正确，如果正确，返回用户信息。type标志是老师还是学生
	public static Map<String,String> isUserPasswordCorrect(String userNumber, String userPassword,int type) throws Exception {
		 //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		String result = "";
		Map<String,String> userInfo = new HashMap<>();
		ResultSet rs = null;
		//pmt对象
        PreparedStatement pmt = null; 
		try {
			/* 设置所需变量 */
			String sql="";
			System.out.println("teacher or students"+ type+ userNumber);
			if(type==0)
				sql ="select * from students where school_number = ? and password = ?";
			else if(type==1 ||type==2)
				sql ="select * from teachers where teacher_number = ? and password = ?";
			/* 完成查询 */
			pmt=JDBCUtil.getPreparedStatement(conn, sql);   
			pmt.setString(1, userNumber);
			pmt.setString(2, userPassword);
			
			rs = pmt.executeQuery();

			/* 如果查询结果不为空，则登陆成功；否则，登陆失败 */
			if (rs.next()) {
				userInfo.put("id", rs.getInt("UserID")+"");
				if(type==0){
					userInfo.put("school_number", rs.getString("school_number"));
				}else {
					userInfo.put("teacher_number", rs.getString("teacher_number"));
				}
				userInfo.put("password", rs.getString("password"));
				userInfo.put("realName", rs.getString("realName"));
				userInfo.put("type", type+"");
			}
			System.out.println(result);
		} catch (SQLException e) {
			// 数据库操作出错
			e.printStackTrace();
			throw e;
		} finally {
			
			//资源的释放的操作
	        JDBCUtil.close(conn, pmt, rs);
		}
		return userInfo;
	}
	
	// 老师个人中心修改个人信息
	public static boolean updateTeacher(Teachers teacher) throws Exception{
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
			
           String sql="update teachers set teacher_number=? ,realName=?,password=? where userID=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setString(1, teacher.getTeacher_number());
			pmt.setString(2, teacher.getRealName());
			pmt.setString(3, teacher.getPassword());
			pmt.setInt(4, teacher.getUserID());
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
	// 学生个人中心修改个人信息
	public static boolean updateStudent(Students student) throws Exception{

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
		
            String sql="update students set school_number=? ,realName=?,password=?,school=?,telephone=? where UserID=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setString(1, student.getSchool_number());
			pmt.setString(2, student.getRealName());
			pmt.setString(3, student.getPassword());
			pmt.setString(4, student.getSchool());
			pmt.setString(5, student.getTelephone());
			pmt.setInt(6, student.getUserID());
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
	 * 管理员获取所有学生信息
	 */
	public static List<Students> getStudent(int userId) throws Exception{
		Connection conn = null;
		List<Students> list = new ArrayList<Students>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		if(userId ==-1){
		    sql="select * from students";
		    pmt=JDBCUtil.getPreparedStatement(conn, sql);
		 
		}else{
			sql="select * from students where UserID=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, userId);
		}
		try {
			ResultSet rs = null;
			
			rs = pmt.executeQuery();
			 while (rs.next()) { 
				Students student=new Students();
				student.setUserID(rs.getInt("UserID"));
				student.setSchool_number(rs.getString("school_number"));
				student.setRealName(rs.getString("realName"));
				student.setPassword(rs.getString("password"));
				student.setSchool(rs.getString("school"));
				student.setTelephone(rs.getString("telephone"));
                list.add(student);
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
	 * 管理员所有老师信息
	 */
	public static List<Teachers> getTeachers(int userId) throws Exception{
		Connection conn = null;
		List<Teachers> list = new ArrayList<Teachers>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		if(userId ==-1){
			sql="select * from teachers";
		    pmt=JDBCUtil.getPreparedStatement(conn, sql);
		 
		}else{
			sql="select * from teachers where userID=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, userId);
		}
		
		try {
			ResultSet rs = null;
			rs = pmt.executeQuery();
			 while (rs.next()) { 
				Teachers teacher=new Teachers();
				teacher.setUserID(rs.getInt("UserID"));
				teacher.setTeacher_number(rs.getString("teacher_number"));
				teacher.setRealName(rs.getString("realName"));
				teacher.setPassword(rs.getString("password"));
                list.add(teacher);
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
	//管理员删除老师信息
	public static boolean deleteUser(int type, int userId) throws Exception{
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
			String sql ="";
			if(type == 0)
               sql="delete from students where userID=?";
			else
			   sql="delete from teachers where userID=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setInt(1, userId);
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
}
