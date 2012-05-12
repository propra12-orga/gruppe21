package mapobjects;

import imageloader.ImageLoader;

import java.awt.Graphics2D;

public class Player extends MoveableObject{
	public Player(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void move(){}

	@Override
	public void update() {
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
	}


}
