package model;


import java.util.ArrayList;

public class Scena {
	protected String naziv; // kljuc
	protected String tipTonskogZapisa;
	protected ArrayList<TipPredstave> podrzanePredstave;
	protected ArrayList<Sediste> sedista;
    protected boolean aktivan;
    
    public Scena() {
		// TODO Auto-generated constructor stub
	}
    
    public Scena(String naziv, String tipTonskogZapisa, ArrayList<TipPredstave> podrzanePredstave,
			ArrayList<Sediste> sedista) {
		super();
		this.naziv = naziv;
		this.tipTonskogZapisa = tipTonskogZapisa;
		this.podrzanePredstave = podrzanePredstave;
		this.sedista = sedista;
		this.aktivan = true;

	}
    
    public Scena(String naziv, String tipTonskogZapisa, ArrayList<TipPredstave> podrzanePredstave, int brRed, int brKol) {
		super();
		this.naziv = naziv;
		this.tipTonskogZapisa = tipTonskogZapisa;
		this.podrzanePredstave = podrzanePredstave;
		this.sedista = new ArrayList<Sediste>();
		for (int i = 1; i<=brRed; i++)
			for (int j =1; j<=brKol; j++)
				sedista.add(new Sediste(i,j));
		this.aktivan = true;
	}

	public boolean isAktivan(){
   	 return aktivan;
    }
   
    public void setAktivan(boolean a){
   	 this.aktivan=a;
    }
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTipTonskogZapisa() {
		return tipTonskogZapisa;
	}

	public void setTipTonskogZapisa(String tipTonskogZapisa) {
		this.tipTonskogZapisa = tipTonskogZapisa;
	}

	public ArrayList<TipPredstave> getPodrzanePredstave() {
		return podrzanePredstave;
	}

	public void setPodrzanePredstave(ArrayList<TipPredstave> podrzanePredstave) {
		this.podrzanePredstave = podrzanePredstave;
	}

	public ArrayList<Sediste> getSedista() {
		return sedista;
	}

	public void setSedista(ArrayList<Sediste> sedista) {
		this.sedista = sedista;
	}

	public boolean dodajSediste(Sediste s){
		for (Sediste se : sedista){
			
			if (se.red==s.red || se.broj ==s.broj)
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Scena : naziv: " + naziv + ", tipTonskogZapisa: " + tipTonskogZapisa + ", podrzanePredstave: "
				+ podrzanePredstave + ", sedista: " + sedista + "\n";
	}
	public String zaIspis(){
		return String.format("%20s|%20s|%21s|%s",naziv,tipTonskogZapisa,podrzanePredstave,sedista);
	}
	
	
}
