package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AnimatedFloor extends MapObject{
	public AnimatedFloor(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
		animation.start("animation");
	}
	
	@Override
	public void draw(Graphics2D g2d,ImageLoader gr){	
		g2d.drawImage(animation.getCurrentImage(),posX,posY,null);
	}
	
	@Override
	public void drawCollision(Graphics2D cm,ImageLoader gr){
		BufferedImage temp = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		Graphics2D d = temp.createGraphics();
		
		if(collides()){
			d.setPaint(Color.black);
			d.fillRect(0, 0, 50, 50);
			d.dispose();
			cm.drawImage(temp,posX,posY,null);
		}else{
			d.setColor(Color.white);
			d.fillRect(0, 0, 50, 50);
			d.dispose();
			cm.drawImage(temp,posX,posY,null);
		}
		
//		if(collides()){
//			BufferedImage temp = animation.getCurrentImage();
//			for(int i=0; i<temp.getWidth(); i++){
//				for(int j=0; j<temp.getHeight(); j++){
//					Color cc = new Color(temp.getRGB(i,j));
//					System.out.println(cc.getAlpha());
//				}
//			}
//		}
	}

	@Override
	public void update(){
		animation.animate();
	}
}
