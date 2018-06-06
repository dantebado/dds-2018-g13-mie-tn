package ar.utn.frba.dds.g13.user;

import java.util.List;

import ar.utn.frba.dds.g13.device.Device;
import ar.utn.frba.dds.g13.device.SmartDevice;

public class Residence {
	
	String address;
	List<Device> devices;
	
	public Residence(String address, List<Device> devices) {
		this.address = address;
		this.devices = devices;
	}
	
	public Boolean anyDeviceOn() {
		for(Device device : devices) {
			if(device.isSmart()) {
				SmartDevice sd = (SmartDevice) device;
				if(sd.isOn()) {
					return true;
				}				
			}
		}
		return false;
	}
	
	public int numberDevicesOn() {
		int counter = 0;
		for(Device device : devices) {
			if(device.isSmart()) {
				SmartDevice sd = (SmartDevice) device;
				if(sd.isOn()) {
					counter++;
				}			
			}
		}
		return counter;
	}
	
	public int numberSmartDevicesTotal() {
		int counter = 0;
		for(Device device : devices) {
			if(device.isSmart()) {
				counter++;		
			}
		}
		return counter;
	}
	
	public int numberDevicesOff() {
		return numberSmartDevicesTotal() - this.numberDevicesOn();
	}
	
	public int numberDevicesTotal() {
		return devices.size();
	}

}
