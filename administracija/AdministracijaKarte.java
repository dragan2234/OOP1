package administracija;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.Utilities;

import model.Biletar;
import model.Izvodjenje;
import model.Karta;
import model.Predstava;
import model.Scena;
import model.Sediste;
import sistem.UcitavanjePodataka;
import sistem.Utility;

public class AdministracijaKarte {

	UcitavanjePodataka podaci;

	public AdministracijaKarte(UcitavanjePodataka up) {
		podaci = up;
	}

	public void pretragaKarataPoId() { // 7
		String ids = "";
		int id;
		while (ids.toLowerCase().compareTo("x") != 0) {
			System.out.println("Unesite id karte: ");
			id = Utility.readInt();
			Karta k = podaci.karte.get(id + "");
			if (k != null && k.isAktivan()) {
				System.out.println(String.format("%20s|%20s|%20s|%6s|%6s|%22s|%5s|", "Naziv predstave", "Vreme pocetka",
						"Naziv scene", "Cena", "Popust", "Vreme izdavanja karte", "ID"));
				System.out.println(k.ispisKarte());
			} else
				System.out.println("Nije nadjena karta");
			System.out.println("x - izlaz\nbilo koji taster - nastavi pretragu");
			ids = Utility.readText();
		}
	}

	public boolean imaMesta(Izvodjenje i) {
		for (Sediste s : i.getScena().getSedista()) {
			boolean slobodno = true;
			for (String se : podaci.karte.keySet())
				if (s.equals(podaci.karte.get(se)))
					slobodno = false;
			if (slobodno)
				return true;
		}
		return false;
	}

	public boolean slobodnoSediste(int r, int ko, Izvodjenje i) {
		System.out.println("prodate karte: " + i.getProdateKarte());
		for (Karta k : i.getProdateKarte()) {
			System.out.println(k.getSediste());
			if (ko == k.getSediste().getKol() && r == k.getSediste().getRed())
				return false;
		}
		return true;
	}

	public Sediste getSedisteScene(int r, int k, Scena c) {
		for (Sediste s : c.getSedista())
			if (s.getRed() == r && s.getKol() == k)
				return s;
		return null;
	}

	public HashMap<Integer, Izvodjenje> izvodjenjaZaProdaju() {
		Date d = new Date(); // danas;
		HashMap<Integer, Izvodjenje> izvodjenja_prodaja = new HashMap<Integer, Izvodjenje>();
		for (int i : podaci.izvodjenja.keySet()) {
			if (podaci.izvodjenja.get(i).getVremePocetka().after(d) && imaMesta(podaci.izvodjenja.get(i))
					&& podaci.izvodjenja.get(i).isAktivan()) {
				izvodjenja_prodaja.put(i, podaci.izvodjenja.get(i));
			}
		}
		return izvodjenja_prodaja;
	}

	void printIzvodjenja(HashMap<Integer, Izvodjenje> i) {
		for (Integer in : i.keySet()) {
			System.out.println(i.get(in));
		}

	}

	public String dodeliSerijskiBrojKarti() {
		System.out.println("===========================================================");
		for (Karta k : podaci.karte.values()) {
			System.out.println(k);
		}
		System.out.println("===========================================================");
		if (podaci.karte.isEmpty())
			return "1";
		return (podaci.karte.size() + 1) + "";
	}

	public void obracunajPopust(Izvodjenje i, int p) {
		i.setCenaKarte(i.getCenaKarte() * (1 - p / 100));
	}

	public void prodajaKarte(Biletar bile) {
		HashMap<Integer, Izvodjenje> izvodjenja = izvodjenjaZaProdaju();
		int id;
		do {
			printIzvodjenja(izvodjenja);
			System.out.println("\nUnesite ID izvodjenja");
			id = Utility.readInt();
		} while (!izvodjenja.containsKey(id));
		Scena sala = izvodjenja.get(id).getScena();
		int red, kol;
		do {
			System.out.println("Slobodna sedista:");
			for (Sediste s : sala.getSedista()) {
				System.out.print(s + " ");
			}

			System.out.println("\n\n\tUnesite red u kojem zelite da sedite : ");

			red = Utility.readInt();

			System.out.println("\tUnesite kolonu u kojoj zelite da sedite : ");
			kol = Utility.readInt();
		} while ((sala.getSedista().get(sala.getSedista().size() - 1)).getRed() < red || red < 1 || kol < 1
				|| kol > (sala.getSedista().get(sala.getSedista().size() - 1)).getKol()
				|| !slobodnoSediste(red, kol, izvodjenja.get(id)));
		Sediste s = getSedisteScene(red, kol, sala); // odabrano sediste;
		int popust = 0;
		do {
			if (popust < 0)
				System.out.println("Greska!");
			System.out.println("Popust (u procentima) : ");
			popust = Utility.readInt();
		} while (popust < 0);

		obracunajPopust(izvodjenja.get(id), popust);
		Karta k = new Karta(dodeliSerijskiBrojKarti(), izvodjenja.get(id).getCenaKarte(), popust, new Date(),
				izvodjenja.get(id), s);
		izvodjenja.get(id).getProdateKarte().add(k);
		bile.getProdateKarte().put(k.getSerijskiBroj(), k);
		podaci.karte.put(k.getSerijskiBroj(), k);
		System.out.println("Karta prodata!");
	}

	public void prikazSvihKarata() {
		int opcija = -1;
		while (opcija != 0) {
			System.out.println("Sortirati po:");
			System.out.println("\t1. Vremenu izdavanja");
			System.out.println("\t2. Popustu");
			System.out.println("\t3. Nazivu predstave, vremenu pocetka izvodjenja, vremenu izdavanja");
			System.out.println("\t0. Nazad");
			System.out.println("Unesite opciju");
			opcija = Utility.readInt();
			ArrayList<Karta> kartee = new ArrayList<Karta>();
			for (Karta ka : podaci.karte.values())
				kartee.add(ka);
			switch (opcija) {
			case 1:
				Collections.sort(kartee, sortPoVremenuIzdavanja);
				break;
			case 2:
				Collections.sort(kartee, sortPoPopustu);
				break;
			case 3:
				Collections.sort(kartee, nazivVremeVreme);
				break;
			case 0:
				return;
			}
			System.out.println(String.format("%20s|%20s|%20s|%6s|%6s|%22s|%5s|%-20s", "Naziv predstave",
					"Vreme pocetka", "Naziv scene", "Cena", "Popust", "Vreme izdavanja karte", "ID", "Vreme pocetka"));
			for (Karta k : kartee)
				if (k.isAktivan())
					System.out.println(k.ispisKarte() + Utility.sdf.format(k.getIzvodjenje().getVremePocetka()));

		}
	}

	public static Comparator<Karta> sortPoVremenuIzdavanja = new Comparator<Karta>() {
		@Override
		public int compare(Karta p1, Karta p2) {
			Date t1 = p1.getVremeIzdavanja();
			Date t2 = p2.getVremeIzdavanja();
			if (t1.after(t2))
				return 1;
			else
				return -1;
		}
	};

	public static Comparator<Karta> sortPoPopustu = new Comparator<Karta>() {

		@Override
		public int compare(Karta p1, Karta p2) {
			int po1 = p1.getPopust();
			int po2 = p2.getPopust();
			return (po1 > po2 ? 1 : -1);
		}
	};

	public static Comparator<Karta> nazivVremeVreme = new Comparator<Karta>() {

		@Override
		public int compare(Karta k1, Karta k2) {
			String naz1 = k1.getIzvodjenje().getPredstava().getNaziv();
			String naz2 = k2.getIzvodjenje().getPredstava().getNaziv();
			Date pocIzv1 = k1.getIzvodjenje().getVremePocetka();
			Date pocIzv2 = k2.getIzvodjenje().getVremePocetka();
			Date izdavanje1 = k1.getVremeIzdavanja();
			Date izdavanje2 = k2.getVremeIzdavanja();
			if (naz1.compareTo(naz2) == 0) {
				if (pocIzv1.after(pocIzv2))
					return 1;
				else if (pocIzv1.before(pocIzv2))
					return -1;
				else {
					if (izdavanje1.after(izdavanje2))
						return 1;
					else if (izdavanje1.before(izdavanje2))
						return -1;
					else
						return 0;
				}
			} else
				return naz1.compareTo(naz2);
		}
	};

}
