
public class GUIElement {

	float x;
	float y;
	float w;
	float h;
	DJHal p; //Parent PApplet

	GUIElement(DJHal _p, float _x, float _y, float _w, float _h) {
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		p = _p;
	}

	void display() {

	}

	boolean contains(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			return true;
		} else {
			return false;
		}
	}

	void clicked() {

	}

}
