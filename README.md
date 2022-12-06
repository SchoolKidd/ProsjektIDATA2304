# BedroomConditionApp

Getting enough sleep is an everyday issue,
especially for students who must be fresh
every day to learn as efficiently as possible.
However, students don't always have time to get the
required sleep, that is what we set out to solve with this program.
We created a program that lets the individual track
various conditions in the bedroom to more easily
determine what conditions that could be improved upon when sleeping.

## abstract
Temperatures in the bedroom are always fluxuating constantly,
and the humidity can be much the same. When going to bed,
it is crucial to get a good nights sleep to be able
to perform your best the very next day. For this to be
just a little bit easier, our users can use this
application to get informed on what their sleeping conditions are.
Our goal with this application is to make it so
that you can trigger the air moisturizer or the window opening,
and then makes those be able to react to the readings 
our app supplies. With this integration peoples sleeping quality
would be improved for the better.

## Table of Contents
* [introduction](#Introduction)

* [Theory and Technology](#Theory-and-Technology)

* [Methodology](#Methodology)

* [Results](#Results)

* [Discussion](#Discussion)

* [Conclusion and Future Work](#Conclusion-and-Future-Work)

* [References](#References)




## Introduction
* We propose an Internet-of-Things (IOT) system that displays and
provides sensor data for your bedroom. To talk you through how the project work and
what protocols are used you can look at [theory and technology](#Theory-and-Technology).
We will then go through our work process and ethics in [Methodology](#Methodology).
Lastly we will go through the [results](#Results) and have a [discussion](#Discussion) and a [conclusion](#conclusion-and-future-work)
to the current project.

## Theory and Technology
### Data Gathering
* We used the temperature and humidity sensors that was already installed in one of our rooms,
we also manipulated the data by opening the windows to get a varied dataset
  (keep in mind that in the current version of the project, all data is emulated)

* For data simulation we used simple solution that takes an assigned random variable
between 18-26 for temperatures. We then use different methods to fluxuate these numbers within
a given range.

***
### MQTT broker
* The MQTT broker made public to us is simply a tool that retrieves and sends data.
This can be done by publishing and subscribing to different topics. This application uses a TCP connection
to keep the broker and the sensor-nodes connected. In our application we used the following topics:
  * ntnu/ankeret/c220/sensors/projectgroup4/bedroomsensor/#
  * The # will be replaced with the appropriate sensor readings, that is "temperature" for temperature and "humidity" for humidity
***
### TCP/IP
* To get a connection to the broker we used the TCP (Transmission Control Protocol) and IP(Internet Protocol).
These protocols were used because TCP is the most reliant protocol to avoid packet loss and to make sure 
the data will arrive in a correct manner. Since we only analyse the reading every other second or so, we have no
need to use the UDP protocol for faster data transfer. Reliability is key here, hence why we went for the TCP connection.
***
### JavaFX
* For the visualization of the sensor-nodes, we chose to go with javaFX.
We chose javaFX because we were familiar with the program, and because it is easier to visualize 
early on how you want the program to look. This is done with Scenebuilder.
Scenebuilder is an amazing tool being used to create the FXML files which we then could implement into our program.
***
### Encryption and Decryption
* Since we don't want others to easily access the data, we included basic encryption
of the data being sent to the broker. In our case we used AES symmetric key cryptography here.
  * AES symmetric key crypthography works by encrypting the data using the Advanced Encryption Standard(AES)
  * The data will then be encrypted using a key into 128-bit block of data, the same key will then
  be used later on to decrypt the data.
  * There are other ways to encrypt data, you can for example use asymmetric key encryption, or you can
  encrypt the data into bigger blocks (256 bit) for example. For the sake of simplicity we went with the easier solution this time around.

***
### Domain knowledge
#### Temperature
* Optimal sleeping temperatures are between 17-19 degrees celcius. It is worth noting that this may fluxuate abit in some individuals
  * To regulate these temperatures to be within the optimal range, we would like
  to make it so that the window opens or that the aircondition triggers when sub optimal readings are observed.
  * Several problems can occur if your bedroom is to warm. Problems like headaches, prefuse sweating, mourning drought can all be related to an overly warm bedroom environment.
  * When the bedroom is too cold, other problems arise. Your immune system gets weakened and bacteria can get you sick at higher rates than with optimal sleeping temperatures.
#### Humidity
* Optimal sleeping humidity lies around 40-60%. This too can fluxuate for some individuals.
  * Although optimal humidity while sleeping is not as important as temperatures, having the ideal humidity in the room can help you avoid bad quality sleep.

***
### Github
* We used GitHub to share the code between the group members. We could then
see the progress through commits.
* To ensure anonymity when this project is handed in, we made new GitHub accounts with no relations to our name.
This being said, it ended up causing a bit of troubles with pushing the project to GitHub.
This was mainly since some of the code were written with the old github users.
We solved this temporary with the use of "upload files" to github directly. After a while the issue resolved itself and we could once again push the project to GitHub.
***
### Maven
* Maven was used as a tool for us to easily manage the project and project structure.
Maven also made it easier to have version control
***
## Extra Features
### Encryption
* As an additional feature we added encryption to our project using the symmetric key solution.
This means that the broker and the client uses the same key to both decrypt and encrypt the key.
***
### Sensor data
* Since we did not have access to an arduino or a raspberry pi we emulated the temperature and humidity data needed.
We tried to make sure the data was as close to realistic values as possible here. In the feature we would like 
to have support for actual sensors instead of emulated ones.
***
### Interface
* We decided to use javaFX as an interface. Besides this causing alot of bugfixing,
it ended up serving as a spectator panel for the value readings from the sensors.
***
## Methodology
* When working on this project we divided it into 4 parts.
    * The first part consisted of reading up on the project and getting familiar with the tools / syntax being used.
    * In the 2nd part we started off making the emulated sensor data.
    * In the 3rd part we focused mainly on the sending and receiving of data to and from the broker.
  This accompanied with trying to implement encryption was the most work heavy part.
    * Lastly we finished up with getting the sensor-nodes visualized using JavaFX.
***
## Results
* At the end of the day we are left with a JavaFx application that mimicks realtime bedroom conditions
* The program will now send and receive emulated sensor data to and from the broker.
***
### Class structure
* Sensor classes are essential to the program. The bounded sensor provides general rules all sensor classes inheriting from bounded sensor needs to follow.
  * The sensor classes then gets a value that can be send through the sensor provider
* The MqttSubscriber and publisher are responsible for sending and receiving the data to and from the broker
* The logic package contains of the encryption class which encrypts and decrypts data and the variables class, which stores different String values used when sending data, such as Strings for the topic and broker IP/Port etc.
* We also have the controller classes which are responsible for the visual view of the program
* Lastly we have the app/main classes that are responsible for running the program.


***

### The application
![](src/main/resources/application.png)
The application works by starting the sensors.
* Once the sensors are started up you can press the humidity button to display the current humidity in the room with the graph.
* You can also press the temperature button and visually display the temperature in the room currently.
* We made it so that the input does not update itself unless the button is pressed.
* When exiting the app you just press the stop sensors button and its close the app.
***
## Discussion
#### What are we happy with?
* The application is clear and hard to missuse.
* The application allows for a visual view into the temperatures

#### What are we unhappy with?
* The application does not allow the change of any values
* The application has a bit of start up delay

***
## Conclusion-and-Future-Work
Our application provides sensor data from a room in real time and shows you the values.
The user can turn the application on and off and can be sure that no one else can read the data. However,the data is not stored anywhere locally and thus you need some
knowledge about the program to take use of it. Future additions to the program could be as follows:
* Store the data locally so that the data does not get destroyed when closing the applicaton. This could be done for example in a database.
Added benefits to doing this would be that you could also view data from other time periods.
* Add security to the application. Currently our application has no security whatsoever. There is
no login required and no password required. Also the current encryption methods would need to be upgraded if the program were to contain actual sensitive data from real people.
* Add more sensors to further improve the quality of sleep. Noise sensor and light sensors would be good additions to the application.
* Add real sensors. The problem with our emulated sensors is that they don't adjust to the time of day. The tempuratures would be just as random if it is night or day.
* It would also be important that the application could react to the data displayed.
This means that if the room is to warm, we could form a trigger that would make the aircondition turn on or the window open etc.
* lastly the application could have needed a big workup on the visual front. We could have made different scenes for the different sensors. Readings could've been ongoing and not static, so here is a lot of future improvement to the application.
## References
* Yu, Zhiwei(2022, August 31), EMQX. https://www.emqx.com/en/blog/how-to-use-mqtt-in-java
* Baeldung (2021, November 14), bealdung. https://www.baeldung.com/java-aes-encryption-decryption
* Danielle Pacheco (2022 June 17), Sleep Foundation https://www.sleepfoundation.org/bedroom-environment
* Oracle(). Oracle https://openjfx.io/openjfx-docs/#maven
* Github(2022, November 6), strazdinsg https://github.com/ntnu-datakomm/project-resources
