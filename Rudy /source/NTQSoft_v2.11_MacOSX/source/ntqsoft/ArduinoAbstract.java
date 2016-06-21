package ntqsoft;

import java.util.ArrayList;

public abstract class ArduinoAbstract {

	public ArrayList<String> getAll() {
		ArrayList<String> pins = new  ArrayList<String>();
		pins.addAll(getAnalogIn());
		pins.addAll(getDigitalInOut());
		return pins;
	}
	
	public boolean hasPin(String pin) {
		ArrayList<String> pins = getAll();
		for (String s: pins) {
			if (s.equals(pin)) return true;
		}
		return false;
	}
	
	public abstract ArrayList<String> getPWM();	

	public abstract ArrayList<String> getAnalogIn();
	
	public abstract ArrayList<String> getDigitalInOut();
	
}
