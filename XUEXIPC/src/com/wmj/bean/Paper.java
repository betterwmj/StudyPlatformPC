package com.wmj.bean;

import java.sql.Timestamp;



/*
 * 试卷
 */
public class Paper {
   private int testpaperID;
   private String testName;
   private int subjectID;
   private int userId;
   private int status;
   private Timestamp createTime;
   private int classId;
	public int getClassId() {
	return classId;
}
public void setClassId(int classId) {
	this.classId = classId;
}
	public int getStatus() {
	return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getTestpaperID() {
		return testpaperID;
	}
	public void setTestpaperID(int testpaperID) {
		this.testpaperID = testpaperID;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public int getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
   
}
