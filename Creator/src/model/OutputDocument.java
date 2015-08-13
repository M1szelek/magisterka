package model;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
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
        BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf, 12);
        
        for(Question q: qb.getQuestions()){
        
	        Paragraph p = new Paragraph(q.getContent(), font);
	
	        document.add(p);
	       
	        p = new Paragraph("a) " + q.getVarA().getContent(), font);
	        
	        document.add(p);
	        
	        p = new Paragraph("b) " + q.getVarB().getContent(), font);
	        
	        document.add(p);
	        
	        p = new Paragraph("c) " + q.getVarC().getContent(), font);
	        
	        document.add(p);
	        
	        document.add( Chunk.NEWLINE );
        }
        //Font f = new Font(bf,(float)22);
        //Paragraph p = new Paragraph("¯ó³æ", new Font(bf, 22));
        
        //document.add(new Paragraph("Kiedy wybuch³a II Wojna Œwiatowa kasjhfajhf fslfhqofgh qqwfgqwgo iiqh qowijqwoigfjh qwdfoijqwfiqjwf qwoifjqwoifhj qwd qowidi oqidjoqiwhfoiqhf oiqwfhqoiwfh",new Font(bf, 22)));
        // step 5
        document.close();
	}
}
