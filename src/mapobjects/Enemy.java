package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy  extends MoveableObject{
	private boolean hiddenObject = false;
	
	public Enemy(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	@Override
	public void move(){
		// also move hidden Object
	}
    
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm){	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
		
		cm.setPaint(Color.green);
		cm.fillRect(posX, posY, 50, 50);
		
	}


	@Override
	public void update(BufferedImage cm){
	}
	
	// hidden object released on die
	public void addHiddenObject(){}
	
	public void die(){}

	@Override
	public boolean hasObjectCollision(int x, int y, BufferedImage cm, String dir) {
		// TODO Auto-generated method stub
		return false;
	}
}
