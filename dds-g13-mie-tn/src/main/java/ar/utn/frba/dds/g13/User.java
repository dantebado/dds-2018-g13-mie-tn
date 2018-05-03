package ar.utn.frba.dds.g13;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

public class User extends Person {
	
	@Expose private String phone;
	@Expose private String idType;
	@Expose private int idNumber;
	@Expose private String category; 
	@Expose private BigDecimal kWConsumption;
	@Expose private List<Device> devices;

	public User(String fullName, String address, Date registrationDate, String username, String password,
			String phone, String idType, int idNumber, String category, BigDecimal kWConsumption) {
		super(fullName, address, registrationDate, username, password);
		this.phone = phone;
		this.idType = idType;
		this.idNumber = idNumber;
		this.category = category; // Debe cambiarse
		this.kWConsumption = kWConsumption;
		devices = new ArrayList<Device>();
	}
	
	/*COMPORTAMENTO*/
	
	public void addDevice(Device device) {
		devices.add(device);
	}
	
	/*GETTERS - SETTERS*/

	@Override
	public User getObj() {
		return this;
	}

}
