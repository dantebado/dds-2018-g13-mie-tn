package ar.utn.frba.dds.g13.device.states;

public interface Turnable {
	
	public boolean isOn();
	public void setState(DeviceState state);
	public void turnOn();
	public void turnOff();
	public void turnEnergySaving();

}
