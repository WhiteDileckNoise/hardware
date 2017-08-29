package ntqsoft;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ArduinoExport {

	private Configurator config;


	public ArduinoExport(Configurator config) {
		this.config = config;
	}

	public Configurator getConfig() {
		return config;
	}

	public HashMap<String, String> getTriggerPinMapping() {
		HashMap<String, String> mapping = new HashMap<String, String>();
		for (int i=0; i<config.getNumTriggers(); i++) {
			mapping.put("outPin"+(i+1), config.getTriggerPin(i));
		}
		return mapping;
	}

	public String[] export(List<int[]> matrix) {

		// Shorten if needed
		optimize(matrix);

		//
		if (null != config) {
			List<String> arduinoCode = new LinkedList<String>();

			createComments(arduinoCode);
			arduinoCode.add("");
			createVariables(arduinoCode, matrix);
			arduinoCode.add("");
			arduinoCode.add("void setup() {");
			createSetup(arduinoCode);
			arduinoCode.add("}");
			arduinoCode.add("");
			createLoop(arduinoCode);
			createTrigger(arduinoCode);
			if (config.getBlink() || config.getUseBlinkCmd()) {
				createStartSequence(arduinoCode);
				createStopSequence(arduinoCode);
			}
			if (config.getUseBlinkCmd()) {
				createReadBlinkButton(arduinoCode);
			}
			if (config.getUseTestCmd()) {
				createReadTestButton(arduinoCode);
			}
			if (config.getUseStartCmd()) {
				createReadStartButton(arduinoCode);
			}
			if (config.getUseAutoLoopCmd()) {
				createReadAutoloopButton(arduinoCode);
			}
			if (config.getUseDelayCmd()) {
				createReadDelayButton(arduinoCode);
			}
			if (config.getUseDottedCmd()) {
				createReadDottedButton(arduinoCode);
			}
			if (config.getUseStepByStepCmd()) {
				createReadStepByStepButton(arduinoCode);
			}
			if(config.getUseStepperCmd()) {
				createReadStepperButton(arduinoCode);
			}
			
			createTest(arduinoCode);
			createOff(arduinoCode);
			createOn(arduinoCode);
			
			createInitButtons(arduinoCode);
			
			debug(arduinoCode);
			
			String[] code = arduinoCode.toArray(new String[0]);
			return code;
		}
		return null;
	}

	private void optimize(List<int[]> matrix) {
		// Scan every row and find longer one

		// If less than the actual size the resize it

	}


	private void createComments(List<String> arduinoCode) {
		arduinoCode.add("/*");
		arduinoCode.add("/ Near Tag Quality (NTQ)");
		arduinoCode.add("/");
		arduinoCode.add("/ A project by Graffiti Research Lab France");
		arduinoCode.add("/ http://graffitiresearchlab.fr");
		arduinoCode.add("/");
		arduinoCode.add("/ This project is published under a Creative Commons: Attribution-NonCommercial-Repercussions 3.0 Unported licence");
		arduinoCode.add("*/");
		arduinoCode.add("");
		arduinoCode.add("");
	}


	private void createVariables(List<String> arduinoCode, List<int[]>  matrix) {
		String code = "";

		// TRIGGERS
		code = "// Triggers";
		arduinoCode.add(code);
		code = "int triggers["+config.getNumTriggers()+"] = {";
		for (int i=0; i<config.getNumTriggers(); i++) {
			code += config.getTriggerPin(i) + ",";
		}
		code += "};";
		arduinoCode.add(code);

		// LED
		if (config.getUseLedCmd()) {
			code = "int startLedPin = "+config.getLedPin()+";";
			arduinoCode.add(code);
		}

		// COMMANDS
		code = "// Commands";
		arduinoCode.add(code);

		arduinoCode.add("");
		if (config.getUseStartCmd()) {
			code = "int startCommandPin = "+config.getStartPin()+";";
			arduinoCode.add(code);
		}
		if (config.getUseAutoLoopCmd()) {
			code = "int autoloopCommandPin = "+config.getAutoLoopPin()+";";
			arduinoCode.add(code);
		}
		if (config.getUseTestCmd()) {
			code = "int testCommandPin = "+config.getTestPin()+";";
			arduinoCode.add(code);
		}
		if (config.getUseDelayCmd()) {
			code = "int delayCommandPin = "+config.getDelayPin()+";";
			arduinoCode.add(code);
		}
		if (config.getUseDottedCmd()) {
			code = "int dottedCommandPin = "+config.getDottedPin()+";";
			arduinoCode.add(code);
		}
		if (config.getUseBlinkCmd()) {
			code = "int blinkCommandPin = "+config.getBlinkPin()+";";
			arduinoCode.add(code);
		}
		if (config.getUseStepByStepCmd()) {
			code = "int stepByStepCommandPin = "+config.getStepByStepPin()+";";
			arduinoCode.add(code);
		}		
		if (config.getUseStepperCmd()) {
			code = "int stepperCommandPin = "+config.getStepperPin()+";";
			arduinoCode.add(code);
		}

		// STATES
		code = "// STATES";
		arduinoCode.add(code);
		if (!config.getUseStartCmd()) {
			code = "boolean started = true;";
		}
		else {
			code = "boolean started = false;";
		}
		
		arduinoCode.add("");
		arduinoCode.add("// DEBUG");
		arduinoCode.add("boolean debug = false;");
		arduinoCode.add("");
		
		// Buttons default values
		code = "// COMMANDS DEFAUTL VALUES";
		arduinoCode.add(code);
		code = "boolean startON = " + (config.getStart() ? "true": "false") + ";";
		arduinoCode.add(code);
		code = "boolean testON = false;";
		arduinoCode.add(code);
		code = "boolean autoloopON = " + (config.getAutoLoop() ? "true": "false") + ";";
		arduinoCode.add(code);
		code = "boolean dottedON = " + (config.getDotted() ? "true": "false") + ";";
		arduinoCode.add(code);
		code = "boolean stepByStepON = " + (config.getStepByStep() ? "true": "false") + ";";
		arduinoCode.add(code);
		code = "boolean stepperON = false;";
		arduinoCode.add(code);
		code = "boolean blinkON = " + (config.getBlink() ? "true": "false") + ";";
		arduinoCode.add(code);


		// Output modules
		arduinoCode.add("");
		code = "//DRAWING INFO";
		arduinoCode.add(code);
		arduinoCode.add("");
		
		code = "int trigs["+matrix.size()+"]["+matrix.get(0).length+"] = {";
		for (int i=0; i<matrix.size(); i++) {
			String arrayAsString = Arrays.toString(matrix.get(i));
			// Change from something like [-1,0,-1] to {-1,0,-1}
			arrayAsString = arrayAsString.replaceAll("\\[", "{");
			arrayAsString = arrayAsString.replaceAll("\\]", "},");
			// ex: outPin1Array = {-1,0,-1};
			code += arrayAsString;
		}
		code += "};";
		
		arduinoCode.add(code);

		arduinoCode.add("");
		code = "int pointer = 0;";
		arduinoCode.add(code);

		code = "boolean started = "+ (config.getStart() ? "true" : "false")+";";
		arduinoCode.add(code);

		
		arduinoCode.add("");
		code = "int triggerDelay = "+config.getCurrentTriggerDelay()+";";
		arduinoCode.add(code);
		code = "int minTriggerDelay = "+config.getMinTriggerDelay()+";";
		arduinoCode.add(code);
		code = "int maxTriggerDelay = "+config.getMaxTriggerDelay()+";";
		arduinoCode.add(code);
		code = "int endTriggerDelay = "+config.getEndDelay()+";";
		arduinoCode.add(code);
		code = "int stepByStepDelay = "+config.getStepByStepDelay()+";";
		arduinoCode.add(code);


	}

	private void createSetup(List<String> arduinoCode) {

		String code = "";
		
		arduinoCode.add("  if (debug) Serial.begin(9600);");

		// 
		code = "  for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {";
		arduinoCode.add(code);
		code = "    pinMode(triggers[i], OUTPUT);";
		arduinoCode.add(code);
		code = "  }";
		arduinoCode.add(code);

		// LED
		if (config.getUseLedCmd()) {
			code = "  pinMode(startLedPin, OUTPUT);";
			arduinoCode.add(code);
		}

		// COMMANDS
		arduinoCode.add("");
		arduinoCode.add("// COMMANDS");
		if (config.getUseStartCmd()) {
			code = "pinMode(startCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}
		if (config.getUseAutoLoopCmd()) {
			code = "pinMode(autoloopCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}
		if (config.getUseTestCmd()) {
			code = "pinMode(testCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}
		if (config.getUseDelayCmd()) {
			code = "pinMode(delayCommandPin, INPUT);";
			arduinoCode.add(code);
		}
		if (config.getUseDottedCmd()) {
			code = "pinMode(dottedCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}
		if (config.getUseBlinkCmd()) {
			code = "pinMode(blinkCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}
		if (config.getUseStepByStepCmd()) {
			code = "pinMode(stepByStepCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}		
		if (config.getUseStepperCmd()) {
			code = "pinMode(stepperCommandPin, INPUT_PULLUP);";
			arduinoCode.add(code);
		}


		// INIT BLINK
		if (config.getUseLedCmd()) {
			arduinoCode.add("// Start signal (blink LED");
			arduinoCode.add(" digitalWrite(startLedPin, HIGH);"); 
			arduinoCode.add("delay(500);");
			arduinoCode.add("digitalWrite(startLedPin, LOW);");
		}
		
		arduinoCode.add("initButtons();");
	}

	private void createLoop(List<String> arduinoCode) {

		arduinoCode.add("void loop() {");

		if (config.getUseAutoLoopCmd()) {
			arduinoCode.add("  readAutoloopButton();");
		}
		if (config.getUseDelayCmd()) {
			arduinoCode.add("  readDelayButton();");
		}
		if (config.getUseDottedCmd()) {
			arduinoCode.add("  readDottedButton();");
		}
		if (config.getUseBlinkCmd()) {
			arduinoCode.add("  readBlinkButton();");
		}
		if (config.getUseStepByStepCmd()) {
			arduinoCode.add("  readStepByStepButton();");
		}		
		if (config.getUseTestCmd()) {
			arduinoCode.add("  readTestButton();");
		}
		if (config.getUseStartCmd()) {
			arduinoCode.add("  readStartButton();");
		}

		arduinoCode.add("  if (started) {");
		if (config.getUseStepByStepCmd()) {
		arduinoCode.add("    if (stepByStepON) {");
		if (config.getUseLedCmd()) {
		arduinoCode.add("      if (blinkON) {");
		arduinoCode.add("        if (pointer == 0) {");
		arduinoCode.add("          digitalWrite(startLedPin, HIGH);");
		arduinoCode.add("        }");
		arduinoCode.add("        else {");
		}
		arduinoCode.add("          digitalWrite(startLedPin, LOW); ");
		if (config.getUseLedCmd()) {		
		arduinoCode.add("        }");
		}
		arduinoCode.add("      }");
		arduinoCode.add("      readStepperButton();");
		arduinoCode.add("      if (stepperON) {");
		arduinoCode.add("        trigger();");
		arduinoCode.add("        pointer += 1;");
		arduinoCode.add("        if (debug) Serial.println(pointer);");
		arduinoCode.add("      }");
		arduinoCode.add("    }");
		arduinoCode.add("    else {");
		}
		if (config.getUseLedCmd()) {
		arduinoCode.add("      if (pointer == 0 && blinkON) {");
		arduinoCode.add("         startLedSequence();");
		arduinoCode.add("      }");
		}
		arduinoCode.add("      trigger();");
		arduinoCode.add("      pointer += 1;");
		arduinoCode.add("      if (debug) Serial.println(pointer);");
		if (config.getUseStepByStepCmd()) {
		arduinoCode.add("    }");
		}
		arduinoCode.add("    if (pointer >= sizeof(trigs[0])/sizeof(int)) {");
		arduinoCode.add("      off();");
		arduinoCode.add("      pointer = 0; ");
		arduinoCode.add("      if (autoloopON) {");
		arduinoCode.add("        delay(endTriggerDelay);");
		arduinoCode.add("      }");
		arduinoCode.add("      else {");
		arduinoCode.add("        started = false;");
		arduinoCode.add("      }");
		arduinoCode.add("    }");
		arduinoCode.add("  }");
		arduinoCode.add("}");
		
	}


	private void createTrigger(List<String> arduinoCode) {
		arduinoCode.add("void trigger() {");
		arduinoCode.add("    for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {");
		arduinoCode.add("      digitalWrite(triggers[i], trigs[i][pointer] == -1 ? LOW : HIGH);");
		arduinoCode.add("    }");
		arduinoCode.add("    delay(triggerDelay);");  
		arduinoCode.add("    if (dottedON) {");
		arduinoCode.add("      off();");
		arduinoCode.add("      delay(triggerDelay);");
		arduinoCode.add("    }");
		arduinoCode.add("  }");
	}
	
	private void createStartSequence(List<String> arduinoCode) {
		arduinoCode.add("void startLedSequence() {");
		arduinoCode.add("	  // SLOW blink 3 times");
		arduinoCode.add("    if (!stepByStepON) {");
		arduinoCode.add("	   for (int i=0; i<3; i++) {");
		arduinoCode.add("        digitalWrite(startLedPin, HIGH);");
		arduinoCode.add("        delay(500);");
		arduinoCode.add("        digitalWrite(startLedPin, LOW);");
		arduinoCode.add("        delay(500);");
		arduinoCode.add("      }");
		arduinoCode.add("	   // QUICK blink 3 times");
		arduinoCode.add("	   for (int i=0; i<3; i++) {");
		arduinoCode.add("        digitalWrite(startLedPin, HIGH);");
		arduinoCode.add("        delay(250);");
		arduinoCode.add("        digitalWrite(startLedPin, LOW);");
		arduinoCode.add("        delay(250);");
		arduinoCode.add("      }");
		arduinoCode.add("   }");
		arduinoCode.add(" }");
	}

	private void createStopSequence(List<String> arduinoCode) {
		arduinoCode.add("void stopLedSequence() {");
		arduinoCode.add("  // QUICK blink 10 times");
		arduinoCode.add("  for (int i=0; i<10; i++) {");
		arduinoCode.add("    digitalWrite(startLedPin, HIGH);");
		arduinoCode.add("    delay(100);");
		arduinoCode.add("    digitalWrite(startLedPin, LOW);");
		arduinoCode.add("    delay(100);");
		arduinoCode.add("  }");
		arduinoCode.add(" }");
	}


	private void createReadBlinkButton(List<String> arduinoCode) {
		arduinoCode.add("void readBlinkButton() {");
		arduinoCode.add("  boolean b = digitalRead(blinkCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (b != blinkON) {");
		arduinoCode.add("    blinkON = b;");
		arduinoCode.add("    if (debug) Serial.print(\"Blink set to: \");");
		arduinoCode.add("    if (debug) Serial.println(blinkON ? \"ON\" : \"OFF\");");    
		arduinoCode.add("  }");
		arduinoCode.add("  delay(20);");
		arduinoCode.add("}");
	}

	private void createReadTestButton(List<String> arduinoCode) {
		arduinoCode.add("void readTestButton() {");
		arduinoCode.add("  testON = digitalRead(testCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (testON) {");
		arduinoCode.add("    if (debug) Serial.println(\"Test button ON\");");
		arduinoCode.add("    started = false;");
		arduinoCode.add("    test();");
		arduinoCode.add("  }");
		arduinoCode.add("  delay(20);");
		arduinoCode.add("}");
	}

	private void createReadStartButton(List<String> arduinoCode) {
		arduinoCode.add("void readStartButton() {");
		arduinoCode.add("  startON = digitalRead(startCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (startON) {");
		arduinoCode.add("    if (debug) Serial.println(\"Start button ON\");");
		arduinoCode.add("    if (started) { // STOP");
		arduinoCode.add("      if (debug) Serial.println(\"STOPPING\");");
		arduinoCode.add("      started = false;");
		arduinoCode.add("      off();");
		arduinoCode.add("      if (blinkON) {");
		arduinoCode.add("        stopLedSequence();");
		arduinoCode.add("      }");
		arduinoCode.add("      else {");
		arduinoCode.add("        delay(1000);");        
		arduinoCode.add("      }");
		arduinoCode.add("    }");
		arduinoCode.add("    else { // START");
		arduinoCode.add("      if (debug) Serial.println(\"STARTING\");");      
		arduinoCode.add("      started  = true;");      
		arduinoCode.add("      pointer = 0;");
		arduinoCode.add("      delay(50);");
		arduinoCode.add("    }");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createReadAutoloopButton(List<String> arduinoCode) {
		arduinoCode.add("void readAutoloopButton() {");
		arduinoCode.add("  boolean b = digitalRead(autoloopCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (b != autoloopON) {");
		arduinoCode.add("    autoloopON = b;");
		arduinoCode.add("    if (debug) Serial.print(\"Autoloop set to: \");");
		arduinoCode.add("    if (debug) Serial.println(autoloopON ? \"true\" : \"false\");");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createReadDelayButton(List<String> arduinoCode) {
		arduinoCode.add("void readDelayButton() {");
		arduinoCode.add("  float delayValue = analogRead(delayCommandPin);");
		arduinoCode.add("  int val = map(delayValue, 0, 1023, minTriggerDelay, maxTriggerDelay);");
		arduinoCode.add("  if (val < triggerDelay-2 || val > triggerDelay+2) {");
		arduinoCode.add("    triggerDelay = val;");
		arduinoCode.add("    if (debug) Serial.print(\"Trigger delay set to: \");");
		arduinoCode.add("    if (debug) Serial.println(triggerDelay);");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createReadDottedButton(List<String> arduinoCode) {
		arduinoCode.add("void readDottedButton() {");
		arduinoCode.add("  boolean b = digitalRead(dottedCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  delay(20);");
		arduinoCode.add("  if (b != dottedON) {");
		arduinoCode.add("    dottedON = b;");
		arduinoCode.add("    Serial.print(\"Dotted set to: \");");
		arduinoCode.add("    Serial.println(dottedON ? \"true\" : \"false\");");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createReadStepByStepButton(List<String> arduinoCode) {
		arduinoCode.add("void readStepByStepButton() {");
		arduinoCode.add("  boolean b = digitalRead(stepByStepCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (b != stepByStepON) {");
		arduinoCode.add("    stepByStepON = b;");
		arduinoCode.add("    if (debug) Serial.print(\"Step by step set to: \");");
		arduinoCode.add("    if (debug) Serial.println(stepByStepON ? \"true\" : \"false\");");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createReadStepperButton(List<String> arduinoCode) {
		arduinoCode.add("void readStepperButton() {");
		arduinoCode.add("  stepperON = digitalRead(stepperCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  delay(20);");
		arduinoCode.add("  if (stepperON) Serial.println(\"Stepper button ON\");");
		arduinoCode.add("  if (stepperON) {");
		arduinoCode.add("    if (stepByStepON) {");
		arduinoCode.add("      if (debug) Serial.println(\"Next step\");");
		arduinoCode.add("    }");
		arduinoCode.add("    else {");
		arduinoCode.add("      if (debug) Serial.println(\"Step by step is OFF doing nothing\");"); 
		arduinoCode.add("    }");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createTest(List<String>  arduinoCode) {
		arduinoCode.add("void test() {");
		arduinoCode.add("  on();");
		arduinoCode.add("  delay(500);");
		arduinoCode.add("  off();");
		arduinoCode.add("}");
	}

	private void createOff(List<String>  arduinoCode) {
		arduinoCode.add("void off() {");
		arduinoCode.add("  for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {");
		arduinoCode.add("    digitalWrite(triggers[i], LOW);");
		arduinoCode.add("  }");
		if (config.getUseLedCmd()) {
			arduinoCode.add("  digitalWrite(startLedPin, LOW);");
		}
		arduinoCode.add("}");
	}

	private void createOn(List<String>  arduinoCode) {
		arduinoCode.add("void on() {");
		arduinoCode.add("  for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {");
		arduinoCode.add("    digitalWrite(triggers[i], HIGH);");
		arduinoCode.add("  }");
		arduinoCode.add("}");
	}

	private void createInitButtons(List<String>  arduinoCode) {
		arduinoCode.add("void initButtons() {");
		if (config.getUseDottedCmd()) {
		arduinoCode.add("  dottedON = digitalRead(dottedCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (debug) Serial.print(\"Dotted: \");");
		arduinoCode.add("  if (debug) Serial.println(dottedON ? \"ON\" : \"OFF\");");
		}
		if (config.getUseBlinkCmd()) {
		arduinoCode.add("  blinkON = digitalRead(blinkCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (debug) Serial.print(\"Blink: \");");
		arduinoCode.add("  if (debug) Serial.println(blinkON ? \"ON\" : \"OFF\");");
		}
		if (config.getUseAutoLoopCmd()) {
		arduinoCode.add("  autoloopON = digitalRead(autoloopCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (debug) Serial.print(\"Autoloop: \");");
		arduinoCode.add("  if (debug) Serial.println(autoloopON ? \"ON\" : \"OFF\");");
		}
		if (config.getUseStepByStepCmd()) {
		arduinoCode.add("  stepByStepON = digitalRead(stepByStepCommandPin) == HIGH ? false : true;");
		arduinoCode.add("  if (debug) Serial.print(\"Step by step: \");");
		arduinoCode.add("  if (debug) Serial.println(stepByStepON ? \"ON\" : \"OFF\");");
		}
		arduinoCode.add("}");
	}

	
	
	private void debug(List<String> arduinoCode) {
		for (String line : arduinoCode) {
			System.out.println(line);
		}
	}

}
