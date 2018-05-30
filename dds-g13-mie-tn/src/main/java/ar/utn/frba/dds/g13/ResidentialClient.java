package ar.utn.frba.dds.g13;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.devices.Device;

public class ResidentialClient extends User {
	
	@Expose private BigDecimal kWConsumption;
	
	@Expose private List<Address> addresses;

	public ResidentialClient(String fullName, int idType, int idNumber, String phone, String username,
			String password, BigDecimal kWConsumption, List<Address> addresses) {
		super(fullName, idType, idNumber, phone, username, password);
		this.kWConsumption = kWConsumption;
		this.addresses = addresses;
	}
	
	/*COMPORTAMENTO*/
	
	public List<Device> avblDevicesList() {
		List<Device> devicesAvbl = new ArrayList<Device>();
		for(Address address:this.getAddresses()) {
			devicesAvbl.addAll(address.getDevices());
		}
		return devicesAvbl;
	}
	
	public int totalAvblDevices() {
		int counter = 0;
		for(Address address:this.getAddresses()) {
			counter += address.totalAvblDevicesAddr();
		}
		return counter;
	}
	
	public List<Device> totalDevicesOn() {
		List<Device> devicesOn = new ArrayList<Device>();
		for(Address address:this.getAddresses()) {
			devicesOn.addAll(address.totalDevicesOnAddr());
		}
		return devicesOn;
	}
	
	public List<Device> totalDevicesOff() {
		List<Device> devicesOff = new ArrayList<Device>();
		for(Address address:this.getAddresses()) {
			devicesOff.addAll(address.totalDevicesOffAddr());
		}
		return devicesOff;
	}
	
	public BigDecimal estimateMonthCost() {
		BigDecimal counter = new BigDecimal("0");
		for(Address address:this.getAddresses()) {
			counter = counter.add(address.getAddresskWConsumption());
		}
		return counter;
	}
	
	/*GETTERS - SETTERS*/
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public ResidentialClient getObj() {
		return this;
	}

}
