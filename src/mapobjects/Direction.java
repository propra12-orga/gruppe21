package mapobjects;

public enum Direction{
	UP(false),
	DOWN(false),
    RIGHT(false),
	LEFT(false);
	
	private Boolean status ;
	
	private Direction(Boolean status){
	this.status=status;
	}
	
	public boolean is(){return status;}
    public void set(Boolean s){this.status = s;}
	
}
