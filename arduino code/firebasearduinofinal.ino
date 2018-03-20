#include <FirebaseArduino.h>

#include <ESP8266WiFi.h>

const int ledPin = 15;





// Set these to your WIFI settings

#define WIFI_SSID "sparsh"

#define WIFI_PASSWORD "sparsh12345"



//Set these to the Firebase project settings

#define FIREBASE_URL "iotboard-f8a25.firebaseio.com"

#define FIREBASE_DB_SECRET "0o5BCW0v0e2fAmHWztdiiD2bNT6FHL0TFxL9beYq"

void setup() {
  

  

  pinMode(ledPin, OUTPUT);

  Serial.begin(9600);



  // connect to wifi.

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  Serial.print("connecting");

  while (WiFi.status() != WL_CONNECTED) {

    Serial.print(".");

    delay(500);

  }

  Serial.println();

  Serial.print("connected: ");

  Serial.println(WiFi.localIP());



  //begin Firebase

  Firebase.begin(FIREBASE_URL, FIREBASE_DB_SECRET);
  Firebase.set("pushbutton", 0);

  Firebase.set("sunlight", 0);

  Firebase.set("redlight", 0);

  Firebase.set("cooldown", 0);

  Firebase.set("brrr", 0);
  


}





void loop() {


  //check if streaming failed and try to restart

  if (Firebase.failed()) {

    Serial.println("streaming error");

    Serial.println(Firebase.error());

    delay(1000);

  //  Firebase.stream(PATH);

    return;

  }

  

  if (Firebase.available()) {

        Serial.print("red light is");
        Serial.print(Firebase.getInt("redlight"));
        
      
      if (Firebase.failed()) {

         Serial.print("setting /number failed:");

         Serial.println(Firebase.error());

      }



    }if(Firebase.getInt("redlight")== 1)
        digitalWrite(ledPin,1);
      else
        digitalWrite(ledPin,0);
      Serial.println("red light is");
        Serial.println(Firebase.getInt("redlight"));

  }   





void blink() {

  digitalWrite(LED_BUILTIN, HIGH);

  delay(500);

  digitalWrite(LED_BUILTIN, LOW);

  delay(500);

}



