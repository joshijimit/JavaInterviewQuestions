package com.evolvision.android.model;



public class Questions {

	private int _ID;
	private String categoryID;
	private String question;
	private String answer;
	private int isFavorite;
	
	public int get_ID() {
		return _ID;
	}
	public void set_ID(int _ID) {
		this._ID = _ID;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	
	
}
