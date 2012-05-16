package mapobjects;

import imageloader.ImageLoader;

import java.awt.Graphics2D;

public abstract class MoveableObject extends MapObject {
	public Direction direction;
	protected int speed = 5;
	
	public MoveableObject(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	public abstract void move();
	
	public int getSpeed(){
		return speed;
	}
}


