package ar.utn.frba.dds.g13.user;

import java.util.Date;
import java.util.List;

import ar.utn.frba.dds.g13.category.Category;

public class Client extends User {
	
	String idType;
	String idNumber;
	String phoneNumber;
	Category category;
	
	List<Residence> residences;

	public Client(String username, String password,
			String fullname, String residenceAddress, Date registrationDate,
			String idType, String idNumber, String phoneNumber,
			Category category,
			List<Residence> residences) {
		super(username, password, fullname, residenceAddress, registrationDate);
		this.idType = idType;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
		this.category = category;
		this.residences = residences;
	}
	
	public Boolean anyDeviceOn() {
		for(Residence residence : residences) {
			if(residence.anyDeviceOn()) {
				return true;
			}
		}
		return false;
	}
	
	public int numberDevicesOn() {
		int counter = 0;
		for(Residence residence : residences) {
			counter += residence.numberDevicesOn();
		}
		return counter;
	}
	
	public int numberDevicesOff() {
		int counter = 0;
		for(Residence residence : residences) {
			counter += residence.numberDevicesOff();
		}
		return counter;
	}
	
	public int numberDevicesTotal() {
		int counter = 0;
		for(Residence residence : residences) {
			counter += residence.numberDevicesTotal();
		}
		return counter;
	}

}