package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class MoveableObject extends MapObject {
	public Direction direction;
	protected int speed = 3;
	
	public MoveableObject(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	public abstract void move();
	
	public abstract boolean hasObjectCollision(int x, int y,BufferedImage cm,String dir);
	
	public boolean hasEnemyCollision(int x, int y){
		return false;
	}
	
	public int getSpeed(){return speed;}
}


