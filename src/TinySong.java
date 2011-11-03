import org.json.*;
import processing.core.PApplet;

public class TinySong {
	
	//Class to look up a song on TinySong
	//The API Key must be in a file in data/tsKey.txt
	
	private static String url = "http://tinysong.com/b/";
	private static String format = "?format=json&key=";
	String formatAndKey;
	PApplet parent;
	
	TinySong(PApplet _parent, String apiKey) {
		formatAndKey = format + apiKey;
		parent = _parent;
	}
	
	public Song findSong(String query) {
		
		// returns the song artist, title, and Grooveshark ID
		// If it is not found, ID is set to -1
		
		query = query.replace(" ", "+");
		String[] response = parent.loadStrings(url+query+formatAndKey);
		// System.out.println(url+query+formatAndKey);
		String concatResponse = "";
		for (String line : response) {
			concatResponse += line;
		}
		Song song = new Song();
		// System.out.println(concatResponse);
		// If TinySong can't find the song, return a song ID of -1
		if (concatResponse.length() < 10){
			song.id = -1;
			return song;
		}
		
		try {
			JSONObject responseJSON = new JSONObject(concatResponse);
			song.id = Integer.parseInt(responseJSON.getString("SongID"));
			song.artist = responseJSON.getString("ArtistName");
			song.title = responseJSON.getString("SongName");
		} catch (JSONException e) {
			System.out.println(e.toString());
		}
		// System.out.println(song.artist + " " + song.title + " " + song.id);
		return song;
	}
	
}
