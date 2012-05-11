package mapobjects;

import java.awt.Graphics2D;

public class Exit extends MapObject{
	private boolean activatet;
	
	public Exit(int x,int y,boolean v,boolean d,boolean c,String p){
		super(x,y,v,d,c,p);
	}
	
	@Override
	public void draw(Graphics2D g2d){	
	}

	@Override
	public void update(){
	}
	
	public void show(){}
	public void hide(){}
	public void activate(){}
	
}
