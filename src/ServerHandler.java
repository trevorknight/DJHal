import processing.net.*;
import processing.core.PApplet;
import java.util.Queue;
import java.util.LinkedList;

public class ServerHandler {

	private Server server;
	private PApplet p;
	private String response;

	private Queue<String> outboundText;

	ServerHandler(PApplet _p, int port) {
		p = _p;
		server = new Server(_p, port);

		outboundText = new LinkedList<String>();
	}

	public void sendText(String text) {
		
		text = "(" + text + ")";
		outboundText.add(text);
	}

	public void emptyQueue() {
//		if (!outboundText.isEmpty()) {

			Client someClient = server.available();

			if (someClient != null) {
				response = someClient.readString();
				int stringEnd = response.indexOf('&');
				response = response.substring(9, stringEnd);
				System.out.println("Client says: " + response);
				server.write(response + outboundText.poll());
				server.disconnect(someClient);
			}
//		}
	}

}
