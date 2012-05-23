package mapobjects;

import imageloader.Animation;
import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;

public class Bomb extends MapObject{

	private int radius = 1;
	Animation armanimation;
	Animation endanimation;
	int arms[] = new int[4];
	boolean playerleft = false;
	/*countdown (4s), explosiontime (1s) in nanosecs*/
	private long countdownTime = 4000000000L, explosionTime = 1000000000,
					beforeTime;
	private boolean exploding = false;

	public Bomb(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr,int r,BufferedImage cm){
		super(x,y,v,d,c,p,gr);
		posX = (x+25)/50*50;
		posY = (y+25)/50*50;
		radius = r;
		arms[0] = r;
		arms[1] = r;
		arms[2] = r;
		arms[3] = r;
		beforeTime = System.nanoTime();
		animation.start("simplebomb");
		armanimation = new Animation("simplebomb",gr);
		endanimation = new Animation("simplebomb",gr);
		armanimation.start("gerade");
		endanimation.start("up");
		
		//armlängen berechnen
		for(int i=0;i<arms.length;i++){
			for(int j=0;j<arms[i];j++){
				if(i==0){if(simpleHasColl(posX,posY-((j+1)*50),cm,Color.black)){arms[i]=j;break;}}
				if(i==1){if(simpleHasColl(posX+((j+1)*50),posY,cm,Color.black)){arms[i]=j;break;}}
				if(i==2){if(simpleHasColl(posX,posY+((j+1)*50),cm,Color.black)){arms[i]=j;break;}}
				if(i==3){if(simpleHasColl(posX-((j+1)*50),posY,cm,Color.black)){arms[i]=j;break;}}
			}
		}
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
			cm.setPaint(Color.orange);
			cm.fillRect(posX, posY, 50, 50);
		}
		
	if(exploding){
		
			
			
			for(int i=0;i<arms.length;i++){
				for(int j=0;j<arms[i];j++){
					if(i==0){
						if(j==arms[i]-1){
							g2d.drawImage(rotate(endanimation.getCurrentImage(),i), posX, posY-(j+1)*50, null);
						}
						else{
							g2d.drawImage(rotate(armanimation.getCurrentImage(),i), posX, posY-(j+1)*50, null);
						}
					    cm.setPaint(Color.orange);
					    cm.fillRect(posX, posY-(j+1)*50, 50, 50);
					}
					if(i==1){
						if(j==arms[i]-1){
							g2d.drawImage(rotate(endanimation.getCurrentImage(),i), posX+(j+1)*50,posY, null);
						}
						else{
						g2d.drawImage(rotate(armanimation.getCurrentImage(),i), posX+(j+1)*50, posY, null);
						}
						cm.setPaint(Color.orange);
					    cm.fillRect(posX+(j+1)*50, posY, 50, 50);
					}
					if(i==2){
						if(j==arms[i]-1){
							g2d.drawImage(rotate(endanimation.getCurrentImage(),i), posX, posY+(j+1)*50, null);
						}
						else{
						g2d.drawImage(rotate(armanimation.getCurrentImage(),i), posX, posY+(j+1)*50, null);
						}
						cm.setPaint(Color.orange);
					    cm.fillRect(posX, posY+(j+1)*50, 50, 50);
					}
					if(i==3){
						if(j==arms[i]-1){
							g2d.drawImage(rotate(endanimation.getCurrentImage(),i), posX-(j+1)*50, posY, null);
						}
						else{
						g2d.drawImage(rotate(armanimation.getCurrentImage(),i), posX-(j+1)*50, posY, null);
						}
						cm.setPaint(Color.orange);
					    cm.fillRect(posX-(j+1)*50, posY, 50, 50);
					}
					
				}
			}
		}
	}
	

	@Override
	public void update(BufferedImage cm){
		armanimation.animate();
		endanimation.animate();
		if (visible && !exploding) {
			animation.animate();
			
			if (beforeTime + countdownTime <= System.nanoTime()) {
				animation.stop();
				animation.change("center");
				exploding = true;
				animation.start("center");
			}
		}
		
		if (visible && exploding) {
			animation.animate();
			collision=false;
			if (beforeTime + countdownTime + explosionTime <= System.nanoTime()) {
				animation.stop();
				visible = false;
				destroyed = true;
				
			}
		}
		
		if(!playerleft){
		if(!simpleHasColl(this.posX,this.posY,cm,Color.green)){playerleft=true;collision=true;}
		}
	}
}

