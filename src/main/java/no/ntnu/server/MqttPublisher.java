package no.ntnu.server;

import no.ntnu.logic.EncryptionDecryption;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;



/**
 * The mqtt publisher class will be responsible for publishing the data to the broker, making sure the topics are correct.
 */
public class MqttPublisher {
    private final String topic;
    private final String broker;
    private final String sensorID;
    private final int qos;
    private MqttClient client;

    //used later for encryption of the data sent
    private IvParameterSpec ivP;

    /**
     * Creates the client that will send the data to the public Mqtt Broker.
     */
    public MqttPublisher(String topic, String broker, String sensorID, int qos) {
        this.topic = topic;
        this.broker = broker;
        this.sensorID = sensorID;
        this.qos = qos;

    }

    /**
     * Creates the client that will send data to the public Mqtt Broker. Additional features: will send it encrypted
     * @param topic             Topic on the broker
     * @param broker            The broker -> connects
     * @param sensorID          The client identification
     * @param qos               Quality of service
     * @param ivP               Initialaztion vector of the publisher
     */
    public MqttPublisher(String topic, String broker, String sensorID, int qos, IvParameterSpec ivP) {
        this.topic = topic;
        this.broker = broker;
        this.sensorID = sensorID;
        this.qos = qos;
        this.ivP = ivP;
    }

    /**
     *  Boot up for the connection of the client and the server. Sends data to the broker.
     */
    public void startConnection() {
        try {
            this.client = new MqttClient(broker, sensorID, new MemoryPersistence());

            //different connect options
            MqttConnectOptions config = new MqttConnectOptions();
            config.setConnectionTimeout(120);
            config.setKeepAliveInterval(120);

            // connect to broker
            client.connect(config);

        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void setIvP(IvParameterSpec ivP) {
        this.ivP = ivP;
    }

    /**
     * Sends a message to the MQTT broker.
     */
    public void publishMessageToBroker(String message) {
        try {
            //encryption
            SecretKey key = EncryptionDecryption.getKeyFromPassword("projectpassword", "thesalt");
            String algorithm = EncryptionDecryption.algorithm;

            String cipherText = EncryptionDecryption.encrypt(algorithm, message, key, ivP);

            //create message and setup quality of service
            MqttMessage message1 = new MqttMessage(cipherText.getBytes());
            message1.setQos(this.qos);

            //publish message
            client.publish(topic, message1);
            System.out.println("Message was sent to topic: " + topic);
            System.out.println("Message content: " + new String(message1.getPayload()));
            System.out.println("Original message: " + message);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }catch (MqttException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Terminates connection with the broker
     */
    public void terminateConnection() {
        try {

            //DISCONNECTS
            client.disconnect();

            // close client
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}

