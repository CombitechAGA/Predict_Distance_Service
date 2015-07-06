import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by Fredrik on 2015-07-06.
 */

//läs config
public class Main {
    public static void main(String[] args) {
        MqttClient client = null;
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            client = new MqttClient("tcp://mqtt.phelicks.net:1883", "distanceService", persistence);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("cab");
            options.setPassword("sjuttongubbar".toCharArray());
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        DistanceCallBack distanceCallBack = new DistanceCallBack(client);
        client.setCallback(distanceCallBack);
        System.out.println("Thomas syso");
        System.out.println("Folke sytso");

        try {
            client.subscribe("telemetry/fuel");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
