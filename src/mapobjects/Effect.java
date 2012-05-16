package mapobjects;

import imageloader.ImageLoader;

import java.awt.Graphics2D;

public class Effect extends MapObject{
	public Effect(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
	}
    
	@Override
	public void update(){
	}
}
