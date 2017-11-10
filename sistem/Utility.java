package sistem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.TipPredstave;

public class Utility {

	private static Scanner sc = new Scanner(System.in);
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	
	public static String readText() {
		boolean notRead = true;
		String tekst = "";
		while (notRead) {
			tekst = sc.nextLine();
			if (!tekst.equals("")) {
				notRead = false;
			} else {
				System.out.println("\tNe mozete ostaviti prazno.Ponovo:");
			}
		}
		return tekst;
	}

	public static String readLine() {
		String tekst = sc.nextLine();
		return tekst;
	}

	public static Integer readInt() {
		int ceoBroj = 0;
		boolean notRead = true;

		do {
			if (sc.hasNextInt()) {
				ceoBroj = sc.nextInt();
				notRead = false;
				sc.nextLine();
			} else {
				if (sc.hasNextLine()){
					if (!sc.nextLine().isEmpty()) {
						System.out.println("Greska, pokusajte ponovo!");
					}
				}
				
			}
		} while (notRead);
		return ceoBroj;
	}

	public static Double readDouble() {
		double doubleBroj = 0;
		boolean notRead = true;

		do {
			if (sc.hasNextDouble()) {
				doubleBroj = sc.nextDouble();
				notRead = false;
				sc.nextLine();
			} else {
				if (!sc.nextLine().isEmpty()) {
					System.out.println("Greska, pokusajte ponovo!");
				}
			}
		} while (notRead);
		return doubleBroj;
	}

	public static float readFloat() {
		float floatBroj = 0;
		boolean notRead = true;

		do {
			if (sc.hasNextFloat()) {
				floatBroj = sc.nextFloat();
				notRead = false;
				sc.nextLine();
			} else {
				if (!sc.nextLine().isEmpty()) {
					System.out.println("Greska, pokusajte ponovo!");
				}
			}
		} while (notRead);
		return floatBroj;
	}

	public static String yesNoQ() {
		String da_ne = "";
		boolean fail = true;
		while (fail) {
			String read = sc.nextLine();
			if (read.toLowerCase().equals("da")) {
				da_ne = "da";
				fail = false;
			} else if (read.toLowerCase().equals("ne")) {
				da_ne = "ne";
				fail = false;
			} else {
				System.out.println("Mora se uneti da ili ne");
			}
		}
		return da_ne;
	}

	public static Date ReadDate() {
		
		boolean notRead = true;
		Date date = null;
		while (notRead == true) {
			try {
				date = sdf.parse(sc.nextLine());
				notRead= false;
			} catch (ParseException e) {
				notRead=true;
				System.out.println("Greska pri unosu datuma. Pokusajte ponovo.");
			}
		}
		
		return date;
	}
	
	public static ArrayList<String> readStringArray(){
		boolean gotovo = false;
		ArrayList<String> lista = new ArrayList<String>();
		while (gotovo == false){
			System.out.println("Unosite podatke, kada zavrsite unos pritisnite x");
			String podatak = readText().trim();
			if (podatak.toLowerCase().compareTo("x")==0){
				gotovo = true;
			}else{
				lista.add(podatak);
			}			
		}return lista;
	}


}
