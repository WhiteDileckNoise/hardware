package ntqsoft;

import java.util.ArrayList;

public class ArduinoMega extends ArduinoAbstract {

	public static String id = "Arduino Mega";
	
	public ArduinoMega() {
		
	}
	
	public ArrayList<String> getPWM() {
		 ArrayList<String> pins = new  ArrayList<String>();
			for (int i=2; i<14; i++) {
				pins.add(""+i);
			}		 
			for (int i=44; i<46; i++) {
				pins.add(""+i);
			}		 
		 return pins;
	}
	
	public ArrayList<String> getAnalogIn() {
		ArrayList<String> pins = new  ArrayList<String>();
		for (int i=0; i<16; i++) {
			pins.add("A"+i);
		}
		return pins;
	}
	
	public  ArrayList<String> getDigitalInOut() {
		ArrayList<String> pins = new  ArrayList<String>();
		for (int i=2; i<14; i++) {
			pins.add(""+i);
		}
		for (int i=22; i<56; i++) {
			pins.add(""+i);
		}
		return pins;
	}
	
}
