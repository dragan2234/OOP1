package model;



import java.util.HashMap;

public class Menadzer extends Korisnik {
	protected HashMap<String,Predstava> dodatePredstave;
    
    public boolean isAktivan(){
   	 return aktivan;
    }
   
    public void setAktivan(boolean a){
   	 this.aktivan=a;
    }
	public Menadzer() {
		// TODO Auto-generated constructor stub
	}
	
	public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String jMBG, HashMap<String, Predstava> dodatePredstave) {
		super(korisnickoIme, lozinka, ime, prezime, jMBG, true);
		this.dodatePredstave = dodatePredstave;

	}
	
	public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String jMBG, Boolean akt) {
		super(korisnickoIme, lozinka, ime, prezime, jMBG, akt);
		this.dodatePredstave = new HashMap<String, Predstava>();

	}
	
	public Menadzer(String korisnickoIme, String lozinka, String ime, String prezime, String jMBG) {
		super(korisnickoIme, lozinka, ime, prezime, jMBG, true);
		dodatePredstave=new HashMap<String, Predstava>();
	}

	public HashMap<String, Predstava> getDodatePredstave() {
		return dodatePredstave;
	}

	public void setDodatePredstave(HashMap<String, Predstava> dodatePredstave) {
		this.dodatePredstave = dodatePredstave;
	}

	public void dodajPredstavu(Predstava p){
		dodatePredstave.put(p.getNaziv(), p);
	}
	
	@Override
	public String toString() {
		String dodate_predstave_ispis="";
		for(String s: dodatePredstave.keySet()){
			dodate_predstave_ispis+=dodatePredstave.get(s).toString()+"\t\t------------------------------";
		}
		return "==============================================\nMenadzer:" + "\n\tusername:\t" + korisnickoIme + "\n\tlozinka:\t"
				+ lozinka + "\n\time:\t\t" + ime + "\n\tprezime:\t" + prezime + "\n\tJMBG:\t\t" + JMBG + "\n\tdodate predstave:\n\t\t"+dodate_predstave_ispis+"\n=============================================="+"\n";
	}
	
	
	
	
}
