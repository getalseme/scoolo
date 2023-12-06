#include <Arduino.h>

#ifndef LED_BUILTIN
  #define LED_BUILTIN PC13
#endif

void setup()
{
  randomSeed(analogRead(0));
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
  delay(2000);
  int tiro = random(1,7);
  printf(tiro + "\n");
  for(int i = 0; i < tiro; i++){
    digitalWrite(LED_BUILTIN, HIGH);
    delay(400);
    digitalWrite(LED_BUILTIN, LOW);
    delay(400);
  }
}

void loop()
{
  digitalWrite(LED_BUILTIN, LOW);
}