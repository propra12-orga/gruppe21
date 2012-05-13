package audio;

public abstract class GameSound {
	
	private String filename;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public abstract void loadSound();
	public abstract void play(boolean loop);
	public abstract void pause();
	public abstract void resume();
	public abstract void stop();
}
