package no.ntnu.javafx.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import no.ntnu.SensorProvider;
import no.ntnu.logic.Variables;
import no.ntnu.server.ClientSender;
import no.ntnu.server.MqttPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainSceneController {

    MqttPublisher temperaturePublisher = new MqttPublisher(Variables.TEMPERATURE_TOPIC, Variables.BROKER, Variables.TEMPERATURE_SENSOR_ID, Variables.QOS);

    MqttPublisher humidityPublisher = new MqttPublisher(Variables.HUMIDITY_TOPIC, Variables.BROKER, Variables.HUMIDITY_SENSOR_ID, Variables.QOS);

    ClientSender clientSender = new ClientSender(Variables.TEMPERATURE_TOPIC, Variables.BROKER, Variables.TEMPERATURE_CLIENT_ID, Variables.QOS);

    ClientSender clientSender1 = new ClientSender(Variables.HUMIDITY_TOPIC, Variables.BROKER, Variables.HUMIDITY_CLIENT_ID, Variables.QOS);

    SensorProvider sensorProvider = new SensorProvider();

    @FXML
    private TextArea console;

    @FXML
    private Button btn_humidity;

    @FXML
    private Button btn_temperature;

    @FXML
    private Button btn_startSensor;

    @FXML
    private Button btn_stopSensor;

    @FXML
    private BarChart barChart;

    @FXML
    private Text temperature;

    private PrintStream printStream;

    private ArrayList<Double> humidity;

    private ArrayList<Double> temperature_AR;

    public MainSceneController() {
        humidity = new ArrayList<>();
        temperature_AR = new ArrayList<>();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>
                observableArrayList(Arrays.asList("Humidity", "TEMPERATURE")));
        xAxis.setLabel("HUMIDITY");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("VALUE");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Show humidity bar chart");
    }

    @FXML
    private void buttonStartSensorPressed(ActionEvent actionEvent)
    {
        Platform.runLater(()->{
            temperaturePublisher.startConnection();
            humidityPublisher.startConnection();

            clientSender.startClient();
            clientSender1.startClient();
        });
    }

    @FXML
    private void buttonStopSensorPressed(ActionEvent actionEvent)
    {
        Platform.runLater(()->{
            humidityPublisher.terminateConnection();
            temperaturePublisher.terminateConnection();

            try{
                clientSender.disconnectClient();
                clientSender1.disconnectClient();
            } catch (MqttException e) {

            }
        });
    }


    @FXML
    private void buttonHumidityPressed(ActionEvent actionEvent) {
        Platform.runLater(()->{
            String st = String.valueOf(sensorProvider.getTemperatureSensor().readValue());
            humidityPublisher.publishMessageToBroker(st);

            System.out.println("Humidity:  " + st + "%");
            try {
                humidity.add(Double.parseDouble(st));
                humidityChart();
            }catch (Exception e) {

            }
        });
    }

    @FXML
    private void buttonTemperaturePressed(ActionEvent actionEvent)
    {
        Platform.runLater(()->{

            String st= String.valueOf(sensorProvider.getTemperatureSensor().readValue());
            temperaturePublisher.publishMessageToBroker(st);
            try{
                temperature_AR.add(Double.parseDouble(st));}
            catch (Exception e)
            {}
            temperature.setText(st+"°C");
        });


    }


    private void humidityChart()
    {
        barChart.getData().clear();

        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        for(int i=0;i< humidity.size();i++)
        {
            series1.getData().add(new XYChart.Data<>((i+1)+"", humidity.get(i)));

        }

        XYChart.Series<String, Double> series2 = new XYChart.Series<>();
        for(int i=0;i< temperature_AR.size();i++)
        {
            series2.getData().add(new XYChart.Data<>((i+1)+"", temperature_AR.get(i)));

        }



        //Setting the data to bar chart
        barChart.getData().addAll(series1,series2);

    }

    public void buttonPressed() {
        System.setOut(printStream);
        System.setErr(printStream);
        System.out.println("Sensor readings: ");
        System.out.println("");

    }

    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        @Override
        public void write(int b) throws IOException {
        }
    }


}
