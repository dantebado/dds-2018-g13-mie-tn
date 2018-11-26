package ar.utn.frba.dds.g13.mosquitto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import ar.utn.frba.dds.g13.device.Device;

public class SGESubMQTT {
	public static List<RecivedMeasure> listaMediciones = new ArrayList<RecivedMeasure>();
	public static boolean listaOnUse = false;

	
	public static List<RecivedMeasure> getListaMediciones() {
		return listaMediciones;
	}
	
	public static void setListaMediciones(List<RecivedMeasure> listaMediciones) {
		SGESubMQTT.listaMediciones = listaMediciones;
		listaOnUse = false;
	}
	
	public static void main(String[] args) throws MqttException, InterruptedException {
        
        System.out.println("== START SGE SUBSCRIBER ==");

        String mqttId = MqttClient.generateClientId();
        MqttClient client = new MqttClient("tcp://52.14.65.40:1883", mqttId);
        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection to MQTT broker lost!");
            }

            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            	byte[] base64Decoded = DatatypeConverter.parseBase64Binary(new String(mqttMessage.getPayload()));
            	
            	System.out.println("Received JSON:" + new String(base64Decoded));	
            	
            	JSONObject Pubmessage = new JSONObject(new String(base64Decoded));
            	String receivedId = Pubmessage.get("id").toString();
            	String receivedName = Pubmessage.get("name").toString();
            	String receivedMessure = Pubmessage.get("measure").toString();
            	int receivedValue = Integer.parseInt(Pubmessage.get("value").toString());
            	RecivedMeasure Messure = new RecivedMeasure(receivedId,receivedName,receivedMessure,receivedValue);
            	
            	listaMediciones.add(Messure);
            	//REFACTOR sendNewMessure(receivedId, receivedValue);
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.connect();

        client.subscribe("measurement");
        System.out.println("Mqtt successfully subscribed");


    }
}