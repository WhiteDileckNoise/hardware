/* ATtiny-based EMF detector with
 * visual, sonic and haptic feedback
 *  
 * Etextiles SummerCamp workshop
 * June 2016
 */

#define NUMREADINGS 15 // raise this number to increase data smoothing

int senseLimit = 100; // raise this number to decrease sensitivity (up to 1023 max)
int coil = A3; // coil antenna connected to analog pin A3
int val = 0; // value reading from the coil

int audioOut = 0; // 8-Ohm speaker or female mini-jack connected to digital pin 0

// variables for smoothing the antenna's readings
int readings[NUMREADINGS];                // the readings from the coil
int index = 0;                            // the index of the current reading
int total = 0;                            // the running total
int average = 0;                          // final average of the coil reading


void setup() {
 
  pinMode(audioOut, OUTPUT); // initialize speaker or mini-jack as an output

  for (int i = 0; i < NUMREADINGS; i++)
    readings[i] = 0;                      // initialize all the readings to 0
}

void loop() {

 val = analogRead(coil);  // take a reading from the coil antenna

  if(val >= 1){                // if the reading isn't zero, proceed

    val = constrain(val, 1, senseLimit);  // turn any reading higher than the senseLimit value into the senseLimit value
    val = map(val, 1, senseLimit, 1, 1023);  // remap the constrained value within a 1 to 1023 range

    total -= readings[index];               // subtract the last reading
    readings[index] = val;                  // read from the coil
    total += readings[index];               // add the reading to the total
    index = (index + 1);                    // advance to the next index

    if (index >= NUMREADINGS)               // if we're at the end of the array...
      index = 0;                            // ...wrap around to the beginning

    average = total / NUMREADINGS;          // calculate the average
    
    int freq = map(average, 0, 1023, 60, 8800); // map the average reading in frequencies from 60-8800Hz
    int dur = map(freq, 60, 8800, 2, 50); // map the freq to control the frequency duration (millis)
    
    beep(audioOut, freq, dur); // call the sound producing function

  }
}


// the sound producing function
void beep (unsigned char speakerPin, int frequencyInHertz, long timeInMilliseconds)
{  // http://web.media.mit.edu/~leah/LilyPad/07_sound_code.html
          int x;   
          long delayAmount = (long)(1000000/frequencyInHertz);
          long loopTime = (long)((timeInMilliseconds*1000)/(delayAmount*2));
          for (x=0;x<loopTime;x++)   
          {  
              digitalWrite(speakerPin,HIGH);
              delayMicroseconds(delayAmount);
              digitalWrite(speakerPin,LOW);
              delayMicroseconds(delayAmount);
          }  
}
