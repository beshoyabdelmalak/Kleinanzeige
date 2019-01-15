package de.unidue.inf.is.domain;
import java.text.SimpleDateFormat;
import java.util.*;

public final class User {

    private String name;
    private String benutzerName;
    private Date eintrittsDatum;
    private int gekauft;


    public User() {
    }


    public User(String name, String benutzerName) {
        this.name = name;
        this.benutzerName = benutzerName;
        this.eintrittsDatum = new Date();

    }


    public String getEintrittsDatum() {
    	SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
    	String mdy = mdyFormat.format(eintrittsDatum);
		return mdy;
	}


	public void setEintrittsDatum(Date eintrittssDatum) {
		this.eintrittsDatum = eintrittsDatum;
	}


	public String getName() {
        return name;
    }


    public String getBenutzerName() {
        return benutzerName;
    }


	public int getGekauft() {
		return gekauft;
	}


	public void setGekauft(int gekauft) {
		this.gekauft = gekauft;
	}

}