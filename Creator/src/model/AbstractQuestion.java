package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public abstract class AbstractQuestion implements Serializable{
	/**
	 * 
	 */
	private String content;
	private static final long serialVersionUID = 1L;
	transient private BufferedImage img;
	private byte[] imgInByte;
	private int imgScalePercent;
	
	public void byteArrayToImg() throws IOException{
		if(imgInByte != null){
			this.img = ImageIO.read(new ByteArrayInputStream(this.imgInByte));
		}
	}
	
	public void deleteImg(){
		this.img = null;
		this.imgInByte = null;
	}
	
	public BufferedImage getImg() {
		return img;
	}


	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public byte[] getImgInByte() {
		return imgInByte;
	}


	public void setImgInByte(byte[] imgInByte) {
		this.imgInByte = imgInByte;
	}
	
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public AbstractQuestion() {
		super();
		this.content = new String();
		this.imgScalePercent = 100;
	}

	public AbstractQuestion(String content) {
		super();
		this.content = content;
		this.imgScalePercent = 100;
	}

	public int getImgScalePercent() {
		return imgScalePercent;
	}

	public void setImgScalePercent(int imgScalePercent) {
		this.imgScalePercent = imgScalePercent;
	}
	
	
	
	
	
	

}
