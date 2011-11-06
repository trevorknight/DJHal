import processing.core.PApplet;

public class SteerBar extends GUIElement {

	String name;
	float value;
	SteerButton leftButton;
	SteerButton rightButton;
    int steerBarNumber;

	SteerBar(DJHal _p, int _steerBarNumber, String _name, float _shownX, float _shownY,
			float _hiddenX, float _hiddenY, float _w, float _h) {
		super(_p, _shownX, _shownY, _hiddenX, _hiddenY, _w, _h);
		steerBarNumber = _steerBarNumber;
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
        p.fill(p.COLOUR2);
        System.out.println(p.steerValues[steerBarNumber]/ p.steerMaxs[steerBarNumber]);
        p.rect(x+h+10, y, p.lerp(x+h+10,w-2*h-20,p.steerValues[steerBarNumber]/p.steerMaxs[steerBarNumber]),h);
		p.fill(0);
		p.textAlign(PApplet.CENTER);
		p.text(name, x + (w) / 2, y + h / 2 + 5);
		leftButton.display();
		rightButton.display();
	}

    void clicked() {
        
        if (leftButton.contains(p.mouseX,p.mouseY)) {
            leftButton.clicked();
        }
        if (rightButton.contains(p.mouseX,p.messageAlpha)) {
            rightButton.clicked();
        }
    }

}