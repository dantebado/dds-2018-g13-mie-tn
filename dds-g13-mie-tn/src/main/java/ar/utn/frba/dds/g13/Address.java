package ar.utn.frba.dds.g13;

import java.math.BigDecimal;
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
	
	public List<Device> totalAvblDevicesAddr() {
		return null;
	}
	
	public List<Device> totalDevicesOnAddr() {
		return null;
	}
	
	public List<Device> totalDevicesOffAddr() {
		return null;
	}
	
	public void addDevice(Device device) {
		devices.add(device);
	}
	
	/*GETTERS - SETTERS*/
	
	public List<Device> getDevices() {
		return devices;
	}

	@Override
	public Address getObj() {
		return this;
	}

}
