package ar.utn.frba.dds.g13.user;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.google.gson.annotations.Expose;

@MappedSuperclass
public abstract class User extends Logueable {
	
	@Column(name="fullname")
	@Expose String fullname;
	
	@Column(name="residenceAddress")
	@Expose String residenceAddress;
	
	@Column(name="registrationDate")
	@Expose Calendar registrationDate;
	

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public Calendar getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}

	public User() {
		super();
	}
	
	public User(String username, String password,
			String fullname, String residenceAddress, Calendar registrationDate) {
		super(username, password);
		this.fullname = fullname;
		this.residenceAddress = residenceAddress;
		this.registrationDate = registrationDate;
	}

}
