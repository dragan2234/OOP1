package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.SimpleAttributeSet;

import sistem.Utility;

public class Izvodjenje {
	protected int id; //kljuc
	protected Date vremePocetka;
	protected double cenaKarte;
	protected Predstava predstava;
	protected Scena scena;
	protected ArrayList<Karta> prodateKarte;
    protected boolean aktivan;
    
    public boolean isAktivan(){
   	 return aktivan;
    }
   
    public void setAktivan(boolean a){
   	 this.aktivan=a;
    }
	
	
	public Izvodjenje() {
		prodateKarte=new ArrayList<Karta>();
		aktivan = true;

	}
	public Izvodjenje(int id, Date vremePocetka, double cenaKarte, Predstava predstava, Scena scena,
			ArrayList<Karta> prodateKarte) {
		super();
		this.id = id;
		this.vremePocetka = vremePocetka;
		this.cenaKarte = cenaKarte;
		this.predstava = predstava;
		this.scena = scena;
		this.prodateKarte = prodateKarte;
		aktivan = true;
	}
	
	public Izvodjenje(int id, Date vremePocetka, double cenaKarte, Predstava predstava, Scena scena,
			ArrayList<Karta> prodateKarte, boolean akt) {
		super();
		this.id = id;
		this.vremePocetka = vremePocetka;
		this.cenaKarte = cenaKarte;
		this.predstava = predstava;
		this.scena = scena;
		this.prodateKarte = prodateKarte;
		aktivan = akt;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getVremePocetka() {
		return vremePocetka;
	}
	public void setVremePocetka(Date vremePocetka) {
		this.vremePocetka = vremePocetka;
	}
	public double getCenaKarte() {
		return cenaKarte;
	}
	public void setCenaKarte(double cenaKarte) {
		this.cenaKarte = cenaKarte;
	}
	public Predstava getPredstava() {
		return predstava;
	}
	public void setPredstava(Predstava predstava) {
		this.predstava = predstava;
	}
	public Scena getScena() {
		return scena;
	}
	public void setScena(Scena scena) {
		this.scena = scena;
	}
	public ArrayList<Karta> getProdateKarte() {
		return prodateKarte;
	}
	public void setProdateKarte(ArrayList<Karta> prodateKarte) {
		this.prodateKarte = prodateKarte;
	}
	@Override
	public String toString() {
		String izlaz = "Izvodjenje : id: " + id + ", vremePocetka: " + Utility.sdf.format(vremePocetka) + ", cenaKarte: " + cenaKarte
				+ ", predstava: " + predstava.getNaziv()+ ", scena: " + scena.getNaziv() + "\n";
		for (Karta k: prodateKarte){
			izlaz+="\n\t"+k.getSediste();
		}
		return izlaz;
	}
	
	public String fileOutputString(){
		String prodate_karte = "";
		for (Karta k : prodateKarte){
			prodate_karte = prodate_karte + k.getSerijskiBroj();
		}
		return id + "|" + vremePocetka + "|" + cenaKarte + "|" + predstava.getNaziv() + "|" + scena.getNaziv() + "|" + prodate_karte + "|" + aktivan;
	}
	
}
