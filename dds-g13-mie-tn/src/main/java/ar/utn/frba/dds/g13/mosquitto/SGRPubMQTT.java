package ar.edu.dds;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

public class SGRPubMQTT {
	public static String recievedId = null;
    public static String recievedAction = null;
    public static String recievedMode = null;
    
	public static void sendAction(String id, String action, String mode) {
		System.out.println("CAMBIANDO");
		recievedId = id;
		recievedAction = action;
		recievedMode = mode;
	}

    public static void main(String[] args) throws MqttException, InterruptedException {

    	System.out.println("== START SGR PUBLISHER ==");

        String mqttId = MqttClient.generateClientId();
        MqttClient client = new MqttClient("tcp://localhost:1883", mqttId);
        client.connect();

        while (true) {
        	System.out.println("HOLA");
        	while(recievedId == null) {
        		System.out.println("recievedId" + recievedId);
        		TimeUnit.SECONDS.sleep(2);
        	}
        	System.out.println("CHAU");
        	String id = recievedId;
        	String action = recievedAction;
        	String mode = recievedMode;
        	recievedId = null;
        	recievedAction = null;
        	recievedMode = null;
        	
            String payload;
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("action", action);
            json.put("mode", mode);

            payload = json.toString();
            byte[] jsonBytes = payload.getBytes();
            String base64Encoded = DatatypeConverter.printBase64Binary(jsonBytes);
     
            MqttMessage message = new MqttMessage();
            message.setPayload(base64Encoded.getBytes());
            client.publish("actions", message);
            System.out.println("new action sended: " + action + " to ActuatorId: " + id);
        }


    }
}