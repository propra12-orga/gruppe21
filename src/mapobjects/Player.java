package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends MoveableObject{
	private int maxbombs = 1;
	private int lives = 1;
	
	public Player(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	@Override
	public void move(BufferedImage cm) {
		int newX,newY;
		if (direction.UP.is()) {
			if(hasObjectCollision(posX,posY-speed,cm)){}
			else{posY-=speed;}
			animation.change("playerUp");
		}
		
		if (direction.DOWN.is()) {
			if(hasObjectCollision(posX,posY+speed,cm)){}
			else{posY+=speed;}
			animation.change("playerDown");
		}
		
		if (direction.LEFT.is()) {
			if(hasObjectCollision(posX-speed,posY,cm)){}
			else{posX-=speed;}
			animation.change("playerLeft");
		}
		
		if (direction.RIGHT.is()) {
			if(hasObjectCollision(posX+speed,posY,cm)){}
			else{posX+=speed;}
			animation.change("playerRight");
		}
	}

	@Override
	public void update() {
		animation.animate();
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm) {	
		g2d.drawImage(animation.getCurrentImage(),posX,posY,null);
		
		cm.setPaint(Color.green);
		cm.fillRect(posX, posY, 50, 50);	
	}
}