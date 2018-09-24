package ar.utn.frba.dds.g13.user;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Administrator extends User {
	
	int id;

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
