import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;

/**
 * Hlavn� funkce programu, na��t� data ze souboru a rozd�luje je do pol�
 * @author Muhr - Pelik�n
 */
public class Simulace {
	
	/**
	 * P�eod v�stupu
	 */
	private static PrintStream console;
	
	/**
	 * Na�ten� Scanneru
	 */
	public static Scanner sc;
	/**
	 * Globalni Counter
	 */
	private static int c;
	/**
	 * Pocet Tovaren
	 */
	private static int d = 0;
	/**
	 * Pocet Supermarketu
	 */
	private static int s = 0;
	/**
	 * Pocet Typu Zbozi
	 */
	private static int z = 0;
	/**
	 * Pocet Dni
	 */
	private static int t = 0;
	/**
	 * Finalni cena
	 */
	public static double cena = 0;
	/**
	 * Maximalni zadany pocet dni pro simulaci
	 * 0 nebo vetsi cislo jak dostupny pocet dni = vsechny dny
	 */
	private static int pocetDni = 0;

	/**
	 * Jestli ma simulace reseni
	 */
	public static boolean maReseni = true;
	
	/**
	 * Den konce simulace
	 */
	private static int denKonce;
	/**
	 * Konec supermarketu
	 */
	private static int supermarketKonec;
	/**
	 * Pocet zbozi na konci
	 */
	private static int pocetZboziKonec;

	/**
	 * File pro statistiky
	 */
	public static File vypisFile = new File("statistiky.txt");
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
	 * Vypis pro statistiky zasob
	 */
	private static StringBuffer vypisZasobZbozi = new StringBuffer();
	/**
	 * Vypis pro statistiky tovaren
	 */
	private static StringBuffer vypisTovaren = new StringBuffer();

	/**
	 * Matice ceny p�evozu
	 */
	private static int[][] cena_provozu;
	/**
	 * Matice po��te�n�ch z�sob
	 */
	private static int[][] pocatecni_zasoby;
	/**
	 * Matice produkce tov�ren
	 */
	private static int[][][] produkce_tovaren;
	/**
	 * Matice popt�vky zbo��
	 */
	private static int[][][] poptavka_zbozi;

	/**
	 * ArrayList pro prvn� data (matice ceny p�evozu)
	 */
	private static List data1 = Collections.synchronizedList(new ArrayList<Integer>());
	/**
	 * ArrayList pro druh� data (matice po��te�n�ch z�sob)
	 */
	private static List data2 = Collections.synchronizedList(new ArrayList<Integer>());
	/**
	 * ArrayList pro t�et� data (matice produkce tov�ren)
	 */
	private static List data3 = Collections.synchronizedList(new ArrayList<Integer>());
	/**
	 * ArrayList pro �tvrt� data (matice popt�vky zbo��)
	 */
	private static List data4 = Collections.synchronizedList(new ArrayList<Integer>());

	/**
	 * Na�te data do "sc" ze vstupn�ho souboru
	 * @param file N�zev souboru, kter� chceme ��st
	 */
	public static void nacteniFilu(File file){
		// -------------------------
		//   Nacteni filu
		// -------------------------


		try {
//			System.out.println("------------Nacitani Souboru---------------\n");
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Soubor nebyl nalezen");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Nacte data a ulozi je do promenych a poli
	 */
	public static void nactiData(int maxPocetDni) {

		c = 0; // counter
		pocetDni = maxPocetDni;
		
		// -------------------------
		// Cteni filu a ukladani hodnot
		// -------------------------
		
		while(sc.hasNext()) {
			String st = sc.nextLine();
			while (st.indexOf('#') == 0 || st.isEmpty()) {
				if (sc.hasNext()) {
					if (st.isEmpty() && c<5) {
						c++;
//						System.out.println("--------- " + c + ". informace ----------");
					}
					st = sc.nextLine();					
				}
				else {
					st = "";
					break;
				}
			}
			if (c == 1) { // Prvni informace
				String[] parts = st.split(" ", 4);
				d = Integer.parseInt(parts[0].trim());
				s = Integer.parseInt(parts[1].trim());
				z = Integer.parseInt(parts[2].trim());
				t = Integer.parseInt(parts[3].trim());
				
				if (t<maxPocetDni) { // Kdyz je zadan vetsi pocet dni nez simulace nabizi, provedou se tak vsechny dostupne dny
					pocetDni = 0;
				}
			}
			else if (c == 2) { // Druha informace
				String[] parts = st.split(" ", s);
				for (String part : parts) {
					data1.add(Integer.parseInt(part.trim()));
				}
			}
			else if (c == 3) { // Treti informace
				String[] parts = st.split(" ", s);
				for (String part : parts) {
					data2.add(Integer.parseInt(part.trim()));
				}
			}
			else if (c == 4) { // Ctvrta informace
				String[] parts = st.split(" ", d);
				for (String part : parts) {
					data3.add(Integer.parseInt(part.trim()));
				}
			}
			else if (c == 5) { // Pata informace
				if (st.equals("")) {
					break;
				}
				String[] parts = st.split(" ", s);
				for (String part : parts) {
					data4.add(Integer.parseInt(part.trim()));
				}
			}
		}
	}
	
	/**
	 * Zpracuje pole z prvostnich ArrayListu do dvou a tri rozmernych poli
	 * a vypise je
	 */
	public static void zpracujHodnoty() {
		// -------------------------
		// Zpracovani data do poli
		// -------------------------
		
		c = 0;
		
		cena_provozu = new int[s][d];

		//P�ed�n� dat do spr�vn�ho pole (matice) -- cena_provozu d,s -- cena p�evozu jednoho kusu libovoln�ho zbo�� z tov�rny D do supermarketu S
		naplnMatici2(cena_provozu, data1);

		pocatecni_zasoby = new int[z][s];

		//P�ed�n� dat do spr�vn�ho pole (matice) -- pocatecni_zasoby z,s -- po��te�n� z�soby v�robku Z v supermarketu S
		naplnMatici2(pocatecni_zasoby, data2);

		produkce_tovaren = new int[d][z][t];

		//P�ed�n� dat do spr�vn�ho pole (matice) -- produkce_tovaren d,z,t -- produkce tov�rnou D druhu zbo�� Z v den T
		naplnMatici3(produkce_tovaren, data3);
		c = 0;
        
        poptavka_zbozi = new int[s][z][t];

		//P�ed�n� dat do spr�vn�ho pole (matice) -- poptavka_zbozi s,z,t --  popt�vka z�kazn�k� po zbo�� Z v supermarketu S dne T
		naplnMatici3(poptavka_zbozi, data4);

		// -------------------------
		// Vypis dat
		// -------------------------
        
		System.out.println("\n------Informace po zpracovani----\n");
		System.out.println("D / Pocet tovaren = " + d);
		System.out.println("S / Pocet supermarketu = " + s);
		System.out.println("Z / Pocet druhu zbozi = " + z);
		System.out.println("T / Pocet dni = " + t);
		System.out.println("\nCena prevozu jednoho zbozi (hodnoty v poli)\n" + data1);
		System.out.println("\nPocatecni skladove zasoby (hodnoty v poli)\n" + data2);
		System.out.println("\nProdukce tovaren (hodnoty v poli)\n" + data3);
		System.out.println("\nPoptavka zbozi (hodnoty v poli)\n" + data4);
		
		data1.clear();
		data2.clear();
		data3.clear();
		data4.clear();
	}

	private static void naplnMatici2(int[][] matice, List<Integer> data) {
		for (int i = 0; i < matice.length; i++) {
			for (int j = 0; j < matice[i].length; j++) {
				matice[i][j] = data.get(c);
				c++;
			}
		}
		c = 0;
	}

	private static void naplnMatici3(int[][][] matice, List<Integer> data) {
		for(int i = 0; i< matice.length ; i++){
			for(int j = 0; j< matice[i].length ; j++){
				for(int k = 0; k< matice[i][j].length ; k++){
					matice[i][j][k] = data.get(c);
					c++;
				}
			}
		}
	}

	/**
	 * Spusti se simulace a vypocita se finalni cena
	 * @return vraci celkovou cenu
	 */
	public static double simulace() {
		//------------------------
		//      Simulace 
		//------------------------
		
		System.out.println("\n------  Simulace  ------");
		vypisZasobZbozi.append("Z�soby supermarket� jsou re�ln� jen prvn� den, proto�e nek�e�kujeme.\r\n");
		
		double celkova_cena = 0; // Celkova cena
		
		maReseni=true;
		
		
		for (int a = 0; a<t;a++) { //Cyklus dn�
			
			if (a>pocetDni-1 && pocetDni != 0) {
				return celkova_cena;
			}
			
			if (!maReseni) {break;}
			
			System.out.println("\n-----" + (a+1) + ". Den-----");
			
			for (int x = 0; x<s; x++) {  // Cyklus Supermarket�
				if (!maReseni) {break;}
				System.out.println("\n----" + (x+1) + ". Supermarket----");
				for (int y = 0; y<z;y++) {  // Cyklus zbozi
					System.out.println("---" + (y+1) + ". Zbozi---");
					
					
					int nutne_zbozi = pocatecni_zasoby[y][x] - poptavka_zbozi[x][y][a]; // potrebne zbozi pro splneni poptavky

					vypisZasobZbozi.append(x+1).append(". supermarket m� ").append(pocatecni_zasoby[y][x]).append(" z�sobu/y ").append(y+1).append(". zbo�� ").append("v den ").append(a+1).append("\r\n");
					
					pocatecni_zasoby[y][x] = 0; // Vypotrebujeme zasoby
					
					System.out.println("Pro " + (x+1) + ". supermarket je nutno sehnat " + Math.abs(nutne_zbozi) + " zbozi cislo " + (y+1));
					
					// zjisteji nejlevnejsi tovarny pro supermarket
					
					ArrayList<Integer> Pouzite_tovarny = new ArrayList<Integer>(); // uchovava indexy pouzitych tovaren
					
					int temp = Integer.MAX_VALUE;
					int nejm_cena = 0;
					int cislo_tovarny = 0;
					for (int i = 0; i<d;i++) {

						if (cena_provozu[x][i]<temp) {
							nejm_cena = cena_provozu[x][i];	
							cislo_tovarny = i;
							temp = cena_provozu[x][i];
						}
					}

					Pouzite_tovarny.add(cislo_tovarny); //Ulozeni indexu pouzite tovarny
					int prebytek = 0;
					// Po nalezeni nejlevnejsi tovarny si z ni vezmeme tolik zbozi, kolik potrebujeme
					nutne_zbozi += produkce_tovaren[cislo_tovarny][y][a];
					int prevozZbozi = Math.min(produkce_tovaren[cislo_tovarny][y][a], poptavka_zbozi[x][y][a]);

					vypisTovaren.append("Tov�rna ��slo ").append(cislo_tovarny+1).append(" dovezla zbo�� do supermarketu ��slo ").append(x+1).append(" a p�evezla ").append(prevozZbozi).append(" zbo�� typu ").append(y+1).append(" v den ").append(a+1).append(".\r\n");

					if (nutne_zbozi>=0) {  // Kontrola, jestli nam stacili zasoby
						System.out.println("Zasoby z tovarny stacili pro splneni poptavky");
						prebytek = nutne_zbozi;

					}
					
					celkova_cena += nejm_cena * produkce_tovaren[cislo_tovarny][y][a] - prebytek; // pripocte cenu
					
					System.out.println("Nejlevnejsi cena je " + nejm_cena + "$ v tovarne cislo " + (cislo_tovarny+1));
					System.out.println("Celkova cena je zatim: " + celkova_cena);
					
					while (nutne_zbozi < 0) {
						System.out.println("Jeste je potreba sehnat: " + Math.abs(nutne_zbozi) + " zbozi cislo " + (y+1));
						
						temp = Integer.MAX_VALUE;
						nejm_cena = 0;
						cislo_tovarny = 0;
						for (int i = 0; i<d;i++) {

							if (cena_provozu[x][i]<temp && !Pouzite_tovarny.contains(i)) {
								nejm_cena = cena_provozu[x][i];	
								cislo_tovarny = i;
								temp = cena_provozu[x][i];
								System.out.println("Nalezena dalsi dostupna nejlevnejsi tovarna cislo " + (cislo_tovarny+1));
								System.out.println("Cena cesty z teto tovarny je " + nejm_cena + "$");
							}
						}
						if (nejm_cena==0) {
							System.out.println("\nNenalezena dalsi tovarna, a zbozi doslo, nutno dokoupit z Ciny");
							denKonce = a;
							supermarketKonec = x;
							pocetZboziKonec = Math.abs(nutne_zbozi);
							maReseni = false;
							break;
						}
						
						Pouzite_tovarny.add(cislo_tovarny); //Ulozeni indexu pouzite tovarny
						prebytek = 0;
						// Po nalezeni nejlevnejsi tovarny si z ni vezmeme tolik zbozi, kolik potrebujeme
						nutne_zbozi += produkce_tovaren[cislo_tovarny][y][a]; 
						if (nutne_zbozi>=0) {  // Kontrola, jestli nam stacili zasoby
							System.out.println("Zasoby z tovarny stacili pro splneni poptavky");
							prebytek = nutne_zbozi;

							System.out.println("Prebytek je: " + prebytek);
						}
						
						celkova_cena+=nejm_cena*(produkce_tovaren[cislo_tovarny][y][a]-prebytek);
						
						System.out.println("Celkova cena je zatim: " + celkova_cena);
						
					}
					
					Pouzite_tovarny.clear();

				}
			}
		}
		return celkova_cena;
	}
	
	/**
	 * Hlavni spusteni a beh simulace
	 * @param dny  Pocet dni behu simulace
	 * @param file Soubor se vstupem
	 * @throws IOException
	 */
	public static void spustSimulaci(int dny, File file) throws IOException {
		
		try {
			console = new PrintStream(new File("simulace.txt"));
			System.setOut(console);
	
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		long timeInMilis = System.currentTimeMillis();

		nacteniFilu(file);
		nactiData(dny);
		
		zpracujHodnoty();
		
		cena = simulace();

		long timeInMilisEnd = System.currentTimeMillis();
		long cas = (timeInMilisEnd-timeInMilis)/1000;

		System.out.println();
		vypisFile.delete();
        fileWriter = new FileWriter("statistiky.txt",true);
		bw = new BufferedWriter(fileWriter);
		out = new PrintWriter(bw);

		out.append(vypisZasobZbozi.toString()).append("\r\n");

		out.append(vypisTovaren.toString()).append("\r\n");

		if (!maReseni) {
			out.append("�loha nem� �e�en�, ").append(String.valueOf(supermarketKonec + 1)).append(". supermarket nebylo mo�n� uz�sobit. Stalo se tak ").append(String.valueOf(denKonce + 1)).append(". dne a chyb�lo ").append(String.valueOf(pocetZboziKonec)).append(" zbo��.").append("\r\n\r\n");
		}
		out.append("Celkov� �as b�hu simulace je: ").append(String.valueOf(cas)).append(" sekund.").append("\r\n\r\n");
		out.append("Celkova cena je: ").append(String.valueOf(cena)).append("\r\n");
		out.close();
		
		System.out.println("-----------------------------------");
		if (!maReseni) {
			System.out.println("Simulace nema reseni bez nakupu z Ciny");
			System.out.println("Celkova cena p�ed koncem byla: " + cena);
		}
		else {System.out.println("Celkova cena je: " + cena);}
		System.out.println("Byl vygenerovan soubor statistiky.txt, kter� obsahuje vsechny dulezite informace");
		
	}
}
