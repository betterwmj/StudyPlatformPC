package com.wmj.bean;

import java.sql.Timestamp;

/*
 * 学生写的作业实体类
 */
public class HomeworkResult {
	private int id;
	private int homeworkId;
	private int studentId;
	private Timestamp time;
	private String evaluation;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	
} 
