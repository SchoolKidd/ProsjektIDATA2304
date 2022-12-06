package no.ntnu;

import no.ntnu.logic.Variables;
import no.ntnu.sensors.Sensor;
import no.ntnu.server.ClientSender;
import no.ntnu.server.MqttPublisher;

/**
 * Represents the whole application, including the logic, sensors etc.
 */
public class App 
{
    private static final long SLEEP_DURATION_MS = 2000;
    double lastTemperatureReading;
    double lastHumidityReading;

    Sensor temperatureSensor;
    Sensor humiditySensor;



/**
 * Run the application, does not return, except if something goes wrong.
 *
 * @throws IllegalStateException If something went wrong during the process
 */
public void run() throws IllegalStateException {
    initializeSensors();
    while (true) {
        readAllSensors();
        sendDataToServer();
        powerNap();
    }
}


/**
 * Initializes all the sensors
 *
 * @throws IllegalStateException If some
 */
private void initializeSensors() throws IllegalStateException {
    SensorProvider sensorProvider = SensorProvider.getInstance();
    temperatureSensor = sensorProvider.getTemperatureSensor();
    if(temperatureSensor ==  null) {
        throw new IllegalStateException("Temperature sensor not found");
    }
    humiditySensor = sensorProvider.getHumiditySensor();
    if(humiditySensor == null) {
        throw new IllegalStateException("Humidity sensor not found");
    }
}

    /**
     * Reads the value of the temperature and humidity sensors
     */
    private void readAllSensors() {
    System.out.println("Reading sensor data...");
    lastTemperatureReading = temperatureSensor.readValue();
    lastHumidityReading = humiditySensor.readValue();
}

    /**
     * Sends the actual data to the server
     */
    private void sendDataToServer() {
    MqttPublisher temperatureReading = new MqttPublisher(Variables.TEMPERATURE_TOPIC, Variables.BROKER, Variables.TEMPERATURE_SENSOR_ID, Variables.QOS);
    temperatureReading.startConnection();
    temperatureReading.publishMessageToBroker(lastTemperatureReading + "");

    MqttPublisher humidityReading = new MqttPublisher(Variables.HUMIDITY_TOPIC, Variables.BROKER, Variables.HUMIDITY_SENSOR_ID, Variables.QOS);
    humidityReading.startConnection();
    humidityReading.publishMessageToBroker(lastHumidityReading + "");

    ClientSender clientSender = new ClientSender(Variables.TEMPERATURE_TOPIC, Variables.BROKER, Variables.TEMPERATURE_CLIENT_ID, Variables.QOS);
    clientSender.startClient();

    ClientSender clientSender1 = new ClientSender(Variables.HUMIDITY_TOPIC, Variables.BROKER, Variables.HUMIDITY_CLIENT_ID, Variables.QOS);
    clientSender1.startClient();
}

    /**
     * Sets the operation to sleep for a set amount of MS.
     */
    private void powerNap() {
    try{
        Thread.sleep(SLEEP_DURATION_MS);
    } catch (InterruptedException e) {
        System.out.println("No powernap done");
    }
}

}