package model;

import java.util.HashMap;

public class Biletar extends Korisnik {
	private HashMap<String, Karta> prodateKarte;

	public Biletar() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Biletar(String korisnickoIme, String lozinka, String ime, String prezime, String JMBG,
			HashMap<String, Karta> prodateKarte) {
		super(korisnickoIme, lozinka, ime, prezime, JMBG, true);
		this.prodateKarte = prodateKarte;
	}

	public Biletar(String korisnickoIme, String lozinka, String ime, String prezime, String JMBG, Boolean akt) {
		super(korisnickoIme, lozinka, ime, prezime, JMBG, akt);
		this.prodateKarte = new HashMap<String, Karta>();
		aktivan = akt;
	}

	public Biletar(String korisnickoIme, String lozinka, String ime, String prezime, String JMBG) {
		super(korisnickoIme, lozinka, ime, prezime, JMBG, true);
		prodateKarte = new HashMap<String, Karta>();
	}

	public HashMap<String, Karta> getProdateKarte() {
		return prodateKarte;
	}

	public void setProdateKarte(HashMap<String, Karta> prodateKarte) {
		this.prodateKarte = prodateKarte;
	}

	@Override
	public String toString() {
		return "Biletar : prodateKarte: " + prodateKarte + super.toString() + "\n";
	}
	
	

}
