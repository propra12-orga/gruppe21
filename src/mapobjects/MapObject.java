package mapobjects;

import imageloader.Animation;

import java.awt.Graphics2D;

/*
  abstract class for all MapObjects     	
*/

public abstract class MapObject {
	private int posX;
	private int posY;
	private boolean visible;
	private boolean destroyable;
	private boolean collision;
	private String imageUrl;
	private Animation objectAnimation;
	
	public MapObject(int x,int y,boolean v,boolean d,boolean c,String img){
		posX=x;
		posY=y;
		visible=v;
		destroyable=d;
		collision=c;
		imageUrl = img;
		//TODO wenn imgUrl mit 'an:' beginnt dann anima
		//TODO allgemein wie animation hinzf
		//TODO setup animation , extra Thread ?
		
	}
	

	//all MapObjects have to implement
	public abstract void update();
	public abstract void draw(Graphics2D g2d);

// Getter und Setter
	
	public int getPosX(){
		return posX;
	}
	
	public void setPosX(int x){
		posX = x;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public void setPosY(int y){
		posY = y;
	}
	
	public boolean isDestroyable(){
		return destroyable;
	}
	
	public void setDestroyable(boolean b){
		destroyable = b;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setVisible(boolean b){
		visible = b;
	}
	
	public boolean collides(){
		return collision;
	}
	
	public void setCollision(boolean b){
		collision = b;
	}
}
