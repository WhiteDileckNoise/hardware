package ntqsoft;

import java.util.ArrayList;
import java.util.List;

public class Configurator {
	
	
	private ArduinoAbstract boardMega, boardStandard;
	

	private String exportFilename = "NTQExport";
	private String backupFilename = "NTQBackup";
	
	private PropertiesManager propertiesManager;
	private String configFolder = "config";
	
	public Configurator() {
		boardMega = new ArduinoMega();
		boardStandard = new ArduinoStandard();
		
		propertiesManager = new PropertiesManager(configFolder);
		
	}
	
	public ArduinoAbstract getBoard() {
		return boardMega;
	}
	
	public boolean needArduinoMega() {
		// TODO Check number of PINS
		
		// Check PINS mapping
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_AUTOLOOP_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.AUTOLOOP_PIN))) return true; 
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_DOTTED_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.DOTTED_PIN))) return true;
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_STEP_BY_STEP_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.STEP_BY_STEP_PIN))) return true;
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_STEPPER_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.STEPPER_PIN))) return true;
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_BLINK_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.BLINK_PIN))) return true;
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_DELAY_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.DELAY_PIN))) return true;
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_TEST_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.TEST_PIN))) return true;
		if (Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_LED_CMD)) && !boardStandard.hasPin(propertiesManager.getProperty(NTQProperties.LED_PIN))) return true;
		for (int i=0; i<Integer.parseInt(propertiesManager.getProperty(NTQProperties.NUM_TRIGGERS)); i++) {
			String pin = propertiesManager.getProperty(NTQProperties.TRIGGER_PINS, i);
			if (!boardStandard.hasPin(pin)) return true;
		}
		return false;
	}

	public void setNumTriggers(int numTriggers) {
		propertiesManager.setProperty(NTQProperties.NUM_TRIGGERS, ""+numTriggers);
	}

	public int getNumTriggers() {
		return Integer.parseInt(propertiesManager.getProperty(NTQProperties.NUM_TRIGGERS));
	}

	public List<String> getAvailablePins() {
		List<String> pins = new ArrayList<String>();
		pins.addAll(getAvailableAnalogPins());
		pins.addAll(getAvailableDigitalPins());
		return pins;
	}
	
	public List<String> getAvailableAnalogPins() {
		List<String> pins = new ArrayList<String>();
		pins.addAll(propertiesManager.getPropertyArray(NTQProperties.AVAILABLE_ANALOG_PINS));
		return pins;
	}
	
	public List<String> getAvailableDigitalPins() {
		List<String> pins = new ArrayList<String>();
		pins.addAll(propertiesManager.getPropertyArray(NTQProperties.AVAILABLE_DIGITAL_PINS));
		return pins;
	}

	public void setTriggerPinsMapping(List<String> values) {
		propertiesManager.setProperty(NTQProperties.TRIGGER_PINS, values);
	}
	
	public List<String> getTriggerPinsMapping() {
		return propertiesManager.getPropertyArray(NTQProperties.TRIGGER_PINS);
	}
	
	public void setTriggerPin(int trigger, String pin) {
		propertiesManager.setProperty(NTQProperties.TRIGGER_PINS, trigger-1, pin);
	}

	public String getTriggerPin(int trigger) {
		return propertiesManager.getProperty(NTQProperties.TRIGGER_PINS, trigger);
	}
	
	public void setCurrentTriggerDelay(int currentTriggerDelay) {
		if (currentTriggerDelay >=0) {
			propertiesManager.setProperty(NTQProperties.CURRENT_TRIGGER_DELAY, ""+currentTriggerDelay);
		}
	}
	
	// DEFAULT VALUES
	
	public int getCurrentTriggerDelay() {
		return Integer.parseInt(propertiesManager.getProperty(NTQProperties.CURRENT_TRIGGER_DELAY));
	}


	public void setMinTriggerDelay(int minTriggerDelay) {
		if (minTriggerDelay >=0) { 
			propertiesManager.setProperty(NTQProperties.MIN_TRIGGER_DELAY, ""+minTriggerDelay);
		}
	}
	
	public int getMinTriggerDelay() {
		return Integer.parseInt(propertiesManager.getProperty(NTQProperties.MIN_TRIGGER_DELAY));
	}


	public void setMaxTriggerDelay(int maxTriggerDelay) {
		if (maxTriggerDelay >=0) { 
			propertiesManager.setProperty(NTQProperties.MAX_TRIGGER_DELAY, ""+maxTriggerDelay);
		}
	}
	
	public int getMaxTriggerDelay() {
		return Integer.parseInt(propertiesManager.getProperty(NTQProperties.MAX_TRIGGER_DELAY));
	}

	public void setEndDelay(int endDelay) {
		if (endDelay >=0) { 
			propertiesManager.setProperty(NTQProperties.END_DELAY, ""+endDelay);
		}
	}
	
	public int getEndDelay() {
		return Integer.parseInt(propertiesManager.getProperty(NTQProperties.END_DELAY));
	}
	
	public void setStepByStepDelay(int stepByStepDelay) {
		if (stepByStepDelay >=0) { 
			propertiesManager.setProperty(NTQProperties.STEP_BY_STEP_DELAY, ""+stepByStepDelay);
		}
	}
	
	public int getStepByStepDelay() {
		return Integer.parseInt(propertiesManager.getProperty(NTQProperties.STEP_BY_STEP_DELAY));
	}
	
	public void setStart(boolean value) {
		propertiesManager.setProperty(NTQProperties.START, Boolean.toString(value));
	}
	
	public boolean getStart() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.START));
	}

	public void setAutoLoop(boolean value) {
		propertiesManager.setProperty(NTQProperties.AUTOLOOP, Boolean.toString(value));
	}
	
	public boolean getAutoLoop() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.AUTOLOOP));
	}
	
	public void setDotted(boolean value) {
		propertiesManager.setProperty(NTQProperties.DOTTED, Boolean.toString(value));
	}
	
	public boolean getDotted() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.DOTTED));
	}
	
	public void setBlink(boolean value) {
		propertiesManager.setProperty(NTQProperties.BLINK, Boolean.toString(value));
	}
	
	public boolean getBlink() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.BLINK));
	}

	public void setStepByStep(boolean value) {
		propertiesManager.setProperty(NTQProperties.STEP_BY_STEP, Boolean.toString(value));
	}
	
	public boolean getStepByStep() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.STEP_BY_STEP));
	}

	// OPTIONAL COMMANDS
	
	public void setUseStartCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_START_CMD, Boolean.toString(value));
	}
	
	public boolean getUseStartCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_START_CMD));
	}
	
	public void setUseAutoLoopCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_AUTOLOOP_CMD, Boolean.toString(value));
	}
	
	public boolean getUseAutoLoopCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_AUTOLOOP_CMD));
	}

	public void setUseTestCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_TEST_CMD, Boolean.toString(value));
	}
	
	public boolean getUseTestCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_TEST_CMD));
	}

	public boolean getUseDottedCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_DOTTED_CMD));
	}

	public void setUseDottedCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_DOTTED_CMD, Boolean.toString(value));
	}

	public boolean getUseBlinkCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_BLINK_CMD));
	}

	public void setUseBlinkCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_BLINK_CMD, Boolean.toString(value));
	}
	
	public boolean getUseLedCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_LED_CMD));
	}

	public void setUseLedCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_LED_CMD, Boolean.toString(value));
	}

	public boolean getUseDelayCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_DELAY_CMD));
	}

	public void setUseDelayCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_DELAY_CMD, Boolean.toString(value));
	}
	
	public boolean getUseStepByStepCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_STEP_BY_STEP_CMD));
	}
	
	public void setUseStepByStepCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_STEP_BY_STEP_CMD, Boolean.toString(value));
	}
	
	public boolean getUseStepperCmd() {
		return Boolean.parseBoolean(propertiesManager.getProperty(NTQProperties.USE_STEPPER_CMD));
	}
	
	public void setUseStepperCmd(boolean value) {
		propertiesManager.setProperty(NTQProperties.USE_STEPPER_CMD, Boolean.toString(value));
	}
	
	// PINS MAPPING
	
	public void setTestPin(String value) {
		propertiesManager.setProperty(NTQProperties.TEST_PIN, value);
	}
	
	public String getTestPin() {
		return propertiesManager.getProperty(NTQProperties.TEST_PIN);
	}
		
	public void setStepByStepPin(String value) {
		propertiesManager.setProperty(NTQProperties.STEP_BY_STEP_PIN, value);
	}
	
	public String getStepByStepPin() {
		return propertiesManager.getProperty(NTQProperties.STEP_BY_STEP_PIN);
	}
		
	public void setStepperPin(String value) {
		propertiesManager.setProperty(NTQProperties.STEPPER_PIN, value);
	}
	
	public String getStepperPin() {
		return propertiesManager.getProperty(NTQProperties.STEPPER_PIN);
	}

	public void setStartPin(String value) {
		propertiesManager.setProperty(NTQProperties.START_PIN, value);
	}
	
	public String getStartPin() {
		return propertiesManager.getProperty(NTQProperties.START_PIN);
	}
	
	public void setDelayPin(String value) {
		propertiesManager.setProperty(NTQProperties.DELAY_PIN, value);
	}
	
	public String getDelayPin() {
		return propertiesManager.getProperty(NTQProperties.DELAY_PIN);
	}
	
	public void setAutoLoopPin(String value) {
		propertiesManager.setProperty(NTQProperties.AUTOLOOP_PIN, value);
	}
	
	public String getAutoLoopPin() {
		return propertiesManager.getProperty(NTQProperties.AUTOLOOP_PIN);
	}
	
	public void setLedPin(String value) {
		propertiesManager.setProperty(NTQProperties.LED_PIN, value);
	}
	
	public String getLedPin() {
		return propertiesManager.getProperty(NTQProperties.LED_PIN);
	}

	public void setDottedPin(String value) {
		propertiesManager.setProperty(NTQProperties.DOTTED_PIN, value);
	}
	
	public String getDottedPin() {
		return propertiesManager.getProperty(NTQProperties.DOTTED_PIN);
	}
	
	public void setBlinkPin(String value) {
		propertiesManager.setProperty(NTQProperties.BLINK_PIN, value);
	}
	
	public String getBlinkPin() {
		return propertiesManager.getProperty(NTQProperties.BLINK_PIN);
	}
	
	public void setExportFilename(String name) {
		exportFilename = name;
	}
	
	public String getExportFilename() {
		return exportFilename;
	}
	
	public void setBackupFilename(String name) {
		backupFilename = name;
	}
	
	public String getBackupFilename() {
		return backupFilename;
	}
	
	public void saveConfig() {
		propertiesManager.saveProperties();
	}
	
	public void reloadConfig() {
		// TODO
	}
	
	public void useDefaultConfig() {
		// TODO
	}
}
