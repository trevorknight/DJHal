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
	private String type = "&type=artist-description"; // types can be artist-description, song
	private String fmaArgs = "&bucket=id:fma&bucket=tracks&limit=true";
	private String[] keywords; // keywords from the session_info command
	private String playlistUrl;
	private String infoUrl;
	private String format;
	private PApplet parent;
	private String enKey;
	
	private String steerCommands;

	EchoNest(PApplet _parent) {
		parent = _parent;
		
		String[] temp = parent.loadStrings("enKey.txt");
		enKey = temp[0];

		format = "&format=json";
		playlistUrl = "http://developer.echonest.com/api/v4/playlist/dynamic?api_key="
				+ enKey;
		infoUrl = "http://developer.echonest.com/api/v4/playlist/session_info?api_key="
				+ enKey + format + "&session_id=";
		
		steerCommands = "";


	}

	public Song newSession(String _description) {
		String description = "&description=" + _description;
		String url = playlistUrl + type + format + fmaArgs + description;
		steerCommands = "";
		JSONObject response = getResponse(url);
		return getSong(response);
	}

	public Song nextSong() {
		String url = playlistUrl + "&session_id=" + sessionId + steerCommands;
		steerCommands = "";
		return getSong(getResponse(url));
	}
	
	public void steer(String steer) {
		System.out.println(steer);
		steerCommands += "&steer="+ steer;
	}

    public void getInfo(String[] steers, float[] steerValues) {
        JSONObject sessionInfo = getResponse(infoUrl + "&session_id=" + sessionId);
        try {
            JSONArray rules = sessionInfo.getJSONArray("rules");
            for (int i = 0; i < rules.length(); i++) {
                String rule = rules.getJSONObject(i).getString("rule");
                for (int j = 0; j < steers.length; j++) {
                    if (rule.contains(steers[j])) {
                        steerValues[j] = Float.parseFloat(rule.substring(rule.lastIndexOf(' ')+1));
                    }
                }
            }
        }
        catch (Exception e) {

        }
        
    }

	private JSONObject getResponse(String url) {
		System.out.println(url);
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
                    System.out.println(sessionId);
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
		String trackID = "";
		Song returningSong = new Song();
		try {
			JSONArray songs = response.getJSONArray("songs");
			JSONObject song = songs.getJSONObject(0);
			title = song.getString("title");
			artist = song.getString("artist_name");
			JSONArray trackInfo = song.getJSONArray("tracks");
			trackID = trackInfo.getJSONObject(0).getString("foreign_id");
			trackID = trackID.substring(trackID.lastIndexOf(':')+1);
		} catch (Exception e) {
			System.out.println("Error parsing JSON");
			System.out.println(e.toString());
			return returningSong;
		}
		returningSong.setTitle(title);
		returningSong.setArtist(artist);
		returningSong.setId(Integer.parseInt(trackID));
		System.out.println(returningSong.getArtist() + " " + returningSong.getTitle() + " " + returningSong.getId());
		return returningSong;
	}


}
