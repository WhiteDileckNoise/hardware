package controls;



import processing.core.PApplet;
import processing.event.MouseEvent;

public class ToggleButton extends UIElement {

	private static final int MIN_WIDTH = 20;
	private static final int MIN_HEIGHT = 20;

	/**
	 * MinusPlusCommand constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param value
	 * @param textColor
	 * @param bgColor
	 */
	public ToggleButton(PApplet parent, String id, int x, int y, int width, int height, String name, String value, int textColor, int bgColor) {
		this.p = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width > MIN_WIDTH ? width : MIN_WIDTH;;
		this.height = height > MIN_HEIGHT ? height : MIN_HEIGHT;
		this.name = name;
		this.value = value.equals("true") || value.equals("false") ? value : "false";
		this.textColor = textColor;
		this.bgColor = bgColor;

//		parent.registerMouseEvent(this);
//		parent.registerDraw(this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("draw", this);

		
		CallBackUtils.checkRequiredCallback(p, "uiEvent", UIEvent.class);

	}

	/**
	 * MinusPlusCommand constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param value
	 */
	public ToggleButton(PApplet parent, String id, int x, int y, int width, int height, String name, String value) {
		this(parent, id, x, y, width, height, name, value, parent.color(0), parent.color(255, 0));
	}

	/**
	 * Draws the control where if should be drawn
	 * @param p
	 */
	public void drawIt() {
		p.pushMatrix();
		p.pushStyle();

		p.translate(x, y);
		p.noFill();
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.fill(bgColor);
		p.stroke(textColor);
		p.rect(0, 0, width, height);

		if (value.equals("true")) {
			p.fill(125, 125);
			p.rect(1, 1, width-2, height-2);
		}
		p.fill(textColor);
		p.text(name, width/2, height/2);

		p.popStyle();
		p.popMatrix();
	}

	/**
	 * Does the require action and sends event
	 */
	protected void act(int absX, int absY) {
		value = value.equals("true") ? "false" : "true";
		sendEvent();
	}

	/**
	 * Callback function to receive mouse events sent by the parent PApplet
	 * @param event
	 */
	public void mouseEvent(MouseEvent event) {
		switch(event.getAction()) {

		case MouseEvent.CLICK :
			int absX = event.getX()-x;
			int absY = event.getY()-y;
			if (hasFocus(absX, absY)) {
				act(absX, absY);
			}
			break;	
		}	 	
	}
}
