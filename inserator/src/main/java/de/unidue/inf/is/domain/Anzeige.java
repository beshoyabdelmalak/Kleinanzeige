package de.unidue.inf.is.domain;
import java.util.*;

public class Anzeige {
	private int id;
	private String titel;
	private String text;
	private float preis;
	private String erstellungsdatum;
	private String status;
	private String ersteller;
	public Anzeige(String titel, String text, float preis, String ersteller, String status) {
		this.titel = titel;
		this.text = text;
		this.preis = preis;
		this.status = status;
		this.ersteller = ersteller;
	}

	public String getErsteller() {
		return ersteller;
	}
	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public float getPreis() {
		return preis;
	}
	public void setPreis(float preis) {
		this.preis = preis;
	}
	public String getErstellungsdatum() {
		return erstellungsdatum;
	}
	public void setErstellungsdatum(String erstellungsdatum) {
		this.erstellungsdatum = erstellungsdatum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
