import processing.core.PApplet;

public class TextBox extends GUIElement {
	
	String defaultText;
	boolean active;

	// SubmitButton addSong;
	// SubmitButton startNewSongSession;
	// SubmitButton startNewDecriptionSession;

	TextBox(PApplet _parent, float _x, float _y, float _w, float _h, String _defaultText) {
		super(_parent, _x, _y, _w, _h);
		defaultText = _defaultText;
		active = false;
	}

	void display() {
		parent.rectMode(parent.CENTER);
		parent.stroke(parent.GREY);
		parent.strokeWeight(1);
		parent.noFill();
		parent.rectMode(parent.CORNER);
		parent.rect(x, y, w, h);
		parent.fill(parent.GRAY);
		parent.textAlign(parent.LEFT);
		if (!active) {
			parent.text(defaultText, x + 3, y + h / 2 + 5);
		} else {
			text(parent.typing, x + 3, y + h / 2 + 5);
			parent.strokeWeight(3);
			stroke(parent.ORANGE);
			if (parent.millis() % 1000 > 500) {
				line(x + textWidth(parent.typing) + 3, y + 4, x + textWidth(parent.typing)+ 3, y + h - 4);
			}
		}
	}

	void clicked() {
		if (!active) {
			active = true;
			parent.typing = "";
		}

	}
}
