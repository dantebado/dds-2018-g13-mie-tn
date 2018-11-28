package ar.utn.frba.dds.g13.mosquitto;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import ar.utn.frba.dds.g13.SparkApp;

import javax.xml.bind.DatatypeConverter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DispPubMQTT {

    public static void main(String[] args) throws MqttException, InterruptedException {
    	
    	if (args.length != 5) {
    		try{
    		    throw new Exception("Missing Params, insert: name minValue maxValue measure");
    		  }catch(Exception e){
    		    System.err.println("Exception caught: "+e);
    		  }
    	}
    	
    	String name = args[0];
    	String minString = args[1];
    	int minValue = Integer.parseInt(minString);
        String maxString = args[2];
        int maxValue = Integer.parseInt(maxString);
        String measure = args[3];
        String id = args[4];
        String mqttiId = MqttClient.generateClientId();

        System.out.println("== START " + name + " PUBLISHER ==");
        
        MqttClient client = new MqttClient(SparkApp.MQTTIP, mqttiId);
        client.connect();


        while (true) {
        	
        	Random random = new Random();
            int value = random.nextInt(maxValue + 1 - minValue) + minValue;
            
            String payload;
            JSONObject json = new JSONObject();
            json.put("type", "device");
            json.put("id", id);
            json.put("name", name);
            json.put("minValue", minValue);
            json.put("maxValue", maxValue);
            json.put("measure", measure);
            json.put("value", value);

            payload = json.toString();
            byte[] jsonBytes = payload.getBytes();
            String base64Encoded = DatatypeConverter.printBase64Binary(jsonBytes);
     
            MqttMessage message = new MqttMessage();
            message.setPayload(base64Encoded.getBytes());
            client.publish("measurement", message);
            
            System.out.println("new measure sended: " + value + " " + measure);
            
            TimeUnit.SECONDS.sleep(10);
            
            //TODO handle console command to exit
        }

        //client.disconnect();

        //System.out.println("== END " + name + " PUBLISHER ==");

    }

}
