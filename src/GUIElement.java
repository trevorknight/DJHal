import processing.core.PApplet;

public class GUIElement {

	float x;
	float y;
	float w;
	float h;
	PApplet parent;

	GUIElement(PApplet _parent, float _x, float _y, float _w, float _h) {
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		parent = _parent;
	}

	void display() {

	}

	boolean contains() {
		if (parent.mouseX > x && parent.mouseX < x + w && parent.mouseY > y && parent.mouseY < y + h) {
			return true;
		} else {
			return false;
		}
	}

	void clicked() {

	}

}
