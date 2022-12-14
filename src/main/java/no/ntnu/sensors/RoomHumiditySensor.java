package no.ntnu.sensors;

/**
 * Imitates a humidity sensor placed in a room. The values will be in percent
 */
public class RoomHumiditySensor extends BoundedSensor {
    private static final double NORMAL_ROOM_HUMIDITY = 50;
    private  static final double MIN_ROOM_HUMIDITY = 40;
    private static final double MAX_ROOM_HUMIDITY = 60;

/**
 * Create a sensor which will imitate humidity readings within a room
 */
public RoomHumiditySensor() {
    super(NORMAL_ROOM_HUMIDITY, MIN_ROOM_HUMIDITY, MAX_ROOM_HUMIDITY);
    }
}
