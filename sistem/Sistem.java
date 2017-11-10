package sistem;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Scanner;

import model.Biletar;
import model.Izvodjenje;
import model.Karta;
import model.Korisnik;
import model.Predstava;
import model.Scena;

public class Sistem implements SistemskeMetode {

	public HashMap<String, Korisnik> korisnici;
	public HashMap<String, Predstava> predstave;
	public HashMap<String, Karta> karte;
	public HashMap<String, Scena> scene;
	public HashMap<Integer, Izvodjenje> izvodjenja;

	public Sistem(UcitavanjePodataka podaci) {
		korisnici = podaci.korisnici;
		predstave = podaci.predstave;
		karte = podaci.karte;
		scene = podaci.scene;
		izvodjenja = podaci.izvodjenja;
	}

	@Override
	public Korisnik logovanje(HashMap<String, Korisnik> korisnici) {
		System.out.println("~ P.O.Z.O.R.I.S.T.E ~\n\n1.Log In:\n");
		Korisnik ulogovani_korisnik = null;
		while (ulogovani_korisnik == null) {
			String username = takeUsername();
			String password = takePassword();
			ulogovani_korisnik = proveraTacnostiLogovanja(username, password);
		}
		return ulogovani_korisnik;
	}

	@Override
	public String takeUsername() {
		System.out.println("-Username: ");
		return Utility.readText();
	}

	@Override
	public String takePassword() {
		System.out.println("-Password: ");
		return Utility.readText();
	}

	@Override
	public Korisnik proveraTacnostiLogovanja(String username, String password) {
		if (korisnici.containsKey(username) && korisnici.get(username).getLozinka().compareTo(password) == 0)
			return korisnici.get(username);
		return null;
	}

	@Override
	public void printLogInWelcomeMessage(Korisnik ulogovani_korisnik, String tip_korisnika) {
		System.out.println("\n*Dobrodosli!*\n============================\nIme:\t\t" + ulogovani_korisnik.getIme()
				+ "\nPrezime:\t" + ulogovani_korisnik.getPrezime() + "\nFunkcija:\t" + tip_korisnika
				+ "\n============================\n");
	}

	public int printMeniBiletar() {
		int opcija = 0;
		do {
			if (opcija != 0)
				System.out.println("Greska!");
			System.out.println("1. Pretraga predstave po nazivu");
			System.out.println("2. Prikaz i sortiranje predstava");
			System.out.println("3. Pretraga i prikaz izvodjenja");
			System.out.println("4. Prodaja karte");
			System.out.println("5. Pronalazenje karte po serijskom broju");
			System.out.println("6. Prikaz svih karata...");
			System.out.println("7. Log out");

			System.out.println("Unesite opciju: ");
			opcija = Utility.readInt();
		} while (opcija < 1 || opcija > 7);
		return opcija;
	}

	public int printMeniMenadzer() {
		int opcija = 0;
		do {
			if (opcija != 0)
				System.out.println("Greska! ");
			System.out.println("1. Pretraga predstave po nazivu");
			System.out.println("2. Pretraga i prikaz predstava");
			System.out.println("3. Unos nove predstave");
			System.out.println("4. Izmena predstave");
			System.out.println("5. Unos nove scene");
			System.out.println("6. Prikaz svih scena");
			System.out.println("7. Unos novog korisnika");
			System.out.println("8. Izmena korisnika");
			System.out.println("9. Brisanje korisnika");
			System.out.println("10. Brisanje predstave");
			System.out.println("11. Brisanje scene");
			System.out.println("12. Brisanje izvodjenja");
			System.out.println("13. Pretraga karte po ID");
			System.out.println("14. Unos novog izvodjenja");
			System.out.println("15. Prikaz svih karata...");
			System.out.println("16. Pretraga izvodjenja po...");
			System.out.println("17. Pretraga i sortiranje korisnika po...");
			System.out.println("18. Log out");
			System.out.println("Unesite opciju: ");
			opcija = Utility.readInt();
		} while (opcija < 0 || opcija > 18);
		return opcija;
	}

}
