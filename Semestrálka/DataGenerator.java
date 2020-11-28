import java.io.*;
import java.util.*;

/**
 * Třída generátoru dat
 */
public class DataGenerator {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		File file = new File("file.txt");
		file.delete();
		FileWriter fileWriter = new FileWriter("file.txt",true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);

		/*
		 * Vyzadame od uzivatele potrebne udaje, na kterych zavisi nasledne generovani dat vcetne rozsahu
		 */

		System.out.print("Zadejte počet supermarketů: ");
		while (!sc.hasNextInt()) {
			System.out.print("Prosím, zadejte číslo! -> ");
			sc.next();
		}
		int s = sc.nextInt();

		System.out.print("Zadejte počet továren: ");
		while (!sc.hasNextInt()) {
			System.out.print("Prosím, zadejte číslo! -> ");
			sc.next();
		}
		int d = sc.nextInt();

		System.out.print("Zadejte počet dní: ");
		while (!sc.hasNextInt()) {
			System.out.print("Prosím, zadejte číslo! -> ");
			sc.next();
		}
		int t = sc.nextInt();

		System.out.print("Zadejte počet typů zboží: ");
		while (!sc.hasNextInt()) {
			System.out.print("Prosím, zadejte číslo! -> ");
			sc.next();
		}
		int z = sc.nextInt();

		System.out.print("Zadejte spodní hranici rozsahu generovaných hodnot: ");
		while (!sc.hasNextInt()) {
			System.out.print("Prosím, zadejte číslo! -> ");
			sc.next();
		}
		int min = sc.nextInt();

		System.out.print("Zadejte horní hranici rozsahu generovaných hodnot: ");
		while (!sc.hasNextInt()) {
			System.out.print("Prosím, zadejte číslo! -> ");
			sc.next();
		}
		int max = sc.nextInt();



		if (min >= max || min < 0){
			System.out.println("Minimum nemůže být menší rovno maximu nebo menší než nula.");
		} else if (s <= 0 || d <= 0 || t <= 0 || z <= 0) {
			System.out.println("Hodnoty pro supermarkety, továrny, dni a typy zboží musí být větší než 0!");
		} else {

			//spočítání průmeru z min a max pro Gaussovské rozložení
			double prumer = (double) (min + max) / 2;
			double rozptyl = (double)(max - min) / 2;
			//cena prevozu jednoho zbozi
			int[][] cenaPrevozu = new int[s][d];
			//pocatecni skladove zasoby
			int[][] pocatecniZasoby = new int[z][s];
			//produkce tovaren
			int[][][] produkceTovaren = new int[d][z][t];
			//poptavka zbozi
			int[][][] poptavkaPoZbozi = new int[s][z][t];

			out.println("Tady je úvodní text celého souboru a nějaké povídání k tomu.");

			out.println("#Toto je první matice");
			out.flush();
			vlozDataRandom2(cenaPrevozu, s, d, min, max);
			vypisMatici2(cenaPrevozu, s, d);
			System.out.println();
			out.println();

			out.println("#Toto je druhá matice");
			out.flush();
			vlozDataRandom2(pocatecniZasoby, z, s, min, max);
			vypisMatici2(pocatecniZasoby, z, s);
			System.out.println();
			out.println();

			out.println("#Toto je třetí matice");
			out.flush();
			vlozDataRandom3(produkceTovaren, d, z, t, min, max);
			vypisMatici3(produkceTovaren, d, z, t);
			System.out.println();
			out.println();

			out.println("#Toto je čtvrtá matice");
			out.flush();
			vlozDataRandom3(poptavkaPoZbozi, s, z, t, min, max);
			vypisMatici3(poptavkaPoZbozi, s, z, t);
			System.out.println();
			out.println();

			out.println("#Toto je první Gaussovská matice");
			out.flush();
			vlozDataGauss2(cenaPrevozu, s, d, prumer, rozptyl);
			vypisMatici2(cenaPrevozu, s, d);
			System.out.println();
			out.println();

			out.println("#Toto je druhá Gaussovská matice");
			out.flush();
			vlozDataGauss2(pocatecniZasoby, z, s, prumer, rozptyl);
			vypisMatici2(pocatecniZasoby, z, s);
			System.out.println();
			out.println();

			out.println("#Toto je třetí Gaussovská matice");
			out.flush();
			vlozDataGauss3(produkceTovaren, d, z, t, prumer, rozptyl);
			vypisMatici3(produkceTovaren, d, z, t);
			System.out.println();
			out.println();

			out.println("#Toto je čtvrtá Gaussovská matice");
			out.flush();
			vlozDataGauss3(poptavkaPoZbozi, s, z, t, prumer, rozptyl);
			vypisMatici3(poptavkaPoZbozi, s, z, t);
			System.out.println();
			out.println();

		}
	}

	/**
	 *	Metoda na generování dat do dvourozměrné matice
	 * @param matice	Předpřipravená matice, do které chceme generovat data
	 * @param rada	Počet řád matice
	 * @param sloupec	Počet sloupců matice
	 * @param min	Minimální hodnota generovaných čísel
	 * @param max	Maximální hodnota generovaných čísel
	 */
	public static void vlozDataRandom2(int[][] matice, int rada, int sloupec, int min, int max) {
		Random random = new Random();
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < sloupec; j++){
				matice[i][j] = Math.round(random.nextInt(max - min + 1) + min);
			}
		}
	}

	/**
	 *	Metoda na výpis dvourozměrné matice
	 * @param matice	Předvyplněná matice, ze které chceme vypisovat data
	 * @param rada	Počet řad matice
	 * @param sloupec	Počet sloupců matice
	 */
	public static void vypisMatici2(int[][] matice, int rada, int sloupec) throws IOException {
		FileWriter fileWriter = new FileWriter("file.txt",true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < sloupec; j++) {
				out.print(matice[i][j]+ "\t");
				System.out.print(matice[i][j]+ "\t");
			}
			System.out.println();
			out.println();
		}
		out.close();
	}

	/**
	 * Metoda, která generuje data do třírozměrné matice
	 * @param matice    Předpřipravená matice, do které chceme generovat data
	 * @param rada	Počet řad matice
	 * @param diagonala Počet diagonál matice
	 * @param sloupec	Počet sloupců matice
	 * @param min	Minimální hodnota generovaných čísel
	 * @param max	Maximální hodnota generovaných čísel
	 */
	public static void vlozDataRandom3(int[][][] matice, int rada, int diagonala, int sloupec, int min, int max) {
		Random random = new Random();
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < diagonala; j++){
				for (int k = 0; k < sloupec; k++){
					matice[i][j][k] = random.nextInt(max - min + 1) + min;
				}
			}
		}
	}
	/**
	 * Metoda, která vypisuje data z třírozměrné matice
	 * @param matice    Předpřipravená matice, ze které chceme vypisovat data
	 * @param rada	Počet řad matice
	 * @param diagonala Počet diagonál matice
	 * @param sloupec	Počet sloupců matice
	 */
	public static void vypisMatici3(int[][][] matice, int rada, int diagonala, int sloupec) throws IOException {
		FileWriter fileWriter = new FileWriter("file.txt",true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < diagonala; j++) {
				for (int k = 0; k < sloupec; k++) {
					out.print(matice[i][j][k]+ "\t");
					System.out.print(matice[i][j][k]+ "\t");
				}
				System.out.println();
				out.println();
			}
		}
		out.close();
	}

	///////////////////////// 		GAUSSOVSKÉ ROZLOŽENÍ 		//////////////////////////////

	/**
	 *	Metoda na generování dat do dvourozměrné matice pomocí Gaussovského rozložení
	 * @param matice	Předpřipravená matice, do které chceme generovat data
	 * @param rada	Počet řád matice
	 * @param sloupec	Počet sloupců matice
	 * @param prumer	Hodnota čísel okolo které chceme generovat
	 * @param rozptyl	Hodnota, která údává jak velký chceme rozptyl od @prumer
	 */
	public static void vlozDataGauss2(int[][] matice, int rada, int sloupec, double prumer, double rozptyl) {
		Random random = new Random();
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < sloupec; j++){
				int val = (int) Math.round((random.nextGaussian() * rozptyl + prumer));
				matice[i][j] = val;
				if (val < 0){
					j--;
				}
			}
		}
	}

	/**
	 *	Metoda na generování dat do třírozměrné matice pomocí Gaussovského rozložení
	 * @param matice	Předpřipravená matice, do které chceme generovat data
	 * @param rada	Počet řád matice
	 * @param diagonala Počet diagonál matice
	 * @param sloupec	Počet sloupců matice
	 * @param prumer	Hodnota čísel okolo které chceme generovat
	 * @param rozptyl	Hodnota, která údává jak velký chceme rozptyl od @prumer
	 */
	public static void vlozDataGauss3(int[][][] matice, int rada, int diagonala, int sloupec, double prumer, double rozptyl) {
		Random random = new Random();
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < diagonala; j++){
				for (int k = 0; k < sloupec; k++){
					int val = (int) Math.round((random.nextGaussian() * rozptyl + prumer));
					matice[i][j][k] = val;
					if (val < 0){
						k--;
					}
				}
			}
		}
	}

	/////////////////////////////	EXTREMÁLNÍ ROZDĚLENÍ	/////////////////////////////////////////
	//////////////	Našel jsem pouze cizí metody, na vlastní implementaci si netroufnu	/////////////









}
