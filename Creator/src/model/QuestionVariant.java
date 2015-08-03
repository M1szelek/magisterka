package model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class QuestionVariant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	transient private BufferedImage img;
	private byte[] imgInByte;

	public byte[] getImgInByte() {
		return imgInByte;
	}

	public void setImgInByte(byte[] imgInByte) {
		this.imgInByte = imgInByte;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QuestionVariant(){
		this.content = new String();
	}
	
	public QuestionVariant(String content) {
		super();
		this.content = content;
	}
	
	
}
