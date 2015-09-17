package model;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class QBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String author;
	transient private String profile;						//FINALNIE WYWALIC TRANSIENT!!!!
	transient private String subjectCode;					//FINALNIE WYWALIC TRANSIENT!!!!
	ArrayList<Question> questions;
	
	public QBase(String name, String author, String profile, String subjectCode) {
		super();
		this.name = name;
		this.author = author;
		this.profile = profile;
		this.subjectCode = subjectCode;
		questions = new ArrayList<Question>();
		addQuestion();
	}



	public String getProfile() {
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}



	public String getSubjectCode() {
		return subjectCode;
	}



	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}



	public void addQuestion(){
		questions.add(new Question());
	}
	
	public void setContent(int i, String var){
		if(i < questions.size())
			questions.get(i).setContent(var);
		else
			System.out.println("Poza tablica");
	}
	
	public void setVarA(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarA(new QuestionVariant(var));
		else
			System.out.println("Poza tablica");
	}
	
	public void setVarB(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarB(new QuestionVariant(var));
		else
			System.out.println("Poza tablica");
	}
	
	public void setVarC(int i, String var){
		if(i < questions.size())
			questions.get(i).setVarC(new QuestionVariant(var));
		else
			System.out.println("Poza tablica");
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
	
	public void byteArrayToImg() throws IOException{
		for(Question q: questions){
			q.byteArrayToImg();
			q.getVarA().byteArrayToImg();
			q.getVarB().byteArrayToImg();
			q.getVarC().byteArrayToImg();
		}
	}
	
	public void shuffleVariants(){
		for(Question q: questions){
			q.shuffleVariants();;
		}
	}
	
	
	
	
	
}
