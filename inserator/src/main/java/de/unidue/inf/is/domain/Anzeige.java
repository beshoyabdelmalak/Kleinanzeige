package de.unidue.inf.is.domain;
import java.util.*;

public class Anzeige {
	private int id;
	private String titel;
	private String text;
	private float preis;
	private Date erstellungsdatum;
	private String status;
	private String ersteller;
	private String buyer;
	
	public Anzeige(String titel, String text, float preis, String ersteller, String status) {
		this.titel = titel;
		this.text = text;
		this.preis = preis;
		this.status = status;
		this.ersteller = ersteller;
	}
	public Anzeige(int id,String titel, String text, float preis, Date erstellungsdatum, String ersteller, String status) {
		this.titel = titel;
		this.text = text;
		this.preis = preis;
		this.status = status;
		this.ersteller = ersteller;
		this.id = id;
		this.erstellungsdatum = erstellungsdatum;
	}
	public Anzeige(int id, String titel, String text, float preis) {
		this.titel = titel;
		this.text = text;
		this.preis = preis;
		this.id = id;
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
	public Date getDate() {
		return erstellungsdatum;
	}
	public void setDate(Date erstellungsdatum) {
		this.erstellungsdatum = erstellungsdatum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Anzeige [id=" + id + ", titel=" + titel + ", text=" + text + ", preis=" + preis + ", erstellungsdatum="
				+ erstellungsdatum + ", status=" + status + ", ersteller=" + ersteller + "]";
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	

}
