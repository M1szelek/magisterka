package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class OutputDocument {
	public static void createDocument(QBase qb) throws DocumentException, IOException{
		
        
     // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
        // step 3
        document.open();
        // step 4
        BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf, 12);
        float indent = 30;
        
        List list = new List(List.ORDERED);
        list.setAutoindent(false);
        list.setSymbolIndent(indent);
       // list.set
       // Image img = Image.getInstance(new URL("http://zdjecia.pl.sftcdn.net/pl/scrn/76000/76818/microsoft-small-basic-22.jpg"));
        //img.setAlignment(Image.RIGHT);
        //img.setScaleToFitHeight(false);
        for(Question q: qb.getQuestions()){
        	
        	
        	ListItem item = new ListItem(q.getContent(),font);
        	if(q.getImgInByte() != null){
        		Image img = Image.getInstance(q.getImgInByte());
        		item.add(new ListItem(new Chunk(img,0,0,true)));
        	}
        	
        	List vars = new List(List.ORDERED, List.ALPHABETICAL);
        	vars.setAutoindent(false);
            vars.setSymbolIndent(indent);
            vars.setLowercase(true);
        	
        	ListItem varA = new ListItem(q.getVarA().getContent(),font);
        	
        	if(q.getVarA().getImgInByte() != null){
        		Image img = Image.getInstance(q.getVarA().getImgInByte());
        		varA.add(new ListItem(new Chunk(img,0,0,true)));
        	}
        	vars.add(varA);
        	
        	ListItem varB = new ListItem(q.getVarB().getContent(),font);
        	
        	if(q.getVarB().getImgInByte() != null){
        		Image img = Image.getInstance(q.getVarB().getImgInByte());
        		varB.add(new ListItem(new Chunk(img,0,0,true)));
        	}
        	vars.add(varB);
        	
        	ListItem varC = new ListItem(q.getVarC().getContent(),font);
        	
        	if(q.getVarC().getImgInByte() != null){
        		Image img = Image.getInstance(q.getVarC().getImgInByte());
        		varC.add(new ListItem(new Chunk(img,0,0,true)));
        	}
        	vars.add(varC);
        	
        	
        	item.add(vars);
        	item.add(Chunk.NEWLINE);

        	list.add(item);
	       
	        
        }
        
        document.add(list);
       
        document.close();
        
        document.close();
	}
	
	
}
