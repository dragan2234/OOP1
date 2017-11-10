package administracija;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import model.Izvodjenje;
import model.Karta;
import model.Menadzer;
import model.Predstava;
import model.Scena;
import model.TipPredstave;
import sistem.UcitavanjePodataka;
import sistem.Utility;

public class AdministracijaIzvodjenja {
	public UcitavanjePodataka podaci;

	public AdministracijaIzvodjenja(UcitavanjePodataka po) {
		podaci = po;
	}

	public int menipretraga() {
		System.out.println("*Pretraga izvodjenja po:");
		System.out.println("\t1. Nazivu predstave");
		System.out.println("\t2. Tipu predstave ");
		System.out.println("\t3. Godini premijere predstave ");
		System.out.println("\t4. Režiseru predstave ");
		System.out.println("\t5. Glumcima koji uèestvuju u predstavi ");
		System.out.println("\t6. Nazivu, režiseru i glumcima predstave ");
		System.out.println("\t7. Vremenu poèetka, pri èemu se unosi poèetno i krajnje vreme ");
		System.out.println("\t8. Nazivu scene ");
		System.out.println("\t0. Nazad");
		System.out.println("\tUnesite broj: ");
		return Utility.readInt();
	}

	public int meniSortiranje() {
		System.out.println("*Sortiranje po: ");
		System.out.println("\t1. nazivu predstave ");
		System.out.println("\t2. vremenu poèetka ");
		System.out.println("\t3. nazivu predstave i vremenu poèetka");
		System.out.println("\t4. tipu predstave ");
		System.out.println("\t5. godini premijere ");
		System.out.println("\t6. tipu predstave i godini premijere ");
		System.out.println("\t7. nazivu scene ");
		System.out.println("\t0. Nazad");
		System.out.println("\tUnesite broj: ");
		return Utility.readInt();
	}

	public void pretragaPrikazIzvodjenja() { // 5. tacka, zavrsiti
		int opcija = -1;

		// pretrage
		ArrayList<Izvodjenje> odabrana_izvodjenja = new ArrayList<Izvodjenje>();
		while (true) {
			do {
				opcija = menipretraga();
			} while (opcija < 0 || opcija > 8);
			switch (opcija) {
			case 0:
				return;
			case 1:
				odabrana_izvodjenja = imaPoNazivu();
				break;
			case 2:
				odabrana_izvodjenja = imaPoTipu();
				break;
			case 3:
				odabrana_izvodjenja = imaPoGodini();
				break;
			case 4:
				odabrana_izvodjenja = imaPoRezi();
				break;
			case 5:
				odabrana_izvodjenja = imaPoGlumcu();
				break;
			case 6:
				odabrana_izvodjenja = pretragaPoNazivuReziseruGlumcima();
				break;
			case 7:
				odabrana_izvodjenja = pretragaPoVremenuPocetka();
				break;
			case 8:
				odabrana_izvodjenja = imaPoSceni();
				break;
			}
			// sortiranja
			do {
				opcija = meniSortiranje();
			} while (opcija < 0 || opcija > 8);
			switch (opcija) {
			case 0:
				return;
			case 1:
				ispisPretrage(odabrana_izvodjenja, sortNazivPredstave);
				break;
			case 2:
				ispisPretrage(odabrana_izvodjenja, sortVremePocetka);
				break;
			case 3:
				ispisPretrage(odabrana_izvodjenja, sortNazivPredstaveVremePocetka);
				break;
			case 4:
				ispisPretrage(odabrana_izvodjenja, sortTipPredstave);
				break;
			case 5:
				ispisPretrage(odabrana_izvodjenja, sortGodinaPremijere);
				break;
			case 6:
				ispisPretrage(odabrana_izvodjenja, sortTipPredstaveGodinapremijere);
				break;
			case 7:
				ispisPretrage(odabrana_izvodjenja, sortNazivScene);
				break;
			}

		}

	}

	public ArrayList<Izvodjenje> pretragaPoNazivuReziseruGlumcima() {
		ArrayList<Izvodjenje> iz1 = imaPoNazivu();
		ArrayList<Izvodjenje> iz2 = imaPoRezi();
		ArrayList<Izvodjenje> iz3 = imaPoGlumcu();
		ArrayList<Izvodjenje> rez = new ArrayList<Izvodjenje>();
		for (Izvodjenje i : podaci.izvodjenja.values())
			if (iz1.contains(i) && iz2.contains(i) && iz3.contains(i))
				rez.add(i);
		return rez;
	}

	public ArrayList<Izvodjenje> pretragaPoVremenuPocetka() {

		System.out.println("Unesite donju vremensku granicu: ");
		Date t1 = Utility.ReadDate();
		System.out.println("Unesite gornju vremensku granicu: ");
		Date t2 = Utility.ReadDate();
		ArrayList<Izvodjenje> izv = new ArrayList<Izvodjenje>();
		for (Izvodjenje iz : podaci.izvodjenja.values())
			if (iz.getVremePocetka().after(t1) && iz.getVremePocetka().before(t2))
				izv.add(iz);
		// ispisPretrage(izv);
		return izv;
	}

	public boolean vremeSepreklapa(Calendar poc1, Calendar kraj1, Calendar poc2, Calendar kraj2) {

		if (poc2.getTime().after(kraj1.getTime()) || poc1.getTime().after(kraj2.getTime()))
			return false;
		return true;
	}

	// public boolean mozeDaSeUbaci(Predstava p, Date vremeNovog){
	// if (vremeOk(vremeNovog, p.getTrajanje()))
	// if ()
	// }

	public void unosIzvodjenja() {
		System.out.println("*Unos izvodjenja");
		System.out.println("==========================");
		System.out.println("*Dostupne predstave");
		for (Predstava p : podaci.predstave.values()) {
			if (p.isAktivan())
				System.out.println(p);
		}
		System.out.println("==========================");
		System.out.println("*Dostupne scene");
		for (Scena p : podaci.scene.values()) {
			if (p.isAktivan())
				System.out.println(p);
		}
		System.out.println("==========================");
		String nazivP = "";
		Predstava pred;
		while (true) {
			System.out.println("Unesite naziv predstave koja se izvodi: ");
			nazivP = Utility.readText().toUpperCase();
			if (podaci.predstave.containsKey(nazivP)) {
				if (podaci.predstave.get(nazivP).isAktivan()) {
					pred = podaci.predstave.get(nazivP);
					break;
				}
			}
			System.out.println("Ne postoji predstava sa unetim nazivom.");
		}
		String nazivS = "";
		Scena sc;
		while (true) {
			System.out.println("Unesite naziv scene na kojoj se predstava izvodi: ");
			nazivS = Utility.readText().toUpperCase();
			if (podaci.scene.containsKey(nazivS)) {
				if (podaci.scene.get(nazivS).isAktivan()) {
					sc = podaci.scene.get(nazivS);
					break;
				}
			}
			System.out.println("Ne postoji scena sa unetim nazivom.");
		}
		int id = podaci.izvodjenja.size() + 1;
		float cena = -1;

		while (cena < 0) {
			System.out.println("Unesite cenu karte za izvodjenje: ");
			cena = Utility.readFloat();
			if (cena < 0)
				System.out.println("Greska pri unosu cene - ne moze biti negativna.");
		}
		Date d = new Date();
		boolean moze = false;
		boolean prvi = true;
		Calendar poc2 = Calendar.getInstance();
		Calendar poc1 = Calendar.getInstance();
		Calendar kraj1 = Calendar.getInstance();
		Calendar kraj2 = Calendar.getInstance();

		while (!moze) {
			if (prvi) {
				prvi = false;
				System.out.println(
						"Za izabranu scenu i predstavu postoji izvodjenje u slicno vreme ili postoji vec izvodjenje na istoj sceni u to vreme. Izaberite neko drugo vreme.");
			}
			System.out.println("Unesite vreme izvodjenja predstave ");
			d = Utility.ReadDate();
			poc2.setTime(d);
			kraj2.setTime(d);
			kraj2.add(Calendar.MINUTE, pred.getTrajanje());
			System.out.println("Pocetak novog: " + Utility.sdf.format(poc2.getTime()));
			System.out.println("kraj novog: " + Utility.sdf.format(kraj2.getTime()));
			for (Izvodjenje i : podaci.izvodjenja.values()) {
				if (i.isAktivan()) {
					poc1.setTime(i.getVremePocetka());
					kraj1.setTime(i.getVremePocetka());
					kraj1.add(Calendar.MINUTE, i.getPredstava().getTrajanje());
				
					if (vremeSepreklapa(poc1, kraj1, poc2, kraj2)) {
						System.out.println("vreme se preklapa sa: " + i.getId());
						System.out.println("Pocetak starog: " + Utility.sdf.format(poc1.getTime()));
						System.out.println("kraj starog: " + Utility.sdf.format(kraj1.getTime()));
						
						if (i.getPredstava().getNaziv().compareTo(pred.getNaziv()) == 0) {
							System.out.println("takodje je i ista predstava");
							moze = false;
							break;
						} else {
							if (sc.getNaziv().compareTo(i.getScena().getNaziv()) == 0) {
								System.out.println(
										"Nije ista predstava ali je u isto vreme na istoj sceni sa: " + i.getId());
								moze = false;
								break;
							} else {
								moze = true;
							}
						}
					} else
						moze = true;
				}
				// za svako izvodjenje
				// ako se preklapa vreme ne sme da bude izvodjenje iste
				// predstave
				// ako je izvodjenje iste predstave moze = false
				// else ako je na istoj sceni moze = false
				// else moze = true
				// ako se ne preklapa vreme moze = true
			}
		}
		Izvodjenje i = new Izvodjenje(id, d, cena, podaci.predstave.get(nazivP), podaci.scene.get(nazivS),
				new ArrayList<Karta>());
		podaci.izvodjenja.put(i.getId(), i);
	}

	public String tabelarnoIzvodjenje(Izvodjenje i) {
		return String.format("%-4d|%-22s|%-8.2f|%-20s|%-20s|%-15s|%-20d", i.getId(),
				Utility.sdf.format(i.getVremePocetka()), i.getCenaKarte(), i.getPredstava().getNaziv(),
				i.getScena().getNaziv(), i.getPredstava().getTipPredstave().toString(), i.getProdateKarte().size());
	}

	public void naslovTabele() {
		System.out.println(String.format("%-4s|%-22s|%-8s|%-20s|%-20s|%-15s|%-20s", "ID", "Vreme pocetka", "Cena",
				"Naziv predstave", "Naziv scene", "Tip predstave", "broj prodatih karata"));
	}

	public void ispisPretrage(ArrayList<Izvodjenje> lista, Comparator<Izvodjenje> com) {
		naslovTabele();
		Collections.sort(lista, com);
		for (Izvodjenje i : lista)
			System.out.println(tabelarnoIzvodjenje(i));
		if (lista.isEmpty())
			System.out.println("Ne postoji izvodjenje za unete podatke");

	}

	public ArrayList<Izvodjenje> imaPoNazivu() {
		System.out.println("Naziv predstave: ");
		String naziv = Utility.readLine();
		ArrayList<Izvodjenje> rezultat = new ArrayList<Izvodjenje>();
		for (Izvodjenje i : podaci.izvodjenja.values())
			if ((i.getPredstava().getNaziv().toLowerCase().contains(naziv.toLowerCase()) || naziv.isEmpty())
					&& i.isAktivan()) {
				rezultat.add(i);
			}
		return rezultat;
	}

	public ArrayList<Izvodjenje> imaPoTipu() {
		String naziv = "";
		ArrayList<Izvodjenje> rezultat = new ArrayList<Izvodjenje>();
		do {
			if (!naziv.isEmpty())
				System.out.println("Greska u unosu (Unesite Balet, opera ili drama).");
			System.out.println("Tip predstave: ");
			naziv = Utility.readLine();
		} while (naziv.compareToIgnoreCase("DRAMA") != 0 && naziv.compareToIgnoreCase("OPERA") != 0
				&& naziv.compareToIgnoreCase("BALET") != 0 && !naziv.isEmpty());

		for (Izvodjenje i : podaci.izvodjenja.values())
			if ((i.getPredstava().getTipPredstave().toString().compareToIgnoreCase(naziv) == 0 || naziv.isEmpty())
					&& i.isAktivan())
				rezultat.add(i);
		return rezultat;
	}

	public ArrayList<Izvodjenje> imaPoGodini() {
		ArrayList<Izvodjenje> rezultat = new ArrayList<Izvodjenje>();
		System.out.println("Unesite godinu premijere: ");
		Integer godina = Utility.readInt();
		for (Izvodjenje i : podaci.izvodjenja.values())
			if (i.getPredstava().getGodina_premijere() == godina && i.isAktivan())
				rezultat.add(i);
		return rezultat;

	}

	public ArrayList<Izvodjenje> imaPoRezi() {
		ArrayList<Izvodjenje> rezultat = new ArrayList<Izvodjenje>();
		System.out.println("Unesite rezisera: ");
		String rez = Utility.readLine();
		for (Izvodjenje i : podaci.izvodjenja.values())
			if ((i.getPredstava().getReziser().toLowerCase().contains(rez.toLowerCase()) || rez.isEmpty())
					&& i.isAktivan())
				rezultat.add(i);
		return rezultat;
	}

	public ArrayList<Izvodjenje> imaPoGlumcu() {
		String[] glumci;
		ArrayList<Izvodjenje> rezultat = new ArrayList<Izvodjenje>();
		System.out.println("Unesite glumca: ");
		String glumac = Utility.readLine();
		for (Izvodjenje i : podaci.izvodjenja.values()) {
			glumci = i.getPredstava().getGlumci().split(", ");
			if (i.isAktivan())
				for (String s : glumci)
					if (s.compareToIgnoreCase(glumac) == 0 || glumac.isEmpty())
						rezultat.add(i);
		}
		return rezultat;
	}

	public ArrayList<Izvodjenje> imaPoSceni() {
		ArrayList<Izvodjenje> rezultat = new ArrayList<Izvodjenje>();
		System.out.println("Naziv scene: ");
		String naziv = Utility.readLine();
		for (Izvodjenje i : podaci.izvodjenja.values())
			if ((i.getScena().getNaziv().toLowerCase().contains(naziv.toLowerCase()) || naziv.isEmpty())
					&& i.isAktivan())
				rezultat.add(i);
		return rezultat;
	}

	//////////////// pretrage
	public void brisanjeIzvodjenja() {
		System.out.println("*Brisanje izvodjenja");
		int id = -1;
		while (!podaci.izvodjenja.containsKey(id)) {
			System.out.println("Unesite id izvodjenja za brisanje: ");
			id = Utility.readInt();
			if (podaci.izvodjenja.containsKey(id))// ako postoji izvodjenje
				if (podaci.izvodjenja.get(id).isAktivan()) {
					for (Karta k : podaci.karte.values())
						if (k.getIzvodjenje().getId() == id && k.getIzvodjenje().isAktivan()) {
							System.out.println("Postoji karta za dato izvodjenje, brisanje prekinuto.");
							return;
						}
					podaci.izvodjenja.get(id).setAktivan(false);
					System.out.println("Izvodjenje obrisano!");
				}
			System.out.println("Izvodjenje sa unetim id je vec obrisano");
		}
		System.out.println("Ne postoji izvodjenje sa unetim id");
	}

	// Komparatori
	// a
	public static Comparator<Izvodjenje> sortNazivPredstave = new Comparator<Izvodjenje>() {

		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			String naz1 = p1.getPredstava().getNaziv();
			String naz2 = p2.getPredstava().getNaziv();
			return (naz1.compareTo(naz2));
		}
	};

	// g
	public static Comparator<Izvodjenje> sortNazivScene = new Comparator<Izvodjenje>() {

		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			return (AdministracijaScene.sortPoNazivu.compare(p1.getScena(), p2.getScena()));
		}
	};

	// b
	public static Comparator<Izvodjenje> sortVremePocetka = new Comparator<Izvodjenje>() {
		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			Date t1 = p1.getVremePocetka();
			Date t2 = p2.getVremePocetka();
			if (t1.after(t2))
				return 1;
			else
				return -1;
		}
	};
	// e
	public static Comparator<Izvodjenje> sortGodinaPremijere = new Comparator<Izvodjenje>() {
		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			return (AdministracijaPredstave.sortPoGodiniPremijere.compare(p1.getPredstava(), p2.getPredstava()));
		}
	};

	// d

	public static Comparator<Izvodjenje> sortTipPredstave = new Comparator<Izvodjenje>() {
		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			return (p1.getPredstava().getTipPredstave().toString()
					.compareToIgnoreCase(p2.getPredstava().getTipPredstave().toString()));
		}
	};

	// f tip predstave i godina premijere

	public static Comparator<Izvodjenje> sortTipPredstaveGodinapremijere = new Comparator<Izvodjenje>() {
		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			if (sortTipPredstave.compare(p1, p2) == 0)
				return (AdministracijaPredstave.sortPoGodiniPremijere.compare(p1.getPredstava(), p2.getPredstava()));
			return (sortTipPredstave.compare(p1, p2));
		}
	};

	// c naziv predstave i vreme pocetka

	public static Comparator<Izvodjenje> sortNazivPredstaveVremePocetka = new Comparator<Izvodjenje>() {
		@Override
		public int compare(Izvodjenje p1, Izvodjenje p2) {
			if (sortNazivPredstave.compare(p1, p2) == 0)
				return (p1.getVremePocetka().after(p2.getVremePocetka()) ? 1 : -1);
			return (sortNazivPredstave.compare(p1, p2));
		}
	};
}
