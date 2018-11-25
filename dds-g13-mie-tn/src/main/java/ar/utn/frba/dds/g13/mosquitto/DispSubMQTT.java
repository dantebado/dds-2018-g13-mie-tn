package ar.edu.dds;

import javax.xml.bind.DatatypeConverter;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

public class DispSubMQTT {
	public static String State = "OFF";
	public static String Mode = "INTELIGENTE";
	
    public static void main(String[] args) throws MqttException, InterruptedException {
    	
    	String name = args[0];
    	String id = args[1];

    	System.out.println("== START " + name + " SUBSCRIBER ==");

        String mqttId = MqttClient.generateClientId();
        MqttClient client = new MqttClient("tcp://localhost:1883", mqttId);
        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection to MQTT broker lost!");
            }

            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            	byte[] base64Decoded = DatatypeConverter.parseBase64Binary(new String(mqttMessage.getPayload()));	
            	
            	JSONObject Pubmessage = new JSONObject(new String(base64Decoded));
            	String receivedId = Pubmessage.getString("id");
            	String receivedAction = Pubmessage.getString("action");
            	String receivedMode = Pubmessage.getString("mode");
            	
            	if (receivedId == id) {
            		switch (receivedAction) {
            		
            			case "ENCENDER": {
            				if (State != "ON") {
	            				if (receivedMode == "INTELIGENTE" && Mode == receivedMode) {
	            					System.out.println("NEW ORDER RECIVED: " + receivedAction);
	            					if (State == "ENERGY_SAVING") {            				
	            						System.out.println("leaving energy saving mode...");
	            					}
	            					else {
	            						System.out.println("turning " + name + " on standard mode...");            				
	            					}
	            					
	            					State = "ON";
	            					break;
	            				}
            					if (receivedMode == "MANUAL") {
            						System.out.println("NEW ORDER RECIVED: " + receivedAction);
            						if (Mode == "INTELIGENTE") {
            							System.out.println("SMART MODE OFF");
            							
            							Mode = "MANUAL";
            						}
	            					if (State == "ENERGY_SAVING") {            				
	            						System.out.println("leaving energy saving mode...");
	            					}
	            					else {
	            						System.out.println("turning " + name + " on standard mode...");            				
	            					}
	            					
	            					State = "ON";
	            					break;
            					}
            				}
            				break;	
            			}
            			
            			case "APAGAR": {
            				if (State != "OFF") {
            					if (receivedMode == "INTELIGENTE" && Mode == receivedMode) {
	            					System.out.println("NEW ORDER RECIVED: " + receivedAction);
	            					System.out.println("turning " + name + " off..."); 
	            					
	            					State = "OFF";
	            					break;
            					}
            					if (receivedMode == "MANUAL") {
            						System.out.println("NEW ORDER RECIVED: " + receivedAction);
            						if (Mode == "INTELIGENTE") {
            							System.out.println("SMART MODE OFF");
            							
            							Mode = "MANUAL";
            						}
	            					System.out.println("turning " + name + " off...");
	            					
	            					State = "OFF";
	            					break;
            					}
            				}
            				break;
            			}
            			
            			case "MODO AHORRO ENERGIA": {
            				if (State != "ENERGY_SAVING") {
            					if (receivedMode == "INTELIGENTE" && Mode == receivedMode) {
	            					System.out.println("NEW ORDER RECIVED: " + receivedAction);
	            					System.out.println("entering energy saving mode...");
	            					
	            					State = "ENERGY_SAVING";
	            					break;
            					}
            					if (receivedMode == "MANUAL") {
            						System.out.println("NEW ORDER RECIVED: " + receivedAction);
            						if (Mode == "INTELIGENTE") {
            							System.out.println("SMART MODE OFF");
            							
            							Mode = "MANUAL";
            						}
	            					System.out.println("entering energy saving mode...");
	            					
	            					State = "ENERGY_SAVING";
	            					break;
            					}
            				}
            				break;
            			}
            			
            			case "MODO INTELIGENTE": {
            				if (Mode == "MANUAL") {
            					System.out.println("SMART MODE ON");
            					
            					Mode = "INTELIGENTE";	
            				}
            				break;
            			}
            			
            			case "MODO MANUAL": {
            				if (Mode == "INTELIGENTE") {
            					System.out.println("SMART MODE OFF");
            					
            					Mode = "MANUAL";	
            				}
            				break;
            			}
            		}
            	}
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.connect();

        client.subscribe("actions");
        System.out.println("ok!");


    }
}