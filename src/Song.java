//import processing.core.PApplet; // Needed for textWidth method

public class Song {
	
	private String artist;
	private String title;
	private int id;
	private String formattedText;
	
	
	// TODO implement smooth movement of song positions
	float y;
	float targetY;
	
	Song(){
		
	}
	
	// TODO use displayText to trim text down to less than half the width of the screen.
	public String displayText() {
		if (formattedText != null) {
			return formattedText;
		} else {
			formattedText = artist + " - " + title;
			return formattedText;
		}
		
	}
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
