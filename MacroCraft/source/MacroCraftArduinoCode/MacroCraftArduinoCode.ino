const int 
PWM_A   = 3,
DIR_A   = 12,
BRAKE_A = 9,
//SNS_A   = A0,
PWM_B   = 11,
DIR_B   = 13,
BRAKE_B = 8,
//SNS_B   = A1,
turn = 7,
back = 6,
forward = 5;

void setup() {
  pinMode(BRAKE_A, OUTPUT);  
  pinMode(DIR_A, OUTPUT);    
  pinMode(BRAKE_B, OUTPUT);  
  pinMode(DIR_B, OUTPUT);    
  pinMode(turn, INPUT_PULLUP);     
  pinMode(back, INPUT_PULLUP);     
  pinMode(forward, INPUT_PULLUP);  
  Serial.begin(9600);
}

void loop() {
  int turnState = digitalRead(turn);
  int backState = digitalRead(back);
  int forwardState = digitalRead(forward);

  if (turnState == 0) {
    digitalWrite(BRAKE_A, LOW);  
    digitalWrite(DIR_A, HIGH);   
    analogWrite(PWM_A, 255); 
  }
    else{
    digitalWrite(BRAKE_A, HIGH); 
  }

  if( forwardState == 0 ){
    digitalWrite(BRAKE_B, LOW);  
    digitalWrite(DIR_B, HIGH);    
    analogWrite(PWM_B, 255); 
  }  
  
    else if( backState == 0){
    digitalWrite(BRAKE_B, LOW);  
    digitalWrite(DIR_B, LOW);    
    analogWrite(PWM_B, 255); 
  }  

  else{
    digitalWrite(BRAKE_B, HIGH); 
  }

  Serial.print(turnState);
  Serial.print("\t");
  Serial.print(backState);
  Serial.print("\t");
  Serial.println(forwardState);

}

