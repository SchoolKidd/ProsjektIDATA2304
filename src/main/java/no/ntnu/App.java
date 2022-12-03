package no.ntnu;

import no.ntnu.sensors.Sensor;

/**
 * Represents the whole application, including the logic.
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

private void readAllSensors() {
    System.out.println("Reading sensor data...");
    lastTemperatureReading = temperatureSensor.readValue();
    lastHumidityReading = humiditySensor.readValue();
}

private void sendDataToServer() {
    //TODO implement sensor data sending to the server
}

private void ReceiveDataFromServer() {
    //TODO implement sensor data
}

private void powerNap() {
    try{
        Thread.sleep(SLEEP_DURATION_MS);
    } catch (InterruptedException e) {
        System.out.println("No powernap done");
    }
}

}