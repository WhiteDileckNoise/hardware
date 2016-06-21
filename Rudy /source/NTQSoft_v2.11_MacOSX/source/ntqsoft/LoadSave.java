package ntqsoft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadSave {
	
	public LoadSave() {
	}
	
	public String[] save(List<int[]>  matrix) {
		ArrayList<String> tmp = new ArrayList<String>();

		for (int i=0; i<matrix.size(); i++) {
			String arrayAsString = Arrays.toString(matrix.get(i));
			tmp.add(arrayAsString);
		}
		
		String[] info = tmp.toArray(new String[0]);
		return info;
	}
	
	/**
	 * Loads a drawing and returns the number of rows
	 * @param matrix
	 * @param info
	 * @return
	 */
	public void load(MatrixRenderer matrix, String[] info) {
		// Check validity
		
		//
		int rows = info.length;
		System.out.println("Rows "+rows);
		
		if (rows > 0) {
			int cols = count(info[0]);
			for (String s : info) {
				if (count(s) != cols) return;
			}
			
			cols = (info[0].substring(1, info[0].length()-1)).split(",").length;
			System.out.println("Cols "+cols);

			matrix.initMatrix(cols, rows);
		
			for (int y=0; y<rows; y++) {
				String[] s = (info[y].substring(1, info[y].length()-1)).split(",");
				for (int x=0; x<cols; x++) {
					matrix.getMatrix().get(y)[x] = Integer.valueOf(s[x].replace(" ", ""));
				}
			}
		}
	}
	
	
	private int count(String s) {
		String tmp = s.substring(1, s.length()-1); // Remove [ ]
		return tmp.split(",").length;
	}
	
	
	


}
