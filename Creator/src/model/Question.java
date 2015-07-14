package model;
import java.awt.Image;


public class Question {
	private String content;
	private QuestionVariant varA;
	private QuestionVariant varB;
	private QuestionVariant varC;
	private int correct;
	
	private Image imgContent;
	
	
	public Question() {
		super();
		this.content = new String();
		this.varA = new QuestionVariant();
		this.varB = new QuestionVariant();
		this.varC = new QuestionVariant();
		this.correct = 1;
		
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public QuestionVariant getVarA() {
		return varA;
	}


	public void setVarA(QuestionVariant varA) {
		this.varA = varA;
	}


	public QuestionVariant getVarB() {
		return varB;
	}


	public void setVarB(QuestionVariant varB) {
		this.varB = varB;
	}


	public QuestionVariant getVarC() {
		return varC;
	}


	public void setVarC(QuestionVariant varC) {
		this.varC = varC;
	}


	public int getCorrect() {
		return correct;
	}


	public void setCorrect(int correct) {
		this.correct = correct;
	}


	public Image getImgContent() {
		return imgContent;
	}


	public void setImgContent(Image imgContent) {
		this.imgContent = imgContent;
	}	
	
	
}
