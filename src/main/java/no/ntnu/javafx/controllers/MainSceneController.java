package no.ntnu.javafx.controllers;

import javafx.fxml.FXML;
import no.ntnu.SensorProvider;
import no.ntnu.logic.Variables;
import no.ntnu.server.ClientSender;
import no.ntnu.server.MqttPublisher;
import javafx.scene.control.TextArea;
import java.io.PrintStream;

public class MainSceneController {

    MqttPublisher temperaturePublisher = new MqttPublisher(Variables.TEMPERATURE_TOPIC, Variables.BROKER, Variables.TEMPERATURE_SENSOR_ID, Variables.QOS);

    MqttPublisher humidityPublisher = new MqttPublisher(Variables.HUMIDITY_TOPIC, Variables.BROKER, Variables.HUMIDITY_SENSOR_ID, Variables.QOS);

    ClientSender clientSender = new ClientSender(Variables.TEMPERATURE_TOPIC, Variables.BROKER, Variables.TEMPERATURE_CLIENT_ID, Variables.QOS);

    ClientSender clientSender1 = new ClientSender(Variables.HUMIDITY_TOPIC, Variables.BROKER, Variables.HUMIDITY_CLIENT_ID, Variables.QOS);

    SensorProvider sensorProvider = new SensorProvider();

    @FXML
    private TextArea console;

    private PrintStream printStream;

}
