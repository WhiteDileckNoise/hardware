package controls;


import processing.core.PApplet;
import processing.event.MouseEvent;

public class DirectionButton extends UIElement {

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
	public DirectionButton(PApplet parent, String id, int x, int y, int width, int height, String name, String value, int textColor, int bgColor) {
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
		
		//parent.registerMouseEvent(this);
		//parent.registerDraw(this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("draw", this);

	
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
	public DirectionButton(PApplet parent, String id, int x, int y, int width, int height, String name, String value) {
		this(parent, id, x, y, width, height, name, value, parent.color(0), parent.color(255));
	}
	
	
	/**
	 * Draws the control where if should be drawn
	 * @param p
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
		if (value.equals("left")) {
			p.text("<", width/2, height/2);
		}
		else if (value.equals("right")) {
			p.text(">", width/2, height/2);
		}
		// TODO up/down

		p.popStyle();
		p.popMatrix();		
	}
	
	
	/**
	 * Sets the value 
	 * @param value
	 */
	@Override
	public void setValue(String value) {
		this.value = value.equals("right") || value.equals("left") ? value : "right";
	}
		
	/**
	 * Does the required action and sends event if needed
	 */
	protected void act(int mouseX, int mouseY) {
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
