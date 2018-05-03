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
		this.fullName = fullName;
		this.address = address;
		this.registrationDate = registrationDate;
		this.username = username;
		this.password = password;
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	@Override
	public Person getObj() {
		return this;
	}

}
