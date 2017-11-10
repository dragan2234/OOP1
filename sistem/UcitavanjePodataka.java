package sistem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

import model.Biletar;
import model.Izvodjenje;
import model.Karta;
import model.Korisnik;
import model.Menadzer;
import model.Predstava;
import model.Scena;
import model.Sediste;
import model.TipPredstave;

public class UcitavanjePodataka {

	public HashMap<String, Korisnik> korisnici;
	public HashMap<String, Predstava> predstave;
	public HashMap<String, Karta> karte;
	public HashMap<String, Scena> scene;
	public HashMap<Integer, Izvodjenje> izvodjenja;
	public BufferedReader bf;
	public PrintWriter pw;

	// ucitavanjeKorisnika se ne koristi nigde
	public void ucitavanjeKorisnika() throws IOException {
		bf = new BufferedReader(new FileReader("./src/korisnici.txt"));
		String linija_fajla;
		while ((linija_fajla = bf.readLine()) != null) {
			String[] podaci = linija_fajla.split("\\|");
			if (podaci[podaci.length - 1].trim().compareTo("menadzer") == 0)
				korisnici.put(podaci[0], new Menadzer(podaci[0], podaci[1], podaci[2], podaci[3], podaci[4],
						Boolean.parseBoolean(podaci[5])));
			else
				korisnici.put(podaci[0], new Biletar(podaci[0], podaci[1], podaci[2], podaci[3], podaci[4],
						Boolean.parseBoolean(podaci[5])));
		}
		bf.close();
	}
	//
	// public void ucitavanjePozorista() throws IOException{
	// bf= new BufferedReader(new FileReader("./src/pozorista.txt"));
	// String linija_fajla;
	// while ((linija_fajla = bf.readLine()) != null) {
	// String[] podaci = linija_fajla.split("\\|");
	// predstave.put(podaci[1], new Predstava(podaci[1],
	// TipPredstave.valueOf(podaci[1]), podaci[1], podaci[1],
	// Integer.parseInt(podaci[1]), podaci[1], podaci[1]));
	// }
	// bf.close();
	// }

	public void dodajKorisnika(String[] podaci) {
		if (podaci[podaci.length - 2].trim().compareTo("menadzer") == 0) {
			korisnici.put(podaci[0], new Menadzer(podaci[0], podaci[1], podaci[2], podaci[3], podaci[4],
					Boolean.parseBoolean(podaci[6])));
		} else {
			korisnici.put(podaci[0], new Biletar(podaci[0], podaci[1], podaci[2], podaci[3], podaci[4],
					Boolean.parseBoolean(podaci[6])));
		}

	}

	public void dodajPredstavu(String[] podaci) {
		Predstava p = new Predstava(podaci[1], TipPredstave.valueOf(podaci[2].toUpperCase()), podaci[3], podaci[4],
				Integer.parseInt(podaci[5]), podaci[6], podaci[7], Integer.parseInt(podaci[8]),
				Boolean.parseBoolean(podaci[9]));
		predstave.put(podaci[1], p);
		((Menadzer) (korisnici.get(podaci[0]))).getDodatePredstave().put(podaci[1], p);
	}

	public void dodajIzvodjenje(String[] podaci) throws NumberFormatException, ParseException {
		ArrayList<Karta> karte_izvodjenja = new ArrayList<Karta>();
		String[] kartice = podaci[5].split(", ");
		int i;
		izvodjenja.put(Integer.parseInt(podaci[0]),
				new Izvodjenje(Integer.parseInt(podaci[0]), Utility.sdf.parse(podaci[1]), Double.parseDouble(podaci[2]),
						predstave.get(podaci[3].toUpperCase()), scene.get(podaci[4]), karte_izvodjenja,
						Boolean.parseBoolean(podaci[6])));
	}

	public Sediste getSedisteScene(int r, int k, Scena c) {
		for (Sediste s : c.getSedista())
			if (s.getRed() == r && s.getKol() == k)
				return s;
		return null;
	}

	public void dodajKartu(String[] podaci) throws NumberFormatException, ParseException {
		// ucitavanje karte iz fajla
		int red = Integer.parseInt(podaci[4].split("/")[0]);
		int kol = Integer.parseInt(podaci[4].split("/")[1]);
		Izvodjenje iz = izvodjenja.get(Integer.parseInt(podaci[3]));
		Sediste s = getSedisteScene(red, kol, iz.getScena());
		Karta k = new Karta(podaci[0], Double.parseDouble(podaci[1]), Integer.parseInt(podaci[2]),
				Utility.sdf.parse(podaci[5]), iz, s, Boolean.parseBoolean(podaci[7]));
		karte.put(k.getSerijskiBroj(), k);
		((Biletar) korisnici.get(podaci[6])).getProdateKarte().put(k.getSerijskiBroj(), k);
		iz.getProdateKarte().add(k);

	}

	public void dodajScenu(String[] podaci) throws IOException {
		// ucitavanje Scene iz fajla u kolekciju
		Scena s = new Scena();
		s.setAktivan(true);
		s.setNaziv(podaci[0]);
		s.setTipTonskogZapisa(podaci[1]);
		ArrayList<Sediste> sedista = new ArrayList<Sediste>();
		int red = Integer.parseInt(podaci[3].split("x")[0]);
		int kol = Integer.parseInt(podaci[3].split("x")[1]);
		for (int i = 1; i <= red; i++)
			for (int j = 1; j <= kol; j++)
				sedista.add(new Sediste(i, j));
		ArrayList<TipPredstave> tipovi = new ArrayList<TipPredstave>();
		for (String st : podaci[2].split(", "))
			tipovi.add(TipPredstave.valueOf(st.toUpperCase()));
		s.setPodrzanePredstave(tipovi);
		s.setSedista(sedista);
		s.setAktivan(Boolean.parseBoolean(podaci[4]));
		scene.put(s.getNaziv(), s);
	}

	public void ucitavanjeUKolekciju(String ime_fajla, String naziv_funkcije)
			throws IOException, NumberFormatException, ParseException {
		bf = new BufferedReader(new FileReader("./src/" + ime_fajla));
		String linija_fajla;
		while ((linija_fajla = bf.readLine()) != null) {
			String[] podaci = linija_fajla.split("\\|");
			// this.getClass().getMethod(naziv_funkcije )
			if (ime_fajla.compareTo("korisnici.txt") == 0)
				dodajKorisnika(podaci);
			else if (ime_fajla.compareTo("predstave.txt") == 0)
				dodajPredstavu(podaci);
			else if (ime_fajla.compareTo("scene.txt") == 0)
				dodajScenu(podaci);
			else if (ime_fajla.compareTo("izvodjenja.txt") == 0)
				dodajIzvodjenje(podaci);
			else if (ime_fajla.compareTo("karte.txt") == 0)
				dodajKartu(podaci);
		}
		bf.close();
	}
	// FUNKCIJA KOJA PRIMA INTERFACE
	// public void ucitavanjeUKolekciju(String ime_fajla, ucitavanjeObjekta uo)
	// throws IOException {
	// bf = new BufferedReader(new FileReader("./src/" + ime_fajla));
	// String linija_fajla;
	// while ((linija_fajla = bf.readLine()) != null) {
	// String[] podaci = linija_fajla.split("\\|");
	// uo.dodaj(podaci);
	// }
	// bf.close();
	// }

	public UcitavanjePodataka() throws IOException, NumberFormatException, ParseException {
		korisnici = new HashMap<String, Korisnik>();
		predstave = new HashMap<String, Predstava>();
		karte = new HashMap<String, Karta>(); // uraditi ucitavanje iz fajla;
		scene = new HashMap<String, Scena>();// uraditi ucitavanje iz fajla;
		izvodjenja = new HashMap<Integer, Izvodjenje>();// uraditi ucitavanje iz
														// fajla;

		ucitavanjeUKolekciju("korisnici.txt", "dodajKorisnika");
		ucitavanjeUKolekciju("predstave.txt", "dodajPredstavu");
		ucitavanjeUKolekciju("scene.txt", "dodajScenu");
		ucitavanjeUKolekciju("izvodjenja.txt", "dodajIzvodjenje");
		ucitavanjeUKolekciju("karte.txt", "dodajKartu");
	}

	public boolean updateKorisnici() throws FileNotFoundException {
		pw = new PrintWriter("./src/korisnici.txt");
		for (Korisnik k : korisnici.values()) {
			pw.write(k.fileOutputString() + "\n");
		}
		pw.close();
		return true;
	}

	public boolean updatePredstava() throws IOException {
		pw = new PrintWriter(new FileWriter("./src/predstave.txt"));
		Menadzer m;
		for (Predstava k : predstave.values()) {

			for (Korisnik kor : korisnici.values()) {
				if (kor instanceof Menadzer) {
					m = (Menadzer) kor;
					if (m.getDodatePredstave().containsKey(k.getNaziv())) {
						pw.write(kor.getKorisnickoIme() + "|" + k.fileOutputString() + "\n");
					}
				}
			}
		}
		pw.close();
		return true;
	}

	public void updateIzvodjenja() throws IOException {
		pw = new PrintWriter(new FileWriter("./src/izvodjenja.txt"));
		String linija, prodate_karte;

		for (Izvodjenje i : izvodjenja.values()) {
			prodate_karte = "";
			for (Karta k : i.getProdateKarte())
				prodate_karte = prodate_karte + k.getSerijskiBroj() + ", ";
			if (!prodate_karte.isEmpty())
				prodate_karte = prodate_karte.substring(0, prodate_karte.length() - 2);
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);

			linija = i.getId() + "|" + Utility.sdf.format(i.getVremePocetka()) + "|" + df.format(i.getCenaKarte()) + "|"
					+ i.getPredstava().getNaziv() + "|" + i.getScena().getNaziv() + "|" + prodate_karte + "|"
					+ i.isAktivan();
			pw.write(linija + "\n");
		}
		pw.close();
	}

	public void updateKarata(Biletar bile) throws IOException {
		pw = new PrintWriter(new FileWriter("./src/karte.txt"));
		for (Korisnik k : korisnici.values())
			if (k instanceof Biletar)
				for (Karta a : ((Biletar) k).getProdateKarte().values())
					pw.write(a.getSerijskiBroj() + "|" + a.getCena() + "|" + a.getPopust() + "|"
							+ a.getIzvodjenje().getId() + "|" + a.getSediste() + "|"
							+ Utility.sdf.format(a.getVremeIzdavanja()) + "|" + bile.getKorisnickoIme() + "|"
							+ a.isAktivan() + "\n");
		pw.close();
	}

	public void updateScena() throws IOException {
		pw = new PrintWriter(new FileWriter("./src/scene.txt"));
		String vrste;
		String sedista = "";
		int red, kol;
		for (Scena s : scene.values()) {
			vrste = "";
			for (TipPredstave tp : s.getPodrzanePredstave())
				vrste = vrste + tp.toString() + ", ";
			vrste = vrste.substring(0, vrste.length() - 2);
			red = s.getSedista().get(s.getSedista().size() - 1).getRed();
			kol = s.getSedista().get(s.getSedista().size() - 1).getKol();
			pw.write(s.getNaziv() + "|" + s.getTipTonskogZapisa() + "|" + vrste + "|" + red + "x" + kol + "|"
					+ s.isAktivan() + "\n");
		}
		pw.close();
	}

	// public void dodaj(String[] podaci) {
	// if (podaci[podaci.length - 1].trim().compareTo("menadzer") == 0)
	// korisnici.put(podaci[0], new Menadzer(podaci[0], podaci[1],
	// podaci[2], podaci[3], podaci[4]));
	// else
	// korisnici.put(podaci[0], new Biletar(podaci[0], podaci[1],
	// podaci[2],podaci[3], podaci[4]));}});
	//
	// ucitavanjeUKolekciju("predstave.txt", new ucitavanjeObjekta() {
	// public void dodaj(String[] podaci) {
	// Predstava p=new Predstava(podaci[1],
	// TipPredstave.valueOf(podaci[2].toUpperCase()), podaci[3], podaci[4],
	// Integer.parseInt(podaci[5]), podaci[6], podaci[7]);
	// predstave.put(podaci[1], p);
	// ((Menadzer)korisnici.get(podaci[0])).getDodatePredstave().put(podaci[1],p);
	// }
	// });

	// }

}
