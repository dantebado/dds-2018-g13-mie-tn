package ar.utn.frba.dds.g13.mosquitto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.automation.Actuator;
import ar.utn.frba.dds.g13.device.automation.AutomationTurnEnergySaving;
import ar.utn.frba.dds.g13.device.automation.AutomationTurnOff;
import ar.utn.frba.dds.g13.device.automation.AutomationTurnOn;
import ar.utn.frba.dds.g13.device.automation.DeviceAction;

public class SGESubMQTT extends Thread {
	public static List<RecivedMeasure> listaMediciones = new ArrayList<RecivedMeasure>();
	public static boolean listaOnUse = false;

	
	public static List<RecivedMeasure> getListaMediciones() {
		return listaMediciones;
	}
	
	public static void setListaMediciones(List<RecivedMeasure> listaMediciones) {
		SGESubMQTT.listaMediciones = listaMediciones;
		listaOnUse = false;
	}
	
	public void run() {
        
        System.out.println("== START SGE SUBSCRIBER ==");

        String mqttId = MqttClient.generateClientId();
        MqttClient client = null;
		try {
			client = new MqttClient("tcp://3.16.14.197:1883", mqttId);
		} catch (MqttException e) {
			e.printStackTrace();
		}
        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection to MQTT broker lost!");
            }

            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            	byte[] base64Decoded = DatatypeConverter.parseBase64Binary(new String(mqttMessage.getPayload()));
            	
            	System.out.println("Received JSON:" + new String(base64Decoded));	
            	
            	JSONObject Pubmessage = new JSONObject(new String(base64Decoded));
            	String type = Pubmessage.get("type").toString();
            	
            	if(type.equalsIgnoreCase("device")) {
            		String receivedId = Pubmessage.get("id").toString();
                	String receivedName = Pubmessage.get("name").toString();
                	String receivedMessure = Pubmessage.get("measure").toString();
                	int receivedValue = Integer.parseInt(Pubmessage.get("value").toString());
                	RecivedMeasure Messure = new RecivedMeasure(receivedId,receivedName,receivedMessure,receivedValue);
                	
                	listaMediciones.add(Messure);
            	} else {
            		//controller
            		String receivedId = Pubmessage.get("id").toString();
                	String receivedAction = Pubmessage.get("action").toString();
                	
                	Actuator act = null;
                	DeviceAction exe = null;
                	for(Actuator a : Actuator.GLOBAL_ACTUATORS) {
	                	if(a.getDevice().getId().toString().equalsIgnoreCase(receivedId)) {
	                		act = a;
	        			}
                	}
                	if(act == null ) {
                		return;
                	}
                	
                	if(receivedAction.equalsIgnoreCase("apagar")) {                			
                		exe = new AutomationTurnOff();
                	} else if(receivedAction.equalsIgnoreCase("encender")) {
                		exe = new AutomationTurnOn();
                	} else if(receivedAction.equalsIgnoreCase("modo ahorro energia")) {
                		exe = new AutomationTurnEnergySaving();
                	}
                	
                	if(exe == null) {
                		return;
                	}
                	exe.execute(act.getDevice(), act);
            	}
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        try {
			client.connect();
		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}

        try {
			client.subscribe("measurement");
			client.subscribe("controller");
		} catch (MqttException e) {
			e.printStackTrace();
		}
        System.out.println("Mqtt successfully subscribed");


    }
}