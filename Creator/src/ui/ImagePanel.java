package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private BufferedImage resizedImage;

    public ImagePanel(BufferedImage _image) {
       if(_image != null){
	   		image = _image;
	  }else{
		  image = null;
	  }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.resizedImage, 0, 0, null); // see javadoc for more info on the parameters            
    }

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
    
	public void resizeImage(){
		
		if(this.image == null){
			this.resizedImage = null;
			this.repaint();
			return;		
		}
		
		int type = this.image.getType() == 0? BufferedImage.TYPE_INT_ARGB : this.image.getType();
		
		double scalex = (double)this.getWidth() / (double)this.image.getWidth();
		double scaley = (double)this.getHeight() / (double)this.image.getHeight();
		
		int IMG_WIDTH = (int) (this.image.getWidth() * scalex);
		int IMG_HEIGHT = (int) (this.image.getHeight() * scaley);
		
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(this.image, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);				//nie skaluje ale tylko wycinek pokazuje TODO: fix it
		g.dispose();
		
		this.resizedImage = resizedImage;
		
		this.repaint();
	 
		//return resizedImage;
	}
    

}
