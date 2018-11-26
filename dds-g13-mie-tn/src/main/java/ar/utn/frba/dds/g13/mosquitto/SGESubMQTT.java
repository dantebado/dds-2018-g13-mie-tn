package ar.utn.frba.dds.g13.mosquitto;

import java.util.List;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import ar.utn.frba.dds.g13.device.Device;

public class SGESubMQTT {
	public static List<RecivedMeasure> listaMediciones;
	public static boolean listaOnUse = false;

	
	public static List<RecivedMeasure> getListaMediciones() {
		if (!listaOnUse) {
    		listaOnUse = true;
    		return listaMediciones;
    	}
    	return null;
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
            	String receivedId = Pubmessage.getString("id");
            	String receivedName = Pubmessage.getString("name");
            	String receivedMessure = Pubmessage.getString("messure");
            	int receivedValue = Pubmessage.getInt("value");
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