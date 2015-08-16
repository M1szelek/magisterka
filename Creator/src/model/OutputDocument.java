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
        Document document = new Document(PageSize.A4);
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
        // step 3
        document.open();
        // step 4
        BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf, 12);
        
        int i = 1;
        float indent = 40;
        
        for(Question q: qb.getQuestions()){
        	List list_content = new List(List.ORDERED);
        	list_content.setPostSymbol(".         ");
	        list_content.setFirst(i);
	        
	        list_content.add(new ListItem(q.getContent(),font));
	        
	        List list_var = new List(List.ORDERED,List.ALPHABETICAL);
	        
	        list_var.setIndentationLeft(indent);
	        list_var.setPostSymbol(")   ");
	        
	        list_var.add(new ListItem(q.getVarA().getContent(),font));
	        list_var.add(new ListItem(q.getVarB().getContent(),font));
	        list_var.add(new ListItem(q.getVarC().getContent(),font));
	        
	        list_content.add(list_var);
	        
	        document.add(list_content);
	        document.add(Chunk.NEWLINE);
	        
	        i++;
	        
        }
        
        /*for(Question q: qb.getQuestions()){
        
	        Paragraph questionContent = new Paragraph(q.getContent(), font);
	
	        document.add(questionContent);
	        
	        Paragraph questionVarA = new Paragraph("a) " + q.getVarA().getContent(), font);
	        Paragraph questionVarB = new Paragraph("b) " + q.getVarB().getContent(), font);
	        Paragraph questionVarC = new Paragraph("c) " + q.getVarC().getContent(), font);
	        
	        
	        questionVarA.setIndentationLeft(indent);
	        questionVarB.setIndentationLeft(indent);
	        questionVarC.setIndentationLeft(indent);
	       
	        //p = new Paragraph("a) " + q.getVarA().getContent(), font);
	        
	        document.add(questionVarA);
	        document.add(questionVarB);
	        document.add(questionVarC);
	        
	        
	       
	        
	        document.add( Chunk.NEWLINE );
	        
	        
	        
	        List list = new List(List.ORDERED);
	        list.setFirst(1);
	        
	        list.add(new ListItem("Cos tam"));
	        
	        List list2 = new List(List.ORDERED,List.ALPHABETICAL);
	        
	        list2.setIndentationLeft(indent);
	        list2.setPostSymbol(")");
	        
	        list2.add(new ListItem("VarA"));
	        list2.add(new ListItem("VarB"));
	        list2.add(new ListItem("VarC"));
	        
	        Image img = Image.getInstance(new URL("http://zdjecia.pl.sftcdn.net/pl/scrn/76000/76818/microsoft-small-basic-22.jpg"));
	        
	        list2.add(img);
	        list2.add(new ListItem(new Chunk(img,0,-100)));
	   
	        list.add(list2);
	        
	        document.add(list);
	        document.add(Chunk.NEWLINE);
	        
	        list = new List(List.ORDERED);
	        list.setFirst(2);
	        
	        list.add(new ListItem("Cos tam as asd f asd asd fqwwqd qwdqwd fqwfqwdqw qwdqfq wfqw qwd qwd qwdqwfdf qwdqwd qwdqwd qw fqw qwd."));
	        
	        List list3 = new List(List.ORDERED,List.ALPHABETICAL);
	        
	        list3.setIndentationLeft(20);
	        
	        list3.add(new ListItem("VarA"));
	        list3.add(new ListItem("VarB"));
	        list3.add(new ListItem("VarC"));
	        
	   
	        
	        list.add(list3);
	        
	       
	        document.add(list);
        }*/
        //Font f = new Font(bf,(float)22);
        //Paragraph p = new Paragraph("���", new Font(bf, 22));
        
        //document.add(new Paragraph("Kiedy wybuch�a II Wojna �wiatowa kasjhfajhf fslfhqofgh qqwfgqwgo iiqh qowijqwoigfjh qwdfoijqwfiqjwf qwoifjqwoifhj qwd qowidi oqidjoqiwhfoiqhf oiqwfhqoiwfh",new Font(bf, 22)));
        // step 5
        document.close();
	}
	
	
}
