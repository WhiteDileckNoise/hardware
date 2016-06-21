/*
/ Near Tag Quality (NTQ)
/
/ A project by Graffiti Research Lab France
/ http://graffitiresearchlab.fr
/
/ This project is published under a Creative Commons: Attribution-NonCommercial-Repercussions 3.0 Unported licence
*/



// Triggers
int triggers[7] = {13,12,11,10,9,8,7,};
// Commands

// STATES

// DEBUG
boolean debug = false;

// COMMANDS DEFAUTL VALUES
boolean startON = true;
boolean testON = false;
boolean autoloopON = true;
boolean dottedON = false;
boolean stepByStepON = false;
boolean stepperON = false;
boolean blinkON = false;

//DRAWING INFO

int trigs[7][20] = {{255, -1, -1, -1, 255, -1, -1, -1, -1, -1, -1, 255, -1, 255, -1, -1, -1, -1, -1, -1},{255, -1, -1, -1, 255, -1, -1, -1, -1, -1, -1, 255, -1, 255, -1, -1, -1, -1, -1, -1},{255, -1, -1, -1, 255, -1, -1, 255, 255, -1, -1, 255, -1, 255, -1, -1, 255, 255, -1, -1},{255, 255, 255, 255, 255, -1, 255, -1, -1, 255, -1, 255, -1, 255, -1, 255, -1, -1, 255, -1},{255, -1, -1, -1, 255, -1, 255, 255, 255, 255, -1, 255, -1, 255, -1, 255, -1, -1, 255, -1},{255, -1, -1, -1, 255, -1, 255, -1, -1, 255, -1, 255, -1, 255, -1, 255, -1, -1, 255, -1},{255, -1, -1, -1, 255, -1, -1, 255, 255, 255, -1, 255, -1, 255, -1, -1, 255, 255, -1, -1},};

int pointer = 0;
boolean started = true;

int triggerDelay = 500;
int minTriggerDelay = 50;
int maxTriggerDelay = 1000;
int endTriggerDelay = 1000;
int stepByStepDelay = 200;

void setup() {
  if (debug) Serial.begin(9600);
  for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {
    pinMode(triggers[i], OUTPUT);
  }

// COMMANDS
initButtons();
}

void loop() {
  if (started) {
      trigger();
      pointer += 1;
      if (debug) Serial.println(pointer);
    if (pointer >= sizeof(trigs[0])/sizeof(int)) {
      off();
      pointer = 0; 
      if (autoloopON) {
        delay(endTriggerDelay);
      }
      else {
        started = false;
      }
    }
  }
}
void trigger() {
    for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {
      digitalWrite(triggers[i], trigs[i][pointer] == -1 ? LOW : HIGH);
    }
    delay(triggerDelay);
    if (dottedON) {
      off();
      delay(triggerDelay);
    }
  }
void test() {
  on();
  delay(500);
  off();
}
void off() {
  for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {
    digitalWrite(triggers[i], LOW);
  }
}
void on() {
  for (int i=0; i<sizeof(triggers)/sizeof(int); i++) {
    digitalWrite(triggers[i], HIGH);
  }
}
void initButtons() {
}

