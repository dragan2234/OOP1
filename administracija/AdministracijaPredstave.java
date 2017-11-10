package administracija;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Izvodjenje;
import model.Menadzer;
import model.Predstava;
import model.TipPredstave;
import sistem.UcitavanjePodataka;
import sistem.Utility;

public class AdministracijaPredstave {

	UcitavanjePodataka podaci;

	public AdministracijaPredstave(UcitavanjePodataka po) {
		podaci = po;
	}

	public void punIspisPredstave(Predstava p) {
		System.out.println("Naziv predstave: " + p.getNaziv());
		System.out.println("Tip predstave: " + p.getTipPredstave().toString());
		System.out.println("Glumci predstave: " + p.getGlumci());
		System.out.println("Reziser predstave: " + p.getReziser());
		System.out.println("Trajanje predstave: " + p.getTrajanje());
		System.out.println("Produkcija predstave: " + p.getProdukcija());
		System.out.println("Opis predstave: " + p.getOpis());
	}

	public Predstava nadjiPredstavu() { // 1. Menadzer i Biletar
		System.out.println("Unesite naziv predstave: ");
		String naziv = Utility.readText().toUpperCase();
		for (Predstava p : podaci.predstave.values()) {
			if (p.isAktivan() && p.getNaziv().toLowerCase().compareTo(naziv.toLowerCase().trim()) == 0) {
				return p;
			}
		}
		return null;
	}

	public Predstava traziPoNazivu() { // 2. Menadzer i Biletar
		ArrayList<Predstava> prs = new ArrayList<Predstava>();
		System.out.println("Pretrazite: ");
		String rec = Utility.readText();
		String da_ne;
		System.out.println("Da li zelite da sortirate? Da/Ne");
		da_ne = Utility.yesNoQ();
		for (String naziv : podaci.predstave.keySet()) {
			if (naziv.toUpperCase().contains(rec.toUpperCase())) {
				prs.add(podaci.predstave.get(naziv));
			}
		}
		if (da_ne.toLowerCase().compareTo("da") == 0) {

			Collections.sort(prs, sortPoDatumuKraja);
			for (Predstava p : prs) {
				if (p.isAktivan())
					System.out.println(p.tabelaranIspis());
			}
		} else {
			for (Predstava p : prs) {
				if (p.isAktivan())
					System.out.println(p.tabelaranIspis());
			}
		}
		return null;
	}

	public Predstava traziPoGodiniPremijere() { // 2. Menadzer i Biletar
		ArrayList<Predstava> prs = new ArrayList<Predstava>();
		System.out.println("Pretrazite: ");
		int god = Utility.readInt();
		String da_ne;
		System.out.println("Da li zelite da sortirate? Da/Ne");
		da_ne = Utility.yesNoQ();
		for (String naziv : podaci.predstave.keySet()) {
			if (podaci.predstave.get(naziv).getGodina_premijere() == god) {
				prs.add(podaci.predstave.get(naziv));
			}
		}
		if (da_ne.toLowerCase().compareTo("da") == 0) {
			Collections.sort(prs, sortPoGodiniPremijere);
			for (Predstava p : prs) {
				if (p.isAktivan())
					System.out.println(p.tabelaranIspis());
			}
		} else {
			for (Predstava p : prs) {
				if (p.isAktivan())
					System.out.println(p.tabelaranIspis());
			}
		}
		return null;
	}

	public void pretragaPredstava() { // 2. Menadzer i Biletar
		int opcija = -1;
		do {
			System.out.println("Nacin pretrage: ");
			System.out.println("1. Po nazivu");
			System.out.println("2. Po godini premijere");
			opcija = Utility.readInt();
		} while (opcija != 1 && opcija != 2);
		switch (opcija) {
		case 1:
			traziPoNazivu();
			break;
		case 2:
			traziPoGodiniPremijere();
			break;
		}

	}

	public void unosPredstave(Menadzer me) { // 3. Menadzer
		String naziv = "";
		do {
			System.out.println("Unesite naziv predstave: ");
			naziv = Utility.readText().toUpperCase();
		} while (podaci.predstave.keySet().contains(naziv));
		System.out.println("Unesite glumce: ");
		String glumci = Utility.readText();
		System.out.println("Unesite reisera: ");
		String reziser = Utility.readText();
		System.out.println("Unesite produkciju: ");
		String produkcija = Utility.readText();
		System.out.println("Unesite opis: ");
		String opis = Utility.readText();
		System.out.println("Unesite trajanje: ");
		int trajanje = Utility.readInt();
		System.out.println("Unesite godinu premije: ");
		int godina_prem = Utility.readInt();
		String tip;
		do {
			System.out.println("Unesite tip (drama, opera, balet): ");
			tip = Utility.readText();
		} while (tip.toLowerCase().compareTo("drama") != 0 && tip.toLowerCase().compareTo("balet") != 0
				&& tip.toLowerCase().compareTo("opera") != 0);
		Predstava p = new Predstava(naziv, TipPredstave.valueOf(tip.toUpperCase()), reziser, glumci, trajanje,
				produkcija, opis, godina_prem);
		podaci.predstave.put(naziv, p);
		me.getDodatePredstave().put(naziv, p);
	}

	public void izmenaPredstave() {
		Predstava p = nadjiPredstavu();
		if (p != null) {
			String tipP = "";
			do {
				System.out.println("Unesite novi tip predstave (Drama, opera, ili balet?): ");
				tipP = Utility.readLine();
			} while (tipP.toLowerCase().compareTo("drama") != 0 && tipP.toLowerCase().compareTo("balet") != 0
					&& tipP.toLowerCase().compareTo("opera") != 0 && tipP.compareTo("") != 0);
			if (tipP.compareTo("") != 0)
				p.setTipPredstave(TipPredstave.valueOf(tipP.toUpperCase()));
			System.out.println("Unesite novog rezisera: ");
			String r = Utility.readLine();
			if (r.compareTo("") != 0)
				p.setReziser(r);

			System.out.println("Unesite novoe glumce: ");
			String gl = Utility.readLine();
			if (gl.compareTo("") != 0)
				p.setGlumci(gl);

			System.out.println("Unesite novi naziv produkcije: ");
			String pr = Utility.readLine();
			if (pr.compareTo("") != 0)
				p.setProdukcija(pr);

			System.out.println("Unesite novi opis: ");
			String op = Utility.readLine();
			if (op.compareTo("") != 0)
				p.setOpis(op);

			String br = "s";
			if (br.compareTo("") != 0) {
				boolean ok = false;
				do {
					System.out.println("Unesite novu godinu premijere: ");
					br = Utility.readLine();
					if (!br.isEmpty())
						try {
							int a = Integer.parseInt(br);
							ok = true;
						} catch (Exception e) {
							System.out.println("Los unos!");
						}
				} while (!ok);
				p.setGodina_premijere(Integer.parseInt(br));
			}
		}
		else
			System.out.println("Predstava ne postoji");
	}

	public static Comparator<Predstava> sortPoDatumuKraja = new Comparator<Predstava>() {

		@Override
		public int compare(Predstava p1, Predstava p2) {
			String naz1 = p1.getNaziv();
			String naz2 = p2.getNaziv();
			return (naz1.compareTo(naz2));
		}

	};

	public static Comparator<Predstava> sortPoGodiniPremijere = new Comparator<Predstava>() {

		@Override
		public int compare(Predstava p1, Predstava p2) {
			int god1 = p1.getGodina_premijere();
			int god2 = p2.getGodina_premijere();
			if (god1 == god2) {
				return 1;
			}
			return -1;
		}
	};

	public boolean prodateKarte(String p) {
		for (Izvodjenje pr : podaci.izvodjenja.values())
			if (!pr.getProdateKarte().isEmpty() && pr.getPredstava().getNaziv().compareTo(p.toUpperCase()) == 0)
				return true;
		return false;
	}

	public void brisanjePredstave() {
		System.out.println("*Brisanje predstave");
		String naziv = "";
		while (naziv.isEmpty() || !podaci.predstave.containsKey(naziv)) {
			System.out.println("Unesite naziv predstave koji zelite da obrisete: ");
			naziv = Utility.readText();
			if (podaci.predstave.containsKey(naziv.toUpperCase()))
				if (podaci.predstave.get(naziv.toUpperCase()).isAktivan())
					if (prodateKarte(naziv)) {
						System.out.println("Postoje prodate karte za ovu predstavu, brisanje prekinuto.");
						return;
					} else {
						podaci.predstave.get(naziv.toUpperCase()).setAktivan(false);
						for (Izvodjenje i : podaci.izvodjenja.values())
							if ((i.getPredstava().getNaziv().compareTo(naziv.toUpperCase())) == 0)
								i.setAktivan(false);
						System.out.println("Predstava uspesno obrisana!");
						return;
					}
			System.out.println("predstava nije pronadjena u bazi");
			naziv = "";

		}

	}

	public void naslovTabele(){
		System.out.println(	String.format("%-20s|%-20s|%-20s|%-60s|%-10s|%-20s|%-20s|%-20s", "Naziv","Tip predstave","Reziser","glumci","Trajanje","Produkcija","Godina premijere","Opis"));
	}

}
