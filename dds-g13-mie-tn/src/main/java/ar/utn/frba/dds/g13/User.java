package ar.utn.frba.dds.g13;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class User extends Person {
	
	@Expose private int phone;
	@Expose private String idType;
	@Expose private int idNumber;
	@Expose private String category;
	@Expose private int kWConsumption;
	
	@Expose private List<Device> devices;

	public User(String fullName, String address, Date registrationDate, String username, String password,
			int phone, String idType, int idNumber, String category, int kWConsumption) {
		super(fullName, address, registrationDate, username, password);
		setPhone(phone);
		setIdType(idType);
		setIdNumber(idNumber);
		setCategory(category);
		setkWConsumption(kWConsumption);
		devices = new ArrayList<Device>();
	}
	
	/*COMPORTAMENTO*/
	
	public void addDevice(Device device) {
		devices.add(device);
	}
	
	/*GETTERS - SETTERS*/

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setkWConsumption(int kWConsumption) {
		this.kWConsumption = kWConsumption;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	@Override
	public User getObj() {
		return this;
	}

}
