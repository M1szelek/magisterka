package model;

import java.awt.Image;

public class QuestionVariant {
	private String content;
	private Image img;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QuestionVariant(){
		this.content = new String();
	}
	
	public QuestionVariant(String content) {
		super();
		this.content = content;
	}
	
	
}
