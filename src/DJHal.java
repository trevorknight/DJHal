import processing.core.*;
import java.util.Random;
import java.util.ArrayList;

public class DJHal extends PApplet {

	/**
	 * Main class for DJHal
	 */
	
	// GUI
	public PFont fontA;
	final public int COLOUR2 = color(255,85,0,180);
	final public int COLOUR1 = color(200);
	final public int BLACK = color(0);
	final public int WHITE = color(255);
	String typing;
	ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
	
	// SESSION
	ArrayList<Song> songs = new ArrayList<Song>();
	String[] descriptors;
	String description;
	
	// ECHONEST
	private String enKey;
	EchoNest echoNest;
	
	// SERVER
	ServerHandler serverHandler;
	
	// OTHER
	Random rand = new Random();
	
	
	// =====================================
	//           METHODS
	// =====================================
	public static void main(String[] args) {
		PApplet.main(new String[] {"--present", "DJHal"});
	}
	
	public void setup() {
		fontA = loadFont("Georgia-36.vlw");
		size(1000,800);
		smooth();
		
		// SESSION
		descriptors = loadStrings("descriptors.txt");
		
		// SERVER
		serverHandler = new ServerHandler(this, 8081);
		
		// ECHONEST
		echoNest = new EchoNest(this);
		newENSession(getRandomDescription());
		
		// GUI 
		guiElements.add(new TextBox(this,500,200,400,75,"Type a description"));
		
	}
	
	public void draw() {
		background(255);
		showTitle();
		displaySongHistory();
		displayGuiElements();
		serverHandler.emptyQueue();
	}
	

	// KEYBOARD AND MOUSE EVENTS
	// =====================================
	public void mousePressed(){
		for (GUIElement g : guiElements) {
			if (g.contains(mouseX, mouseY)) {
				g.clicked();
			}
		}
	}
	
	public void keyTyped() {
		  if (key == BACKSPACE) {
		    typing = typing.substring(0, constrain(typing.length()-1,0,200));
		  } else if (keyCode == TAB || key == ENTER || key == RETURN) {
		    newENSession(typing);
		    typing = "";
		  } else {
		    typing += key;
		  }
		  if (key == '0') {
			  playSong(echoNest.nextSong());
			  
		  }
		}
	
	// SONGS AND DESCRIPTION
	// =====================================
	private void newENSession(String descrip) {
		description = descrip;
		descriptors = append(descriptors, descrip);
		saveStrings("descriptors.txt", descriptors);
		descrip = descrip.replace(" ", "%20");
		playSong(echoNest.newSession(descrip));	
	}
	
	// returns a random descriptor to be used to start a playlist
	private String getRandomDescription() { 
		int i = rand.nextInt(descriptors.length);
		return descriptors[i];
	}
	
	private void playSong(Song song) {
		songs.add(song);
		serverHandler.sendText(Integer.toString(song.id));
	}
	
	
	// GUI ELEMENT METHODS
	// =====================================
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
