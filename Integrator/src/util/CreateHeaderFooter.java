package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
public class CreateHeaderFooter  {
	static public class HeaderFooterPageEvent extends PdfPageEventHelper {
	    public void onStartPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);
	    }
	    public void onEndPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Left"), rect.getLeft(), rect.getBottom(), 0);
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Right"), rect.getRight(), rect.getBottom(), 0);
	    }
	} 
	
	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {
	Document document = new Document();
	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HeaderFooter.pdf"));
        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
        document.open();
        document.add(new Paragraph("This is Page One"));
        document.newPage();
        document.add(new Paragraph("This is Page two"));
        document.close();
	System.out.println("Done");
    }
} 