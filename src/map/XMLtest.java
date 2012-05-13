package map;

import javax.swing.JFrame;

public class XMLtest extends JFrame{
	
	public XMLtest(){
		
		add(new Board());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,450);
		setLocationRelativeTo(null);
		setTitle("MapReader Test");
		setVisible(true);
	}
	
	public static void main(String[] args){
		new XMLtest();
	}
}
