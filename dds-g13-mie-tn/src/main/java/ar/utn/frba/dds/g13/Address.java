package ar.utn.frba.dds.g13;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.devices.Device;
import json.BeanToJson;

public class Address extends BeanToJson {
	
	@Expose private int addressId;
	@Expose private Date registrationDate;
	@Expose private String transformer;
	@Expose private BigDecimal addresskWConsumption;
	
	@Expose private Category category;
	
	@Expose private List<Device> devices;
	
	public Address(int addressId, Date registrationDate, String transformer,
			BigDecimal addresskWConsumption, Category category, List<Device> devices) {
		this.addressId = addressId;
		this.registrationDate = registrationDate;
		this.transformer = transformer;
		this.addresskWConsumption = addresskWConsumption;
		this.category = category;
		this.devices = devices;
	}
	
	/*COMPORTAMENTO*/
	
	public int totalAvblDevicesAddr() {
		return devices.size();	
	}
	
	public List<Device> totalDevicesOnAddr() {
		List<Device> devicesOn = new ArrayList<Device>();
		for(Device device:this.getDevices()) {
			if (device.isOn()) {
				devicesOn.add(device);
			}
		}
		return devicesOn;
	}
	
	public List<Device> totalDevicesOffAddr() {
		List<Device> devicesOff = new ArrayList<Device>();
		for(Device device:this.getDevices()) {
			if (!device.isOn()) {
				devicesOff.add(device);
			}
		}
		return devicesOff;
	}
	
	public void addDevice(Device device) {
		devices.add(device);
	}
	
	/*GETTERS - SETTERS*/
	
	public List<Device> getDevices() {
		return devices;
	}
	
	public BigDecimal getAddresskWConsumption() {
		return addresskWConsumption;
	}

	@Override
	public Address getObj() {
		return this;
	}

}
