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
@Table(name = "Administrator")
public class Administrator extends User {
	
	@Id								
	@GeneratedValue
	@Column(name="administrator_id")
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Administrator() {
		super();
	}
	
	public Administrator(
			String username, String password,
			String fullname, String residenceAddress, Calendar registrationDate) {
		super(username, password, fullname, residenceAddress, registrationDate);
	}
	
	@SuppressWarnings("deprecation")
	public int antiquity() {
		LocalDate ld = LocalDate.now();
		Date now = new Date(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
		return now.getYear() - registrationDate.get(1);
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
