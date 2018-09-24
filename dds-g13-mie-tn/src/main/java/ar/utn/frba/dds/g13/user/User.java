package ar.utn.frba.dds.g13.user;

import java.util.Calendar;
import java.util.Date;

import com.google.gson.annotations.Expose;

public abstract class User extends Logueable {
	
	@Expose String fullname;
	@Expose String residenceAddress;
	@Expose Calendar registrationDate;
	

	public User(String username, String password,
			String fullname, String residenceAddress, Calendar registrationDate) {
		super(username, password);
		this.fullname = fullname;
		this.residenceAddress = residenceAddress;
		this.registrationDate = registrationDate;
	}

}
