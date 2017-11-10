package administracija;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Izvodjenje;
import model.Karta;
import model.Predstava;
import model.Scena;
import model.TipPredstave;
import sistem.UcitavanjePodataka;
import sistem.Utility;

public class AdministracijaScene {
	public UcitavanjePodataka podaci;

	public AdministracijaScene(UcitavanjePodataka po) {
		podaci = po;
	}

	public void unosNoveScene() {
		System.out.println("*Nova scena");
		String naziv = "";
		do {
			System.out.println("\tUnesite naziv scene: ");
			naziv = Utility.readText();
		} while (naziv.compareTo("") == 0 || (podaci.scene.containsKey(naziv) && podaci.scene.get(naziv).isAktivan()));
		String tipTZ = "";
		do {
			System.out.println("\tUnesite tip tonskog zapisa: ");
			tipTZ = Utility.readText();
		} while (tipTZ.compareTo("") == 0);
		System.out.println("\tUnesite tip predstave (drama, balet, opera). Pritisnite x kada zavrsite");
		ArrayList<String> unos = Utility.readStringArray();
		ArrayList<TipPredstave> validan_unos = new ArrayList<TipPredstave>();
		for (String s : unos)
			if ((s.toUpperCase().compareTo("DRAMA") == 0 || s.toUpperCase().compareTo("OPERA") == 0
					|| s.toUpperCase().compareTo("BALET") == 0)
					&& (!validan_unos.contains(TipPredstave.valueOf(s.toUpperCase()))))
				validan_unos.add(TipPredstave.valueOf(s.toUpperCase()));
		int red = 0;
		do {
			System.out.println("\tUnesite broj redova sedista: ");
			red = Utility.readInt();
		} while (red <= 0);
		int kol = 0;
		do {
			System.out.println("\tUnesite broj kolona sedista: ");
			kol = Utility.readInt();
		} while (kol <= 0);

		Scena nova = new Scena(naziv, tipTZ, validan_unos, red, kol);
		podaci.scene.put(nova.getNaziv(), nova);
		System.out.println("*Scena dodata!\n");
	}

	public void prikazScena() { // 13. Menadzer
		System.out.println(
				String.format("%20s|%20s|%21s|%s", "Naziv scene", "Tip tonskog zvona", "Vrste izvodjenja", "Sedista"));
		ArrayList<Scena> sortScene = new ArrayList<Scena>();
		for (Scena s : podaci.scene.values())
			sortScene.add(s);
		Collections.sort(sortScene, sortPoNazivu);
		for (Scena s : sortScene) {
			if (s.isAktivan())
				System.out.println(s.zaIspis());
		}
	}

	public static Comparator<Scena> sortPoNazivu = new Comparator<Scena>() { 

		@Override
		public int compare(Scena p1, Scena p2) {
			String naz1 = p1.getNaziv();
			String naz2 = p2.getNaziv();
			return (naz1.compareTo(naz2));
		}
	};

	public void brisanjeScene() {
		System.out.println("*Brisanje scene");
		String ime = "";

		while (true) {
			System.out.println("Unesite naziv scene za brisanje: ");
			ime = Utility.readText();
			if (podaci.scene.containsKey(ime)) // ako je ime u bazi
				if (podaci.scene.get(ime).isAktivan()) { // i ako je scena sa
															// tim imenom
															// aktivna
					for (Karta k : podaci.karte.values()) { // provera da li
															// postoji karta sa
															// izvodjenjem na
															// toj sceni
						if (k.getIzvodjenje().getScena().getNaziv().compareToIgnoreCase(ime) == 0 && k.isAktivan()) {
							System.out.println(
									"postoji karta za izvodjenje predstave na trazenoj sceni, brisanje prekinuto.");
							return;
						}
					}
					// ako ne postoji, super, onda brisem scenu i sva izvodjenja
					// na toj sceni
					podaci.scene.get(ime).setAktivan(false);
					for (Izvodjenje iz : podaci.izvodjenja.values())
						if (iz.getScena().getNaziv().toLowerCase().compareTo(ime.toLowerCase()) == 0 && iz.isAktivan())
							iz.setAktivan(false);
					System.out.println("Scena uspesno obrisana! (Sa njom i odredjena izvodjenja na toj sceni)");
					return;
				}
			System.out.println("Trazeni naziv ne postoji u bazi.");
		}
	}
}
