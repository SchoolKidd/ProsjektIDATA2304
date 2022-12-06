package no.ntnu.logic;

public class Variables {

    /**
     * Set up fields of variables used to define the topics to receive data from and send data to.
     */
    public static final String BROKER = "tcp://129.241.152.12:1883";
    public static final String CLIENT = "project_group4_client";
    public static final String SENSOR = "project_group4_sensor";
    public static final String TEMPERATURE_CLIENT_ID = "project_group4_temperature_client";
    public static final String TEMPERATURE_SENSOR_ID = "project_group4_temperature_sensor";
    public static final String HUMIDITY_CLIENT_ID = "project_group4_humidity_client";
    public static final String HUMIDITY_SENSOR_ID = "project_group4_humidity_sensor";
    public static final int QOS = 0;



    public static final String TEMPERATURE_TOPIC = "ntnu/ankeret/c220/sensors/projectgroup4/bedroomsensor/temperature";
    public static final String HUMIDITY_TOPIC = "ntnu/ankeret/c220/sensors/projectgroup4/bedroomsensor/humidity";
    public static final String GROUP_TOPIC = "ntnu/ankeret/c220/sensors/projectgroup4/#";


}
