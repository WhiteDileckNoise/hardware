package controls;

import controls.enums.XAlign;
import controls.enums.YAlign;
import processing.core.PApplet;

public class AlignUtils {

	
	public static void positionText(PApplet p, final int width, final int height, final XAlign xAlign, final YAlign yAlign) {
		if (xAlign == XAlign.LEFT) {
			// No translation
			if (yAlign == YAlign.TOP) {
				// No translation
				p.textAlign(PApplet.LEFT, PApplet.TOP);
			}
			else if (yAlign == YAlign.MIDDLE) {
				p.translate(0, height/2);
				p.textAlign(PApplet.LEFT, PApplet.CENTER);
			}
			else if (yAlign == YAlign.BOTTOM) {
				p.translate(0, height);
				p.textAlign(PApplet.LEFT, PApplet.BOTTOM);
			}			
		}
		else if (xAlign == XAlign.MIDDLE) {
			p.translate(width/2, 0);
			if (yAlign == YAlign.TOP) {
				// No translation
				p.textAlign(PApplet.CENTER, PApplet.TOP);
			}
			else if (yAlign == YAlign.MIDDLE) {
				p.translate(0, height/2);
				p.textAlign(PApplet.CENTER, PApplet.CENTER);
			}
			else if (yAlign == YAlign.BOTTOM) {
				p.translate(0, height);
				p.textAlign(PApplet.CENTER, PApplet.BOTTOM);
			}	
		}
		else if (xAlign == XAlign.RIGHT) {
			p.translate(width, 0);
			if (yAlign == YAlign.TOP) {
				// No translation
				p.textAlign(PApplet.RIGHT, PApplet.TOP);
			}
			else if (yAlign == YAlign.MIDDLE) {
				p.translate(0, height/2);
				p.textAlign(PApplet.RIGHT, PApplet.CENTER);
			}
			else if (yAlign == YAlign.BOTTOM) {
				p.translate(0, height);
				p.textAlign(PApplet.RIGHT, PApplet.BOTTOM);
			}
		}
	}
	
	
	/**
	 * Gets Processing align code based on a XAlign enum value
	 * @param xAlign
	 * @return
	 */
	public static int getXAlign(XAlign xAlign) {
		int xxAlign = PApplet.LEFT;
		if (xAlign == XAlign.MIDDLE) xxAlign = PApplet.CENTER;
		else if (xAlign == XAlign.RIGHT) xxAlign = PApplet.RIGHT;
		return xxAlign;
	}
	
	/**
	 * Gets Processing align code based on a YAlign enum value
	 * @param yAlign
	 * @return
	 */
	public static int getYAlign(YAlign yAlign) {
		int yyAlign = PApplet.CENTER;
		if (yAlign == YAlign.TOP) yyAlign = PApplet.TOP;
		else if (yAlign == YAlign.BOTTOM) yyAlign = PApplet.BOTTOM;
		return yyAlign;
	}


}
