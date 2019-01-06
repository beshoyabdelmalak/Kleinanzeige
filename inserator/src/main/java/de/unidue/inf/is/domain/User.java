package de.unidue.inf.is.domain;
import java.util.*;

public final class User {

    private String name;
    private String benutzerName;
    private Date erstellungsDatum;


    public User() {
    }


    public User(String name, String benutzerName) {
        this.name = name;
        this.benutzerName = benutzerName;
        this.erstellungsDatum = new Date();

    }


    public Date getErstellungsDatum() {
		return erstellungsDatum;
	}


	public void setErstellungsDatum(Date erstellungsDatum) {
		this.erstellungsDatum = erstellungsDatum;
	}


	public String getname() {
        return name;
    }


    public String getBenutzerName() {
        return benutzerName;
    }

}