import processing.core.PApplet;

public class Button extends GUIElement {

	Button(DJHal _p, float _shownX, float _shownY, float _hiddenX, float _hiddenY, float _w, float _h) {
		super(_p, _shownX, _shownY, _hiddenX, _hiddenY, _w, _h);
		System.out.println(shownX + " " + shownY  + " " + hiddenX  + " " + hiddenY);
	}

	void display() {
		super.display();
		if (contains(p.mouseX,p.mouseY)) {
			p.fill(p.COLOUR2);
		} else {
			p.fill(p.COLOUR1);
		}
		p.noStroke();
		p.rectMode(PApplet.CORNER);
		p.rect(x, y, w, h);
	}

}
