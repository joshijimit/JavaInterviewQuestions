package com.evolvision.android.model;

public class Item {

	private Integer ID;
	private String title;
	private String description;
	private int isFavourite;
	
	public Item(Integer ID,String title, String description, int isFavourite) {
		super();
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.isFavourite = isFavourite;
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
	
	public int getIsFavourite() {
		return isFavourite;
	}
	public void setIsFavourite(int isFavourite) {
		this.isFavourite = isFavourite;
	}	
	
}
