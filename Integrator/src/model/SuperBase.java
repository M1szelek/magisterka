package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import util.DeepCopy;
import util.OutputDocument;

import com.itextpdf.text.DocumentException;

public class SuperBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<QBase> qbcoll;

	public ArrayList<QBase> getQbcoll() {
		return qbcoll;
	}
	
	public SuperBase() {
		super();
		this.qbcoll = new ArrayList<QBase>();
	}

	public void setQbcoll(ArrayList<QBase> qbcoll) {
		this.qbcoll = qbcoll;
	}
	
	public void add(QBase qb){
		qbcoll.add(qb);
	}
	
	public void remove(int i){
		qbcoll.remove(i);
	}
	
	private void generateDocuments(ArrayList<QBase> tests) throws DocumentException, IOException{
		for(QBase qb: tests){
			OutputDocument.createDocuments(qb);
		}
	}
	
	public void createSets(char setStartLetter, int count) throws DocumentException, IOException{
		ArrayList<QBase> res = new ArrayList<QBase>();
		
		setStartLetter = Character.toUpperCase(setStartLetter);
		
		for(int i = (int)setStartLetter; i < (int)setStartLetter + count; i++){
			res.add(createTest((char)i));
		}
		
		generateDocuments(res);
		
		//return res;
	}
	
	private QBase createTest(char setletter){
		
		QBase resbase = new QBase();							
		
		resbase.setLetterOfSet(setletter);
		resbase.setProfile(qbcoll.get(0).getProfile());
		
		for(QBase qb: qbcoll){
			
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			
			for(int i = 0; i < qb.getQuestions().size(); i++){
				tmp.add(i);
			}
			Collections.shuffle(tmp);
			
			for(int i = 0; i < qb.getAmountToTest(); i++){
				resbase.getQuestions().add(qb.getQuestions().get(tmp.get(i)));	//dodawanie losowo pytan z danej bazy do bazy testowej
			}
		}
		
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		for(int i = 0; i < resbase.getQuestions().size(); i++){
			tmp.add(i);
		}
		Collections.shuffle(tmp);
		
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();
		
		for(int i = 0; i < 3; i++){
			tmp2.add(i);
		}
		Collections.shuffle(tmp2);
		
		int div = (int) Math.round((float)resbase.getQuestions().size() / 3.0);
		
		
		
		for(int i = 0; i < resbase.getQuestions().size(); i++){
			if(i < div){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(tmp2.get(0));
			}
			if(div <= i && i < div*2){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(tmp2.get(1));
			}
			if(div*2 <= i){
				resbase.getQuestions().get(tmp.get(i)).setCorrectThenShuffle(tmp2.get(2));
			}
		}
		
		resbase.showCorrectPercentage();
		
		resbase = (QBase)DeepCopy.copy(resbase);				//DEEP COPY FOR THE RESCUE!!!!!!!!!!!!!!!
		
		return resbase;
		
	}
	
	private void setAllMaxAmountToTest(){
		for(QBase qb: qbcoll){
			qb.setAmountToTest(qb.getQuestions().size());
		}
	}
	
	public void createDocumentForWeb() throws DocumentException, IOException{
		setAllMaxAmountToTest();
		QBase qb = createTest('A');
		OutputDocument.createDocumentForWeb(qb);
		
	}
}
