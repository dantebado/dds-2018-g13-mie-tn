package ar.utn.frba.dds.g13.devices;

import java.math.BigDecimal;

public interface StateDevice {

	public abstract boolean isOn(SmartDevice device);
	public abstract boolean isOff(SmartDevice device);
	public abstract BigDecimal energyConsumptionHours(SmartDevice device, float hours);
	//public abstract BigDecimal energyConsumptionPeriod(SmartDevice device, float hours);
	public abstract void setDeviceOn(SmartDevice device);
	public abstract void setDeviceOff(SmartDevice device);
	public abstract void setDeviceEnergySaving(SmartDevice device);
	
}
