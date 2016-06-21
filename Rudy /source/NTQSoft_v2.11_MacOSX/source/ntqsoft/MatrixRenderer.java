package ntqsoft;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class MatrixRenderer {

	private PApplet parent;

	// Component position
	private int x;
	private int y;
	
	private int width;
	
	// Grid's dimension
	private Matrix matrix;
	private int step = 10;
	private boolean showGrid = true;

	private int color = 255;

	private NTQFont font;

	private PVector currentPos, targetPos;
	private PVector horizontalShift = new PVector(50, 0);
	float speed = 0.1f;
	
	private int drawColor = 255;

	private int cursorPos = 0;
	
	private boolean isActive;
	
	private boolean drawMode = true;

	/**
	 * MatrixRenderer constructor
	 * @param parent
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param cols
	 * @param rows
	 */
	public MatrixRenderer(PApplet parent, int x, int y, int width, int cols, int rows) {
		this.parent = parent;
		
		this.x = x;
		this.y = y;
		
		if (width % step != 0) {
			width = ((int) width/step) * step;
			System.out.println(width);
		}
		this.width = width;
		
		this.matrix = new Matrix(cols, rows);

		this.currentPos = new PVector(0, 0);
		this.targetPos = new PVector(currentPos.x, currentPos.y);
		this.horizontalShift = new PVector(width/3, 0);

		font = new NTQFont();

//		parent.registerDraw(this);
//		parent.registerMouseEvent(this);
//		parent.registerKeyEvent(this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("keyEvent", this);
		parent.registerMethod("draw", this);

	}

	/**
	 * Returns the current position of the grid 
	 * @return
	 */
	public PVector getPostion() {
		return currentPos;
	}
	
	/**
	 * Sets the current font
	 * @param font
	 */
	public void setFont(NTQFont font) {
		this.font = font;
	}

	/**
	 * Moves the grid view to the left
	 */
	public void moveLeft() {
		System.out.println(targetPos.x);
		if (targetPos.x + step*matrix.getWidth() >  width) {
			this.targetPos.sub(horizontalShift);
		}
	}

	/**
	 * Moves the grid view to the right
	 */
	public void moveRight() {
		if(targetPos.x < 0) {
			this.targetPos.add(horizontalShift);
		}
	}

	/**
	 * Returns the matrix
	 * @return
	 */
	public ArrayList<int[]> getMatrix() {
		return matrix.getMatrix();
	}
	
	/**
	 * Activate or disable the control so it will catch keyEvents of not
	 */
	public void activate(boolean value) {
		this.isActive = value;
	}
	
	/**
	 * Returns if the Matrix renderer is active of not and will catch keyEvents
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Callback function to draw the grid
	 */
	public void draw() {

		//currentPos.interpolateToSelf(targetPos, speed);
		currentPos.lerp(targetPos, speed);

		parent.pushMatrix();
		
		parent.translate(x, y);
		
		parent.pushMatrix();
		parent.translate(currentPos.x, 0);
		
		// Show start of Grid
		parent.pushMatrix();
		parent.pushStyle();
		parent.fill(drawColor);
		parent.translate(-20, 0);
		parent.rect(0, 0, 20, matrix.getHeight()*step);
		parent.popStyle();
		parent.popMatrix();

		// Show end of Grid
		parent.pushMatrix();
		parent.pushStyle();
		parent.fill(drawColor);
		parent.translate(0, 0);
		parent.rect(matrix.getWidth()*step, 0, 20, matrix.getHeight()*step);
		parent.popStyle();
		parent.popMatrix();
		

		
		// Draw the grid
		if (showGrid) {
			parent.pushStyle();
			parent.noFill();
			parent.strokeWeight(1);
			parent.stroke(drawColor,128);
			for (int i=0; i<matrix.getWidth(); i++) {
				for (int j=0; j<matrix.getHeight(); j++) {
					parent.rect(i*step, j*step, step, step);
				}
			}
			parent.popStyle();
		}

		// Matrix content
		// Draw a portion of the grid
		parent.pushStyle();
		parent.ellipseMode(PApplet.CORNER);
		parent.noStroke();
		for (int i=0; i<matrix.getWidth(); i++) {
			for (int j=0; j<matrix.getHeight(); j++) {
				int color = matrix.get(i,j);
				if (color != -1) {
					parent.fill(color);
					parent.ellipse(1+i*step, 1+j*step, step-2, step-2);
				}
			}
		}
		parent.popStyle();		
		parent.popMatrix();
		
		
		// Draws the cursor
		// Cursor position
		parent.pushMatrix();
		parent.translate(currentPos.x, 0);
		parent.pushStyle();
		parent.translate(cursorPos*step, 0);
		parent.stroke(255,0,0);
		parent.strokeWeight(2);
		parent.line(0, 0, 0, matrix.getHeight()*step);
		parent.line(-10, -1, 10, -1);
		parent.line(-10, matrix.getHeight()*step+1, 10, matrix.getHeight()*step+1);
			
		parent.popStyle();
		parent.popMatrix();

		
		// Draws the whole grid
		parent.pushMatrix();
		parent.translate(0, matrix.getHeight()*step+10);
		// Matrix content
		parent.pushStyle();
		parent.ellipseMode(PApplet.CORNER);
		parent.noStroke();
		for (int i=0; i<matrix.getWidth(); i++) {
			if (i > 0 && (i*2 % width) == 0) {
				parent.translate(-width, matrix.getHeight()*2 + 4);
			}
			for (int j=0; j<matrix.getHeight(); j++) {
				int color = matrix.get(i,j);
				if (color != -1) {
					parent.fill(color);
					parent.ellipse(i*2, j*2, 2, 2);
				}
			}
			if (i == cursorPos) {
				parent.pushStyle();
				parent.fill(255,0,0);
				parent.ellipse(i*2, matrix.getHeight()*2+2, 2, 2);
				parent.popStyle();
			}
		}
		parent.popStyle();
		parent.popMatrix();
		
		parent.popMatrix();
		
		// Show [...] when not beginning of grid
		
		if (currentPos.x < -1) {
			parent.pushMatrix();
			parent.pushStyle();
			parent.translate(0, y);
			parent.fill(drawColor);
			parent.stroke(drawColor);
			parent.rect(0,0, 20, matrix.getHeight()*step);
			parent.fill(0);
			parent.ellipseMode(PApplet.CENTER);
			parent.ellipse(10, matrix.getHeight()*step/3, 5, 5);
			parent.ellipse(10, matrix.getHeight()*step/2, 5, 5);
			parent.ellipse(10, matrix.getHeight()*2*step/3, 5, 5);
			
			parent.popStyle();
			parent.popMatrix();
			
		}
		
		// Show [...] when not end of grid
		
		if (currentPos.x + matrix.getWidth()*step > parent.width-40 ) {
			parent.pushMatrix();
			parent.pushStyle();
			parent.translate(parent.width-20, y);
			parent.fill(drawColor);
			parent.stroke(drawColor);
			parent.rect(0,0, 20, matrix.getHeight()*step);
			parent.fill(0);
			parent.ellipseMode(PApplet.CENTER);
			parent.ellipse(10, matrix.getHeight()*step/3, 5, 5);
			parent.ellipse(10, matrix.getHeight()*step/2, 5, 5);
			parent.ellipse(10, matrix.getHeight()*2*step/3, 5, 5);
			
			parent.popStyle();
			parent.popMatrix();
		}

		
	}

	/**
	 * Toggle to show/hide the grid
	 */
	public void toggleGrid() {
		showGrid = !showGrid;
	}

	/**
	 * Callback function to receive key events sent by the parent PApplet
	 * @param event
	 */
	public void keyEvent(KeyEvent event) {
		if (event.getAction() == 1 && isActive) {
			
			// Backspace
			if (event.getKeyCode() == 8) {
				matrix.removeColumn(cursorPos-1);
				if (matrix.getWidth() > 0 && cursorPos > 0) {
					shortenMatrix(1, 0);
				}
				if (cursorPos > 0) {
					cursorPos--;
					keepCursorOnScreen();
				}
			}
			
			// Left arrow
			if (event.getKeyCode() == 37) {
				if (cursorPos > 0) {
					cursorPos--;
					keepCursorOnScreen();
				}
			}
			
			// Right arrow
			if (event.getKeyCode() == 39) {
				if (cursorPos < matrix.getWidth()) {
					cursorPos++;
					keepCursorOnScreen();
				}
			}

			// Space
			if (event.getKey() == ' ') {
				matrix.insertColumn(cursorPos);
				cursorPos++;
				keepCursorOnScreen();
			}

			// Other keys
			char c = event.getKey();
			NTQChar character = font.getChar(c); 
			if (null != character) {
				addCharacter(character);
				keepCursorOnScreen();
			}
		}
	}
	
	private void keepCursorOnScreen() {
		// Keep the cursor visible
		if (matrix.getWidth()*step > parent.width-20) {
			if (cursorPos*step + currentPos.x > parent.width-20) {
				while(targetPos.x + cursorPos * step > parent.width-20) {
					moveLeft();
				}
			}
			else if (cursorPos*step + currentPos.x < 0) {
				while(targetPos.x  + cursorPos * step < 0) {
					moveRight();
				}
			}
		}

	}

	/**
	 * Inverts a character in the matrix if available in the current font
	 * @param character
	 */
	private void addCharacter(NTQChar character) {
		if (character.getHeight() > matrix.getHeight()) {
			System.out.println("Incompatible font (too many rows ["+character.getHeight()+"] for current matrix ["+matrix.getHeight()+"])");
			return;
		}
		// Extend matrix
		int originalMatrixWidth = matrix.getWidth();
		extendMatrix(character.getWidth()+1, 0);
		
		// Move characters after the cursor
		for (int i=0; i<matrix.getHeight(); i++) {
			for(int j=originalMatrixWidth-1; j>=cursorPos; j--) {
				matrix.getMatrix().get(i)[j+character.getWidth()+1] = matrix.getMatrix().get(i)[j];
			}
		}
		
		// Insert character at cursor position
		for (int i=0; i<character.getHeight(); i++) {
			int[] tmpArray = character.get().get(i);
			for (int j=0; j<tmpArray.length; j++) {
				matrix.set(cursorPos+j, i, tmpArray[j] == -1 ? -1 : color);
			}
		}

		// Add white space right after
		for (int i=0; i<matrix.getHeight(); i++) {
			matrix.getMatrix().get(i)[cursorPos+character.getWidth()] = -1;
		}
		
		// Move cursor
		cursorPos += character.getWidth()+1;
	}


	/**
	 * Callback function to receive mouse events sent by the parent PApplet
	 * @param event
	 */
	public void mouseEvent(MouseEvent event) {
		
		if (event.getX() > 20 && event.getX() < parent.width-20) {
			int px = event.getX()-(int)currentPos.x-x;
			int py = event.getY()-(int)currentPos.y-y;
			
			if (event.getButton() == PApplet.RIGHT) {
				setColor(-1);
			}
			else {
				setColor(255);
			}

			switch(event.getAction()) {
	
			case MouseEvent.CLICK :
				check(px, py);
				break;	
			case MouseEvent.DRAG :
				check(px, py);
				break;	
			}
		}
	}

	/**
	 * Sets color
	 * @param x
	 * @param y
	 */
	private void check(int px, int py) {

		// Remap to match grid position
		int xCoord = (int) (px / step);
		int yCoord = (int) (py / step);
		// Sets color
		if (xCoord < matrix.getWidth() && yCoord < matrix.getHeight()) {
				matrix.set(xCoord, yCoord, color);
		}
	}

	/**
	 * Sets the color used to draw
	 * @param c
	 */
	public void setColor(int c) {
		this.color = c;
	}

	/**
	 * Inverts colors
	 */
	public void negative() {
		matrix.negative();
	}

	
	/**
	 * Horizontally flips the matrix
	 */
	public void horizontalFlip() {
		matrix.horizontalFlip();
	}


	/**
	 * Vertically flips the matrix
	 */
	public void verticalFlip() {
		matrix.verticalFlip();
	}

	
	/**
	 * Extends or shorten the matrix's rows
	 * @param size
	 */
	public void setRows(int size) {
		if (size > matrix.getHeight()) {
			extendMatrix(0, size-matrix.getHeight());
		}
		else if (size < matrix.getHeight()) {
			shortenMatrix(0, matrix.getHeight()-size);
		}

	}
	
	/**
	 * Sets the matrix to a certain size
	 * @param cols
	 * @param rows
	 */
	public void initMatrix(int cols, int rows) {
		matrix.initMatrix(cols, rows);
	}
	
	/**
	 * Extends the matrix by the given amount of columns and rows
	 * @param cols
	 * @param rows
	 */
	public void extendMatrix(int cols, int rows) {
		matrix.extendMatrix(cols, rows);
	}

	/**
	 * Shortens the matrix by the given amount of columns and rows
	 * @param cols
	 * @param rows
	 */	
	public void shortenMatrix(int cols, int rows) {
		if (cols < matrix.getWidth() && rows < matrix.getHeight()) {
			matrix.shortenMatrix(cols, rows);
		}
	}

	
	/**
	 * Shortens the matrix to remove white space after last characters
	 */
	public void fitToWidth() {
		int lastWhiteCol = matrix.getWidth();
		boolean isEmpty = true;
		
		// Columns from end
		for (int i = matrix.getWidth()-1; i > 0; i--) {
			// Rows from top to bottom
			for(int j=0; j<matrix.getHeight(); j++) {
				
				int value = matrix.get(i,j);
				
				if (value != -1) {
					isEmpty = false;
					break;
				}
			}
			if (!isEmpty) {
				break;
			}
			lastWhiteCol = i;			
		}
		if (lastWhiteCol > 0) {
			shortenMatrix(matrix.getWidth()-(lastWhiteCol+1), 0);
		}
		cursorPos = lastWhiteCol;
	}
	
	/**
	 * Clears the matrix content
	 */
	public void clear() {
		matrix.initMatrix(width/step, matrix.getHeight());
		cursorPos = 0;
		targetPos.x = 0;
	}

	/**
	 * Returns the current color used to draw (or -1 if in erase mode)
	 * @return
	 */
	public int getColor() {
		return color;
	}
}