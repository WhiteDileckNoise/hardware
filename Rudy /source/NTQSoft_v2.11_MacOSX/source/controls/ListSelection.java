package controls;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class ListSelection extends UIElement{

	private List<String> values = new ArrayList<String>();
	private int selected;
	
	private static final int MIN_WIDTH = 70;
	private static final int MIN_HEIGHT = 20;
	

	/**
	 * ListSelection constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param values
	 * @param selected
	 */
	public ListSelection(PApplet parent, String id, int x, int y, int width, int height, String name, List<String> values, String selectedValue) {
		this.p = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width > MIN_WIDTH ? width : MIN_WIDTH;
		this.height = height > MIN_HEIGHT ? height : MIN_HEIGHT;
		this.name = name;
		this.values.addAll(values);
		setSelectedFromValue(selectedValue);
		this.textColor = 255;
		this.bgColor = 0;

//		p.registerMouseEvent(this);
//		p.registerDraw(this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("draw", this);
		
		CallBackUtils.checkRequiredCallback(p, "uiEvent", UIEvent.class);
	}
	
	
	/**
	 * Sets the selected index from the given value.
	 * If not found, the value is added to the list 
	 * @param value
	 */
	public void setSelectedFromValue(String value) {
		selected = values.indexOf(value);
		if (selected == -1) {
			values.add(value);
			selected = values.indexOf(value);
		}
		this.value = values.get(selected);
	}
	
	
	/**
	 * Draws the control where if should be drawn
	 * @param p
	 */
	public void drawIt() {
		p.pushMatrix();
		p.pushStyle();

		p.translate(x, y);
		p.stroke(255);
		p.noFill();
		p.textAlign(PApplet.LEFT, PApplet.CENTER);
				
		// Name
	
		p.pushMatrix();
		p.fill(textColor);
		p.text(name, 0,height/2);
		p.popMatrix();
		
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		
		// Selector
		p.pushMatrix();
		p.translate(width, 0);
		p.fill(bgColor);
		p.rect(-height*4, 0, height*4, height);
		
		// Minus
		p.rect(-height*4,0,height, height);
		p.fill(textColor);
		p.text("-", -height*4+height/2, height/2);

		// Value
		p.text(values.size() > 0 ? values.get(selected) : "", -height*2, height/2);
		
		// Plus
		p.fill(bgColor);
		p.rect(-height,0,height, height);
		p.fill(textColor);
		p.text("+", -height/2, height/2);

		p.popMatrix();
		p.popStyle();
		p.popMatrix();		
	}
	
	
	/**
	 * Gets the current value of the control
	 * @return
	 */
	@Override
	public String getValue() {
		return values.get(selected);
	}
	
	/**
	 * Gets the current value display name (as displayed in the component)
	 * @return
	 */
	public String getValueDisplayName() {
		return (values.size() > 0 ? values.get(selected) : "");
	}
	
	/**
	 * Returns the number of values available
	 * @return
	 */
	public int getValuesCount() {
		return values.size();
	}
	
	/**
	 * Replaces the existing new values and adjust the selected index accordingly if needed
	 * @param value
	 */
	public void setValues(ArrayList<String> values) {
		this.values.clear();
		this.values.addAll(values);
		if (selected > values.size()-1) {
			selected = values.size()-1;
		}

	}
	
	/**
	 * Checks if the click occured in one of the control area and adjusts value accordingly
	 * @param mouseX
	 * @param mouseY
	 */
	protected void act(int mouseX, int mouseY) {
		if (mouseX >= width-height*4 && mouseX <= width-height*3 && mouseY >=0 && mouseY < height) {
			if (selected > 0) selected--;
			else selected = values.size()-1;
			value = values.get(selected);
			sendEvent();
		}
		if (mouseX >= width-height && mouseX <= width && mouseY >=0 && mouseY < height) {
			if (selected < values.size()-1) selected++;
			else selected = 0;
			value = values.get(selected);
			sendEvent();
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
				act(absX, absY);
			}
			break;	
		}	 	
	}
}
