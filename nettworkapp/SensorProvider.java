package com.example.nettworkapp;


/**
 * Manages a collection of all available sensors
 * package no.ntnu;
 * import com.example.nettworkapp.sensors.Sensor;
 * import com.example.nettworkapp.sensors.TemperatureSensor;
 */



public class SensorProvider {
    private final TemperatureSensor temperatureSensor = new TemperatureSensor() {
        @Override
        public double readValue() {
            return 0;
        }
    };

    public SensorProvider() {
    }

    private static final SensorProvider instance = new SensorProvider();

    /**
     * @return Get a singleton instance of the class
     */
    public static SensorProvider getInstance() {
        return instance;
    }

    /**
     * Get access to the temperature sensor on the platform
     *
     * @return Temperature sensor instance
     */
    public Sensor getTemperatureSensor() {
        return this.temperatureSensor;
    }



}