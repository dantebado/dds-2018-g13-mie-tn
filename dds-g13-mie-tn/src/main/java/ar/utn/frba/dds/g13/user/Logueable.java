package ar.utn.frba.dds.g13.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.json.BeanToJson;

@MappedSuperclass
public class Logueable extends BeanToJson {
	
	@Column(name="username")
	@Expose private String username;
	
	@Column(name="password")
	@Expose private String password;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Logueable() {
		super();
	}
	
	public Logueable(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean login(String username, String password) {
		return (username == this.username && password == this.password);
	}

	@Override
	public Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

}
