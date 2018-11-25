package ar.utn.frba.dds.g13.mosquitto;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

public class SGEPubMQTT {
    
	public static void sendAction(String recievedId, String recievedAction, String recievedMode)throws MqttException, InterruptedException {
		
		String id = recievedId;
    	String action = recievedAction;
    	String mode = recievedMode;
    	recievedId = null;
    	recievedAction = null;
    	recievedMode = null;

        String mqttId = MqttClient.generateClientId();
        MqttClient client = new MqttClient("tcp://52.14.65.40:1883", mqttId);
        client.connect();
        	
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