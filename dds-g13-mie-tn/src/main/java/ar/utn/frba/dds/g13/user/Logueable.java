package ar.utn.frba.dds.g13.user;

public class Logueable {
	
	private String username;
	private String password;
	
	public Logueable(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean login(String username, String password) {
		return (username == this.username && password == this.password);
	}

}
