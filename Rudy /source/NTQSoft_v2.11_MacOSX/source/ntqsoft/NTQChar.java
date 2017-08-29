package ntqsoft;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NTQChar {
	
	private ArrayList<int[]> dotsArray;
	private int cols;
	private int rows;
	
	
	public NTQChar(int cols, int rows, String dots) {
		dotsArray = new ArrayList<int[]>();
		this.cols = cols;
		this.rows = rows;
		if (!isConsistent(cols, rows, dots)) {
			// If not consistent, create an empty placeholder
			this.cols = 1;
			this.rows = rows;
			for(int i=0; i<rows; i++) {
				int[] tmp = NTQUtils.fillIntArray(1, -1);
				dotsArray.add(tmp);
			}
		}
		else {
			createArray(dots);
		}
	}

	
	public void createArray(String dots) {
		int[] tmpArray = new int[cols];
		for(int i=0; i<dots.length(); i++) {
			tmpArray[i%cols] = (dots.charAt(i) == '0' ? -1 : 0);
			if (i%cols == cols-1) {
				dotsArray.add(tmpArray);
				tmpArray = new int[cols];
			}
		}
	}

	
	public boolean isConsistent(int cols, int rows, String dots) {		
		if (cols <= 0 || rows <= 0) return false;
		if (cols*rows != dots.length()) return false;
		return isContentValid(dots);
	}
		
	
	public boolean isContentValid(String dots) {
		String regex = "^[01]+$"; // Only 0 or 1
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(dots);
		if (m.find()) {
		  return true;
		}
		return false; 
	}
	
	
	public ArrayList<int[]> get() {
		ArrayList<int[]> tmpDots = new ArrayList<int[]>();
		tmpDots.addAll(dotsArray);
		return tmpDots;
	}
	
	
	public int getWidth() {
		return cols;
	}
	
	
	public int getHeight() {
		return rows;
	}
}
