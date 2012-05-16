package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends MoveableObject{
	private int maxbombs = 1;
	private int lives = 1;
	//private vector updates
	//private boolean moveUP, moveDOWN, moveLEFT, moveRIGHT;
	
	public Player(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	@Override
	public void move() {
		if (direction.UP.is()) {
			posY-=speed;
			//animation.change("playerUp");
		}
		
		if (direction.DOWN.is()) {
			posY+=speed;
			//animation.change("playerDown");
		}
		
		if (direction.LEFT.is()) {
			posX-=speed;
			//animation.change("playerLeft");
		}
		
		if (direction.RIGHT.is()) {
			posX+=speed;
			//animation.change("playerRight");
		}
	}

	@Override
	public void update() {
		animation.animate();
		move();
		
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm) {	
		g2d.drawImage(animation.getCurrentImage(),posX,posY,null);
		
		cm.setPaint(Color.green);
		cm.fillRect(posX, posY, 50, 50);
		
		
	}
	
//	public void keyPressed(KeyEvent e) {
//		int key = e.getKeyCode();
//		
//		if (key == KeyEvent.VK_UP) {
//			direction = Direction.UP;	//TODO: enum sinnvoll?
//			moveUP = true;
//		}
//		
//		if (key == KeyEvent.VK_DOWN) {
//			direction = Direction.DOWN;
//			moveDOWN = true;
//		}
//		
//		if (key == KeyEvent.VK_LEFT) {
//			direction = Direction.LEFT;
//			moveLEFT = true;
//		}
//		
//		if (key == KeyEvent.VK_RIGHT) {
//			direction = Direction.RIGHT;
//			moveRIGHT = true;
//		}
//	}
//
}
