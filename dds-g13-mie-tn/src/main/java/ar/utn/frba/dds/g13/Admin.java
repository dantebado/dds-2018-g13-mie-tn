package ar.utn.frba.dds.g13;


import com.google.gson.annotations.Expose;

public class Admin extends Person {
	
	@Expose private int id;

	public Admin(int id, String fullName, String address, Date registrationDate, String username,
			String password) {
		super(fullName, address, registrationDate, username, password);
		setId(id);
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Admin getObj() {
		return this;
	}

}
