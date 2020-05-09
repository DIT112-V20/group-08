#include <ESPmDNS.h>
#include <Smartcar.h>
#include <WebServer.h>
#include <WiFi.h>

const char* ssid     = "testing";
const char* password = "123456789";
const auto pulsesPerMeter = 600;

WebServer server(80);

BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);
GY50 gyroscope(37);
DirectionlessOdometer leftOdometer(
    smartcarlib::pins::v2::leftOdometerPin, []() { leftOdometer.update(); }, pulsesPerMeter);
DirectionlessOdometer rightOdometer(
    smartcarlib::pins::v2::rightOdometerPin, []() { rightOdometer.update(); }, pulsesPerMeter);
SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

void setup(void)
{
    Serial.begin(115200);
    WiFi.softAP(ssid, password);
    IPAddress local_ip(192, 168, 0, 10);
    IPAddress gateway(192, 168, 0, 1);
    IPAddress subnet(255, 255, 255, 0);

    WiFi.config(local_ip, gateway, subnet);
    delay(100);
    
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
    Serial.println(WiFi.gatewayIP());
   // note to self: IP/something?command=integer will work
    server.on("/drive", []() {
        const auto arguments = server.args();

        for (auto i = 0; i < arguments; i++)
        {
            const auto command = server.argName(i);
            if (command == "speed")
            {
                car.setSpeed(server.arg(i).toInt());
            }
            else if (command == "angle")
            {
                car.setAngle(server.arg(i).toInt());
            }
        }
        server.send(200, "text/plain", "ok");
    });

    server.on("/gyro", []() {
        server.send(200, "text/plain", String(car.getHeading()));
    });

    server.onNotFound(
        []() { server.send(404, "text/plain", "Unknown command"); });

    server.begin();
    Serial.println("HTTP server started");
}

void loop(void)
{
    server.handleClient();
    car.update();
}