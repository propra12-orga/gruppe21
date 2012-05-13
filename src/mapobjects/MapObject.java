package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Graphics2D;

import main.GameConstants;

/*
  abstract class for all MapObjects     	
*/

public abstract class MapObject {
	protected int posX;
	protected int posY;
	protected boolean visible;
	protected boolean destroyable;
	protected boolean collision;
	protected String imageUrl;
	protected Animation objectAnimation;
	
	public MapObject(int x,int y,boolean v,boolean d,boolean c,String img){
		posX=x;
		posY=y;
		visible=v;
		destroyable=d;
		collision=c;
		imageUrl = GameConstants.MAP_GRAPHICS_DIR+img;
		//TODO wenn imgUrl mit 'an:' beginnt dann anima
		//TODO allgemein wie animation hinzf
		//TODO setup animation , extra Thread ?
		
	}
	

	//all MapObjects have to implement
	public abstract void update();
	public abstract void draw(Graphics2D g2d,ImageLoader gr);

// Getter und Setter
	public String getImageUrl(){
		return imageUrl;
	}
	
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
