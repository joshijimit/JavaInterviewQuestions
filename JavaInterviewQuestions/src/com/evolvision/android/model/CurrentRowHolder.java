package com.evolvision.android.model;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CurrentRowHolder {
	
	public Integer questionID;
	public TextView value;
	public Button button;
	public ImageButton buttonFav;
	public ImageButton getButtonFav() {
		return buttonFav;
	}
	public void setButtonFav(ImageButton buttonFav) {
		this.buttonFav = buttonFav;
	}
	public TextView getValue() {
		return value;
	}
	public void setValue(TextView value) {
		this.value = value;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public Integer getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}
	
	
	
}
