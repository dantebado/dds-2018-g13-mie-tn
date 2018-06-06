package ar.utn.frba.dds.g13.user;

import java.util.Date;

public abstract class User extends Logueable {
	
	String fullname;
	String residenceAddress;
	Date registrationDate;

	public User(String username, String password,
			String fullname, String residenceAddress, Date registrationDate) {
		super(username, password);
		this.fullname = fullname;
		this.residenceAddress = residenceAddress;
		this.registrationDate = registrationDate;
	}

}
