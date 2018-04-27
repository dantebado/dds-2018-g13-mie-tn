package ar.utn.frba.dds.g13;

import com.google.gson.annotations.Expose;

public class Date {
	
	@Expose private int year;
	@Expose private int month;
	@Expose private int day;
	
	public Date(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}
	
	/*COMPORTAMIENTO*/
	
	/*GETTERS - SETTERS*/	
	
	public void setYear(int year) {
		this.year = year;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setDay(int day) {
		this.day = day;
	}

}
