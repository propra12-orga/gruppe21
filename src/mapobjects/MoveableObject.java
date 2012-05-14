package mapobjects;

import java.awt.Graphics2D;

public abstract class MoveableObject extends MapObject{
	protected Direction direction;
	protected int dx;
	protected int dy;
	
	public MoveableObject(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	public abstract void move();
}


