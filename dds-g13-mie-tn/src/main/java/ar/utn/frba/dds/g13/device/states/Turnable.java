package ar.utn.frba.dds.g13.device.states;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface Turnable {
	
	public boolean isOn();
	public void setState(DeviceState state);
	public void turnOn() throws MqttException, InterruptedException;
	public void turnOff() throws MqttException, InterruptedException;
	public void turnEnergySaving() throws MqttException, InterruptedException;

}
