import org.json.*;
import processing.core.PApplet;

public class EchoNest {

	// This class communicates with Echonest
	// -------------------------------------
	// public Song newSession(description) -- starts a new playlist with the
	// description and returns the first song, complete with Grooveshark song ID
	//
	// public Song nextSong() -- gets the next song of the playlist
	//
	// TODO getInfo();

	private String sessionId; // Unique string provided back by EN
	private String type = "&type=artist-description"; // types can be artist,
														// song,
	private String[] keywords; // keywords from the session_info command
	private String playlistUrl;
	private String infoUrl;
	private String format;
	private PApplet parent;
	private String enKey;

	private String tsKey;
	private TinySong tinySong;

	EchoNest(PApplet _parent, String _enKey) {

		enKey = _enKey;

		format = "&format=json";
		playlistUrl = "http://developer.echonest.com/api/v4/playlist/dynamic?api_key="
				+ enKey + format;
		infoUrl = "http://developer.echonest.com/api/v4/playlist/session_info?api_key="
				+ enKey + format + "&session_id=";

		parent = _parent;

		String[] temp = parent.loadStrings("tsKey.txt");
		tsKey = temp[0];
		tinySong = new TinySong(parent, tsKey);

	}

	public Song newSession(String _description) {
		String description = "&description=" + _description;
		String url = playlistUrl + type + description;
		JSONObject response = getResponse(url);
		return getSong(response);
	}

	public Song nextSong() {
		String url = playlistUrl + "&session_id=" + sessionId;
		return getSong(getResponse(url));
	}

	private JSONObject getResponse(String url) {
		String[] returnedStrings = parent.loadStrings(url);
		String returnedString = "";
		for (String s : returnedStrings) {
			returnedString += s;
		}
		JSONObject response = new JSONObject();
		try {
			JSONObject returnedJSON = new JSONObject(returnedString);
			response = returnedJSON.getJSONObject("response");
			JSONObject status = response.getJSONObject("status");
			if (status.getString("message").equals("Success")) {
				if (sessionId == null) {
					sessionId = response.getString("session_id");
					// System.out.println(sessionId);
				}
			} else {
				System.out.println("Error with Echonest response!");
				System.out.println(status.getString("message"));
			}
		} catch (JSONException e) {
			System.out.println("Error parsing JSON");
			System.out.println(e.toString());
		}
		return response;
	}

	private Song getSong(JSONObject response) {
		String title = "";
		String artist = "";
		try {
			JSONArray songs = response.getJSONArray("songs");
			JSONObject song = songs.getJSONObject(0);
			title = song.getString("title");
			artist = song.getString("artist_name");
		} catch (JSONException e) {
			System.out.println("Error parsing JSON");
			System.out.println(e.toString());
		}
		Song returningSong = tinySong.findSong(artist + " " + title);
		if (returningSong.id < 0) { // Check if TinySong couldn't find it
			returningSong = nextSong(); // and get the next song instead
		}
		// System.out.println(returningSong.artist + " " + returningSong.title + " " + returningSong.id);
		return returningSong;
	}
}
