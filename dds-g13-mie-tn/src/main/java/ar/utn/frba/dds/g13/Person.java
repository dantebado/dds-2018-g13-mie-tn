package ar.utn.frba.dds.g13;


import com.google.gson.annotations.Expose;

import json.BeanToJson;

public class Person extends BeanToJson {
	
	@Expose protected String fullName;
	@Expose protected String address;
	@Expose protected Date registrationDate;
	@Expose protected String username;
	@Expose protected String password;
	
	public Person(String fullName, String address, Date registrationDate, String username, String password) {
		setFullName(fullName);
		setAddress(address);
		setRegistrationDate(registrationDate);
		setUsername(username);
		setPassword(password);
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Person getObj() {
		return this;
	}

}
