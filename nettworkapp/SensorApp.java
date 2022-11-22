package com.example.nettworkapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * import com.example.nettworkapp.sensors.Sensor;
 * import com.example.nettworkapp.sensors.TemperatureSensor;
 *
 */



public class SensorApp extends Application {
    private static final long SLEEP_DURATION_MS = 2000;

    TemperatureSensor temperatureSensor=new TemperatureSensor();
    double lastTemperatureReading;

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
             * @throws IllegalStateException If some sensors are not found
             */
            private void initializeSensors() throws IllegalStateException {
            SensorProvider sensorProvider = SensorProvider.getInstance();
            TemperatureSensor temperatureSensor = (TemperatureSensor) sensorProvider.getTemperatureSensor();
            if (temperatureSensor == null) {
                throw new IllegalStateException("Temperature sensor not found");
            }
        }

            private void readAllSensors() {
            System.out.println("Reading sensor data...");
            lastTemperatureReading = temperatureSensor.readValue();

        }

            private void sendDataToServer() {
            // TODO - implement sensor data sending to the server
            System.out.println("Sending data to server:");
            System.out.println("  temp: " + lastTemperatureReading + "C");
            System.out.println("");
        }

            private void powerNap() {
            try {
                Thread.sleep(SLEEP_DURATION_MS);
            } catch (InterruptedException e) {
                System.out.println("Ooops, someone woke us up in the middle of a nap");
            }
        }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensorApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}













