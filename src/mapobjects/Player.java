package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends MoveableObject{
	private int maxbombs = 3;
	private int actualbombs = 0;
	private int lives = 1;
	private String bombtype = "standart";
	
	public Player(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	//TODO UseWeapon or LayBomB,ChangeWeapon
	
	@Override
	public void move(BufferedImage cm) {
		if (direction.UP.is()) {
			if(hasObjectCollision(posX,posY-speed,cm,"UP")){}
			else{posY-=speed;}
			animation.change("playerUp");
		}
		
		if (direction.DOWN.is()) {
			if(hasObjectCollision(posX,posY+speed,cm,"DOWN")){}
			else{posY+=speed;}
			animation.change("playerDown");
		}
		
		if (direction.LEFT.is()) {
			if(hasObjectCollision(posX-speed,posY,cm,"LEFT")){}
			else{posX-=speed;}
			animation.change("playerLeft");
		}
		
		if (direction.RIGHT.is()) {
			if(hasObjectCollision(posX+speed,posY,cm,"RIGHT")){}
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
	
	public boolean reachedMaxBombs(){
		return(actualbombs+1>maxbombs);
	}

	public void addBomb() {
		actualbombs++;
	}
}