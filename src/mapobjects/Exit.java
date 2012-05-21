package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;

public class Exit extends MapObject{
	private boolean activated = true;
	
	public Exit(int x,int y,int r,boolean v,boolean d,boolean c,String p){
		super(x,y,r,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
		if(collides()){
			if (activated) {
				cm.setPaint(Color.yellow);
			} else {
				cm.setPaint(Color.black);
			}
			cm.fillRect(posX, posY, 50, 50);
		}else{
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
		}
	}
	@Override
	public void update(){
	}
	
	public void show(){}
	public void hide(){}
	public void activate(){}
	
}
