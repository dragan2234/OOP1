package model;

import java.util.Date;

import sistem.Utility;

public class Karta {
	protected String serijskiBroj;// kljuc
	protected double cena;
	protected int popust;
	protected Date vremeIzdavanja;
	protected Izvodjenje izvodjenje;
	protected Sediste sediste; // koje pripada sali izvodjenja
	protected boolean aktivan;

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean a) {
		this.aktivan = a;
	}

	public Karta(String serijskiBroj, double cena, int popust, Date vremeIzdavanja, Izvodjenje izvodjenje,
			Sediste sediste) {
		super();
		this.serijskiBroj = serijskiBroj;
		this.cena = cena;
		this.popust = popust;
		this.vremeIzdavanja = vremeIzdavanja;
		this.izvodjenje = izvodjenje;
		this.sediste = sediste;
		aktivan = true;

	}

	public Karta(String serijskiBroj, double cena, int popust, Date vremeIzdavanja, Izvodjenje izvodjenje,
			Sediste sediste, boolean akt) {
		super();
		this.serijskiBroj = serijskiBroj;
		this.cena = cena;
		this.popust = popust;
		this.vremeIzdavanja = vremeIzdavanja;
		this.izvodjenje = izvodjenje;
		this.sediste = sediste;
		aktivan = akt;
	}

	public String getSerijskiBroj() {
		return serijskiBroj;
	}

	public void setSerijskiBroj(String serijskiBroj) {
		this.serijskiBroj = serijskiBroj;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public Date getVremeIzdavanja() {
		return vremeIzdavanja;
	}

	public void setVremeIzdavanja(Date vremeIzdavanja) {
		this.vremeIzdavanja = vremeIzdavanja;
	}

	public Izvodjenje getIzvodjenje() {
		return izvodjenje;
	}

	public void setIzvodjenje(Izvodjenje izvodjenje) {
		this.izvodjenje = izvodjenje;
	}

	public Sediste getSediste() {
		return sediste;
	}

	public void setSediste(Sediste sediste) {
		this.sediste = sediste;
	}

	@Override
	public String toString() {
		return "Karta : serijskiBroj: " + serijskiBroj + ", cena: " + cena + ", popust: " + popust
				+ ", vremeIzdavanja: " + Utility.sdf.format(vremeIzdavanja) + ", izvodjenje: " + izvodjenje.getId()
				+ ", sediste: " + sediste + "\n";
	}

	public String ispisKarte(){
		String popust;
		if(getPopust() != 0)
    	popust = getPopust()+"%";
		else popust = "";
    	return String.format("%20s|%20s|%20s|%6.2f|%6d|%22s|%5s|", getIzvodjenje().getPredstava().getNaziv(), Utility.sdf.format(getIzvodjenje().getVremePocetka()),getIzvodjenje().getScena().getNaziv(),getCena(),getPopust(),Utility.sdf.format(getVremeIzdavanja()),getSerijskiBroj()); 
    }

}
