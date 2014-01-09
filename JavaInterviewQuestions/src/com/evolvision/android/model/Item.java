package com.evolvision.android.model;

public class Item {

	private Integer ID;
	private String title;
	private String description;
	private int isFavorite;
	
	public Item(Integer ID,String title, String description, int isFavorite) {
		super();
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.isFavorite = isFavorite;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}	
	
}
