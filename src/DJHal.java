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
	String message;
	boolean showMessage;
	boolean isPlaying = true;
	int messageColour;
	int messageAlpha;
	int messageStart;
	String typing;
	ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
	
	// SESSION
	ArrayList<Song> songs = new ArrayList<Song>();
    Song nextSong;
	ArrayList<String> descriptors = new ArrayList<String>();
	private String description;
	
	// ECHONEST
	EchoNest echoNest;
	String[] steers = {"energy", "tempo", "song_hotttnesss", "danceability", "loudness"};
    float[] steerValues = {0, 0, 0, 0, 0};
    float[] steerMaxs = {1.0f, 250.0f, 1.0f, 1.0f, -50.0f};
	
	
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
		size(1200,800);
		smooth();
		
		// SESSION
		loadDescriptors();
		
		// ECHONEST
		echoNest = new EchoNest(this);
		newENSession(getRandomDescription());
		
		// GUI 
		guiElements.add(new TextBox(this,width-500,50,width,50,450,50,"Type a description"));
		for (int i = 0; i < steers.length; i++) {
			guiElements.add(new SteerBar(this, i, steers[i], width/2+50, 110+(height/14+5)*i, width, 100+(height/12+25)*i, width/2-75, height/14));
		}
		
		message = "";
		messageColour = color(220,10,10);
		messageAlpha = 0;
		showMessage = false;
		
	}
	
	public void draw() {
		background(255);
		showTitle();
		displaySongHistory();
		drawGuiElements();
		displayMessage();
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
              echoNest.getInfo(steers, steerValues);
              for (float f : steerValues) {
                  System.out.println(f);
              }
		  }
		  if (key == '9') {
			  showHideControls();
		  }
		}
	
	// SONGS AND DESCRIPTION
	// =====================================
	private void newENSession(String descrip) {
		String subsDescrip = descrip.replace(" ", "%20");
		Song tempSong = echoNest.newSession(subsDescrip);
		if (tempSong.getArtist() != null) {
			StringFunctions.addIfNew(descriptors, descrip);
			setDescription(descrip);
			saveDescriptors();
			playSong(tempSong);
		} else {
			setMessage("Sorry, no " + descrip + " songs found.");
		}
	}
	
	// returns a random descriptor to be used to start a playlist
	private String getRandomDescription() { 
		int i = rand.nextInt(descriptors.size());
		return descriptors.get(i);
	}
	
	private void playSong(Song song) {
		songs.add(song);
		
		Object[] parsed_file_contents = Stream.getStreamURL(song.id)
		streamurl = parsed_file_contents[0].toString();
		Stream.Play(streamurl);
		isPlaying = true;
		length = parsed_file_contents[1].toString();
				
	}
	
	private void loadDescriptors() {
		String[] tempDescriptors = loadStrings("descriptors.txt");
		for (String s : tempDescriptors) {
			descriptors.add(s);
		}
	}
	
	private void saveDescriptors() {
		String[] tempDescriptors = new String[descriptors.size()];
		descriptors.toArray(tempDescriptors);
		saveStrings("data/descriptors.txt", tempDescriptors);
	}
	
	private void setDescription(String d) {
		description = d;
	}
	
	
	// GUI ELEMENT METHODS
	// =====================================
	private void displayMessage() {
		if ((millis()/1000) - messageStart > 5 ) {
			showMessage = false;
		}
		if (showMessage) {
			messageAlpha = constrain(messageAlpha+10,0,255);
		} else {
			messageAlpha = constrain(messageAlpha-10,0,255);
		}
		fill(color(red(messageColour), green(messageColour), blue(messageColour), messageAlpha));
		text(message,width-400,25);
	}
	
	public void setMessage(String text) {
		message = text;
		showMessage = true;
		messageStart = millis()/1000;
	}
	
	private void drawGuiElements() {
		for (GUIElement g : guiElements) {
			g.display();
		}
	}
	
	private void showHideControls() {
		for (GUIElement g : guiElements) {
			g.showHide();
		}
	}
	
	private void displaySongHistory() {
		int fillColor = 0;
		fill(200);
		textFont(fontA,16);
		textAlign(LEFT);
		text("Now: ", 10, 150);
		text("Next: ", 10, 100);
		textFont(fontA,24);
		int newestSong = songs.size()-1;
		for (int i = newestSong; i >= 0; i--) {
			fill(fillColor);
			text(songs.get(i).getArtist() + " - " + songs.get(i).getTitle(), 50, 150+((newestSong - i)*50));
			fillColor += 20;
		}
	}
	
	private void showTitle() {
		fill(200);
		textFont(fontA, 36);
		textAlign(LEFT);
		text("Now Playing: " + description + " songs", 50, 50);
	}
}
