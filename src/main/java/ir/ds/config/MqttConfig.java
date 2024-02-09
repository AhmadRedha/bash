package ir.ds.config;


import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {


    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.broker.port}")
    private int brokerPort;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        String[] uri = {"tcp://" + brokerUrl + ":" + brokerPort};
        options.setServerURIs(uri);
        options.setCleanSession(true);
        if (username != null && password != null) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1); // Consider using MQTT 5.0 for advanced features
        return options;
    }

    public static MqttClient client;
    @Bean
    public MqttClient mqttClient(MqttConnectOptions options) throws MqttException {
        client = new MqttClient(options.getServerURIs()[0], clientId);
        client.connect(options);
        client.setCallback(new MyMqttCallback());
        return client;
    }

    class MyMqttCallback implements MqttCallbackExtended {

        @Override
        public void connectionLost(Throwable cause) {
            System.err.println("Connection lost: " + cause.getMessage());

            // Implement retry logic with backoff:
            int retryCount = 0;
            int baseWaitTime = 2; // Start with a base wait time (seconds)
            int maxRetries = 5; // Define maximum retry attempts

            while (retryCount < maxRetries) {
                System.err.println("Attempting reconnect #" + (retryCount + 1));

                try {
                    // Reconnect the client here
                    client.reconnect();
                    System.out.println("Reconnected successfully!");
                    break; // Exit loop on successful reconnect
                } catch (MqttException e) {
                    System.err.println("Reconnect failed: " + e.getMessage());
                    retryCount++;
                    int waitTime = (int) Math.pow(baseWaitTime, retryCount); // Exponential backoff
                    System.err.println("Waiting " + waitTime + " seconds before retrying...");
                    try {
                        Thread.sleep(waitTime * 1000); // Wait before retrying
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            if (retryCount >= maxRetries) {
                System.err.println("Reached maximum retries, giving up...");
                // Add logic for handling unrecoverable connection failure
            }

        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }

        @Override
        public void connectComplete(boolean b, String s) {

        }

    }

}


