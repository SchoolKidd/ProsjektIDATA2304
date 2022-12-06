package no.ntnu.sensors;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import no.ntnu.server.MqttPublisher;

/**
 * Provides sensor values within a given range
 */
public abstract class BoundedSensor implements Sensor{
    private double currentValue;
    private final double min;
    private final double max;

    private static final Random randomGenerator = new Random();

    /**
     * Create a sensor which will provide values in a given range
     *
     * @param initialValue the initial value for the sensor
     * @param minValue minimum allowed value
     * @param maxValue maximum allowed value
     */
    public BoundedSensor(double initialValue, double minValue, double maxValue) {
        currentValue = initialValue;
        min = minValue;
        max = maxValue;
    }

    /**
     * Reads the current value
     * @return the current value
     */
    @Override
    public double readValue() {
        changeCurrentValueRandomly();
        return currentValue;
    }

    /**
     * Changes the current value randomly within a given range
     */
    private void changeCurrentValueRandomly() {
        double delta = generateRandomDelta();
        currentValue += delta;
        if(isCurrentValueOutOfBoundaries()) {
            currentValue -= 2 * delta;
        }
        roundCurrentValueToOneDecimal();
    }

    /**
     * Generate a random delta, which is a small fraction of the allowed value range.
     *
     * @return The delta value. Note: it can be negative
     */
    private double generateRandomDelta() {
        double maxDelta = (max - min) / 50.0;
        return -maxDelta*randomGenerator.nextDouble()+maxDelta;
        //TODO - Doublecheck that this logic works
    }

    /**
     * Checks if our current value is inside the boundaries we set for humidity and temperature
     * @return the value
     */
    private boolean isCurrentValueOutOfBoundaries()
    {
        return currentValue > max || currentValue < min;
    }

    /**
     * Rounds the current value to have one decimal
     */
    private void roundCurrentValueToOneDecimal() {
        //code adapted adapted from https://stackoverflow.com/a/21596413/1703497
        //Note: this won't work, as it will round the number down: (int) (currentValue * 10) / 10.0
        currentValue = BigDecimal.valueOf(currentValue)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
