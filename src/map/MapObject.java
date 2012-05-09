package map;

/***********************************************************************
  MapObject: all objects drawn on the map are derivatet from this class
  2 Constructors:
  First one is for all animated MapObjects
  Second one is for MapObjects only using stills     	
 ************************************************************************/


public class MapObject {
	private int posX;
	private int posY;
	private boolean visible;
	private boolean destroyable;
	private boolean collision;
	private String filename;
	
	
	public MapObject(int x,int y,boolean v,boolean d,boolean c){
		posX=x;
		posY=y;
		visible=v;
		destroyable=d;
		collision=c;
		//TODO Bild oder Animation in jew unterklasse;
	}
	
	public MapObject(int x,int y,boolean v,boolean d,boolean c,String img){
		posX=x;
		posY=y;
		visible=v;
		destroyable=d;
		collision=c;
		filename = img;
	}

	
	public void draw(){
		//TODO draw imple
	}

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
