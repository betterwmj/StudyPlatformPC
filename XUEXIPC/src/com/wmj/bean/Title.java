package com.wmj.bean;
/*
 * 选择题实体类
 */
public class Title {
   private int itemId;
   private String title;
   private int type;
   private String optionA;
   private String optionB;
   private String optionC;
   private String optionD;
   private String answer;
   private int subjectId;
   private int teacherId;
   private int score;
   public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public int getType() {
	return type;
	}
	public void setType(int type) {
		this.type = type;
	}
   public int getItemId() {
	return itemId;
   }
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	   
}
