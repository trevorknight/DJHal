//import processing.core.PApplet; // Needed for text width command

public class Song {
	
	private String artist;
	private String title;
	private int id;
	private String formattedText;
	
	Song(){
		
	}
	
	public String displayText() {
		if (formattedText != null) {
			return formattedText;
		} else {
			formattedText = artist + " - " + title;
			// TODO if statement to shorten the text if it is wider than a passed in variable
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
