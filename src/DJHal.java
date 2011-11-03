import processing.core.*;
import java.util.Random;
import java.util.ArrayList;

public class DJHal extends PApplet {

	/**
	 * Main class for DJHal
	 */
	
	// INTERFACE
	public PFont fontA;
	final public int ORANGE = color(255,85,0,180);
	final public int  GREY = color(200);
	final public int BLACK = color(0);
	final public int WHITE = color(255);
	String typing;
	ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
	
	// ECHONEST
	private String enKey;
	EchoNest echoNest;
	
	// SESSION INFO
	ArrayList<Song> songs = new ArrayList<Song>();
	String description;
	
	// OTHER
	Random rand = new Random();
	
	
	// =====================================
	public void setup() {
		fontA = loadFont("Georgia-36.vlw");
		size(screenWidth,screenHeight);
		smooth();
		
		// APIs
		String[] temp = loadStrings("enKey.txt");
		enKey = temp[0];
		echoNest = new EchoNest(this, enKey);
		songs.add(echoNest.newSession(getRandomDescription()));
		
		// GUI ELEMENTS
		// guiElements.add(new TextBox(this,500,200,400,75,"Type a description"));
		
	}
	
	// =====================================
	public void draw() {
		background(255);
		showTitle();
		displaySongHistory();
		displayGuiElements();
		
	}
	
	public void mousePressed(){
		songs.add(echoNest.nextSong());
	}
	
	// =====================================
	public static void main(String[] args) {
		PApplet.main(new String[] {"--present", "DJHal"});
	}
	
	private String getRandomDescription() { // returns a random descriptor from the database to start a new playlist
		String[] descriptors = loadStrings("descriptors.txt");
		int i = rand.nextInt(descriptors.length);
		description = descriptors[i];
		return description;
	}
	
	private void displayGuiElements() {
		for (GUIElement g : guiElements) {
			g.display();
		}
	}
	
	private void displaySongHistory() {
		int fillColor = 0;
		fill(200);
		textFont(fontA,16);
		text("Now: ", 10, 150);
		text("Next: ", 10, 100);
		textFont(fontA,24);
		int newestSong = songs.size()-1;
		for (int i = newestSong; i >= 0; i--) {
			fill(fillColor);
			text(songs.get(i).artist + " - " + songs.get(i).title, 50, 150+((newestSong - i)*50));
			fillColor += 20;
		}
	}
	
	private void showTitle() {
		fill(200);
		textFont(fontA, 36);
		text("Now Playing: " + description + " songs", 50, 50);
	}
}
