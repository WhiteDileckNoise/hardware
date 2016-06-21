package ntqsoft;

import java.util.ArrayList;
import java.util.Collections;

public class Matrix {

	private ArrayList<int[]> matrix;

	public Matrix(int cols, int rows) {
		matrix = new ArrayList<int[]>();
		initMatrix(cols, rows);
	}

	public void initMatrix(int cols, int rows) {
		matrix.clear();
		for (int i=0; i<rows; i++) {
			// Create a tab per row
			matrix.add(NTQUtils.fillIntArray(cols, -1));
		}
	}


	public int get(int x, int y) {
		if (x >= 0 && x < getWidth() && y >= 0 && y <getHeight()) {
			return matrix.get(y)[x];
		}
		return -1;
	}

	public ArrayList<int[]> getMatrix() {
		ArrayList<int[]> tmpMatrix = new ArrayList<int[]>();
		tmpMatrix.addAll(matrix);
		return tmpMatrix;
	}

	
	public int getWidth() {
		return matrix.get(0).length;
	}
	
	public int getHeight() {
		return matrix.size();
	}
	
	
	public void set(int x, int y, int value) {
		if (matrix.size() > 0 && x >=0 && x <= matrix.get(0).length && y >=0 && y < matrix.size()) {
			matrix.get(y)[x] = value;
		}
	}


	public void negative() {
		for (int x=0; x<matrix.size(); x++) {
			for (int y=0; y<matrix.get(0).length; y++) {
				if (matrix.get(x)[y] == -1) {
					matrix.get(x)[y] = 255;
				}
				else {
					matrix.get(x)[y] = -1;
				}
			}
		}
	}

	
	public void removeColumn(int x) {
		if (x >= 0) {
			for (int i=0; i<matrix.size(); i++) {
				for (int j=x; j<matrix.get(0).length; j++) {
					if (j == matrix.get(0).length-1) {
						matrix.get(i)[j] = -1;
					}
					else {
						matrix.get(i)[j] = matrix.get(i)[j+1];
					}
				}
			}
		}
	}

	public void insertColumn(int x) {
		extendMatrix(1, 0);
		if (x >= 0) {
			for (int i=0; i<matrix.size(); i++) {
				for (int j=matrix.get(0).length-1; j>x; j--) {					
					matrix.get(i)[j] = matrix.get(i)[j-1];
				}
			}
			for (int i=0; i<matrix.size(); i++) {
				matrix.get(i)[x] = -1;
			}
		}
	}

	/**
	 * Extends the matrix by adding the number of columns and rows passed as arguments
	 * @param cols
	 * @param rows
	 */
	public void extendMatrix(int cols, int rows) {

		int totalCols = cols;

		if (matrix.size() > 0) {
			totalCols = matrix.get(0).length + cols;
		}

		// If rows needs to be added
		if (rows > 0) {
			for (int i=0; i<rows; i++) {
				matrix.add(NTQUtils.fillIntArray(totalCols, -1));
			}
		}

		// If cols needs to be added
		if (cols > 0) {
			int currentCols = matrix.get(0).length;	

			for (int i=0; i<matrix.size(); i++) {
				int[] newArray = NTQUtils.fillIntArray(currentCols+cols, -1);
				int[] currentArray = matrix.get(i);
				System.arraycopy(currentArray, 0, newArray, 0, currentArray.length);
				matrix.set(i, newArray);
			}			
		}

	}


	/**
	 * Shortens the matrix by removing the number of columns and rows passed as arguments
	 * @param cols
	 * @param rows
	 */
	public void shortenMatrix(int cols, int rows) {

		// If cols needs to be added
		if (rows > 0) {
			for (int i=0; i<rows; i++) {
				if (matrix.size() == 0) return;
				matrix.remove(matrix.size()-1);
			}
		}

		// If rows needs to be removed
		if (matrix.size() > 0 && cols > 0) {
			int currentCols = matrix.get(0).length;	
			int newCols = currentCols-cols;
			if (newCols <= 0) return;


			for (int i=0; i<matrix.size(); i++) {
				int[] newArray = new int[newCols];
				int[] currentArray = matrix.get(i);
				System.arraycopy(currentArray, 0, newArray, 0, newArray.length);
				matrix.set(i, newArray);
			}			
		}
	}

	/**
	 * Flips the matrix vertically
	 */
	public void verticalFlip() {
		Collections.reverse(matrix);
	}


	/**
	 * Flips the matrix horizontically
	 */
	public void horizontalFlip() {
		for(int i=0; i<matrix.size(); i++) {
			int[] tmpArray = matrix.get(i);
			int[] newArray = new int[tmpArray.length];
			for(int j=0; j<tmpArray.length; j++) {
				newArray[j] = tmpArray[(tmpArray.length-j)-1];
			}
			matrix.set(i, newArray);
		}
	}

}
