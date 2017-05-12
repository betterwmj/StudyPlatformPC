package com.wmj.bean;

import java.sql.Timestamp;


/*
 * 老师发布作业实体类
 */
public class HomeWork {
   private int homeId;
   private String homeWorkName;
   private  Timestamp time;
   private int teacherId;
   private int subjectId;
   private Timestamp finishTime;
   private int status;
	public String getHomeWorkName() {
		return homeWorkName;
	}
	public void setHomeWorkName(String homeWorkName) {
		this.homeWorkName = homeWorkName;
	}

	public int getHomeId() {
		return homeId;
	}
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp time) {
		this.finishTime = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	} 
}
