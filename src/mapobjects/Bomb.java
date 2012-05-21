package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Bomb extends MapObject{

	private int radius = 1;
	Animation armanimation;
	/*countdown (4s), explosiontime (1s) in nanosecs*/
	private long countdownTime = 4000000000L, explosionTime = 1000000000,
					beforeTime;
	private boolean exploding = false;

	public Bomb(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
		posX = (x+25)/50*50;
		posY = (y+25)/50*50;
		beforeTime = System.nanoTime();
		animation.start("simplebomb");
		armanimation = new Animation("simplebomb",gr);
		armanimation.start("up");
	}

	/*radius changes conditioned by upgrades*/
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void draw(Graphics2D g2d,ImageLoader gr,Graphics2D cm){
	g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
		if(collides()){
			cm.setPaint(Color.black);
			cm.fillRect(posX, posY, 50, 50);
		}else{
			cm.setPaint(Color.white);
			cm.fillRect(posX, posY, 50, 50);
		}
		
	if(exploding){
			g2d.drawImage(armanimation.getCurrentImage(), posX, posY-50, null);
			if(collides()){
				cm.setPaint(Color.black);
				cm.fillRect(posX, posY-50, 50, 50);
			}else{
				cm.setPaint(Color.white);
				cm.fillRect(posX, posY-50, 50, 50);
			}
			
			g2d.drawImage(rotate(armanimation.getCurrentImage(),1), posX+50, posY, null);
			if(collides()){
				cm.setPaint(Color.black);
				cm.fillRect(posX+50, posY, 50, 50);
			}else{
				cm.setPaint(Color.white);
				cm.fillRect(posX+50, posY, 50, 50);
			}
			
			g2d.drawImage(rotate(armanimation.getCurrentImage(),2), posX, posY+50, null);
			if(collides()){
				cm.setPaint(Color.black);
				cm.fillRect(posX, posY+50, 50, 50);
			}else{
				cm.setPaint(Color.white);
				cm.fillRect(posX, posY+50, 50, 50);
			}
			
			g2d.drawImage(rotate(armanimation.getCurrentImage(),3), posX-50, posY, null);
			if(collides()){
				cm.setPaint(Color.black);
				cm.fillRect(posX-50, posY, 50, 50);
			}else{
				cm.setPaint(Color.white);
				cm.fillRect(posX-50, posY+50, 50, 50);
			}
		}
	}
	

	@Override
	public void update(){
		if (visible && !exploding) {
			animation.animate();
			armanimation.animate();
			if (beforeTime + countdownTime <= System.nanoTime()) {
				animation.stop();
				animation.change("center");
				exploding = true;
				animation.start("center");
			}
		}
		
		if (visible && exploding) {
			animation.animate();
			if (beforeTime + countdownTime + explosionTime <= System.nanoTime()) {
				animation.stop();
				visible = false;
				destroyed = true;
			}
		}
	}

	public void explode() {
	//	animTickingBomb.stop();
	//	currentAnim = animExplosion;
	//	animExplosion.start();
//		exploding = true;
	}

//	public void increaseRadius(int rplus){}
//	public void decreaseRadius(int rminus){}
}