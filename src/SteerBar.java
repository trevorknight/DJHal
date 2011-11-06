import processing.core.PApplet;

public class SteerBar extends GUIElement {

	String name;
	float value;
	SteerButton leftButton;
	SteerButton rightButton;

	SteerBar(DJHal _p, String _name, float _shownX, float _shownY,
			float _hiddenX, float _hiddenY, float _w, float _h) {
		super(_p, _shownX, _shownY, _hiddenX, _hiddenY, _w, _h);
		name = _name;
		leftButton = new SteerButton(p, this, shownX, shownY, hiddenX, hiddenY, h,
				"left");
		rightButton = new SteerButton(p, this, shownX + w - h, shownY, hiddenX + w
				- h, hiddenY, h, "right");
	}

	void display() {
		super.display();
		p.noStroke();
		p.fill(200);
		p.rect(x + h + 10, y, w - 2 * h - 20, h);
		p.fill(0);
		p.textAlign(PApplet.CENTER);
		p.text(name, x + (w) / 2, y + h / 2 + 5);
		leftButton.display();
		rightButton.display();
	}

}