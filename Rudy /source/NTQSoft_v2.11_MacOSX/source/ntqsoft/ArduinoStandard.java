package ntqsoft;

import java.util.ArrayList;

public class ArduinoStandard extends ArduinoAbstract {

	public static String id = "Arduino standard";
	
	public ArduinoStandard() {
		
	}
		
	public ArrayList<String> getPWM() {
		 ArrayList<String> pins = new  ArrayList<String>();
		 pins.add(""+3);
		 pins.add(""+5);
		 pins.add(""+6);
		 pins.add(""+9);
		 pins.add(""+10);
		 pins.add(""+11);
		 return pins;
	}
	
	public ArrayList<String> getAnalogIn() {
		ArrayList<String> pins = new  ArrayList<String>();
		for (int i=0; i<6; i++) {
			pins.add("A"+i);
		}
		return pins;
	}
	
	public  ArrayList<String> getDigitalInOut() {
		ArrayList<String> pins = new  ArrayList<String>();
		for (int i=2; i<14; i++) {
			pins.add(""+i);
		}
		return pins;
	}
	
}
