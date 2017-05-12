package com.wmj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wmj.bean.Classes;
import com.wmj.bean.Students;
import com.wmj.util.JDBCUtil;

public class OperatorClass {
	
	/*
	 * 老师获取该班级所有学生信息
	 */
	public static List<Students> getClassStudent(int classid) throws Exception{
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
		sql="select * from students where classid=? ";
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, classid);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
			   Students student=new Students();
			   student.setUserID(rs.getInt("UserID"));
			   student.setSchool_number(rs.getString("school_number"));
			   student.setRealName(rs.getString("RealName"));
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
	 * 获取该老师该科目所有班级信息
	 */
	public static List<Classes> getTeacherSubjectClass(int teacherId,int subjectid) throws Exception{
		Connection conn = null;
		List<Classes> list = new ArrayList<Classes>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		sql="select a.* from classes as a,teacherclass_relation as b where a.ClassID =b.classID  and b.teacherId =? and b.subjectid =?;  ";
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, teacherId);
			pmt.setInt(2, subjectid);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
				 Classes classes=new Classes();
				 classes.setClassId(rs.getInt("ClassID"));
				 classes.setClassName(rs.getString("ClassName"));
                 list.add(classes);
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
	 * 获取所有班级信息
	 */
	public static List<Classes> getClasses() throws Exception{
		Connection conn = null;
		List<Classes> list = new ArrayList<Classes>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		sql="select * from classes  ";
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
				 Classes classes=new Classes();
				 classes.setClassId(rs.getInt("ClassID"));
				 classes.setClassName(rs.getString("ClassName"));
                 list.add(classes);
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
	 * 老师给学生分班
	 */
	public static Object updateClassId(List<Students> list,int classId) throws Exception{
		
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		int resultCount = 0;
		ResultSet rs=null;
		int flag=1;
		try {
			
			for(int i=0;i<list.size();i++){
				Students student=list.get(i);
				sql ="select * from  student_class_relationship where studentid =? and classid=?";
				pmt=JDBCUtil.getPreparedStatement(conn, sql);
				pmt.setInt(1, student.getUserID());
				pmt.setInt(2, classId);
				rs = pmt.executeQuery();
				if(rs.next()){	
					flag = 0;
					return student;	
				}
			}
			if( flag == 1){
				for(int i=0;i<list.size();i++){
					Students student=list.get(i);
					sql="insert into  student_class_relationship (studentid,classid) values(?,?) ";
					pmt=JDBCUtil.getPreparedStatement(conn, sql);
					pmt.setInt(1, student.getUserID());
					pmt.setInt(2, classId);
					if(pmt.executeUpdate()>0){
						resultCount++;
					}
				}
				if( resultCount == list.size() ){
					return true;
				}else{
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return false;
   }
	//学生更新班级
   public static boolean updateStudentClassId(int studentId,int classId) throws Exception{
		
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		try {
				sql ="delete  from  student_class_relationship where studentid =? ";
				pmt=JDBCUtil.getPreparedStatement(conn, sql);
				pmt.setInt(1, studentId);
				
				if(pmt.executeUpdate()>0){	
					sql="insert into  student_class_relationship (studentid,classid) values(?,?) ";
					pmt=JDBCUtil.getPreparedStatement(conn, sql);
					pmt.setInt(1, studentId);
					pmt.setInt(2, classId);
					if(pmt.executeUpdate()>0){
						return true;
				     }
			    }		
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return false;
    }
 //老师删除班级
   public static boolean deleteTeacherClass(int teacherId,int classId) throws Exception{
		
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		ResultSet rs=null;
		try {
				sql ="select * from teacherclass_relation where subjectid+teacherId in (select subjectid+teacherId  from  teacherclass_relation where teacherId =? and classID=?)";
				pmt=JDBCUtil.getPreparedStatement(conn, sql);
				pmt.setInt(1, teacherId);
				pmt.setInt(2, classId);
				rs =pmt.executeQuery();
				int count=0;
				while(rs.next()){
					count++; 
				}
				System.out.print("count"+count);
				if(count< 1 || count ==1){
					sql ="delete  from  teacher_subject_relation where teacherid+subjectid  in (select teacherId+subjectid  from  teacherclass_relation where teacherId =? and classID=?)";
					pmt=JDBCUtil.getPreparedStatement(conn, sql);
					pmt.setInt(1, teacherId);
					pmt.setInt(2, classId);
					pmt.executeUpdate();
				}
				sql ="delete  from  teacherclass_relation where teacherId =? and classID=?";
				pmt=JDBCUtil.getPreparedStatement(conn, sql);
				pmt.setInt(1, teacherId);
				pmt.setInt(2, classId);
				
				if(pmt.executeUpdate()>0){	
					  sql ="delete  from  classes where ClassID=?";
					  pmt=JDBCUtil.getPreparedStatement(conn, sql);
					  pmt.setInt(1, classId);
					  System.out.print("ClassID"+classId);
					  if(pmt.executeUpdate()>0){	
						 return true;     
				       }	    
			    }		
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return false;
    }
   //学生退出班级
   public static boolean deleteStudentClass(int studentId,int classId) throws Exception{
		
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		try {
				sql ="delete  from  student_class_relationship where studentid =? and classid=?";
				pmt=JDBCUtil.getPreparedStatement(conn, sql);
				pmt.setInt(1, studentId);
				pmt.setInt(2, classId);
				if(pmt.executeUpdate()>0){	
					 return true;     
			    }		
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			
			// 关闭连接
			JDBCUtil.close(conn, pmt);
		}
		return false;
   }
	/*
	 * 老师创建班级
	 */
	public static boolean insertClass(Classes classes,int teacherID,int subjectId) throws Exception{
		boolean result=false;
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
			
			String sql="select * from classes where ClassName = ?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 	
			pmt.setString(1, classes.getClassName());
			ResultSet rs=null;
			rs = pmt.executeQuery();
			if(rs.next()){
				return false;
			}else{
				int classId=0;
				String sql2="insert into classes (ClassName) values(?)";
				pmt=JDBCUtil.getPreparedStatement(conn, sql2,Statement.RETURN_GENERATED_KEYS); 
				pmt.setString(1, classes.getClassName());
				if(pmt.executeUpdate()>0){
					rs = pmt.getGeneratedKeys(); //获取结果   
					if (rs.next()) {
						classId = rs.getInt(1);//取得ID
						String sql3="insert into teacherclass_relation (classID,teacherId,subjectid) values(?,?,?)";
						pmt=JDBCUtil.getPreparedStatement(conn, sql3); 
						pmt.setInt(1, classId);
						pmt.setInt(2, teacherID);
						pmt.setInt(3, subjectId);
						if(pmt.executeUpdate()>0){
							String sql4="select * from teacher_subject_relation where teacherid=? and subjectid= ?";
							pmt=JDBCUtil.getPreparedStatement(conn, sql4); 
							pmt.setInt(1, teacherID);
							pmt.setInt(2, subjectId);
							rs = pmt.executeQuery();
							if(rs.next()){
								result = true;
							}else{
								String sql5="insert into teacher_subject_relation (teacherid,subjectid) values(?,?)";
								pmt=JDBCUtil.getPreparedStatement(conn, sql5); 
								pmt.setInt(1, teacherID);
								pmt.setInt(2, subjectId);
								if(pmt.executeUpdate()>0){
									result = true;
								}else{
									result = false;
								}
							}
							
						}
						 
					} else {
						result = false;
						
					}
					
				}
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

	/*
	 * 根据老师id获取该老师创建的所有班级
	 */
	public static List<Map<String,Object>> getTeacherClasses(int teacherID) throws Exception{
		Connection conn = null;
		List<Map<String,Object>> list = new ArrayList();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		sql="select c.*,b.* from teacherclass_relation as  a,classes as b ,subjects as c where a.classID=b.ClassID and a.subjectid = c.SubjectID and a.teacherId=? ";
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, teacherID);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
				 Map<String,Object> classesMap = new HashMap<>();
				 Classes classes=new Classes();
				 classes.setClassId(rs.getInt("ClassID"));
				 classes.setClassName(rs.getString("ClassName"));
				 classesMap.put("classes", classes);
				 classesMap.put("subjectName", rs.getString("SubjectName"));
                 list.add(classesMap);
                
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
	 * 根据班级id获取所有班级名字
	 */
	public static Classes getClassesByclassId(int classID) throws Exception{
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null; 
		String sql = "";
		sql="select * from classes where ClassID=? ";
		try {
			ResultSet rs = null;
			pmt=JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, classID);
			rs = pmt.executeQuery();
			 while (rs.next()) { 
				 Classes classes=new Classes();
				 classes.setClassId(rs.getInt("ClassID"));
				 classes.setClassName(rs.getString("ClassName"));
                 return classes;
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
