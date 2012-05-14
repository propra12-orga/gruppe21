package mapobjects;

import imageloader.ImageLoader;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player extends MoveableObject{
	
	private boolean moveUP, moveDOWN, moveLEFT, moveRIGHT;
	
	public Player(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void move() {
		if (moveUP) {
			posX--;
		}
		
		if (moveDOWN) {
			posX++;
		}
		
		if (moveLEFT) {
			posY--;
		}
		
		if (moveRIGHT) {
			posY++;
		}
	}

	@Override
	public void update() {
		move();
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr) {	
		g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			direction = Direction.UP;	//TODO: enum sinnvoll?
			moveUP = true;
		}
		
		if (key == KeyEvent.VK_DOWN) {
			direction = Direction.DOWN;
			moveDOWN = true;
		}
		
		if (key == KeyEvent.VK_LEFT) {
			direction = Direction.LEFT;
			moveLEFT = true;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			direction = Direction.RIGHT;
			moveRIGHT = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			direction = Direction.UP;	
			moveUP = false;
		}
		
		if (key == KeyEvent.VK_DOWN) {
			direction = Direction.DOWN;
			moveDOWN = false;
		}
		
		if (key == KeyEvent.VK_LEFT) {
			direction = Direction.LEFT;
			moveLEFT = false;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			direction = Direction.RIGHT;
			moveRIGHT = false;
		}
	}
}
