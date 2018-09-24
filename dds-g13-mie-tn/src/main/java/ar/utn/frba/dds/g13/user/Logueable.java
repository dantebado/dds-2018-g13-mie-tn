package ar.utn.frba.dds.g13.user;

import com.google.gson.annotations.Expose;

import ar.utn.frba.dds.g13.json.BeanToJson;

public class Logueable extends BeanToJson {
	
	@Expose private String username;
	@Expose private String password;
	
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
