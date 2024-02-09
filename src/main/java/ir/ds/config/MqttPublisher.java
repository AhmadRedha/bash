package ir.ds.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisher {

    @Autowired
    private MqttClient mqttClient;
    @Value("${mqtt.qos}")
    private int qos;

    public void publishMessage(String topic, String message) throws MqttException {
        mqttClient.publish(topic, message.getBytes(), qos, false);
    }

    public void reconnect() {
            int retryCount = 0;
            int baseWaitTime = 2; // Start with a base wait time (seconds)
            int maxRetries = 5;
            while (retryCount < maxRetries) {
                retryCount++;
                int waitTime = (int) Math.pow(baseWaitTime, retryCount); // Exponential backoff
                System.err.println("Waiting " + waitTime + " seconds before retrying...");
                try {
                    mqttClient.reconnect();
                    Thread.sleep(waitTime * 1000);
                    break;
                } catch (InterruptedException ex) {
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
