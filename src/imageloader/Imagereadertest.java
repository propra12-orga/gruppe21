package imageloader;

public class Imagereadertest {
	static ImageLoader stack = new ImageLoader();
	
	public static void main(String[] args){

		stack.addImage("/graphics/game/map/placeholder.png");
		stack.addImage("/graphics/game/map/test1.png");
		stack.addImage("/graphics/game/map/nocheintest.png");
		stack.printNames();
	}
	
}
