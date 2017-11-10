package sistem;

import model.Korisnik;
import java.util.HashMap;
import java.util.Scanner;

public interface SistemskeMetode {
	
	public Korisnik logovanje(HashMap<String,Korisnik> korisnici);
	public String takeUsername();
	public String takePassword();
	public Korisnik proveraTacnostiLogovanja(String username, String password);
	public void printLogInWelcomeMessage(Korisnik ulogovani_korisnik, String tip_korisnika);
}
