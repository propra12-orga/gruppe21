package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Wall extends MapObject{
	private boolean hiddenObject;
	
	public Wall(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
	}
	
	@Override
	public void drawCollision(Graphics2D cm,ImageLoader gr){
		BufferedImage temp = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		Graphics2D d = temp.createGraphics();
		
		if(collides()){
			d.setPaint(Color.black);
			d.fillRect(0, 0, 50, 50);
			d.dispose();
			cm.drawImage(temp,posX,posY,null);
		}else{
			d.setColor(Color.white);
			d.fillRect(0, 0, 50, 50);
			d.dispose();
			cm.drawImage(temp,posX,posY,null);
		}
	}

	@Override
	public void update(){
	}
	
	public void addHiddenObject(){}
	
	public void destroy(){
		//show hidden Object
	}
}
