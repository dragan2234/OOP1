package model;


public class Predstava {
    protected String naziv; //kljuc
    protected TipPredstave tipPredstave;
    protected String reziser;
    protected String glumci;
    protected int trajanje; //minuti
    protected String produkcija;
    protected String opis;
    protected boolean aktivan;
    protected int godina_premijere;
    
    public int getGodina_premijere() {
		return godina_premijere;
	}

	public void setGodina_premijere(int godina_premijere) {
		this.godina_premijere = godina_premijere;
	}

	public boolean isAktivan(){
    	return aktivan;
    }
    
    public void setAktivan(boolean a){
    	this.aktivan=a;
    }
    
    public Predstava() {
		// TODO Auto-generated constructor stub
	}
    
	public Predstava(String naziv, TipPredstave tipPredstave, String reziser, String glumci, int trajanje,
			String produkcija, String opis, int godinaPremijere) {
		super();
		this.naziv = naziv;
		this.tipPredstave = tipPredstave;
		this.reziser = reziser;
		this.glumci = glumci;
		this.trajanje = trajanje;
		this.produkcija = produkcija;
		this.opis = opis;
		this.godina_premijere=godinaPremijere;
		this.aktivan = true;
	}
	public Predstava(String naziv, TipPredstave tipPredstave, String reziser, String glumci, int trajanje,
			String produkcija, String opis, int godinaPremijere, boolean akt) {
		super();
		this.naziv = naziv;
		this.tipPredstave = tipPredstave;
		this.reziser = reziser;
		this.glumci = glumci;
		this.trajanje = trajanje;
		this.produkcija = produkcija;
		this.opis = opis;
		this.godina_premijere=godinaPremijere;
		this.aktivan = akt;
	}


	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipPredstave getTipPredstave() {
		return tipPredstave;
	}

	public void setTipPredstave(TipPredstave tipPredstave) {
		this.tipPredstave = tipPredstave;
	}

	public String getReziser() {
		return reziser;
	}

	public void setReziser(String reziser) {
		this.reziser = reziser;
	}

	public String getGlumci() {
		return glumci;
	}

	public void setGlumci(String glumci) {
		this.glumci = glumci;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public String getProdukcija() {
		return produkcija;
	}

	public void setProdukcija(String produkcija) {
		this.produkcija = produkcija;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	@Override
	public String toString() {
		return "\n\t\tnaziv: " + naziv + "\n\t\ttipPredstave: " + tipPredstave.toString() + "\n\t\treziser: " + reziser
				+ "\n\t\tglumci: " + glumci + "\n\t\ttrajanje: " + trajanje + "\n\t\tprodukcija: " + produkcija + "\n\t\topis: " + opis
				+ "\n\t\tgodina premijere: "+ godina_premijere+ "\n";
	}
    
	public String fileOutputString(){
		return naziv + "|" + tipPredstave.toString() + "|" + reziser + "|" + glumci + "|" + trajanje + "|" + produkcija + "|" +  opis  + "|" + godina_premijere + "|" + aktivan;
	}
    
	
	public String tabelaranIspis(){
		String glumci2 = glumci;
		if (glumci.length() > 60)
			glumci2 = glumci.substring(0, 56)+"...";
		String opis2 = opis;
		if (opis.length() > 60)
			opis2 = opis.substring(0, 56)+"...";
		return String.format("%-20s|%-20s|%-20s|%-60s|%-10d|%-20s|%-20d|%-20s", naziv,tipPredstave.toString(),reziser,glumci2,trajanje,produkcija,godina_premijere,opis2);
	}
}
