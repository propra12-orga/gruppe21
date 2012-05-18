package mapobjects;

import imageloader.ImageLoader;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class MoveableObject extends MapObject {
	public Direction direction;
	protected int speed = 3;
	
	public MoveableObject(int x,int y,boolean v,boolean d,boolean c,String p,ImageLoader gr){
		super(x,y,v,d,c,p,gr);
	}
	
	public abstract void move(BufferedImage cm);
	
	public boolean hasObjectCollision(int x, int y,BufferedImage cm,String dir){
		if(x<0 || y<0 || x>cm.getWidth() || y>cm.getHeight()){return true;}
		int t=20;
		int cornercounter = 0;
		boolean collision = false;
		boolean upleft = false;
		boolean upright = false;
		boolean downleft = false;
		boolean downright = false;
		BufferedImage collTest = cm.getSubimage(x, y, 50, 50);
		for(int i=0; i<collTest.getWidth(); i++){
			for(int j=0; j<collTest.getHeight(); j++){
				Color test = new Color(collTest.getRGB(i, j));
				if(test.equals(Color.black)){
					if(i<t && j<t){upleft = true;}
					else if(i>50-t && j<t){upright = true;}
					else if(i<t && j>50-t){downleft = true;}
					else if(i>50-t && j>50-t){downright = true;}
					else{return true;}
				}
			}
			
		}
		
	    if(upleft ^ upright ^ downleft ^ downright){
	    	if(upleft){
	    		if(dir.equals("UP")){
	    			System.out.println("corner upleft");
	    		    this.posX+=1;
	    		    System.out.println(posX);
	    			return true;
	    		}
	    		
	    		if(dir.equals("LEFT")){
	    			System.out.println("corner upleft");
	    		    this.posY+=1;
	    		    System.out.println(posY);
	    			return true;
	    		}
	    	}
	    	
	    	if(upright){
	    		if(dir.equals("UP")){
	    			System.out.println("corner upright");
	    		    this.posX-=1;
	    		    System.out.println(posX);
	    			return true;
	    		}
	    		
	    		if(dir.equals("RIGHT")){
	    			System.out.println("corner upright");
	    			this.posY+=1;
	    		    System.out.println(posY);
	    			return true;
	    		}
	    	}
	    	
	    	if(downleft){
	    		if(dir.equals("DOWN")){
	    			System.out.println("corner downleft");
	    		    this.posX+=1;
	    		    System.out.println(posX);
	    			return true;
	    		}
	    		
	    		if(dir.equals("LEFT")){
	    			System.out.println("corner downleft");
	    		    this.posY-=1;
	    		    System.out.println(posY);
	    			return true;
	    		}
	    	}
	    	
	    	if(downright){
	    		if(dir.equals("DOWN")){
	    			System.out.println("corner downright");
	    		    this.posX-=1;
	    		    System.out.println(posX);
	    			return true;
	    		}
	    		
	    		if(dir.equals("RIGHT")){
	    			System.out.println("corner downright");
	    			this.posY-=1;
	    		    System.out.println(posY);
	    			return true;
	    		}
	    	}
	    }
		return false;
	}
	
	public boolean hasEnemyCollision(int x, int y){
		return false;
	}
	
	public int getSpeed(){return speed;}
}


