package ar.edu.dds;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

public class SGRSubMQTT {

    public static void main(String[] args) throws MqttException, InterruptedException {
        
        System.out.println("== START SGR SUBSCRIBER ==");

        String mqttId = MqttClient.generateClientId();
        MqttClient client = new MqttClient("tcp://localhost:1883", mqttId);
        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection to MQTT broker lost!");
            }

            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            	byte[] base64Decoded = DatatypeConverter.parseBase64Binary(new String(mqttMessage.getPayload()));
            	
            	System.out.println("Received JSON:" + new String(base64Decoded));	
            	
            	JSONObject Pubmessage = new JSONObject(new String(base64Decoded));
            	String receivedId = Pubmessage.getString("id");
            	String receivedName = Pubmessage.getString("name");
            	String receivedMessure = Pubmessage.getString("messure");
            	int receivedValue = Pubmessage.getInt("value");
            	
            	//sendNewMessure(receivedId, receivedValue);
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.connect();

        client.subscribe("measurement");
        System.out.println("ok!");


    }
}