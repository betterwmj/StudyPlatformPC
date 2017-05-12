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

import com.wmj.bean.Paper;
import com.wmj.bean.PaperResult;
import com.wmj.bean.PaperResultDetail;
import com.wmj.bean.Title;
import com.wmj.util.JDBCUtil;
public class OperatorTestPaper {
	/*
	 * 获取该老师所创建的所有试卷
	 */
	public static  List<Paper> getTestPaperByTeacherId(int teacherId) throws Exception{
		
		 //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;
		List<Paper> list = new ArrayList<Paper>();
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
			String sql="select * from  paper where UserID=?";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setInt(1, teacherId);
			rs = pmt.executeQuery();
			 while (rs.next()) {
			   Paper paper=new Paper();
			   paper.setTestpaperID(rs.getInt("TestpaperID"));
			   paper.setTestName(rs.getString("TestName"));
			   paper.setSubjectID(rs.getInt("SubjectID"));
			   paper.setUserId(rs.getInt("UserID"));
			   paper.setStatus(rs.getInt("status"));
			   paper.setCreateTime(rs.getTimestamp("create_time"));
               list.add(paper);
         
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
	 * 获取该科目的所有试卷
	 */
	public static  List<Paper> getTestPaperBySubjectId(int subjectId,int studentId) throws Exception{
		
		 //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
		Connection conn = null;					
		List<Paper> list = new ArrayList<Paper>();
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
			String sql="select DISTINCT a.*,d.classid from paper as a, students as b , student_class_relationship as c, teacherclass_relation as d "
           +"where  b.UserID = c.studentid and c.classid = d.classID and d.teacherId = a.UserID and a.status=1 and d.subjectid = ? and a.SubjectID=? and b.UserID=? ";
			pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			pmt.setInt(1, subjectId);
			pmt.setInt(2, subjectId);
			pmt.setInt(3, studentId);
			rs = pmt.executeQuery();
			 while (rs.next()) {
			   Paper paper=new Paper();
			   paper.setTestpaperID(rs.getInt("TestpaperID"));
			   paper.setTestName(rs.getString("TestName"));
			   paper.setSubjectID(rs.getInt("SubjectID"));
			   paper.setUserId(rs.getInt("UserID"));
			   paper.setStatus(rs.getInt("status"));
			   paper.setCreateTime(rs.getTimestamp("create_time"));  
			   paper.setClassId(rs.getInt("classid"));
         list.add(paper);
         
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
	   * 发布试卷，更新试卷状态为1
	   */
	  public static  boolean updatePaper(int paperID) throws Exception{
	    
	     //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
	    boolean result=false;
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
	      String sql="update paper set status=1 where TestpaperID=?";
	      pmt=JDBCUtil.getPreparedStatement(conn, sql); 
	      pmt.setInt(1, paperID);
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
	   * 获取试卷详情
	   */
	  public static  List<Title> getPaperDetail(int paperID) throws Exception{
		    
		     //数据库连接的获取的操作，对用的是自己封装的一个util包中的类进行的操作
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
		      String sql="select a.score,b.* from paper_detail as a,title as b  where a.TitleID=b.ItemID and a.TestpaperID=?;";
		      pmt=JDBCUtil.getPreparedStatement(conn, sql); 
		      pmt.setInt(1, paperID);
		      rs = pmt.executeQuery();
		       while (rs.next()) {
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
		         title.setScore(rs.getInt("score"));
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
		 * 学生做完试卷提交到数据库
		 */
		public static boolean submitTestPaper(List<PaperResultDetail> list,PaperResult paper) throws Exception{
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
				int paperResultId = 0;
				ResultSet rs = null;
				String sql="insert into paper_result (paper_id,student_id,time,score) values(?,?,?,?)";
				pmt=JDBCUtil.getPreparedStatement(conn, sql,Statement.RETURN_GENERATED_KEYS); 	
				pmt.setInt(1, paper.getPaperId());
				pmt.setInt(2, paper.getStudentId());
				pmt.setTimestamp(3, paper.getTime());
				pmt.setInt(4, paper.getScore());
				//pmt.setDate(4, (Date) paper.getCreateTime());
				if(pmt.executeUpdate()>0){
					rs = pmt.getGeneratedKeys(); //获取结果   
					if (rs.next()) {
						paperResultId = rs.getInt(1);//取得ID
					} else {
						result = false;
						return result;
					}
					int resultCount = 0;
					for(int i=0;i<list.size();i++){
						PaperResultDetail paperTitle=list.get(i);
						String sqls="insert into paper_result_detail (paper_result_id,question_id,answer) values(?,?,?)";
						pmt=JDBCUtil.getPreparedStatement(conn, sqls); 
						pmt.setInt(1, paperResultId);;
						pmt.setInt(2, paperTitle.getQuestionId());
						pmt.setString(3, paperTitle.getAnswer());
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
			return result;
		}
		  /*
		   * 获取试卷结果
		   */
		  public static  List<Map<String,Object>> getPaperResult(int typeId,int paperID) throws Exception{
			    
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
			    String sql="";
			    try {
			      ResultSet rs = null;
			      if(typeId==0){
			    	  sql="select a.*, b.RealName from paper_result as a,students as b where  a.student_id=b.UserID and b.UserID=?;";
			      }else{
			    	  sql="select a.*, b.RealName from paper_result as a,students as b where  a.student_id=b.UserID and a.paper_id=?;";
					     
			      }
			     
			      pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			      pmt.setInt(1, paperID);
			      rs = pmt.executeQuery();
			       while (rs.next()) {
			    	 Map<String,Object> paperResultMap = new HashMap<>();
			    	 PaperResult paper=new PaperResult();
			    	 paper.setId(rs.getInt("id"));
			    	 paper.setPaperId(rs.getInt("paper_id"));
			    	 paper.setStudentId(rs.getInt("student_id"));
			    	 paper.setTime(rs.getTimestamp("time"));
			    	 paper.setScore(rs.getInt("score"));
			    	 paperResultMap.put("paper", paper);
			    	 paperResultMap.put("studentName", rs.getString("RealName"));
			    	 list.add(paperResultMap);
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
		  public static List<Map<String,Object>> getPaperResultDetail(int paperResultID) throws Exception{
			    
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
			      String sql="select b.id,b.paper_result_id,b.question_id,b.answer as studentAnswer,c.* from paper_result as a,paper_result_detail as b,title as c where a.id=b.paper_result_id and b.question_id=c.ItemID and b.paper_result_id=?;";
			      pmt=JDBCUtil.getPreparedStatement(conn, sql); 
			      pmt.setInt(1, paperResultID);
			      rs = pmt.executeQuery();
			       while (rs.next()) {
		    	     Map<String,Object> paperResultMap = new HashMap<>();
			    	 PaperResultDetail paperResultDetail=new PaperResultDetail();
			    	 paperResultDetail.setId(rs.getInt("id"));
			    	 paperResultDetail.setPaperResultId(rs.getInt("paper_result_id"));
			    	 paperResultDetail.setQuestionId(rs.getInt("question_id"));
			    	 paperResultDetail.setAnswer(rs.getString("studentAnswer"));
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
			         paperResultMap.put("paperResultDetail", paperResultDetail);
			         paperResultMap.put("title", title);
			         list.add(paperResultMap);
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
		//删除试卷
			public static boolean deletePaper(int paperId) throws Exception{
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
		            sql="delete a.*,b.* from paper as a, paper_detail as b where a.TestpaperID =b.TestpaperID and a.TestpaperID=?";
					pmt=JDBCUtil.getPreparedStatement(conn, sql); 
					pmt.setInt(1, paperId);
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
