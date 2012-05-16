package map;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel{
	public Map map;
	public Board(){
		map = new Map("testmap");
		}
	
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		map.update();
		map.drawMap(g2d);
		//g2d.drawImage(map.CollisionMap,0,0,null);
		g2d.dispose();
		waiting(200);
	}
	
	public void update(){repaint();};
	public BufferedImage toBuff(String p){
		
		ImageIcon ii = new ImageIcon(p);
	    Image image = ii.getImage();
	    BufferedImage buff = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = buff.createGraphics();
		b.drawImage(image,0,0,null);
		b.dispose();
		
		return buff;
	}
	
	public  void waiting (int n){
		         long t0, t1;
		         t0 =  System.currentTimeMillis();
		         do{
		        	 t1 = System.currentTimeMillis();
		         }
		         	while (t1 - t0 < n);
    }
	
	public void keyPressed(KeyEvent e) {
	int key = e.getKeyCode();
	
	if (key == KeyEvent.VK_UP) {
		map.mapplayer.direction.UP.set(true);
	}
	
	if (key == KeyEvent.VK_DOWN) {
		map.mapplayer.direction.DOWN.set(true);
	}
	
	if (key == KeyEvent.VK_LEFT) {
		map.mapplayer.direction.LEFT.set(true);
	}
	
	if (key == KeyEvent.VK_RIGHT) {
		map.mapplayer.direction.RIGHT.set(true);
	}
	}

	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			map.mapplayer.direction.UP.set(false);
		}
		
		if (key == KeyEvent.VK_DOWN) {
			map.mapplayer.direction.DOWN.set(false);
		}
		
		if (key == KeyEvent.VK_LEFT) {
			map.mapplayer.direction.LEFT.set(false);
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			map.mapplayer.direction.RIGHT.set(false);
		}
		
	}

	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}	
