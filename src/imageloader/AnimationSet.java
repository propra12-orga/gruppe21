package imageloader;

import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import main.GameConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class AnimationSet {
		private Document setXML;
		private Element setRoot;
	    private String setName;
        public GameImage defaultImage;
        private Vector<GameAnimation> animationList = new Vector<GameAnimation>();
        private Vector<GameImage> imageList = new Vector<GameImage>(); 
       
        public AnimationSet(String sn,String type){
        	setName = sn;
        	System.out.println("ani set aufger");
        	try {
    			setXML = new SAXBuilder().build(GameConstants.ANIMATION_FILES_DIR+type+"/"+sn+".xml");
    		} catch (JDOMException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
        	setRoot = setXML.getRootElement();
       //reads animation  xml file , loads animations in animationlist
       // TODO optimieren , ist das mit den einzelbildern sinnvoll ?
        	List<Element> animations = setRoot.getChildren("animation");
        	List<Element> stills = setRoot.getChildren("still");
        	defaultImage = new GameImage(GameConstants.ANIMATION_FILES_DIR+type+"/"+setRoot.getAttributeValue("default")); 
        	
        	for(int i=0; i<animations.size(); i++){
        		System.out.println("funzt");
        		animationList.add(new GameAnimation(
        				animations.get(i).getAttributeValue("name"),
        				Integer.parseInt(animations.get(i).getAttributeValue("frames")),
        				GameConstants.ANIMATION_FILES_DIR+type+"/"+animations.get(i).getText()                            
        				));
        	}
        	
        	for(int i=0; i<stills.size(); i++){
        		imageList.add(new GameImage(
        				GameConstants.ANIMATION_FILES_DIR+type+"/"+stills.get(i).getText()
        				)); 
        	}
        }
        
        public GameGraphic getAnimation(String n){
        	for(int i=0; i<animationList.size();i++){
        		if(animationList.get(i).nameEquals(n)){
        			return animationList.get(i);
        		}
        	}
        	return defaultImage;
        }

		public Image getDefault() {
			return defaultImage.getImage();
		}
		
		public String getDefaultName(){
			return defaultImage.getPath();
		}
		
		public Boolean nameEquals(String n){
			return n.equals(setName);
		}
        
		public String getSetName(){
			return setName;
		}
       //TODO entweder Image oder Object ! 

}


