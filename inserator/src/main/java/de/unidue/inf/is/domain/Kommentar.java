package de.unidue.inf.is.domain;

import java.sql.Date;

public class Kommentar {
	private int id ;
	private String text;
	private Date erstellungdatum;
	public Kommentar(String text) {
		this.text = text;
	}
	public Kommentar(int id, String text, Date erstellungdatum) {
		this.id = id;
		this.text = text;
		this.erstellungdatum = erstellungdatum;
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
	public Date getErstellungdatum() {
		return erstellungdatum;
	}
	public void setErstellungdatum(Date erstellungdatum) {
		this.erstellungdatum = erstellungdatum;
	}


}
