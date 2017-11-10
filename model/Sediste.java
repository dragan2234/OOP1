package model;



public class Sediste {
	protected int red;
	protected int broj;
    protected boolean aktivan; //Mislim da mi ovde ne treba aktivnost za sediste
    
    public boolean isAktivan(){
   	 return aktivan;
    }
   
    public void setAktivan(boolean a){
   	 this.aktivan=a;
    }
	public Sediste() {
		// TODO Auto-generated constructor stub
	}
	
	public Sediste(int red, int br){
		this.red=red;
		broj=br;
	}
	
	public int getRed(){
		return red;
	}
	
	public void setRed(int r){
		this.red=r;
	}
	public int getKol(){
		return broj;
	}
	
	public void setKol(int r){
		this.broj=r;
	}
	
	@Override
	public String  toString(){
		return red+"/"+broj;
	}
}
