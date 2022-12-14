package no.ntnu.server;

import no.ntnu.logic.EncryptionDecryption;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Establish connection and receives the files from the MQTT broker
 */
public class MqttSubscriber implements  MqttCallback {

    private final String topic;

    private List<Double> data;

    private final String broker;

    private final String clientID;

    private final int qos;

    private MqttClient client;

    private IvParameterSpec ivP;


    /**
     * Creates the client, so we can receive data from the broker
     *
      * @param topic The topic we are subscribing from
     * @param broker The broker to receive the data from
     * @param clientID id of the client
     * @param qos quality of service
     */
    public MqttSubscriber(String topic, String broker, String clientID, int qos) {
        this.broker = broker;
        this.clientID = clientID;
        this.qos = qos;
        this.topic = topic;
        this.data = new ArrayList<>();
    }

    /**
     * Creates the client, so we can receive data from the broker with ivp for decryption of the encrypted data
     *
     * @param topic The topic we are subscribing from
     * @param broker The broker to receive the data from
     * @param clientID id of the client
     * @param qos quality of service
     * @param ivP initialize the subscribers vector
     */
    public MqttSubscriber(String topic, String broker, String clientID, int qos, IvParameterSpec ivP) {
        this.broker = broker;
        this.clientID = clientID;
        this.qos = qos;
        this.topic = topic;
        this.data = new ArrayList<>();
        this.ivP = ivP;
    }


    /**
     * Starts the client
     */
    public void startClient() {
        try {
            client = new MqttClient(broker, clientID, new MemoryPersistence());

            //different connect options
            MqttConnectOptions config = new MqttConnectOptions();
            config.setConnectionTimeout(120);
            config.setKeepAliveInterval(120);

            //callback
            client.setCallback(this);

            client.connect(config);
            client.subscribe(this.topic, this.qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Informs the user if connection is lost
     * @param throwable the reason behind the loss of connection.
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("lost connection" + throwable);
    }

    public void setIvP(IvParameterSpec ivP) {
        this.ivP = ivP;
    }

    /**
     *  Confirms the arrival of the message, also deals with encryption and decryption
     * @param topic name of the topic on the message was published to
     * @param messageMQTT the actual message.
     */
    @Override
    public void messageArrived(String topic, MqttMessage messageMQTT)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        String message = new String(messageMQTT.getPayload());

        SecretKey key = EncryptionDecryption.getKeyFromPassword("password1", "19283764");
        String algorithm = EncryptionDecryption.algorithm;

        String plainText = EncryptionDecryption.decrypt(algorithm, message, key, ivP);

        System.out.println("Received from broker - topic: " + topic);
        System.out.println("Message " + message);
        System.out.println("Message decrypted " + plainText);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        //TODO edit incoming data
        //this.data.add(Double.parseDouble(plainText));
    }

    /**
     * Notifies the user when the delivery is complete
     * @param DeliveryToken the delivery token associated with the message.
     */
    public void deliveryComplete(IMqttDeliveryToken DeliveryToken) {
        System.out.println("dataReceived: " + DeliveryToken);
    }

    /**
     * disconnects the client.
     * @throws MqttException
     */
    public void disconnectClient() throws MqttException {
        this.client.disconnect();
    }

    /**
     * getter for client ID
     * @return clientID
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * getter for data
     * @return data
     */
    public List<Double> getData() {
        return this.data;
    }

    /**
     * getter for topic
     * @return topic
     */
    public String getTopic() {
        return topic;
    }




}
