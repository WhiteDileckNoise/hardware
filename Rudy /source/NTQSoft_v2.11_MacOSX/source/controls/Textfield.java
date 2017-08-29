package controls;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Textfield extends UIElement {

	private String regex = "";
	private boolean isActive;
	private boolean isSubmited;
	private String defaultValue = "";

	private static final int MIN_WIDTH = 100;
	private static final int MIN_HEIGHT = 20;
	private static final String DEFAULT_REGEX = "[a-zA-Z_\\-0-9]+";
	
	/**
	 * Textfield constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param regex
	 */
	public Textfield(PApplet parent, String id, int x, int y, int width, int height, String name, String defaultValue, String regex, int textColor, int bgColor) {
		this.p = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width > MIN_WIDTH ? width : MIN_WIDTH;;
		this.height = height > MIN_HEIGHT ? height : MIN_HEIGHT;
		this.name = name;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.regex = regex;
		this.textColor = textColor;
		this.bgColor = bgColor;
		
		setRegex(regex);
		
//		parent.registerDraw(this);
//		parent.registerKeyEvent(this);
//		parent.registerMouseEvent(this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("keyEvent", this);
		parent.registerMethod("draw", this);

		
		CallBackUtils.checkRequiredCallback(p, "uiEvent", UIEvent.class);
		
	}
	
	/**
	 * TextField constructor using standard color values
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param regex
	 */
	public Textfield(PApplet parent, String id, int x, int y, int width, int height, String name, String defaultValue, String regex) {
		this(parent, id, x, y, width, height, name, defaultValue, regex, 0, 255);
	}
	
	
	/**
	 * Draws the control where if should be drawn
	 */
	public void drawIt() {
		p.pushMatrix();
		p.pushStyle();

		p.translate(x, y);
		p.noFill();
		p.stroke(textColor);
		
		p.fill(bgColor);
		if(isActive) {
			p.rect(-2, -2, width+4, height+4);
			p.rect(-1, -1, width+2, height+2);
		}
		p.rect(0, 0, width, height);


		p.rect(width-20, 0, 20, 20);
		
		// Text
		p.fill(textColor);
		p.textAlign(PApplet.LEFT, PApplet.CENTER);		
		p.text(value, 5, height/2);

		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text("X", width-10, height/2);

		p.popStyle();
		p.popMatrix();
	}
	
	
	/**
	 * Sets the value if matches the regex
	 * @param value
	 */
	@Override
	public void setValue(String value) {
		if (value.matches(regex)) {
			this.value = value;
		}
	}
	
	/**
	 * Sets the regex validation rule
	 * @param regex
	 */
	public void setRegex(String regex) {
		
		if (null == regex || "".equals(regex)) {
			this.regex = DEFAULT_REGEX;
		}
		else {
			try {
				Pattern.compile(regex);
				this.regex = regex;
			}
			catch (PatternSyntaxException e) {
				this.regex = DEFAULT_REGEX;
			}
		}
	}
	
	/**
	 * Tells if the current control is active
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * Activates the control
	 * @param isActive
	 */
	public void isActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * Tells if the current control is submited
	 * @return
	 */
	public boolean isSubmited() {
		return isSubmited;
	}
	
	/**
	 * Submits the control
	 * @param isSubmited
	 */
	public void isSubmited(boolean isSubmited) {
		this.isSubmited = isSubmited;
	}
	
	/**
	 * Clears the textfield
	 */
	public void clear() {
		value = "";
	}

	/**
	 * Resets the textfield (clears and set submited to false)
	 */
	public void reset() {
		clear();
		isSubmited(false);
	}
	
	/**
	 * Does the required action and sends event
	 * @param mouseX
	 * @param mouseY
	 */
	protected void act(int mouseX, int mouseY) {
		if (mouseX >= width-20 && mouseX <= width && mouseY >=0 && mouseY < height) {
			clear();
			value = defaultValue;
		}
		sendEvent();
	}

	/**
	 * Listens to key events
	 * @param event
	 */
	public void keyEvent(KeyEvent event) {
		if (isActive) {		
			switch(event.getAction()) {
	
				case KeyEvent.RELEASE :
					// Type
					if (p.textWidth(value) < width - 35) {
						char c = event.getKey();
						if ((""+c).matches(regex)) value+= c;
					}
					// Backspace key
					if (event.getKeyCode() == 8) {
						if (value.length() > 0) {
							value = value.substring(0, value.length()-1);
						}
					}
					// Enter key
					if (event.getKeyCode() == 10) {
						isActive = false;
					}
					sendEvent();
					
					break;
			}
		}
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
				isActive = true;
				act(absX, absY);
			}
			else {
				isActive(false);
			}
			break;	
		}	 	
	}
}
