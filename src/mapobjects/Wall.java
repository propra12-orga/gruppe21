package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends MapObject{
	private boolean hiddenObject;
	
	public Wall(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
		
		if(collides()){
			cm.setPaint(Color.black);
			cm.fillRect(posX, posY, 50, 50);
		}else{
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
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
