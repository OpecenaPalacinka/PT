import java.io.*;
import java.util.*;

/**
 * T��da gener�toru dat
 * Ukazkova trida, ktera ukazuje obe moznosti generace
 * @author Muhr - Pelik�n
 */
public class DataGenerator {
	/**
	 * Nacteni scanneru
	 */
	public static Scanner sc = new Scanner(System.in);
	/**
	 * Vytvo�en� souboru
	 */
	public static File file = new File("data_generated.txt");
	/**
	 * Vytvoreni FileWriteru
	 */
	public static FileWriter fileWriter;
	/**
	 * Vytvoreni BufferedWriteru
	 */
	public static BufferedWriter bw;
	/**
	 * Vytvoreni PrintWriteru
	 */
	public static PrintWriter out;
	/**
	 * Vytvoreni Randomu
	 */
	public static Random random;

	/**
	 * Uvodni string
	 */
	public static final String uvod = "# Data pro semestralni praci KIV/PT 2020/2021\r\n" +
			"# Vytvoreno systemem \"Muhr && Pelik�n\", 31.8.2020.\r\n" +
			"#\r\n" +
			"# prazdne radky nebo radky zacinajici znakem # se ingoruji\r\n" +
			"# v opacnem pripade jsou na kazde radce ciselne hodnoty oddelene mezerou\r\n" +
			"# data jsou popsana vzdy nad prislusnym blokem dat, bloky jsou oddelene\r\n" +
			"# prazdnym radkem nasledujici radkem viditelne oznacenym vyrazem \"BLOK:\"\r\n";

	/**
	 * String se seznamem hodnot
	 */
	public static final String poctyZakladu = "# BLOK: pocet tovaren D, pocet supermarketu S, pocet druhu zbozi Z, pocet dni T";

	/**
	 * String s cenou prevozu
	 */
	public static final String cenaPrevozuText = "# BLOK: Cena prevozu jednoho zbozi c_{s,d}\r\n" +
			"#\r\n" +
			"# c_{1,1} c_{2,1} ... c_{S,1}\r\n" +
			"# c_{1,2} c_{2,2} ... c_{S,2}\r\n" +
			"#    .      .    .      .\r\n" +
			"#    .      .     .     .\r\n" +
			"#    .      .      .    .\r\n" +
			"# c_{1,D} c_{2,D} ... c_{S,D}";

	/**
	 * String s pocatecnimi zasobami
	 */
	public static final String pocatecniZasobyText = "# BLOK: Pocatecni skladove zasoby q_{z,s}\r\n" +
			"#\r\n" +
			"# q_{1,1} q_{1,2} ... q_{1,S}\r\n" +
			"# q_{2,1} q_{2,2} ... q_{2,S}\r\n" +
			"#    .      .    .      .\r\n" +
			"#    .      .     .     .\r\n" +
			"#    .      .      .    .\r\n" +
			"# q_{Z,1} q_{Z,2} ... q_{Z,S}";

	/**
	 * String s prokukci tovaren
	 */
	public static final String produkceTovarenText = "# BLOK: Produkce tovaren p_{d,z,t}\r\n" +
			"# p_{1,1,1} p_{2,1,1} .... p_{D,1,1}\r\n" +
			"# p_{1,1,2} p_{2,1,2} .... p_{D,1,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"# p_{1,1,T} p_{2,1,T} .... p_{D,1,T}\r\n" +
			"# p_{1,2,1} p_{2,2,1} .... p_{D,2,1}\r\n" +
			"# p_{1,2,2} p_{2,2,2} .... p_{D,2,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"#      .         .       .      .\r\n" +
			"# p_{1,Z,T} p_{2,Z,T} .... p_{D,Z,T}";

	/**
	 * String s poptavkou
	 */
	public static final String poptavkaText = "# BLOK: Poptavka zbozi r_{s,z,t}\r\n" +
			"# r_{1,1,1} r_{2,1,1} .... r_{S,1,1}\r\n" +
			"# r_{1,1,2} r_{2,1,2} .... r_{S,1,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"# r_{1,1,T} r_{2,1,T} .... r_{S,1,T}\r\n" +
			"# r_{1,2,1} r_{2,2,1} .... r_{S,2,1}\r\n" +
			"# r_{1,2,2} r_{2,2,2} .... r_{S,2,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"#      .         .       .      .\r\n" +
			"# r_{1,Z,T} r_{2,Z,T} .... r_{S,Z,T}";

	/**
	 * String s cenou prevozu gaussove
	 */
	public static final String cenaPrevozuGauss = "# GAUSSOVSK� BLOK: Cena prevozu jednoho zbozi c_{s,d}\r\n" +
			"#\r\n" +
			"# c_{1,1} c_{2,1} ... c_{S,1}\r\n" +
			"# c_{1,2} c_{2,2} ... c_{S,2}\r\n" +
			"#    .      .    .      .\r\n" +
			"#    .      .     .     .\r\n" +
			"#    .      .      .    .\r\n" +
			"# c_{1,D} c_{2,D} ... c_{S,D}";

	/**
	 * String s pocatecnimi zasobami gaussove
	 */
	public static final String pocatecniZasobyGauss = "# GAUSSOVSK� BLOK: Pocatecni skladove zasoby q_{z,s}\r\n" +
			"#\r\n" +
			"# q_{1,1} q_{1,2} ... q_{1,S}\r\n" +
			"# q_{2,1} q_{2,2} ... q_{2,S}\r\n" +
			"#    .      .    .      .\r\n" +
			"#    .      .     .     .\r\n" +
			"#    .      .      .    .\r\n" +
			"# q_{Z,1} q_{Z,2} ... q_{Z,S}";

	/**
	 * String s produkci tovaren gaussove
	 */
	public static final String produkceTovarenGauss = "# GAUSSOVSK� BLOK: Produkce tovaren p_{d,z,t}\r\n" +
			"# p_{1,1,1} p_{2,1,1} .... p_{D,1,1}\r\n" +
			"# p_{1,1,2} p_{2,1,2} .... p_{D,1,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"# p_{1,1,T} p_{2,1,T} .... p_{D,1,T}\r\n" +
			"# p_{1,2,1} p_{2,2,1} .... p_{D,2,1}\r\n" +
			"# p_{1,2,2} p_{2,2,2} .... p_{D,2,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"#      .         .       .      .\r\n" +
			"# p_{1,Z,T} p_{2,Z,T} .... p_{D,Z,T}";

	/**
	 * String s poptavkou gausove
	 */
	public static final String poptavkaGauss = "# GAUSSOVSK� BLOK: Poptavka zbozi r_{s,z,t}\r\n" +
			"# r_{1,1,1} r_{2,1,1} .... r_{S,1,1}\r\n" +
			"# r_{1,1,2} r_{2,1,2} .... r_{S,1,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"# r_{1,1,T} r_{2,1,T} .... r_{S,1,T}\r\n" +
			"# r_{1,2,1} r_{2,2,1} .... r_{S,2,1}\r\n" +
			"# r_{1,2,2} r_{2,2,2} .... r_{S,2,2}\r\n" +
			"#      .         .    .         .\r\n" +
			"#      .         .     .        .\r\n" +
			"#      .         .      .       .\r\n" +
			"#      .         .       .      .\r\n" +
			"# r_{1,Z,T} r_{2,Z,T} .... r_{S,Z,T}";

    /**
     * Hlavni generacni trida, ktera vyfeneruje cely soubor
     * @param d Pocet tovaren
     * @param s Pocet supermarketu
     * @param z Pocet typu zbozi
     * @param t Pocet dni
     * @param min minimalni hranice
     * @param max maximalni hranice
     * @return Vraci -1 kdy� je spatne minimun, -2 kdyz jsou spatne hodnoty a 0 kdyz je vse v poradku
     * @throws IOException
     */
	public static int generuj(int d, int s, int z, int t, int min, int max) throws IOException {

		file.delete();
		fileWriter = new FileWriter("data_generated.txt",true);
		bw = new BufferedWriter(fileWriter);
		out = new PrintWriter(bw);

		if (min >= max || min < 0){
			System.out.println("Minimum nem��e b�t men�� rovno maximu nebo men�� ne� nula.");
			return -1;
		} else if (s <= 0 || d <= 0 || t <= 0 || z <= 0) {
			System.out.println("Hodnoty pro supermarkety, tov�rny, dni a typy zbo�� mus� b�t v�t�� ne� 0!");
			return -2;
		} else {

			//spo��t�n� pr�meru z min a max pro Gaussovsk� rozlo�en�
			double prumer = (double) (min + max) / 2;
			double rozptyl = (double)(max - min) / 2;
			//cena prevozu jednoho zbozi
			int[][] cenaPrevozu = new int[d][s];
			//pocatecni skladove zasoby
			int[][] pocatecniZasoby = new int[z][s];
			//produkce tovaren
			int[][][] produkceTovaren = new int[z][t][d];
			//poptavka zbozi
			int[][][] poptavkaPoZbozi = new int[z][t][s];

			out.println(uvod);
			out.println(poctyZakladu);
			out.println(d+" "+s+" "+z+" "+t+"\r\n");

			out.println(cenaPrevozuText);
			out.flush();
			vlozDataRandom2(cenaPrevozu, d, s, min, max);
			vypisMatici2(cenaPrevozu, d, s);
			out.println();

			out.println(pocatecniZasobyText);
			out.flush();
			vlozDataRandom2(pocatecniZasoby, z, s, min, max);
			vypisMatici2(pocatecniZasoby, z, s);
			out.println();

			out.println(produkceTovarenText);
			out.flush();
			vlozDataRandom3(produkceTovaren, z, t, d, min, max);
			vypisMatici3(produkceTovaren, z, t, d);
			out.println();

			out.println(poptavkaText);
			out.flush();
			vlozDataRandom3(poptavkaPoZbozi, z, t, s, min, max);
			vypisMatici3(poptavkaPoZbozi, z, t, s);
			out.println();

			out.println(cenaPrevozuGauss);
			out.flush();
			vlozDataGauss2(cenaPrevozu, d, s, prumer, rozptyl);
			vypisMatici2(cenaPrevozu, d, s);
			out.println();

			out.println(pocatecniZasobyGauss);
			out.flush();
			vlozDataGauss2(pocatecniZasoby, z, s, prumer, rozptyl);
			vypisMatici2(pocatecniZasoby, z, s);
			out.println();

			out.println(produkceTovarenGauss);
			out.flush();
			vlozDataGauss3(produkceTovaren, z, t, d, prumer, rozptyl);
			vypisMatici3(produkceTovaren, z, t, d);
			out.println();

			out.println(poptavkaGauss);
			out.flush();
			vlozDataGauss3(poptavkaPoZbozi, z, t, s, prumer, rozptyl);
			vypisMatici3(poptavkaPoZbozi, z, t, s);
			out.close();
			bw.close();
			fileWriter.close();

			System.out.println("Byl vygenerovan soubor data_generated.txt");
			return 0;
		}
	}

	/**
	 *	Metoda na generov�n� dat do dvourozm�rn� matice
	 * @param matice	P�edp�ipraven� matice, do kter� chceme generovat data
	 * @param rada	Po�et ��d matice
	 * @param sloupec	Po�et sloupc� matice
	 * @param min	Minim�ln� hodnota generovan�ch ��sel
	 * @param max	Maxim�ln� hodnota generovan�ch ��sel
	 */
	public static void vlozDataRandom2(int[][] matice, int rada, int sloupec, int min, int max) {
		random = new Random();
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < sloupec; j++){
				matice[i][j] = Math.round(random.nextInt(max - min + 1) + min);
			}
		}
	}

	/**
	 *	Metoda na v�pis dvourozm�rn� matice
	 * @param matice	P�edvypln�n� matice, ze kter� chceme vypisovat data
	 * @param rada	Po�et �ad matice
	 * @param sloupec	Po�et sloupc� matice
	 */
	public static void vypisMatici2(int[][] matice, int rada, int sloupec) throws IOException {
		FileWriter fileWriter = new FileWriter("data_generated.txt",true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < sloupec; j++) {
				out.print(matice[i][j]+ " ");
			}
			out.println();
		}
		out.close();
		bw.close();
		fileWriter.close();
	}

	/**
	 * Metoda, kter� generuje data do t��rozm�rn� matice
	 * @param matice    P�edp�ipraven� matice, do kter� chceme generovat data
	 * @param rada	Po�et �ad matice
	 * @param diagonala Po�et diagon�l matice
	 * @param sloupec	Po�et sloupc� matice
	 * @param min	Minim�ln� hodnota generovan�ch ��sel
	 * @param max	Maxim�ln� hodnota generovan�ch ��sel
	 */
	public static void vlozDataRandom3(int[][][] matice, int rada, int diagonala, int sloupec, int min, int max) {
		random = new Random();
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < diagonala; j++){
				for (int k = 0; k < sloupec; k++){
					matice[i][j][k] = random.nextInt(max - min + 1) + min;
				}
			}
		}
	}
	/**
	 * Metoda, kter� vypisuje data z t��rozm�rn� matice
	 * @param matice    P�edp�ipraven� matice, ze kter� chceme vypisovat data
	 * @param rada	Po�et �ad matice
	 * @param diagonala Po�et diagon�l matice
	 * @param sloupec	Po�et sloupc� matice
	 */
	public static void vypisMatici3(int[][][] matice, int rada, int diagonala, int sloupec) throws IOException {
		FileWriter fileWriter = new FileWriter("data_generated.txt",true);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		PrintWriter out = new PrintWriter(bw);
		for (int i = 0; i < rada; i++) {
			for (int j = 0; j < diagonala; j++) {
				for (int k = 0; k < sloupec; k++) {
					out.print(matice[i][j][k]+ " ");
				}
				out.println();
			}
		}
		out.close();
		bw.close();
		fileWriter.close();
	}

	///////////////////////// 		GAUSSOVSK� ROZLO�EN� 		//////////////////////////////

	/**
	 *	Metoda na generov�n� dat do dvourozm�rn� matice pomoc� Gaussovsk�ho rozlo�en�
	 * @param matice	P�edp�ipraven� matice, do kter� chceme generovat data
	 * @param rada	Po�et ��d matice
	 * @param sloupec	Po�et sloupc� matice
	 * @param prumer	Hodnota ��sel okolo kter� chceme generovat
	 * @param rozptyl	Hodnota, kter� �d�v� jak velk� chceme rozptyl od @prumer
	 */
	public static  void vlozDataGauss2(int[][] matice, int rada, int sloupec, double prumer, double rozptyl) {
		random = new Random();
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
	 *	Metoda na generov�n� dat do t��rozm�rn� matice pomoc� Gaussovsk�ho rozlo�en�
	 * @param matice	P�edp�ipraven� matice, do kter� chceme generovat data
	 * @param rada	Po�et ��d matice
	 * @param diagonala Po�et diagon�l matice
	 * @param sloupec	Po�et sloupc� matice
	 * @param prumer	Hodnota ��sel okolo kter� chceme generovat
	 * @param rozptyl	Hodnota, kter� �d�v� jak velk� chceme rozptyl od @prumer
	 */
	public static void vlozDataGauss3(int[][][] matice, int rada, int diagonala, int sloupec, double prumer, double rozptyl) {
		random = new Random();
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

}
