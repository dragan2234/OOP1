package administracija;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Biletar;
import model.Izvodjenje;
import model.Karta;
import model.Korisnik;
import model.Menadzer;
import model.Predstava;
import sistem.UcitavanjePodataka;
import sistem.Utility;

public class AdministracijaKorisnika {
	public UcitavanjePodataka podaci;

	public AdministracijaKorisnika(UcitavanjePodataka po) {
		podaci = po;
	}

	public void unosKorisnika() {
		System.out.println("*Unos novog korisnika");
		String username = "";
		String password = "";
		String name = "";
		String last_name = "";
		String jmbg = "";

		do {
			System.out.println("\tUnesite username: ");
			username = Utility.readLine();
		} while (podaci.korisnici.containsKey(username) || username.compareTo("") == 0);
		System.out.println("\tUnesite password: ");
		password = Utility.readText();
		System.out.println("\tUnesite ime: ");
		name = Utility.readText();
		System.out.println("\tUnesite prezime: ");
		last_name = Utility.readText();
		System.out.println("\tUnesite jmbg: ");
		jmbg = Utility.readText();
		System.out.println("\tUnesite funkciju zaposlenog (menadzer/biletar): ");
		String uloga = "";
		do {
			if (!uloga.isEmpty())
				System.out.println("Neispravan unos");
			uloga = Utility.readText();
		} while (uloga.toLowerCase().compareTo("biletar") != 0 && uloga.toLowerCase().compareTo("menadzer") != 0);
		Korisnik k;
		if (uloga.toLowerCase().compareTo("biletar") == 0)
			k = new Biletar(username, password, name, last_name, jmbg);
		else
			k = new Menadzer(username, password, name, last_name, jmbg);
		podaci.korisnici.put(k.getKorisnickoIme(), k);
	}

	public void izmenaKorisnika() {
		for (Korisnik k : podaci.korisnici.values()) {
			System.out.println(k.korisnikIspis());
			System.out.println("Status: " + k.isAktivan());
		}
		System.out.println("*Izmena korisnika");
		String username = "";
		do {
			if (!username.isEmpty())
				System.out.println("Username ne postoji u bazi.");
			System.out.println("Username: ");
			username = Utility.readText();
		} while (!podaci.korisnici.containsKey(username) || (!podaci.korisnici.get(username).isAktivan()));
		Korisnik k = podaci.korisnici.get(username);
		System.out.println("Izmenite zeljene podatke. Za one koje ne zelite da promenite, pritisnite samo enter.");
		System.out.println("Novi password");
		String pas = Utility.readLine();
		if (!pas.isEmpty())
			k.setLozinka(pas);
		System.out.println("Novo ime: ");
		String ime = Utility.readLine();
		if (!ime.isEmpty())
			k.setIme(ime);
		System.out.println("Novo prezime: ");
		String prezime = Utility.readLine();
		if (!prezime.isEmpty())
			k.setPrezime(prezime);
		System.out.println("Novi jmbg (nzm jel ovo logicno da menjam): ");
		String jmbg = Utility.readLine();
		if (!jmbg.isEmpty())
			k.setJMBG(jmbg);
	}

	public void brisanjeKorisnika() {
		System.out.println("*Brisanje korisnika");
		System.out.println("Username korisnika koji se brise: ");
		String us = "";
		do {
			if (!us.isEmpty())
				System.out.println("Trazeni korisnik ne postoji u bazi. Pokusajte ponovo (x za nazad): ");
			us = Utility.readText();
			if (us.toLowerCase().compareTo("x")==0)
				break;
		} while (!podaci.korisnici.containsKey(us));
		podaci.korisnici.get(us).setAktivan(false);
		System.out.println("Korisnik obrisan!");
	}

	public Integer menipretraga() {
		System.out.println("*Pretraga korisnika po:");
		System.out.println("\t1. Korisnickom imenu");
		System.out.println("\t2. Imenu i prezimenu");
		System.out.println("\t3. Nazivu predstave koju je menadžer dodao / za koju je biletar izdao kartu ");
		System.out.println("\t0. Nazad");
		return Utility.readInt();
	}

	public Integer menisortiranje() {
		System.out.println("*Sortiranje korisnika po:");
		System.out.println("\t1. Korisnickom imenu");
		System.out.println("\t2. Imenu i prezimenu");
		System.out.println("\t3. Tipu korisnika ");
		System.out.println("\t0. Nazad");
		return Utility.readInt();
	}

	public void pretragaPrikazKorisnika() {
		int opcija = -1;
		ArrayList<Korisnik> kori = new ArrayList<Korisnik>();
		while (true) {
			do {
				opcija = menipretraga();
			} while (opcija < 0 || opcija > 8);
			switch (opcija) {
			case 0:
				return;
			case 1:
				kori = traziKorIme();
				break;
			case 2:
				kori = traziImePrezime();
				break;
			case 3:
				kori = nazivPredstave();
				break;
			}
			if (kori.size() != 1) {
				do {
					opcija = menisortiranje();
				} while (opcija < 0 || opcija > 3);
				switch (opcija) {
				case 0:
					return;
				case 1:
					ispisPretrage(kori, sortKorIme);
					break;
				case 2:
					ispisPretrage(kori, sortImePrezime);
					break;
				case 3:
					ispisPretrage(kori, sortTipKor);
				}
			} else {
				if (kori.isEmpty()) {
					System.out.println("Nema korisnika sa unetim podacima,");
					break;
				}
				ispisPretrage(kori, sortTipKor);
				if (kori.get(0) instanceof Menadzer) {
					Menadzer kk = (Menadzer) kori.get(0);
					System.out.println("*Predstave (" + kk.getDodatePredstave().size() + ")");
					for (Predstava pr : kk.getDodatePredstave().values())
						System.out.println(pr);
				} else {
					Biletar bibi = (Biletar) kori.get(0);
					System.out.println("*Karte (" + bibi.getProdateKarte().size() + ")");
					for (Karta kar : bibi.getProdateKarte().values())
						System.out.println(kar.ispisKarte());

				}
			}
		}
	}

	public void naslovTabele() {
		System.out.println(
				String.format("%-20s|%-20s|%-20s|%-20s|%-10s", "Korisnicko ime", "Ime", "Prezime", "JMBG", "Uloga"));
	}

	public void ispisPretrage(ArrayList<Korisnik> lista, Comparator<Korisnik> com) {
		naslovTabele();
		Collections.sort(lista, com);
		for (Korisnik i : lista)
			System.out.println(i.korisnikIspis());
		if (lista.isEmpty())
			System.out.println("Ne postoji izvodjenje za unete podatke");

	}

	public ArrayList<Korisnik> traziKorIme() {
		ArrayList<Korisnik> rez = new ArrayList<Korisnik>();
		System.out.println("Korisnicko ime: ");
		String user = Utility.readLine();
		for (Korisnik k : podaci.korisnici.values())
			if (k.getKorisnickoIme().toLowerCase().contains(user.toLowerCase()))
				rez.add(k);
		return rez;
	}

	public ArrayList<Korisnik> traziImePrezime() {
		ArrayList<Korisnik> rez = new ArrayList<Korisnik>();
		System.out.println("Ime: ");
		String ime = Utility.readLine();
		System.out.println("Prezime: ");
		String prezime = Utility.readLine();
		for (Korisnik k : podaci.korisnici.values())
			if (k.getIme().toLowerCase().contains(ime.toLowerCase()) && k.getPrezime().contains(prezime))
				rez.add(k);
		return rez;
	}

	public ArrayList<Korisnik> nazivPredstave() {
		Biletar b;
		Menadzer m;
		ArrayList<Korisnik> rez = new ArrayList<Korisnik>();
		System.out.println("Naziv predstave: ");
		String show = Utility.readLine();
		for (Korisnik k : podaci.korisnici.values())
			if (k instanceof Biletar) {
				b = (Biletar) k;
				for (Karta ka : b.getProdateKarte().values())
					if (ka.getIzvodjenje().getPredstava().getNaziv().toLowerCase().contains(show.toLowerCase()))
						if (!rez.contains(k))
							rez.add(k);
			} else {
				m = (Menadzer) k;
				for (Predstava p : m.getDodatePredstave().values())
					if (p.getNaziv().toLowerCase().contains(show.toLowerCase()))
						rez.add(k);
			}
		return rez;
	}

	public static Comparator<Korisnik> sortKorIme = new Comparator<Korisnik>() {

		@Override
		public int compare(Korisnik p1, Korisnik p2) {
			String naz1 = p1.getKorisnickoIme();
			String naz2 = p2.getKorisnickoIme();
			return (naz1.compareTo(naz2));
		}
	};

	public static Comparator<Korisnik> sortImePrezime = new Comparator<Korisnik>() {

		@Override
		public int compare(Korisnik p1, Korisnik p2) {
			String naz1 = p1.getIme() + p1.getPrezime();
			String naz2 = p2.getIme() + p2.getPrezime();
			return (naz1.compareTo(naz2));
		}
	};
	public static Comparator<Korisnik> sortTipKor = new Comparator<Korisnik>() {

		@Override
		public int compare(Korisnik p1, Korisnik p2) {
			String naz1 = p1.getClass().getName();
			String naz2 = p2.getClass().getName();
			return (naz1.compareTo(naz2));
		}
	};

}
