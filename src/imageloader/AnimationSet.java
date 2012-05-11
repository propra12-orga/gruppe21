package imageloader;

import java.awt.Image;
import java.util.Vector;

public class AnimationSet {
        String setName;
        GameImage defaultImage;
        Vector<GameAnimation> animationList = new Vector<GameAnimation>();
        Vector<GameImage> imageList = new Vector<GameImage>(); 
       
        
        public GameGraphic getAnimation(String n){
        	for(int i=0; i<animationList.size();i++){// Vector bei zugriff ausserhalb bereich kein prob ?
        		if(animationList.get(i).nameEquals(n)){
        			return animationList.get(i);
        		}
        	}
        	return defaultImage;
        }
        
       //TODO entweder Image oder Object ! 

}


