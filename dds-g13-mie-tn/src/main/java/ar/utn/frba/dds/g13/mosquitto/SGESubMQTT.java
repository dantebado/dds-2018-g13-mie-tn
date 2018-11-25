package ar.utn.frba.dds.g13.mosquitto;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class SGESubMQTT {
	public static JSONArray listaMediciones = new JSONArray();
	public static boolean listaOnUse = false;
	
    public static JSONArray getListaMediciones() {
    	if (!listaOnUse) {
    		listaOnUse = true;
    		return listaMediciones;
    	}
    	return null;
	}

	public static void setListaMediciones(JSONArray listaMediciones) {
		SGESubMQTT.listaMediciones = listaMediciones;
		listaOnUse = false;
	}

	public static void main(String[] args) throws MqttException, InterruptedException {
        
        System.out.println("== START SGE SUBSCRIBER ==");

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
            	
            	listaMediciones.put(Pubmessage);
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