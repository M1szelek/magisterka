package util;

import java.io.FileOutputStream;
import java.io.IOException;

import model.QBase;
import model.Question;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class OutputDocument {
	
	static public class HeaderFooterPageEvent extends PdfPageEventHelper {
	    private char letter;
		
		public HeaderFooterPageEvent(char letter) {
			super();
			this.letter = letter;
		}
		/*public void onStartPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);
	    }*/
	    public void onEndPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        //ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Left"), rect.getLeft(), rect.getBottom(), 0);
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase(new Phrase(letter + "/" + String.format("%d", writer.getCurrentPageNumber()))), rect.getRight(), rect.getBottom(), 0);
	    }
	} 
	
	public static void createDocumentAndCalque(QBase qb) throws DocumentException, IOException{
		createDocument(qb);
		createCalque2(qb);
	}
	
	public static void createDocument(QBase qb) throws DocumentException,
			IOException {

		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test"+qb.getLetterOfSet()+".pdf"));
		Rectangle rect = new Rectangle(30, 30, 550, 800);
	    writer.setBoxSize("art", rect);
		// step 3
	    HeaderFooterPageEvent event = new HeaderFooterPageEvent(qb.getLetterOfSet());
		writer.setPageEvent(event);
		document.open();
		// step 4
		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		
		System.out.println(document.getPageSize());

		Font font = new Font(bf, 12);
		
		Paragraph title = new Paragraph("Egzamin in�ynierski - zestaw "+qb.getLetterOfSet(), font);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		document.add(Chunk.NEWLINE);
		
		float indent = 30;

		List list = new List(List.ORDERED);
		list.setAutoindent(false);
		list.setSymbolIndent(indent);
		
		for (Question q : qb.getQuestions()) {

			ListItem item = new ListItem(q.getContent(), font);
			if (q.getImgInByte() != null) {
				Image img = Image.getInstance(q.getImgInByte());
				img.scalePercent((q.getImgScalePercent()));
				item.add(new ListItem(new Chunk(img, 0, 0, true)));
			}

			List vars = new List(List.ORDERED, List.ALPHABETICAL);
			vars.setAutoindent(false);
			vars.setSymbolIndent(indent);
			vars.setLowercase(true);

			ListItem varA = new ListItem(q.getVarA().getContent(), font);

			if (q.getVarA().getImgInByte() != null) {
				Image img = Image.getInstance(q.getVarA().getImgInByte());
				img.scalePercent((q.getVarA().getImgScalePercent()));
				varA.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varA);

			ListItem varB = new ListItem(q.getVarB().getContent(), font);

			if (q.getVarB().getImgInByte() != null) {
				Image img = Image.getInstance(q.getVarB().getImgInByte());
				img.scalePercent((q.getVarB().getImgScalePercent()));
				varB.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varB);

			ListItem varC = new ListItem(q.getVarC().getContent(), font);

			if (q.getVarC().getImgInByte() != null) {
				Image img = Image.getInstance(q.getVarC().getImgInByte());
				img.scalePercent((q.getVarC().getImgScalePercent()));
				varC.add(new ListItem(new Chunk(img, 0, 0, true)));
			}
			vars.add(varC);

			item.add(vars);
			item.add(Chunk.NEWLINE);

			list.add(item);

		}

		document.add(list);

		document.close();
	}

	public static void createCalque(QBase qb) throws DocumentException,
			IOException {
		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream("kalka"+qb.getLetterOfSet()+".pdf"));
		// step 3
		document.open();

		PdfPTable table = new PdfPTable(4);
		PdfPCell cell = new PdfPCell();
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("A"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("B"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("C"));
		table.addCell(cell);

		int i = 1;
		for (Question q : qb.getQuestions()) {
			cell = new PdfPCell(new Phrase(Integer.toString(i)));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarA().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarB().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarC().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			i++;
		}
		document.add(table);

		document.close();
	}

	public static void createCalque2(QBase qb) throws DocumentException,
			IOException {
		Document document = new Document(/*PageSize.A4.rotate()*/);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("kalka"+qb.getLetterOfSet()+".pdf"));
		// step 3
		document.open();
		
		BaseFont bf = BaseFont.createFont("Arial Unicode MS.ttf",
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		Font font = new Font(bf, 12);
		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(4);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell = new PdfPCell();
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("A"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("B"));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("C"));
		table.addCell(cell);
		
		table.setHeaderRows(1);
		
		

		int i = 1;
		for (Question q : qb.getQuestions()) {
			cell = new PdfPCell(new Phrase(Integer.toString(i)));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarA().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarB().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			cell = new PdfPCell();
			if (!q.getVarC().isCorrect())
				cell.setBackgroundColor(new BaseColor(0, 0, 0));
			table.addCell(cell);

			i++;
		}

		ColumnText column = new ColumnText(writer.getDirectContent());
		// List days = PojoFactory.getDays(connection);
//		float[][] x = { { document.left(), document.left() + 380 },
//				{ document.right() - 380, document.right() } };
		
		float[][] x = { { document.left(), document.left() + 250 },
				{ document.right() - 250, document.right() } };
		
			column.addElement(table);
			int count = 0;
			float height = 0;
			int status = ColumnText.START_COLUMN;
			while (ColumnText.hasMoreText(status)) {
				if (count == 0) {
				height = addHeaderTable(document,qb);
				}
				column.setSimpleColumn(x[count][0], document.bottom(),
						x[count][1], document.top() - height - 10);
				status = column.go();
				if (++count > 1) {
					count = 0;
					document.newPage();
				}
			}
		
		

		document.close();
	}
	
	static public float addHeaderTable(Document document, QBase qb)
	        throws DocumentException {
	        PdfPTable header = new PdfPTable(1);
	        header.setWidthPercentage(100);
	        header.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
	        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	        Phrase p = new Phrase("Zestaw "+Character.toUpperCase(qb.getLetterOfSet()), font);
	        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	        header.addCell(p);
	       // p = new Phrase(day.toString(), font);
	        //header.addCell(p);
	        //header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	        //p = new Phrase(String.format("page %d", page), font);
	        //header.addCell(p);
	        document.add(header);
	        return header.getTotalHeight();
	    }
	
	static public void createAnswerCard(QBase qb){
		
	}

}
