package no.ntnu.server;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import no.ntnu.server.MqttSubscriber;

/**
 * Responsible for connection to the mqtt broker.
 */
public class ClientSender extends MqttSubscriber {

    double lastMessage;

    /**
     * Creates a connection to the broker, so we can send and receive data.
     * @param topic
     * @param broker
     * @param clientID
     * @param qos
     */
    public ClientSender(String topic, String broker, String clientID, int qos) {
        super(topic, broker, clientID, qos);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        double message = Double.parseDouble(new String(mqttMessage.getPayload()));

        System.out.println("Topic = " + topic);
        System.out.println("Message = " + message);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
        this.lastMessage = message;
    }

    /**
     * getter for last message value
     * @return lastMessage
     */
    public double getLastMessage() {
        return lastMessage;
    }


}
