package model;


public class QuestionVariant extends AbstractQuestion {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean correct;

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public QuestionVariant(){
		super();
		this.correct = false;
	}
	
	public QuestionVariant(String content) {
		super(content);
		this.correct = false;
	}
	
	
	
	
}
