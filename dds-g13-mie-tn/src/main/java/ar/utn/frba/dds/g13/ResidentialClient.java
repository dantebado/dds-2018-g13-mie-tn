package ar.utn.frba.dds.g13;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.Expose;

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
	
	public List<Device> totalAvblDevices() {
		return null;
	}
	
	public List<Device> totalDevicesOn() {
		return null;
	}
	
	public List<Device> totalDevicesOff() {
		return null;
	}
	
	public List<Device> avblDevicesList() {
		return null;
	}
	
	public BigDecimal estimateMonthCost() {
		return null;
	}
	
	/*GETTERS - SETTERS*/
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public ResidentialClient getObj() {
		return this;
	}

}
