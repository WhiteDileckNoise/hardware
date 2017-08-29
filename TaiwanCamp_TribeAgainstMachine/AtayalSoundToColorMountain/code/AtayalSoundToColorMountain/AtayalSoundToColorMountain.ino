///////////////////////////////////////////
///////////////////////////////////////////
////TRIBE_AGAINST_MACHINE//////////////////
////Code for interactive sound to color////
////traditional Atayal pattern/////////////
///////////////////////////////////////////
////27/08/17///////////////////////////////
////WhiteDileckNoise///////////////////////
///////////////////////////////////////////
///                                     ///
///////////////////////////////////////////

#include <elapsedMillis.h>
#include <Adafruit_NeoPixel.h>

elapsedMillis countMillis;

#define PIN 1

const int MIC_PIN = A2;
const int NUMPIXELS = 4;
int calibrate = 5000;
unsigned int signalMax = 0;
unsigned int signalMin = 1024;
int mic;


Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

void setup() {
  pinMode(led, OUTPUT);
  strip.begin();
  strip.show();
  while (countMillis < calibrate) {
    mic = analogRead(MIC_PIN);
    if (mic > signalMax) {
      signalMax = mic;  // save just the max levels
    }
    else if (mic < signalMin) {
      signalMin = mic;  // save just the min levels
    }
  }
}

void loop() {
  mic = analogRead(MIC_PIN);

  if (mic >= signalMin + 100) {
    playColor();
  }
  else {
    for (int i = 0; i <= NUMPIXELS; i++) {
      strip.setPixelColor(i, 0, 0, 0);
      strip.show();
    }
  }
}

void playColor() {
  for (int i = 0; i <= NUMPIXELS; i++) {
    for (int c = 0; c <= 255; c++) {
      strip.setPixelColor(i, c, 0, 0);
      strip.show();
      delay(1);
    }
  }
}

