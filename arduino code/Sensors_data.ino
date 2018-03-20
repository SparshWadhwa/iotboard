#include<Wire.h>

const int DHT = A0; 
const int MQ135 = 3;
       
void setup() {
  Wire.begin();
  Serial.begin(9600);
}

void loop() {
  int DHT_val = analogRead(DHT);
  int MQ135_val=analogRead(MQ135);
  
  //int MQ135_ans=map(MQ135_val,0,1023,0,255);
  //int DHT_ans=map(DHT_val,0,1023,0,255);
  
  Serial.print("Humidity value (out of 0-1023)= ");
  Serial.print(DHT_val);
  Serial.print("\t Conc. of impurities (0-1023)= ");
  Serial.println(MQ135_val);
  delay(200);
  
  if(!Wire.available()){
    Serial.println("........");
  }
  else{
      Wire.beginTransmission(0);
      Wire.write(DHT_val+" "+MQ135_val);
      //Wire.endTransmission();
      Serial.println("Sended him some values. Awaiting confirmation...");
      Wire.requestFrom(0,1);
      while(Wire.available()){
        char c= Wire.read();
        Serial.println(c);
      }
      Wire.endTransmission();
      Serial.println();
      delay(500);
    }
}
