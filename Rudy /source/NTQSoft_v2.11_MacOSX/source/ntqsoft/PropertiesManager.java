package ntqsoft;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesManager {
	
	private final String propertiesFile = "ntq.properties";
	private String configFolder;
	private Properties properties;
	
	public PropertiesManager(String configFolder) {
		this.configFolder = configFolder;
		loadProperties();
	}
	
	/**
	 * Loads the app configuration from properties file
	 */
	private void loadProperties() {

		// create and set default properties
		properties = new Properties();
		setDefaultProperties();

		// Attempt to load and replace with custom properties
		FileInputStream in = null; 
		try {
			in = new FileInputStream(configFolder+"/"+propertiesFile);
			properties.load(in);
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	
	private void setDefaultProperties() {
		
		properties.setProperty(NTQProperties.NUM_TRIGGERS, "7");
		properties.setProperty(NTQProperties.CURRENT_TRIGGER_DELAY, "250");
		properties.setProperty(NTQProperties.MIN_TRIGGER_DELAY, "100");
		properties.setProperty(NTQProperties.MAX_TRIGGER_DELAY, "400");
		properties.setProperty(NTQProperties.END_DELAY, "2500");
		properties.setProperty(NTQProperties.STEP_BY_STEP_DELAY, "100");

		// Optional commands
		properties.setProperty(NTQProperties.USE_START_CMD, "true");
		properties.setProperty(NTQProperties.USE_AUTOLOOP_CMD, "true");
		properties.setProperty(NTQProperties.USE_DOTTED_CMD, "true");
		properties.setProperty(NTQProperties.USE_STEP_BY_STEP_CMD, "true");
		properties.setProperty(NTQProperties.USE_BLINK_CMD, "true");
		properties.setProperty(NTQProperties.USE_DELAY_CMD, "true");
		properties.setProperty(NTQProperties.USE_TEST_CMD, "true");
		properties.setProperty(NTQProperties.USE_STEPPER_CMD, "true");
		properties.setProperty(NTQProperties.USE_LED_CMD, "true");
		
		// Default values
		properties.setProperty(NTQProperties.START, "false");
		properties.setProperty(NTQProperties.AUTOLOOP, "true");
		properties.setProperty(NTQProperties.DOTTED, "false");
		properties.setProperty(NTQProperties.STEP_BY_STEP, "false");
		properties.setProperty(NTQProperties.BLINK, "true");

		// Commands
		properties.setProperty(NTQProperties.TEST_PIN, "A1");
		properties.setProperty(NTQProperties.START_PIN, "3");
		properties.setProperty(NTQProperties.AUTOLOOP_PIN, "A2");
		properties.setProperty(NTQProperties.BLINK_PIN, "4");
		properties.setProperty(NTQProperties.STEP_BY_STEP_PIN, "A3");
		properties.setProperty(NTQProperties.STEPPER_PIN, "A4");
		properties.setProperty(NTQProperties.DOTTED_PIN, "A5");
		properties.setProperty(NTQProperties.DELAY_PIN, "A0");

		// LED
		properties.setProperty(NTQProperties.LED_PIN, "2");
		
		// Triggers
		setProperty(NTQProperties.TRIGGER_PINS, new ArduinoMega().getAll());
		//properties.setProperty(NTQProperties.TRIGGERS_PIN, config.getBoard().getDigitalInOut().toString().replaceAll("[^A0-9,]",""));		
		
		// Available Digital pins
		setProperty(NTQProperties.AVAILABLE_DIGITAL_PINS, new ArduinoMega().getDigitalInOut());
		// Available Analog pins
		setProperty(NTQProperties.AVAILABLE_ANALOG_PINS, new ArduinoMega().getAnalogIn());
	}
	
	/**
	 * Sets a property
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	/**
	 * Sets a composite property
	 * @param key
	 * @param index
	 * @param value
	 */
	public void setProperty(String key, int index, String value) {
		String[] s = properties.getProperty(key).split(",");
		if (index >= 0 && index <= s.length) {
			s[index] = value;
		}
		String s2 = "";
		for (int i=0; i<s.length; i++) {
			s2 += s[i];
			if (i<s.length-1) {
				s2+=",";
			}
		}
		properties.setProperty(key, s2);
	}
	
	/**
	 * Creates a composite property value from a list of values
	 * @param key
	 * @param values
	 */
	public void setProperty(String key, List<String> values) {
		properties.setProperty(key, values.toString().replaceAll("[^A0-9,]",""));
	}
	
	/**
	 * Gets composite property values as a List
	 * @param key
	 * @return
	 */
	public List<String> getPropertyArray(String key) {
		List<String> values = new ArrayList<String>();
		String[] s = properties.getProperty(key).split(",");
		values.addAll(Arrays.asList(s));
		return values;
	}
	
	/**
	 * Gets a property value
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * Gets a composite property value
	 * @param key
	 * @param index
	 * @return
	 */
	public String getProperty(String key, int index) {
		String[] s = properties.getProperty(key).split(",");
		return s[index];
	}
	
	/**
	 * Saves the configuration in a properties file
	 */
	public void saveProperties() {
		// Save custom properties file
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(configFolder+"/"+propertiesFile);
			properties.store(out, "---No Comment---");
			out.close();
		}
		catch (Exception e) {
			System.out.println("Properties file not found");
		}
	}
}
