package com.evolvision.android.model;



public class Categories {

	private int _ID;
	private String categoryName;
	private String categoryDesc;
	
	public Categories(){}
	
	public Categories(String categoryName, String categoryDesc) {
		super();
		this.categoryName = categoryName;
		this.categoryDesc = categoryDesc;
	}
	
	
	
	public int get_ID() {
		return _ID;
	}

	public void set_ID(int _ID) {
		this._ID = _ID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	@Override
	public String toString() {
		return "Book [id=" + _ID + ", title=" + categoryName + ", author=" + categoryDesc
				+ "]";
	}
	
	
	
}
