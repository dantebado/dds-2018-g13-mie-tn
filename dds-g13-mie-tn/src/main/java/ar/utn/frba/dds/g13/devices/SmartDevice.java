package ar.utn.frba.dds.g13.devices;

import java.math.BigDecimal;

public class SmartDevice extends Device {
	
	private StateDevice state;

	public SmartDevice(Boolean deviceOn, BigDecimal consumptionkWh, String name, StateDevice state) {
		super(deviceOn, consumptionkWh, name);
		this.state = state;
	}
	
	public StateDevice getState() {
		return state;
	}
	
	public void setState(StateDevice state) {
		this.state = state;
	}
	
	public boolean isOn() {
		return state.isOn(this);
	}
	
	public boolean isOff() {
		return state.isOff(this);
	}
	
	public BigDecimal energyConsumptionHours(float hours) {
		return state.energyConsumptionHours(this, hours);
	}
	
	//public BigDecimal energyConsumptionPeriod() {
	//	return state.isOn(this);
	//}
	
	public void setDeviceOn() {
		state.setDeviceOn(this);
	}
	
	public void setDeviceOff() {
		state.setDeviceOff(this);
	}
	
	public void setDeviceEnergySaving() {
		state.setDeviceEnergySaving(this);
	}

}
