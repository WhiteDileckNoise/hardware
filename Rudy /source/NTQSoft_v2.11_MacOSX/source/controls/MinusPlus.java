package controls;

import java.lang.reflect.InvocationTargetException;


import processing.core.PApplet;
import processing.event.MouseEvent;

public class MinusPlus extends UIElement {

	private int min;
	private int max;
	private int step = 1;
	private int val;

	private static final int MIN_WIDTH = 65;
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
	 * @param min
	 * @param max
	 * @param textColor
	 * @param bgColor
	 */
	public MinusPlus(PApplet parent, String id, int x, int y, int width, int height, String name, String value, int min, int max, int step, int textColor, int bgColor) {
		this.p = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width > MIN_WIDTH ? width : MIN_WIDTH;;
		this.height = height > MIN_HEIGHT ? height : MIN_HEIGHT;
		this.name = name;
		this.min = min;
		setMax(max);
		setValue(value);
		this.textColor = textColor;
		this.bgColor = bgColor;
		this.setStep(step);

//		parent.registerMouseEvent(this);
//		parent.registerDraw(this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("draw", this);

		CallBackUtils.checkRequiredCallback(parent, "uiEvent", UIEvent.class);

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
	 * @param min
	 * @param max
	 */
	public MinusPlus(PApplet parent, String id, int x, int y, int width, int height, String name, String value, int min, int max, int step) {
		this(parent, id, x, y, width, height, name, value, min, max, parent.color(0), parent.color(255), step);
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
		p.rect(0, 0, 20, height);
		p.rect(width-20, 0, 20, height);

		p.fill(textColor);
		// Minus
		p.text("-", 10, height/2-1);

		// Value
		p.text(""+val, width/2, height/2-1);

		// Plus
		p.text("+", width-10, height/2-1);

		p.popStyle();
		p.popMatrix();		
	}


	/**
	 * Gets the current value of the control
	 * @return
	 */
	@Override
	public String getValue() {
		return ""+val;
	}

	/**
	 * Sets the value if between the min/max limit
	 * Otherwise sets the value to either min or max if lower or greater than min/max
	 * @param value
	 */
	public void setValue(String value) {
		int tmpVal = 0;
		try {
			tmpVal = Integer.parseInt(value);
		}
		catch (Exception e) {
		}
		if (tmpVal >= min && tmpVal <= max) {
			this.val = tmpVal;
		}
		else if (tmpVal < min) {
			val = min;
		}
		else if (tmpVal > max) {
			val = max;
		}
	}

	/**
	 * Sets the minimum value
	 * @param min
	 */
	public void setMin(int min) {
		this.min = min;
		if (this.min > this.max) {
			this.max = this.min;
		}
	}

	/**
	 * Returns the min value for this component
	 * @return
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Sets the maximum value
	 * @param min
	 */
	public void setMax(int max) {
		this.max = max;
		if (this.max < this.min) {
			this.min = this.max;
		}
	}

	/**
	 * Returns the max value for this component
	 * @return
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Sets the increment/decrement step for this component
	 * @param step
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * Returns the increment/decrement value used by this component
	 * @return
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Does the require action and sends event
	 * @param mouseX
	 * @param mouseY
	 */
	protected void act(int mouseX, int mouseY) {
		if (mouseX >= 0 && mouseX <= width/2 && mouseY >=0 && mouseY < height) {
			if (val-step >= min) val -= step;
		}
		else if (mouseX >= width/2 && mouseX <= width && mouseY >=0 && mouseY < height) {
			if (val+step <= max) val += step;
		}
		value = ""+val;
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


