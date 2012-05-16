package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Graphics2D;

public class Bomb extends MapObject{
	
	private int radius = 1;
	
	/*countdown (4s), explosiontime (1s) in nanosecs*/
	private long countdownTime = 4000000000L, explosionTime = 1000000000,
					beforeTime;
	private boolean exploding = false;
	
	public Bomb(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
		beforeTime = System.nanoTime();
		animation.start("simplebomb");
		//animTickingBomb.start();
		//currentAnim = animTickingBomb;
	}
	
	/*radius changes conditioned by upgrades*/
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr){	
    //	g2d.drawImage(gr.getImage(imageUrl),posX,posY,null);
	g2d.drawImage(animation.getCurrentImage(), posX, posY, null);
	}
	
	@Override
	public void drawCollision(Graphics2D cm,ImageLoader gr){
	}
	@Override
	public void update(){
		animation.animate();
//		if (visible && !exploding) {
//			if (beforeTime + countdownTime <= System.nanoTime()) {
//				explode();
//			}
//		}
//		
//		if (visible && exploding) {
//			if (beforeTime + countdownTime + explosionTime <= System.nanoTime()) {
//		//		animExplosion.stop();
//				visible = false;
//			}
//		}
	}
	
	public void explode() {
	//	animTickingBomb.stop();
	//	currentAnim = animExplosion;
	//	animExplosion.start();
		exploding = true;
	}
	
//	public void increaseRadius(int rplus){}
//	public void decreaseRadius(int rminus){}
}
