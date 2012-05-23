package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Wall extends MapObject{
	private boolean hiddenObject;
	
	public Wall(int x,int y,int r,boolean v,boolean d,boolean c,String p){
		super(x,y,r,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
		
		if(collides()){
			if(destroyable){cm.setPaint(Color.gray);}else{cm.setPaint(Color.black);}
			cm.fillRect(posX, posY, 50, 50);
		}else{
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
		}
	}


	@Override
	public void update(BufferedImage cm){
		if(isDestroyable()){
			if(simpleHasColl(posX,posY,cm,Color.orange)){destroyed=true;visible=false;}
		}
	}
	
	public void addHiddenObject(){}
	
	public void destroy(){
		//show hidden Object
	}
}
