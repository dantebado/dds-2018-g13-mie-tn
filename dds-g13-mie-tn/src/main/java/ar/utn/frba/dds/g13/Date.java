package ar.utn.frba.dds.g13;

import com.google.gson.annotations.Expose;

public class Date {
	
	@Expose private int year;
	@Expose private int month;
	@Expose private int day;
	
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	/*COMPORTAMIENTO*/

}
