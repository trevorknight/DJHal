import processing.core.PApplet;

public class SteerButton extends Button {

	String direction;
	float[] triangle_vertices;
	SteerBar steerBar;
	
	SteerButton(DJHal _p, SteerBar _steerBar, float _shownX, float _shownY, float _hiddenX,	float _hiddenY, float _s, String _direction) {
		super(_p, _shownX, _shownY, _hiddenX, _hiddenY, _s, _s);
		steerBar = _steerBar;
		setHidden(steerBar.getHidden());
		direction = _direction;
		triangle_vertices = new float[6];

		if (direction.equals("right")) {
			triangle_vertices[0] = PApplet.map(0.15f, 0, 1, x, x + w);
			triangle_vertices[1] = PApplet.map(0.15f, 0, 1, y, y + h);
			triangle_vertices[2] = PApplet.map(0.15f, 0, 1, x, x + w);
			triangle_vertices[3] = PApplet.map(0.85f, 0, 1, y, y + h);
			triangle_vertices[4] = PApplet.map(0.85f, 0, 1, x, x + w);
			triangle_vertices[5] = y + h / 2;
		}
		if (direction.equals("left")) {
			triangle_vertices[0] = PApplet.map(0.15f, 0, 1, x + w, x);
			triangle_vertices[1] = PApplet.map(0.15f, 0, 1, y, y + h);
			triangle_vertices[2] = PApplet.map(0.15f, 0, 1, x + w, x);
			triangle_vertices[3] = PApplet.map(0.85f, 0, 1, y, y + h);
			triangle_vertices[4] = PApplet.map(0.85f, 0, 1, x + w, x);
			triangle_vertices[5] = y + h / 2;
		}
	}

	void display() {
		setHidden(steerBar.getHidden());
		super.display();
		if (direction.equals("right")) {
			triangle_vertices[0] = PApplet.map(0.15f, 0, 1, x, x + w);
			triangle_vertices[1] = PApplet.map(0.15f, 0, 1, y, y + h);
			triangle_vertices[2] = PApplet.map(0.15f, 0, 1, x, x + w);
			triangle_vertices[3] = PApplet.map(0.85f, 0, 1, y, y + h);
			triangle_vertices[4] = PApplet.map(0.85f, 0, 1, x, x + w);
			triangle_vertices[5] = y + h / 2;
		}
		if (direction.equals("left")) {
			triangle_vertices[0] = PApplet.map(0.15f, 0, 1, x + w, x);
			triangle_vertices[1] = PApplet.map(0.15f, 0, 1, y, y + h);
			triangle_vertices[2] = PApplet.map(0.15f, 0, 1, x + w, x);
			triangle_vertices[3] = PApplet.map(0.85f, 0, 1, y, y + h);
			triangle_vertices[4] = PApplet.map(0.85f, 0, 1, x + w, x);
			triangle_vertices[5] = y + h / 2;
		}
		p.fill(255);
		p.triangle(triangle_vertices[0], triangle_vertices[1],
				triangle_vertices[2], triangle_vertices[3],
				triangle_vertices[4], triangle_vertices[5]);
	}

	void clicked() {
		if (direction.equals("left")) {
			p.echoNest.steer(steerBar.name + "^0.7");
		}
		if (direction.equals("right")) {
			p.echoNest.steer(steerBar.name + "^1.3");
		}
        p.setMessage("Session info will update at the start of the next song.");
	}
}