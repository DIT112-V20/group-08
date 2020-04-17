# Mini project: BergerBil - EEG in IoT
## Team members
- Liv Alterskjaer, gusalteli
- Annan Lao, guslaoan
- Zhijie Wei, gusweizh
- Douglas Johansson, gusjohdo
- Mattias Ekdahl, gusekdmad
- Simon Engström, gussimen
## What?
A software that allows an end-user to remotely control a smart-car in a less traditional way, with the use of an EEG-headset and a mobile app. Moreover, the smart-car can protect itself to prevent for example hitting an obstacle.
## Why?
The main objective of this software is to utilize and demonstrate the use and application of EEG (Electroencephalography) readings to IoT items. By exploring this domain we hope to demonstrate ways using software engineering to develop for people with different levels of mobility.
## How?
The EEG-chip within the “Force Trainer II Bluetooth Headset” measures brain activity in Hz, and depending on users’ mental state e.g. stressed or relaxed, the headset will pick up frequencies within different Hz intervals. With the data retrieved from the headset, we will be able to control the forward throttle of the car, while being able to steer right and left via an app.  
The smart-car will also be able to prevent itself from colliding with obstacles by using ultrasonic sensors. Once the ultrasonic wave reaches the wall or obstacle it bounces back and the displacement between the car and the wall is calculated, and once it is too short the car will be locked in that direction.
## Resources
### Hardware
- 1x Smartcar
- 8x AA rechargeable batteries
- 2x micro-LIDAR sensors
- 2x ultrasonic sensors
- 1x MicroUSB cable
- [Bluetooth EEG headset](https://estore.nu/sv/star-wars/5028-star-wars-force-trainer-ii-8001444158953.html?SubmitCurrency=1&id_currency=1&gclid=EAIaIQobChMIoN7K4YrC6AIV2OeaCh3drQbnEAQYASABEgJZZfD_BwE)
- Smartphone
### Software
- Smart Car shield library
- Java
- C++
- Kotlin
- Travis CI
- GitHub