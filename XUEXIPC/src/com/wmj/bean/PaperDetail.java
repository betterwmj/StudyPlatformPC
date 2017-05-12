package com.wmj.bean;
/*
 * 试卷详细题实体类
 */
public class PaperDetail {
    private int id;
    private int titleId;
    private int testPaperId;
    private int score;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	
	public int getTestPaperId() {
		return testPaperId;
	}
	public void setTestPaperId(int testPaperId) {
		this.testPaperId = testPaperId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
