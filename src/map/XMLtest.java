package map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class XMLtest extends JFrame implements KeyListener{
	static Board myb;
	public XMLtest(){
		
		myb = new Board();
		add(myb);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
		setFocusable(true);
		setLocationRelativeTo(null);
		setTitle("MapReader Test");
		setVisible(true);
		addKeyListener(this);
	}
	
	public static void main(String[] args){
		new XMLtest();
		while(true){XMLtest.update();}
	}
	
	public static void update(){myb.update();}


public void keyPressed(KeyEvent e) {
	myb.keyPressed(e);

}


public void keyReleased(KeyEvent e) {
	myb.keyReleased(e);
	
}


public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}
