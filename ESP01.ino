const char* ssid = "xxxxxxxx";//your wifi ssid
const char* password = "xxxxxxxx";//your wifi password
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <Servo.h>
WiFiServer server(80);//esp wifi server
Servo myservo;//servo object
int pos;//servo position
void setup(void) {
  myservo.attach(2);//which pin is the control input of the servo on(GPIO2)
  WiFi.begin(ssid, password);//start connecting
  server.begin();//start the esp server

}

void loop(void) {
  // Check if module is still connected to WiFi.
  WiFiClient client = server.available();//assign a connecting client if there is any
  if (client) {//check if there is a client connected
    while (client.connected()) {//loops while the client is connected
      if(client.available()){ //check if any data is availble from the client
      if(client.read() == '1'){//read the data and check if it is equals to '1'
        for(pos = 0; pos <= 180; pos += 1)//loop from 0 to 180 degrees to move the Servo
          {
            myservo.write(pos);//move the servo to the position
            delay(15);//let the servo reach the position
          }
        for(pos = 180; pos>=0; pos-=1)//loop from 0 to 180 degrees to move the Servo
         {                                
            myservo.write(pos);//move the servo to the position
            delay(15);//let the servo reach the position
         } 
      }
      }
    }
    client.stop();
  }
  
}
