package de.unidue.inf.is.domain;

import com.ibm.db2.jcc.am.l;

public class Nachricht {
	
	private int id;
	private String text;
	private String absender;
	private String empfaenger;
	
	public Nachricht( String text , String absender, String empfaenger) {
		this.text = text;
		this.absender = absender;
		this.empfaenger = empfaenger;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEmpfaenger() {
		return empfaenger;
	}
	public void setEmpfaenger(String empfaenger) {
		this.empfaenger = empfaenger;
	}
	public String getAbsender() {
		return absender;
	}
	public void setAbsender(String absender) {
		this.absender = absender;
	}
	
	

}
