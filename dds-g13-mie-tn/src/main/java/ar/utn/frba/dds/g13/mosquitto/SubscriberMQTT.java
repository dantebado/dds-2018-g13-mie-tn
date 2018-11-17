package ar.utn.frba.dds.g13.mosquitto;


import org.eclipse.paho.client.mqttv3.*;

public class SubscriberMQTT {

    public static void main(String[] args) throws MqttException, InterruptedException {

        System.out.println("== START SUBSCRIBER ==");

        MqttClient client = new MqttClient("tcp://localhost:1883", "serverClientId");
        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection to MQTT broker lost!");
            }

            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("Message received:\t" + new String(mqttMessage.getPayload()));
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.connect();

        client.subscribe("iot_data");
        System.out.println("ok!");


    }

}
