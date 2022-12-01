package no.ntnu.sensors;


/**
 * Represents (or imitates) one physical sensor on the platform
 */
    public interface Sensor {
        /**
         * Read the current value of the sensor
         * @return the current sensor value
         */
        double readValue();
    }
