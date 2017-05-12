package com.wmj.bean;
/*
 * 学生作业详细题及回答答案
 */
public class homeworkResultDetail {
  private int id;
  private int homeworkResultId;
  private int questionId;
  private String answer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHomeworkResultId() {
		return homeworkResultId;
	}
	public void setHomeworkResultId(int homeworkResultId) {
		this.homeworkResultId = homeworkResultId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
