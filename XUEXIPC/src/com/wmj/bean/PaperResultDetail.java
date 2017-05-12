package com.wmj.bean;
/*
 * 学生所做试卷详细题目类
 */
public class PaperResultDetail {
   private int id;
   private int paperResultId;
   private int QuestionId;
	private String answer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPaperResultId() {
		return paperResultId;
	}
	public void setPaperResultId(int paperResultId) {
		this.paperResultId = paperResultId;
	}
	public int getQuestionId() {
		return QuestionId;
	}
	public void setQuestionId(int questionId) {
		QuestionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
