module BedroomVitalsApp {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.eclipse.paho.client.mqttv3;
    requires java.logging;

    opens no.ntnu.javafx.controllers to javafx.fxml;
    opens no.ntnu.javafx to javafx.fxml;
    opens no.ntnu.server to javafx.fxml;
    opens no.ntnu.logic to javafx.fxml;
    opens no.ntnu.sensors to javafx.fxml;
    opens no.ntnu to javafx.fxml;

    exports no.ntnu.javafx.controllers;
    exports no.ntnu.javafx;
    exports no.ntnu.logic;
    exports no.ntnu.sensors;
    exports no.ntnu.server;
    exports no.ntnu;
}