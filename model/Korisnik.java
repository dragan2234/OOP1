package model;

public abstract class Korisnik {
	protected String korisnickoIme;// kljuc
	protected String lozinka;
	protected String ime;
	protected String prezime;
	protected String JMBG;
	protected boolean aktivan;

	public Korisnik() {
		// TODO Auto-generated constructor stub
		aktivan = true;
	}

	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String jMBG, boolean akt) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.JMBG = jMBG;
		this.aktivan = akt;
	}

	public Korisnik(Korisnik k) {
		this.ime = k.ime;
		this.JMBG = k.JMBG;
		this.prezime = k.prezime;
		this.lozinka = k.lozinka;
		this.korisnickoIme = k.korisnickoIme;
		this.aktivan = k.aktivan;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivnost) {
		aktivan = aktivnost;
	}

	@Override
	public String toString() {
		return "Korisnik : korisnickoIme: " + korisnickoIme + ", lozinka: " + lozinka + ", ime: " + ime + ", prezime: "
				+ prezime + ", JMBG: " + JMBG + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Korisnik))
			return false;
		Korisnik other = (Korisnik) obj;
		if (korisnickoIme == null) {
			if (other.korisnickoIme != null)
				return false;
		} else if (!korisnickoIme.equals(other.korisnickoIme))
			return false;
		return true;
	}

	public String fileOutputString() {
		return korisnickoIme + "|" + lozinka + "|" + ime + "|" + prezime + "|" + JMBG + "|"
				+ this.getClass().getSimpleName().toLowerCase() + "|" + aktivan;
	}
	
	
	public String korisnikIspis(){
		return String.format("%-20s|%-20s|%-20s|%-20s|%-10s", korisnickoIme,ime,prezime,this.JMBG, this.getClass().getSimpleName());
	}
}
