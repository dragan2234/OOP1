package sistem;

import model.Biletar;
import model.Izvodjenje;
import model.Korisnik;
import model.Menadzer;
import model.Predstava;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import administracija.AdministracijaIzvodjenja;
import administracija.AdministracijaKarte;
import administracija.AdministracijaKorisnika;
import administracija.AdministracijaPredstave;
import administracija.AdministracijaScene;

public class Main {

	public static boolean valjaInt() {
		Scanner sc = new Scanner(System.in);
		try {
			sc.nextInt();
			sc.close();
			return true;
		} catch (Exception e) {
			System.out.println("Los unos!");
			sc.close();
			return false;
		}

	}

	public static void main(String[] args) throws IOException, NumberFormatException, ParseException {
		UcitavanjePodataka data = new UcitavanjePodataka();
		Sistem sistem = new Sistem(data); // sistemske metode
		AdministracijaPredstave admPredstave = new AdministracijaPredstave(data);
		AdministracijaIzvodjenja admIzvodjenje = new AdministracijaIzvodjenja(data);
		AdministracijaKarte admKarta = new AdministracijaKarte(data);
		AdministracijaScene admScena = new AdministracijaScene(data);
		AdministracijaKorisnika admKorisnik = new AdministracijaKorisnika(data);
		while (true) {
			Korisnik ulogovani_korisnik = sistem.logovanje(data.korisnici); // log
			boolean ulogovan = true;
			while (ulogovan) {

				if (ulogovani_korisnik instanceof Biletar) {
					Biletar ulogovani_biletar = (Biletar) ulogovani_korisnik;
					sistem.printLogInWelcomeMessage(ulogovani_korisnik, "biletar");
					int opcija;
					opcija = sistem.printMeniBiletar();
					switch (opcija) {
					case 1:
						Predstava p = admPredstave.nadjiPredstavu();
						if (p == null)
							System.out.println("Nema trazene predstave.");
						else
							admPredstave.punIspisPredstave(p);
						break;
					case 2:
						admPredstave.pretragaPredstava();
						break;
					case 3:
						admIzvodjenje.pretragaPrikazIzvodjenja();
						break;
					case 4:
						admKarta.prodajaKarte(ulogovani_biletar);
						data.updateKarata(ulogovani_biletar);
						data.updateIzvodjenja();
						break;
					case 5:
						admKarta.pretragaKarataPoId();
						break;
					case 6:
						admKarta.prikazSvihKarata();
						break;
					case 7:
						ulogovan = false;
					}
				} else {
					Menadzer ulogovani_menadzer = (Menadzer) ulogovani_korisnik;
					sistem.printLogInWelcomeMessage(ulogovani_korisnik, "menadzer");
					int opcija;
					opcija = sistem.printMeniMenadzer();
					switch (opcija) {
					case 3:
						admPredstave.naslovTabele();
						for (Predstava p : sistem.predstave.values())
							System.out.println(p.tabelaranIspis());
						
						admPredstave.unosPredstave(ulogovani_menadzer);
						data.updatePredstava();
						break;
					case 2:
						admPredstave.pretragaPredstava();
						break;
					case 1:
						Predstava p = admPredstave.nadjiPredstavu();
						if (p == null)
							System.out.println("Nema trazene predstave.");
						else
							admPredstave.punIspisPredstave(p);
						break;
					case 4: // Izmena predstave
						admPredstave.izmenaPredstave();
						data.updatePredstava();
						break;
					case 5:
						// admKarta.pretragaKarataPoId();
						admScena.unosNoveScene();
						data.updateScena();
						break;
					case 6:
						admScena.prikazScena();
						break;
					case 7:
						admKorisnik.unosKorisnika();
						data.updateKorisnici();
						break;
					case 8:
						admKorisnik.izmenaKorisnika();
						data.updateKorisnici();
						break;
					case 9:
						admKorisnik.brisanjeKorisnika();
						data.updateKorisnici();
						break;
					case 10:
						admPredstave.brisanjePredstave();
						data.updatePredstava();
						data.updateIzvodjenja();
						break;
					case 11:
						admScena.brisanjeScene();
						data.updateScena();
						data.updateIzvodjenja();
						break;
					case 12:
						admIzvodjenje.brisanjeIzvodjenja();
						data.updateIzvodjenja();
						break;
					case 13:
						admKarta.pretragaKarataPoId();
						break;
					case 14:
						admIzvodjenje.unosIzvodjenja();
						data.updateIzvodjenja();
						break;
					case 15:
						admKarta.prikazSvihKarata();
						break;
					case 16:
						admIzvodjenje.pretragaPrikazIzvodjenja();
						break;
					case 17:
						admKorisnik.pretragaPrikazKorisnika();
						break;
					case 18:
						ulogovan = false;
					}
				}
			}
		}
	}
}
