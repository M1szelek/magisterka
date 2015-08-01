package model;
import java.io.Serializable;
import java.util.ArrayList;


public class QBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String author;
	ArrayList<Question> questions;
	
	public QBase(String name, String author) {
		super();
		this.name = name;	
		this.author = author;
		questions = new ArrayList<Question>();
		addQuestion();
	}
	
	public void addQuestion(){
		questions.add(new Question());
	}
	
	public void setContent(int i, String var){
		if(i < questions.size())
			questions.get(i).setContent(var);
		else
			System.out.println("Poza tablico");
	}
	
	public void setVarA(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarA(new QuestionVariant(var));
		else
			System.out.println("Poza tablico");
	}
	
	public void setVarB(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarB(new QuestionVariant(var));
		else
			System.out.println("Poza tablico");
	}
	
	public void setVarC(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarC(new QuestionVariant(var));
		else
			System.out.println("Poza tablico");
	}
	
	public void removeQuestion(int i){
		questions.remove(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	
	
	
	
}