package com.wmj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wmj.bean.Classes;
import com.wmj.bean.HomeWork;
import com.wmj.bean.HomeWorkDetail;
import com.wmj.bean.HomeworkResult;
import com.wmj.bean.PaperResultDetail;
import com.wmj.bean.Title;
import com.wmj.bean.homeworkResultDetail;
import com.wmj.util.JDBCUtil;

public class OperatorHomeWork {
	/*
	 * 老师添加作业到数据库
	 */
	public static boolean insertHomeWork(List<HomeWorkDetail> list, HomeWork home) throws Exception {
		boolean result = false;
		// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
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
			int homeworkID = 0;
			ResultSet rs = null;
			String sql = "insert into homeworks (homeworkname,time,teacherID,SubjectID,Finishtime,status) values(?,?,?,?,?,0)";
			pmt = JDBCUtil.getPreparedStatement(conn, sql, Statement.RETURN_GENERATED_KEYS);
			pmt.setString(1, home.getHomeWorkName());
			pmt.setTimestamp(2, home.getTime());
			pmt.setInt(3, home.getTeacherId());
			pmt.setInt(4, home.getSubjectId());
			pmt.setTimestamp(5, home.getFinishTime());
			if (pmt.executeUpdate() > 0) {
				rs = pmt.getGeneratedKeys(); // 获取结果
				if (rs.next()) {
					homeworkID = rs.getInt(1);// 取得ID
				} else {
					result = false;
					return result;
				}
				int resultCount = 0;
				for (int i = 0; i < list.size(); i++) {
					HomeWorkDetail homeTitle = list.get(i);
					String sqls = "insert into homeworks_detail (ItemID,HomeworkID) values(?,?)";
					pmt = JDBCUtil.getPreparedStatement(conn, sqls);
					pmt.setInt(1, homeTitle.getItemId());
					pmt.setInt(2, homeworkID);
					if (pmt.executeUpdate() > 0) {
						resultCount++;
					}
				}
				if (resultCount == list.size()) {
					return true;
				} else {
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
		return result;
	}

	/*
	 * 老师发布作业，更新作业状态为1,并且把班级id插入到班级作业表中
	 */
	public static boolean updateHomeWork(int homeworkID,List<Classes> list) throws Exception {

		// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		boolean result = false;
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

			String sql = "update homeworks set status=1 where HomeworkID=?";
			pmt = JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, homeworkID);
			if (pmt.executeUpdate() > 0){
			
				for(int i=0;i<list.size();i++){
					Classes classes=list.get(i);
					String sqls="insert into homework_class_relation (HomeworkID,classID) values (?,?)";
					pmt=JDBCUtil.getPreparedStatement(conn, sqls); 
					pmt.setInt(1, homeworkID);
					pmt.setInt(2, classes.getClassId());
					if(pmt.executeUpdate()>0){
						result = true;
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
	 * 获取该老师所创建的所有作业根据老师id
	 */
	public static List<HomeWork> getHomeWorkByTeacherId(int teacherId) throws Exception {

		// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		List<HomeWork> list = new ArrayList<HomeWork>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null;
		try {
			ResultSet rs = null;
			String sql = "select * from  homeworks where teacherID=?";
			pmt = JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, teacherId);
			rs = pmt.executeQuery();
			while (rs.next()) {
				HomeWork home = new HomeWork();
				home.setHomeId(rs.getInt("HomeworkID"));
				home.setHomeWorkName(rs.getString("homeworkname"));
				home.setTeacherId(rs.getInt("teacherID"));
				home.setSubjectId(rs.getInt("SubjectID"));
				home.setFinishTime(rs.getTimestamp("Finishtime"));
				home.setStatus(rs.getInt("status"));
				home.setTime(rs.getTimestamp("time"));
				list.add(home);

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
	 * 获取该科目的所有作业
	 */
	public static List<HomeWork> getHomeWorkBySubjectId(int subjectId,int studentId) throws Exception {

		// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		List<HomeWork> list = new ArrayList<HomeWork>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null;
		try {
			ResultSet rs = null;
			String sql = "select a.* from  homeworks as a, students as b, student_class_relationship as c,homework_class_relation as d "
			+ "where b.UserID = c.studentid and c.classid = d.classID and d.HomeworkID =a.HomeworkID"
			+" and a.SubjectID=? and b.UserID =? and a.status=1";
			pmt = JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, subjectId);
			pmt.setInt(2, studentId);
			rs = pmt.executeQuery();
			while (rs.next()) {
				HomeWork home = new HomeWork();
				home.setHomeId(rs.getInt("HomeworkID"));
				home.setHomeWorkName(rs.getString("homeworkname"));
				home.setTeacherId(rs.getInt("teacherID"));
				home.setSubjectId(rs.getInt("SubjectID"));
				home.setFinishTime(rs.getTimestamp("Finishtime"));
				home.setStatus(rs.getInt("status"));
				home.setTime(rs.getTimestamp("time"));
				list.add(home);

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
	 * 获取作业详情
	 */
	public static List<Title> getHomeWorkDetail(int homeworkID) throws Exception {

		// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		List<Title> list = new ArrayList<Title>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null;
		try {
			ResultSet rs = null;
			String sql = "select b.* from homeworks_detail as a,title as b  where a.ItemID=b.ItemID and a.HomeworkID=?;";
			pmt = JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, homeworkID);
			rs = pmt.executeQuery();
			while (rs.next()) {
				Title title = new Title();
				title.setItemId(rs.getInt("ItemID"));
				title.setTitle(rs.getString("title"));
				title.setType(rs.getInt("type"));
				title.setOptionA(rs.getString("optionA"));
				title.setOptionB(rs.getString("optionB"));
				title.setOptionC(rs.getString("optionC"));
				title.setOptionD(rs.getString("optionD"));
				title.setAnswer(rs.getString("answer"));
				title.setSubjectId(rs.getInt("SubjectID"));
				title.setTeacherId(rs.getInt("teacherid"));
				list.add(title);

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
	 * 学生做完作业提交到数据库
	 */
	public static boolean submitHomeWork(List<homeworkResultDetail> list, HomeworkResult homeWork) throws Exception {
		boolean result = false;
		// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
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
			int homeworkResultId = 0;
			ResultSet rs = null;
			String sql = "insert into homework_result (homeworkId,student_id,time,Evaluation) values(?,?,?,?)";
			pmt = JDBCUtil.getPreparedStatement(conn, sql, Statement.RETURN_GENERATED_KEYS);
			pmt.setInt(1, homeWork.getHomeworkId());
			pmt.setInt(2, homeWork.getStudentId());
			pmt.setTimestamp(3, (Timestamp) homeWork.getTime());
			pmt.setString(4, homeWork.getEvaluation());
			if (pmt.executeUpdate() > 0) {
				rs = pmt.getGeneratedKeys(); // 获取结果
				if (rs.next()) {
					homeworkResultId = rs.getInt(1);// 取得ID
				} else {
					result = false;
					return result;
				}
				int resultCount = 0;
				for (int i = 0; i < list.size(); i++) {
					homeworkResultDetail homeworkDetail = list.get(i);
					String sqls = "insert into homework_result_detail (homework_result_id,question_id,answer) values(?,?,?)";
					pmt = JDBCUtil.getPreparedStatement(conn, sqls);
					pmt.setInt(1, homeworkResultId);
					pmt.setInt(2, homeworkDetail.getQuestionId());
					pmt.setString(3, homeworkDetail.getAnswer());
					if (pmt.executeUpdate() > 0) {
						resultCount++;
					}
				}
				if (resultCount == list.size()) {
					return true;
				} else {
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
		return result;
	}

	/*
	 * 学生界面获取该学生所有应写的作业
	 */
	public static List<HomeWork> getHomeWork(int classId) throws Exception {
		Connection conn = null;
		List<HomeWork> list = new ArrayList<HomeWork>();
		try {
			conn = JDBCUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		PreparedStatement pmt = null;
		String sql = "";
		sql = "select homeworkname from homeworks where ClassID=? ";
		try {
			ResultSet rs = null;
			pmt = JDBCUtil.getPreparedStatement(conn, sql);
			pmt.setInt(1, classId);
			rs = pmt.executeQuery();
			while (rs.next()) {
				HomeWork homework = new HomeWork();
				homework.setHomeId(rs.getInt("HomeworkID"));
				homework.setHomeWorkName(rs.getString("homeworkname"));
				list.add(homework);
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
	   * 获取作业结果
	   */
	  public static  List<Map<String,Object>> getHomeWorkResult(int typeId,int homeworkID) throws Exception{
		    
		     //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		    Connection conn = null;
		    try {
		      conn = JDBCUtil.getConnection();
		    } catch (SQLException e1) {
		      // TODO Auto-generated catch block
		      e1.printStackTrace();
		      throw e1;
		    }
		    List<Map<String,Object>> list=new ArrayList();
		    PreparedStatement pmt = null; 
		    try {
		      ResultSet rs = null;
		      String sql="";
		      if(typeId==0){
		    	  sql="select a.*, b.RealName from homework_result as a,students as b where  a.student_id=b.UserID and b.UserID=?;";
		      }else{
		    	  sql="select a.*, b.RealName from homework_result as a,students as b where  a.student_id=b.UserID and a.homeworkId=?;";
		      }     
		      pmt=JDBCUtil.getPreparedStatement(conn, sql); 
		      pmt.setInt(1, homeworkID);
		      rs = pmt.executeQuery();
		       while (rs.next()) {
		    	 Map<String,Object> HomeWorkResultMap = new HashMap<>();
		    	 HomeworkResult homework=new HomeworkResult();
		    	 homework.setId(rs.getInt("id"));
		    	 homework.setHomeworkId(rs.getInt("homeworkId"));
		    	 homework.setStudentId(rs.getInt("student_id"));
		    	 homework.setTime(rs.getTimestamp("time"));
		    	 homework.setEvaluation(rs.getString("Evaluation"));
		    	 HomeWorkResultMap.put("homework", homework);
		    	 HomeWorkResultMap.put("studentName", rs.getString("RealName"));
		         list.add(HomeWorkResultMap);    
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
	   * 获取试卷结果详情
	   */
	  public static List<Map<String,Object>> getHomeWorkResultDetail(int HomeWorkResultDetailID) throws Exception{
		    
		     //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		    Connection conn = null;
		    try {
		      conn = JDBCUtil.getConnection();
		    } catch (SQLException e1) {
		      // TODO Auto-generated catch block
		      e1.printStackTrace();
		      throw e1;
		    }
		    List<Map<String,Object>> list=new ArrayList();
		    
		    PreparedStatement pmt = null; 
		    try {
		      ResultSet rs = null;
		      String sql="select b.id,b.homework_result_id,b.question_id,b.answer as studentAnswer,c.* from homework_result as a,homework_result_detail as b,title as c where a.id=b.homework_result_id and b.question_id=c.ItemID and b.homework_result_id=?;";
		      pmt=JDBCUtil.getPreparedStatement(conn, sql); 
		      pmt.setInt(1, HomeWorkResultDetailID);
		      rs = pmt.executeQuery();
		       while (rs.next()) {
	    	     Map<String,Object> HomeWorkResultDetailMap = new HashMap<>();
	    	     homeworkResultDetail HomeResultDetail=new homeworkResultDetail();
	    	     HomeResultDetail.setId(rs.getInt("id"));
	    	     HomeResultDetail.setHomeworkResultId(rs.getInt("homework_result_id"));
	    	     HomeResultDetail.setQuestionId(rs.getInt("question_id"));
	    	     HomeResultDetail.setAnswer(rs.getString("studentAnswer"));
		         Title title=new Title();
		         title.setItemId(rs.getInt("ItemID"));
		         title.setTitle(rs.getString("title"));
		         title.setType(rs.getInt("type"));
		         title.setOptionA(rs.getString("optionA"));
		         title.setOptionB(rs.getString("optionB"));
		         title.setOptionC(rs.getString("optionC"));
		         title.setOptionD(rs.getString("optionD"));
		         title.setAnswer(rs.getString("answer"));
		         title.setSubjectId(rs.getInt("SubjectID"));
		         title.setTeacherId(rs.getInt("teacherid"));    
		         HomeWorkResultDetailMap.put("HomeResultDetail", HomeResultDetail);
		         HomeWorkResultDetailMap.put("title", title);
		         list.add(HomeWorkResultDetailMap);
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
	  public static boolean teacherEvaluation(int studentId,int homeWorkId,String evaluation) throws Exception {
			
			// 数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
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
			
				String sql = "UPDATE homework_result SET Evaluation=?"
                +" where homeworkId=? and student_id=?";
				pmt = JDBCUtil.getPreparedStatement(conn, sql);
				pmt.setString(1, evaluation);
				pmt.setInt(2, homeWorkId);
				pmt.setInt(3, studentId);
				if (pmt.executeUpdate() > 0) {
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
}
