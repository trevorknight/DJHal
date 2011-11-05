import processing.core.PApplet;

public class TextBox extends GUIElement {
	
	String defaultText;
	boolean active;

	// SubmitButton addSong;
	// SubmitButton startNewSongSession;
	// SubmitButton startNewDecriptionSession;

	TextBox(DJHal _p, float _shownX, float _shownY, float _hiddenX, float _hiddenY, float _w, float _h, String _defaultText) {
		super(_p, _shownX, _shownY, _hiddenX, _hiddenY, _w, _h);
		defaultText = _defaultText;
		active = false;
	}

	void display() {
		super.display();
		p.rectMode(PApplet.CENTER);
		p.stroke(p.COLOUR1);
		p.strokeWeight(2);
		p.noFill();
		p.rectMode(PApplet.CORNER);
		p.rect(x, y, w, h);
		p.fill(p.COLOUR1);
		p.textAlign(PApplet.LEFT);
		if (!active) {
			p.text(defaultText, x + 3, y + h / 2 + 5);
		} else {
			p.text(p.typing, x + 3, y + h / 2 + 5);
			p.strokeWeight(3);
			p.stroke(p.COLOUR2);
			if (p.millis() % 1000 > 500) {
				p.line(x + p.textWidth(p.typing) + 3, y + 4, x + p.textWidth(p.typing)+ 3, y + h - 4);
			}
		}
	}

	void clicked() {
		if (!active) {
			active = true;
			p.typing = "";
		}

	}
}
