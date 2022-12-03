package no.ntnu.sensors;

public class RoomTemperatureSensor extends BoundedSensor{
    private static final double NORMAL_ROOM_TEMPERATURE = 23.3;
    private static final double MIN_ROOM_TEMPERATURE = 18;
    private static final double MAX_ROOM_TEMPERATURE = 26;


    /**
     * Create a sensor which will imitate temperature readings within a room
     */
    public RoomTemperatureSensor() {
        super(NORMAL_ROOM_TEMPERATURE, MIN_ROOM_TEMPERATURE, MAX_ROOM_TEMPERATURE);
    }
}
