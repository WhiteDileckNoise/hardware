package controls;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class CheckBox extends UIElement{

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
	public CheckBox(PApplet parent, String id, int x, int y, int width, int height, String name, String value, int textColor, int bgColor) {
		this.p = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width > MIN_WIDTH ? width : MIN_WIDTH;;
		this.height = height > MIN_HEIGHT ? height : MIN_HEIGHT;
		this.name = name;
		setValue(value);
		this.textColor = textColor;
		this.bgColor = bgColor;
		
//		p.registerMouseEvent(this);
//		p.registerDraw(this);
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
	public CheckBox(PApplet parent, String id, int x, int y, int width, int height, String name, boolean value) {
		this(parent, id, x, y, width, height, name, "true", parent.color(0), parent.color(255));
	}
	
	
	/**
	 * Draws the control where if should be drawn
	 */
	public void drawIt() {
		p.pushMatrix();
		p.pushStyle();

		p.translate(x, y);
		p.fill(bgColor);
		p.stroke(textColor);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		
		p.rect(0, 0, width, height);
		
		p.fill(textColor);
		if (value.equals("true")) {
			p.text("X", width/2, height/2);
		}

		p.popStyle();
		p.popMatrix();		
	}
	
	
	/**
	 * Sets the value 
	 * @param value
	 */
	@Override
	public void setValue(String value) {
		this.value = (value.equals("true") || value.equals("false")) ? value : "false";
		this.active = value.equals("true");
		
	}
		
	/**
	 * Does the require action and sends event
	 */
	protected void act(int mouseX, int mouseY) {
		value = value.equals("true") ? "false" : "true";
		sendEvent();
	}

	/**
	 * Callback function to receive mouse events sent by the parent PApplet
	 * @param event
	 */
	public void mouseEvent(MouseEvent event) {
		int absX = event.getX()-x;
		int absY = event.getY()-y;
		
		switch(event.getAction()) {
	 	
		case MouseEvent.CLICK :
			if (hasFocus(absX, absY)) {
				active = !active;
				act(absX, absY);
			}
			break;	
		}	 	
	}
	
}
