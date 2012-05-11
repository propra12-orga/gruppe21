package mapobjects;

import java.awt.Graphics2D;

public class Bomb extends MapObject{
	int radius = 1;
	
	public Bomb(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d){	
	}

	@Override
	public void update(){
	}
	
	public void increaseRadius(int rplus){}
	public void decreaseRadius(int rminus){}
}
