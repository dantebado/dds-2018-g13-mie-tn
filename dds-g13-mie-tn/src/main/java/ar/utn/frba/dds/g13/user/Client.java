package ar.utn.frba.dds.g13.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.category.Category;
import ar.utn.frba.dds.g13.device.Device;

public class Client extends User {
	
	@Expose String idType;
	@Expose String idNumber;
	@Expose String phoneNumber;
	@Expose Category category;
	
	@Expose int score;
	
	@Expose List<Residence> residences;

	public Client(String username, String password,
			String fullname, String residenceAddress, Calendar registrationDate,
			String idType, String idNumber, String phoneNumber,
			Category category, int score,
			List<Residence> residences) {
		super(username, password, fullname, residenceAddress, registrationDate);
		this.idType = idType;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
		this.category = category;
		this.score = score;
		this.residences = residences;
	}
	
	public List<Residence> getResidences() {
		return residences;
	}
	
	public Calendar getRegistrationDate() {
		return registrationDate;
	}
	
	public void registerDevice(Device device, Residence residence) {
		if(residences.contains(residence)) {
			residence.addDevice(device);
			if(device.isSmart()) {
				increaseScore(15);
			}
		} else {
			//Error
		}
	}
	
	public void adaptStandardDevice(Device device, Residence residence) {
		if(residences.contains(residence)) {
			residence.adaptStandardDevice(device);
			increaseScore(10);
		} else {
			//Error
		}
	}
	
	public void increaseScore(int ammount) {
		score += ammount;
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
