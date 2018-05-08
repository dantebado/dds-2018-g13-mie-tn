package ar.utn.frba.dds.g13;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

import json.BeanToJson;

public class User extends BeanToJson {
	
	@Expose private String fullName;
	@Expose private int idType;
	@Expose private int idNumber;
	@Expose private String phone;
	@Expose private String username;
	@Expose private String password;

	public User(String fullName, int idType, int idNumber,
			String phone, String username, String password) {
		this.fullName= fullName;
		this.idType = idType;
		this.phone = phone;
		this.username = username;
		this.password = password;
	}
	
	/*COMPORTAMENTO*/
	
	Boolean login(String username, String password) {
		return true;
	}
	
	/*GETTERS - SETTERS*/

	public int getIdNumber() {
		return idNumber;
	}

	public String getUsername() {
		return username;
	}

	public User getObj() {
		return this;
	}

}
