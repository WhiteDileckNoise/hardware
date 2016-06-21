package controls;


import controls.enums.XAlign;
import controls.enums.YAlign;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Label extends UIElement {

	private XAlign xAlign;
	private YAlign yAlign;

	private static final int MIN_WIDTH = 20;
	private static final int MIN_HEIGHT = 20;
	
	// TODO limit size
	
	/**
	 * Label constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public Label(PApplet parent, String id, int x, int y, int width, int height, XAlign xAlign, YAlign yAlign, String name, int textColor) {
		this.p = parent;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = (width > MIN_WIDTH ? width : MIN_WIDTH);
		this.height = (height > MIN_HEIGHT ? height : MIN_HEIGHT);
		this.xAlign = xAlign;
		this.yAlign = yAlign;
		this.textColor = textColor;
		this.name = name;
		
//		parent.registerDraw(this);
		parent.registerMethod("draw", this);
		
	}
	
	/**
	 * Label constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public Label(PApplet parent, String id, int x, int y, int width, int height, XAlign xAlign, YAlign yAlign, String name) {
		this(parent, id, x, y, width, height, xAlign, yAlign, name, parent.color(0));
	}

	/**
	 * Label constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public Label(PApplet parent, String id, int x, int y, int width, int height, String name, int textColor) {
		this(parent, id, x, y, width, height, XAlign.MIDDLE, YAlign.MIDDLE, name, textColor);
	}

	
	/**
	 * Label constructor
	 * @param parent
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 */
	public Label(PApplet parent, String id, int x, int y, int width, int height, String name) {
		this(parent, id, x, y, width, height, XAlign.MIDDLE, YAlign.MIDDLE, name);		
	}
	
	/**
	 * Draws the control where if should be drawn
	 */
	public void drawIt() {
		p.pushMatrix();
		p.pushStyle();

		p.translate(x, y);
		p.fill(textColor);

		AlignUtils.positionText(p, width, height, xAlign, yAlign);
		
		p.text(name, 0, 0);

		p.popStyle();
		p.popMatrix();
	}

	protected void act(int mouseX, int mouseY) {
		// Do nothing
	}

	public void mouseEvent(MouseEvent event) {
		// Do nothing
	}
	
}
