package ntqsoft;

import java.io.File;

import processing.core.PApplet;
import controls.CheckBox;
import controls.DirectionButton;
import controls.Label;
import controls.ListSelectionCheckbox;
import controls.MinusPlus;
import controls.PushButton;
import controls.PushButton;
import controls.Textfield;
import controls.ToggleButton;
import controls.UIEvent;
import controls.enums.Direction;
import controls.enums.XAlign;
import controls.enums.YAlign;


public class NTQSoft extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String needMega = "Based on your current settings you'll need an Arduino Mega. If that's not what you want, check the pins configuration and/or remove optional commands to lower the amount of pins used";

	private MatrixRenderer matrixRenderer;
	private Configurator config;
	private ArduinoExport exporter;
	private LoadSave loadsave;
	
	private TriggerPinsManager triggerManager;
	private MinusPlus rowsControl, minDelayControl, maxDelayControl, endDelayControl, defaultDelayControl, stepByStepDelayControl;
	private DirectionButton moveLeftControl, moveRightControl;
	private CheckBox autoLoopControl, blinkControl, startControl, dottedControl, stepByStepControl;
	private PushButton exportControl, openControl, backupControl, horizontalFlipControl, verticalFlipControl, clearAllControl, fitToWidthControl;
	private PushButton negativeControl;
	private PushButton loadConfigControl, saveConfigControl;
	private ToggleButton toggleGridControl;
	private ListSelectionCheckbox autoloopPin, testPin, blinkPin, delayPin, stepperPin, dottedPin, stepByStepPin, startPin, ledPin;
	private Textfield exportTextfield, backupTextfield;
	
	private int labelWidth = 100;
	private int labelHeight = 20;
	
	private int componentWidth = 100;
	private int componentHeight = labelHeight;

	//private int hSpace = 14;
	private int hSpace = 29;
	private int vSpace = 22;
	
	private int buttonWidth = 100;
	private int buttonHeight = 20;
	
	private int bgColor = 0;
	private int textColor = 255;
	
	public void setup() {
		size(1024, 700);
		smooth();
		
		config = new Configurator();

		int x, y = 0;

		x = 20;
		y = 10;
		negativeControl = new PushButton(this, "color", x, y, buttonWidth, buttonHeight, "Negative", "true", textColor, bgColor);
		horizontalFlipControl = new PushButton(this, "horizontalFlip", x+=buttonWidth+hSpace+5, y, buttonWidth, buttonHeight, "Horizontal flip", "true", textColor, bgColor);
		verticalFlipControl = new PushButton(this, "verticalFlip", x+=buttonWidth+hSpace+5, y, buttonWidth, buttonHeight, "Vertical flip", "true", textColor, bgColor);
		fitToWidthControl = new PushButton(this, "fitToWidth", x+=buttonWidth+hSpace+5, y, buttonWidth, buttonHeight, "Fit to width", "true", textColor, bgColor);
		clearAllControl = new PushButton(this, "clearAll", x+=buttonWidth+hSpace+5, y, buttonWidth, buttonHeight, "Clear all", "true", textColor, bgColor);
		toggleGridControl = new ToggleButton(this, "showGrid", x+=buttonWidth+hSpace+5, y, buttonWidth, buttonHeight, "Hide grid", "false", textColor, bgColor);
		
		new Label(this, "rows", x+=buttonWidth+hSpace+30, y, 40, buttonHeight, XAlign.LEFT, YAlign.MIDDLE, "Rows", textColor);
		rowsControl = new MinusPlus(this, "rowSize", x+20+hSpace, y, labelWidth, buttonHeight, "rows", ""+config.getNumTriggers(), 1, 20, 1, textColor, bgColor);

		x = 20;
		y = 40;

		moveLeftControl = new DirectionButton(this, "moveLeft", x, y, width/2-30, 50, "", "left", textColor, bgColor);
		moveRightControl = new DirectionButton(this, "moveRight", width/2+7, y, width/2-30, 50, "", "right", textColor, bgColor);

		x = 20; 
		y = 100;
		exporter = new ArduinoExport(config);
		loadsave = new LoadSave();
		matrixRenderer = new MatrixRenderer(this, x, y, width, 100, config.getNumTriggers());
		
		
		x = 20;
		y = 430;

		new Label(this, "default", x, y-35, labelWidth-30, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "DEFAULT PARAMETERS", textColor);
		
		new Label(this, "blink", x, y, labelWidth-20, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Blink", textColor);
		blinkControl = new CheckBox(this, "blinkdefault", x+labelWidth-45+hSpace, y, 20, 20, "blinkdefault", Boolean.toString(config.getBlink()), textColor, bgColor);

		new Label(this, "autoloop", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Autoloop", textColor);
		autoLoopControl = new CheckBox(this, "autoLoopdefault", x+labelWidth-45+hSpace, y, 20, 20, "autoloopdefault", Boolean.toString(config.getAutoLoop()), textColor, bgColor);

		new Label(this, "start", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Start", textColor);
		startControl = new CheckBox(this, "startdefault", x+labelWidth-45+hSpace, y, 20, 20, "startdefault", Boolean.toString(config.getStart()), textColor, bgColor);

		new Label(this, "dotted", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Dotted", textColor);
		dottedControl = new CheckBox(this, "dotteddefault", x+labelWidth-45+hSpace, y, 20, 20, "dotteddefault", Boolean.toString(config.getDotted()), textColor, bgColor);

		new Label(this, "stepbystep", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Step by step", textColor);
		stepByStepControl = new CheckBox(this, "stepbystepdefault", x+labelWidth-45+hSpace, y, 20, 20, "stepbystepdefault", Boolean.toString(config.getStepByStep()), textColor, bgColor);
				
		x= 160;
		y=430;
		new Label(this, "Default delay", x, y, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Default delay", textColor);
		defaultDelayControl = new MinusPlus(this, "delay", x+labelWidth+hSpace-5, y, componentWidth, componentHeight, "delay", ""+config.getCurrentTriggerDelay(), 100, 10000, 10, textColor, bgColor);
		new Label(this, "End delay", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "End delay", textColor);
		endDelayControl = new MinusPlus(this, "endDelay", x+labelWidth+hSpace-5, y, componentWidth, componentHeight, "endDelay", ""+config.getEndDelay(), 100, 10000, 100, textColor, bgColor);
		new Label(this, "Min delay", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Minimum delay", textColor);
		minDelayControl = new MinusPlus(this, "minDelay", x+labelWidth+hSpace-5, y, componentWidth, componentHeight, "minDelay", ""+config.getMinTriggerDelay(), 100, 10000, 10, textColor, bgColor);
		new Label(this, "Max delay", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Maximum delay", textColor);
		maxDelayControl = new MinusPlus(this, "maxDelay", x+labelWidth+hSpace-5, y, componentWidth,componentHeight, "maxDelay", ""+config.getMaxTriggerDelay(), 100, 10000, 10, textColor, bgColor);
		new Label(this, "Step by step delay", x, y+=vSpace, labelWidth, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "Step by step delay", textColor);
		stepByStepDelayControl = new MinusPlus(this, "stepbystepDelay", x+labelWidth+hSpace-5, y, componentWidth,componentHeight, "stepbystepDelay", ""+config.getStepByStepDelay(), 100, 10000, 10, textColor, bgColor);
		
		x = width-600;
		y = 430;
		
		new Label(this, "mapping", x, y-35, labelWidth-30, labelHeight, XAlign.LEFT, YAlign.MIDDLE, "PIN MAPPING", textColor);
		
		ledPin = new ListSelectionCheckbox(this, "ledPin", x, y, 200, 15, "LED", config.getUseLedCmd(), config.getAvailablePins(), config.getLedPin());
		startPin = new ListSelectionCheckbox(this, "startPin", x, y+=23, 200, 15, "Start", config.getUseStartCmd(), config.getAvailablePins(), config.getStartPin());
		delayPin = new ListSelectionCheckbox(this, "delayPin", x, y+=23, 200, 15, "Delay", config.getUseDelayCmd(), config.getAvailableAnalogPins(), config.getDelayPin());
		testPin = new ListSelectionCheckbox(this, "testPin", x, y+=23, 200, 15, "Test", config.getUseTestCmd(), config.getAvailablePins(), config.getTestPin());
		System.out.println(config.getUseAutoLoopCmd());
		autoloopPin = new ListSelectionCheckbox(this, "autoloopPin", x, y+=23, 200, 15, "Autoloop", config.getUseAutoLoopCmd(), config.getAvailablePins(), config.getAutoLoopPin());
		stepByStepPin = new ListSelectionCheckbox(this, "stepByStepPin", x, y+=23, 200, 15, "Step by step", config.getUseStepByStepCmd(), config.getAvailablePins(), config.getStepByStepPin());
		stepperPin = new ListSelectionCheckbox(this, "stepperPin", x, y+=23, 200, 15, "Stepper", config.getUseStepperCmd(), config.getAvailablePins(), config.getStepperPin());
		dottedPin = new ListSelectionCheckbox(this, "dottedPin", x, y+=23, 200, 15, "Dotted", config.getUseDottedCmd(), config.getAvailablePins(), config.getDottedPin());
		blinkPin = new ListSelectionCheckbox(this, "blinkPin", x, y+=23, 200, 15, "Blink", config.getUseBlinkCmd(), config.getAvailablePins(), config.getBlinkPin());
		
		
		triggerManager = new TriggerPinsManager(this, width-360, 430, config);
		
		x = 20;
		y = height-55;
		new Label(this, "filename", x, y, 100, 20, XAlign.LEFT, YAlign.MIDDLE, "Arduino Filename", textColor);
		exportTextfield = new Textfield(this, "text", x, y+=25, 225, 20, "input", "NTQExport", "[tt", 255, 0);
		exportControl = new PushButton(this, "export", x+=235, y, buttonWidth, buttonHeight, "Export", "true", textColor, bgColor);

		backupTextfield = new Textfield(this, "text", x+=150, y, 225, 20, "input", "NTQBackup", "[tt", 255, 0);
		new Label(this, "filename", x, y-25, 100, 20, XAlign.LEFT, YAlign.MIDDLE, "Backup Filename", textColor);
		backupControl = new PushButton(this, "backup", x+=235, y, buttonWidth, buttonHeight, "Backup", "true", textColor, bgColor);
		openControl = new PushButton(this, "open", x+=110, y, buttonWidth, buttonHeight, "Open", "true", textColor, bgColor);
		
		//loadConfigControl = new PushButton(this, "load config", x+=buttonWidth+hSpace*2, y, buttonWidth, buttonHeight, "Load config", textColor, bgColor);
		saveConfigControl = new PushButton(this, "save config", x+=buttonWidth+hSpace*2, y, buttonWidth, buttonHeight, "Save config", "true", textColor, bgColor);

	}

	public void draw() {
			
		background(bgColor);
		fill(255,0,0);
		if (config.needArduinoMega()) {
			text(needMega, 20, 590, 380, 80);
		}			
	}
	
	
	public void fileSelected(File selection) {
		if (selection == null) {
		    // Do nothing
		  } else {
			String[] s = loadStrings(selection.getAbsolutePath());
			loadsave.load(matrixRenderer, s);
			rowsControl.setValue(""+s.length);
			triggerManager.setNumPins(s.length);
			config.setNumTriggers(s.length);
		  }
	}
	
	
	public void keyPressed() {
		
		if (exportTextfield.isActive() || backupTextfield.isActive()) {
			matrixRenderer.activate(false);
		}
		else {
			matrixRenderer.activate(true);
		}
		
		if (keyCode == 17) {
			toggleDrawerEraser();
		}
	}
	
	private void toggleDrawerEraser() {
		if (matrixRenderer.getColor() == -1) {
			negativeControl.setName("Draw mode");
			matrixRenderer.setColor(255);
		}
		else {
			negativeControl.setName("Erase mode");
			matrixRenderer.setColor(-1);				
		}
	}
	
	public void keyReleased() {

	}
	
	public void mouseMoved() {
		if (!exportTextfield.isActive() && !backupTextfield.isActive() && !matrixRenderer.isActive()) {
			matrixRenderer.activate(true);
		}
	}

	
	public void uiEvent(UIEvent event) {
		if (event.id == exportTextfield.getId()) {
			matrixRenderer.activate(false);
			config.setExportFilename(event.value);
		}
		else if (event.id == backupTextfield.getId()) {
			matrixRenderer.activate(false);
			config.setBackupFilename(event.value);
		}

		else if (event.id == negativeControl.getId()) {
			matrixRenderer.negative();
		}
		else if (event.id.indexOf("trigger") > -1) {
			//System.out.println("Trigger n¡ "+event.id.replaceAll("trigger", "") +" set to Pin "+event.getAsString());
			int trigger = Integer.parseInt(event.id.replaceAll("trigger", ""));
			String pin = event.getAsString();
			config.setTriggerPin(trigger, pin);
		}
		else if (event.id.indexOf("Pin") > -1) {
			String cmd = event.id;
			String pin = event.getAsString();
			if (cmd.equals(autoloopPin.getId())) {
				config.setAutoLoopPin(pin);
				config.setUseAutoLoopCmd(event.isActive);
			}
			if (cmd.equals(startPin.getId())) {
				config.setStartPin(pin);
				config.setUseStartCmd(event.isActive);
				if (!event.isActive) {
					config.setStart(true);
					startControl.setValue("true");
				}
			}
			if (cmd.equals(blinkPin.getId())) {
				config.setBlinkPin(pin);
				config.setUseBlinkCmd(event.isActive);
				if (event.isActive && !config.getUseLedCmd()) {
					config.setUseLedCmd(true);
					ledPin.enable(true);
				}
				else {
					if (!config.getBlink()) {
						config.setBlink(true);
						blinkControl.setValue("true");
					}
				}
			}
			if (cmd.equals(stepByStepPin.getId())) {
				config.setStepByStepPin(pin);
				config.setUseStepByStepCmd(event.isActive);	
				if (event.isActive) { // If active, enable stepper
					config.setUseStepperCmd(true);
					stepperPin.enable(true);
				}
				else {
					if (config.getUseStepperCmd()) { // If stepper is enabled, use step-by-step is true
						config.setStepByStep(true);
						stepByStepControl.setValue("true");
					}
				}
			}
			if (cmd.equals(stepperPin.getId())) {
				config.setStepperPin(pin);
				config.setUseStepperCmd(event.isActive);
				if (!event.isActive) { // If not active, no need for stepper
					config.setUseStepByStepCmd(false);	
					stepByStepPin.enable(false);
					config.setStepByStep(false);
					stepByStepControl.setValue("false");
				}
				else {
					if (!config.getUseStepByStepCmd()) {
						config.setStepByStep(true);
						stepByStepControl.setValue("true");
					}
				}
			}
			if (cmd.equals(delayPin.getId())) {
				config.setDelayPin(pin);
				config.setUseDelayCmd(event.isActive);
			}
			if (cmd.equals(dottedPin.getId())) {
				config.setDottedPin(pin);
				config.setUseDottedCmd(event.isActive);
			}
			if (cmd.equals(testPin.getId())) {
				config.setTestPin(pin);
				config.setUseTestCmd(event.isActive);
			}
			if (cmd.equals(ledPin.getId())) {
				config.setLedPin(pin);
				config.setUseLedCmd(event.isActive);
				if (!config.getUseBlinkCmd()) {
					config.setBlink(true);
					blinkControl.setValue("true");					
				}
				if (!event.isActive) { // Disable blink if no LED
					config.setUseBlinkCmd(false);
					blinkPin.enable(false);
					config.setBlink(false);
					blinkControl.setValue("false");
				}
			}
			
		}
		else if (event.id.equals(rowsControl.getId())) {
			matrixRenderer.setRows(event.getAsInt());
			triggerManager.setNumPins(event.getAsInt());
			config.setNumTriggers(event.getAsInt());
		}
		else if (event.id.equals(toggleGridControl.getId())) {
			matrixRenderer.toggleGrid();
		}
		else if (event.id.equals(exportControl.getId())) {
			matrixRenderer.fitToWidth();
			String[] code = exporter.export(matrixRenderer.getMatrix());
			saveStrings(sketchPath("exports")+"/"+config.getExportFilename()+"/"+config.getExportFilename()+".ino", code);
		}
		else if (event.id.equals(backupControl.getId())) {
			matrixRenderer.fitToWidth();
			String[] backup = loadsave.save(matrixRenderer.getMatrix());
			saveStrings(sketchPath("backups")+"/"+config.getBackupFilename()+"/"+config.getBackupFilename()+".txt", backup);
		}
		else if (event.id.equals(openControl.getId())) {
			selectInput("Select a file to process:", "fileSelected");
		}
		else if (event.id.equals(horizontalFlipControl.getId())) {
			matrixRenderer.horizontalFlip();
		}
		else if (event.id.equals(verticalFlipControl.getId())) {
			matrixRenderer.verticalFlip();
		}
		else if (event.id.equals(moveLeftControl.getId())) {
			matrixRenderer.moveRight();
		}
		else if (event.id.equals(fitToWidthControl.getId())) {
			matrixRenderer.fitToWidth();
		}
		else if (event.id.equals(clearAllControl.getId())) {
			matrixRenderer.clear();
		}

		else if (event.id.equals(moveRightControl.getId())) {
			matrixRenderer.moveLeft();
		}

		else if (event.id.equals(blinkControl.getId())) {
			config.setBlink(event.getAsBoolean());

			if (event.isActive) {
				config.setUseLedCmd(true);
				ledPin.enable(true);
				ledPin.setValue("true");
			}
		}

		else if (event.id.equals(autoLoopControl.getId())) {
			config.setAutoLoop(event.getAsBoolean());
		}

		else if (event.id.equals(startControl.getId())) {
			config.setStart(event.getAsBoolean());
		}

		else if (event.id.equals(dottedControl.getId())) {
			config.setDotted(event.getAsBoolean());
		}

		else if (event.id.equals(stepByStepControl.getId())) {
			config.setStepByStep(event.getAsBoolean());
		}
		
		else if (event.id.equals(defaultDelayControl.getId())) {
			config.setCurrentTriggerDelay(event.getAsInt());
		}		
		else if (event.id.equals(endDelayControl.getId())) {
			config.setEndDelay(event.getAsInt());
		}		
		else if (event.id.equals(maxDelayControl.getId())) {
			config.setMaxTriggerDelay(event.getAsInt());
			if (event.getAsInt() < config.getMinTriggerDelay()) {
				minDelayControl.setValue(event.getAsString());
				config.setMinTriggerDelay(event.getAsInt());
			}
		}
		else if (event.id.equals(minDelayControl.getId())) {
			config.setMinTriggerDelay(event.getAsInt());
			if (event.getAsInt() > config.getMaxTriggerDelay()) {
				maxDelayControl.setValue(event.getAsString());
				config.setMaxTriggerDelay(event.getAsInt());
			}
		}
		else if (event.id.equals(stepByStepDelayControl.getId())) {
			config.setStepByStepDelay(event.getAsInt());
		}		

		else if (event.id.equals(saveConfigControl.getId())) {
			config.saveConfig();
		}
		/*else if (event.id.equals(loadConfigControl.getId())) {
			config.reloadConfig();
			// TODO load config
		}*/
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { ntqsoft.NTQSoft.class.getName() });
	}
}
