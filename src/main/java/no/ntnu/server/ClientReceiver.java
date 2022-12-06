package no.ntnu.server;

import no.ntnu.logic.Variables;

public class ClientReceiver {

    MqttSubscriber receiveBrokerData;

    public static void main(String[] args) {
        ClientReceiver clientReceiver = new ClientReceiver();
        clientReceiver.start();
        System.out.println(clientReceiver.getTopic());
    }

    public void start() {
        try {
            receiveDataFromTopic("ntnu/ankeret/#");
            receiveBrokerData.startClient();
            System.out.println(test());
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    private void receiveDataFromTopic(String topic) {
        receiveBrokerData = new MqttSubscriber(topic, Variables.BROKER, Variables.TEMPERATURE_CLIENT_ID, Variables.QOS);
    }


    public String getTopic() {
        return receiveBrokerData.getTopic();
    }

    public String test() {
        return receiveBrokerData.getClientID().toString();
    }
}


