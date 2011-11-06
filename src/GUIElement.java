import processing.core.PApplet;

public class GUIElement {

	float x;
	float y;
	float w;
	float h;
	float shownX;
	float shownY;
	float hiddenX;
	float hiddenY;
	private boolean hidden;
	DJHal p; //Parent PApplet

	GUIElement(DJHal _p, float _shownX, float _shownY, float _hiddenX, float _hiddenY, float _w, float _h) {
		shownX = _shownX;
		shownY = _shownY;
		hiddenX = _hiddenX;
		hiddenY = _hiddenY;
		hidden = true;
		x = hiddenX;
		y = hiddenY;
		w = _w;
		h = _h;
		p = _p;
	}

	void display() {
		if (hidden) {
			x = PApplet.lerp(x, hiddenX, 0.1f);
			y = PApplet.lerp(y, hiddenY, 0.1f);
		} else {
			x = PApplet.lerp(x, shownX, 0.1f);
			y = PApplet.lerp(y, shownY, 0.1f);
		}

	}

	boolean contains(float mouseX, float mouseY) {
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			return true;
		} else {
			return false;
		}
	}
	
	void showHide() {
		hidden = !hidden;
	}
	
	void show() {
		hidden = false;
	}
	
	void hide() {
		hidden = true;
	}
	
	boolean getHidden() {
		return hidden;
	}
	
	void setHidden(boolean b) {
		hidden = b;
	}

	void clicked() {

	}

}
