package map;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel{
	public Map map;
	public Board(){
		map = new Map("testmap");
		}
	
	public void paint(Graphics g){
		
		
		
		Graphics2D g2d = (Graphics2D) g;
		map.drawMap(g2d);
	}
}
