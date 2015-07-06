import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Fredrik on 2015-07-06.
 */
public class DistanceCallBack implements MqttCallback{
    private MqttClient client;

    public DistanceCallBack(MqttClient client){
        this.client = client;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("RangePredictonService lost connection!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println(mqttMessage.toString());
        String message = mqttMessage.toString();
        String[] messageParts =message.split(";");
        String carID = messageParts[0];
        float fuelLevel = Float.parseFloat(messageParts[1]);
        if(fuelLevel>10){
            fuelLevel=fuelLevel-10.0f;
        }
        else{
            fuelLevel=0.0f;
        }
        String fuelLevelAsString = ""+fuelLevel;
        System.out.println("'"+carID+"/distancePrediction"+"'");
        System.out.println(fuelLevelAsString);
        String topicToPublish = carID + "/distancePrediction";
        new PublishThread(client,topicToPublish,fuelLevelAsString).start();
        //client.publish(carID + "/distancePrediction", fuelLevelAsString.getBytes(),0, false);




      //  "d8:50:e6:71:c6:db/distancePrediction"

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
