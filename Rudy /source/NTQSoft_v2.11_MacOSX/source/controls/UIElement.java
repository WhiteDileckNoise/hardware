package controls;


import java.lang.reflect.InvocationTargetException;

import processing.core.PApplet;
//import processing.event.MouseEvent;
import processing.event.MouseEvent;

public abstract class UIElement {
	
	protected PApplet p;
	protected String id;
	protected String name;
	protected String value;
	protected boolean active;
	
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public int textColor;
	public int bgColor;

	
	public void draw() {
		drawIt();
	}
	
	public abstract void drawIt();
	
	/**
	 * Shows the UI Element
	 */
	public void show() {
		//p.registerDraw(this);
		//p.registerMouseEvent(this);
		p.registerMethod("draw", this);
		p.registerMethod("mouseEvent", this);

	}

	/**
	 * Hides the UI Element
	 */
	public void hide() {
//		p.unregisterDraw(this);
//		p.unregisterMouseEvent(this);
		p.unregisterMethod("draw", this);
		p.unregisterMethod("mouseEvent", this);

	}
	
	/**
	 * Sets the position of the control
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the id of the control
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the name of the control
	 * @return
	 */
	public String getName() {
		return id;
	}
	
	/**
	 * Sets the name of the control
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the value of the component
	 * @return
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the component
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Check if component has focus
	 * @param mouseX
	 * @param mouseY
	 */
	public boolean hasFocus(int mouseX, int mouseY) {
		if (mouseX >= 0 && mouseX <= width && mouseY >=0 && mouseY < height) {
			return true;
		}
		return false;
	}
	
	/**
	 * Does the require action
	 */
	protected abstract void act(int mouseX, int mouseY);
	
	/**
	 * Callback to receive mouseEvents from PApplet when registered
	 * @param event
	 */
	public abstract void mouseEvent(MouseEvent event);
	
	/**
	 * Fires a UIEvent when required
	 */
	protected void sendEvent() {
		try {
			p.getClass().getMethod("uiEvent", UIEvent.class).invoke(p, new UIEvent(id, value, active));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
