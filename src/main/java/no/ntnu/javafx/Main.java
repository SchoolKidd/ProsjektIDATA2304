package no.ntnu.javafx;

import no.ntnu.App;

/**
 * Main entrypoint for the application
 */
public class Main {
    /**
     * Start the application
     * @param args Not used
     */
    public static void main(String[] args) {
        System.out.println("Starting the application");
        App app = new App();
        try{
            app.run();

        } catch (Exception e) {
            System.out.println("oops: " + e.getMessage());
        }
        System.out.println("Application finished");
    }

}
