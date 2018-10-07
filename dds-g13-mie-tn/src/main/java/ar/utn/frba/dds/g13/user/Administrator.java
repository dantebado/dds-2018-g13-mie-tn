package ar.utn.frba.dds.g13.user;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Administrators")
public class Administrator extends User {
	
	@Id
	@Column(name="administrator_id")
	int id;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Administrator() {
		super();
	}
	
	public Administrator(int id,
			String username, String password,
			String fullname, String residenceAddress, Calendar registrationDate) {
		super(username, password, fullname, residenceAddress, registrationDate);
		this.id = id;
	}
	
	@SuppressWarnings("deprecation")
	public int antiquity() {
		LocalDate ld = LocalDate.now();
		Date now = new Date(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
		return now.getYear() - registrationDate.get(1);
	}

}
