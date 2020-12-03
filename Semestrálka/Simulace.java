import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Hlavní tøída programu, naèítá data ze souboru a rozdìluje je do polí
 * @author Muhr && Pelikán
 */
public class Simulace {

	/**
	 * Naètení Scanneru
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
	 * Matice ceny pøevozu
	 */
	private static int[][] cena_provozu;
	/**
	 * Matice poèáteèních zásob
	 */
	private static int[][] pocatecni_zasoby;
	/**
	 * Matice produkce továren
	 */
	private static int[][][] produkce_tovaren;
	/**
	 * Matice poptávky zboží
	 */
	private static int[][][] poptavka_zbozi;

	/**
	 * ArrayList pro první data (matice ceny pøevozu)
	 */
	private static ArrayList<Integer> data1 = new ArrayList<Integer>();
	/**
	 * ArrayList pro druhá data (matice poèáteèních zásob)
	 */
	private static ArrayList<Integer> data2 = new ArrayList<Integer>();
	/**
	 * ArrayList pro tøetí data (matice produkce továren)
	 */
	private static ArrayList<Integer> data3 = new ArrayList<Integer>();
	/**
	 * ArrayList pro ètvrtá data (matice poptávky zboží)
	 */
	private static ArrayList<Integer> data4 = new ArrayList<Integer>();

	/**
	 * Naète data do "sc" ze vstupního souboru
	 * @param nazevSouboru Název souboru, který chceme èíst
	 */
	public static void nacteniFilu(String nazevSouboru){
		// -------------------------
		//   Nacteni filu
		// -------------------------

		System.out.println("------------Nacitani Souboru---------------\n");

		File vstup = new File(nazevSouboru);
		try {
			sc = new Scanner(vstup);
		} catch (FileNotFoundException e) {
			System.out.println("Soubor nebyl nalezen");
			e.printStackTrace();
		}
	}
	
	/**
	 * Nacte data a ulozi je do promenych a poli
	 */
	public static void nactiData() {

		c = 0; // counter

		// -------------------------
		// Cteni filu a ukladani hodnot
		// -------------------------
		
		while(sc.hasNext()) {
			String st = sc.nextLine();
			while (st.indexOf('#') == 0 || st.isEmpty()) {
				if (sc.hasNext()) {
					if (st.isEmpty() && c<5) {
						c++;
						System.out.println("--------- " + c + ". informace ----------");
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
			
			//System.out.println(st);
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

		//Pøedání dat do správného pole (matice) -- cena_provozu d,s -- cena pøevozu jednoho kusu libovolného zboží z továrny D do supermarketu S
        for (int i = 0; i < cena_provozu.length; i++) {
            for (int j = 0; j < cena_provozu[i].length; j++) {
                cena_provozu[i][j] = data1.get(c);
                c++;
//                System.out.print(cena_provozu[i][j] + " ");
            }
        }
        c = 0;

		pocatecni_zasoby = new int[z][s];

		//Pøedání dat do správného pole (matice) -- pocatecni_zasoby z,s -- poèáteèní zásoby výrobku Z v supermarketu S
        for (int i = 0; i < pocatecni_zasoby.length; i++) {
            for (int j = 0; j < pocatecni_zasoby[i].length; j++) {
                pocatecni_zasoby[i][j] = data2.get(c);
                c++;
//                System.out.print(pocatecni_zasoby[i][j] + " ");
            }
        }
        c = 0;

        produkce_tovaren = new int[d][z][t];

		//Pøedání dat do správného pole (matice) -- produkce_tovaren d,z,t -- produkce továrnou D druhu zboží Z v den T
        for(int i=0 ; i<produkce_tovaren.length ; i++){
            for(int j=0 ; j<produkce_tovaren[i].length ; j++){
                for(int k=0 ; k<produkce_tovaren[i][j].length ; k++){
                	produkce_tovaren[i][j][k] = data3.get(c);
                	c++;
//                	System.out.println(produkce_tovaren[i][j][k] + " ");
                }
            }
        }
        c = 0;
        
        poptavka_zbozi = new int[s][z][t];

		//Pøedání dat do správného pole (matice) -- poptavka_zbozi s,z,t --  poptávka zákazníkù po zboží Z v supermarketu S dne T
        for(int i=0 ; i<poptavka_zbozi.length ; i++){
            for(int j=0 ; j<poptavka_zbozi[i].length ; j++){
                for(int k=0 ; k<poptavka_zbozi[i][j].length ; k++){
                	poptavka_zbozi[i][j][k] = data4.get(c);
                	c++;
//                	System.out.println(poptavka_zbozi[i][j][k] + " ");
                }
            }
        }
        
		// -------------------------
		// Vypis dat
		// -------------------------
        
		System.out.println("\n------Informace po zpracovani----\n");
		System.out.println("D / Pocet tovaren = " + d);
		System.out.println("S / Pocet supermarketu = " + s);
		System.out.println("Z / Pocet druhu zbozi = " + z);
		System.out.println("T / Pocet dni = " + t);
		//System.out.println("\nCena prevozu jednoho zbozi (hodnoty v poli)\n" + data1);
		//System.out.println("\nPocatecni skladove zasoby (hodnoty v poli)\n" + data2);
		//System.out.println("\nProdukce tovaren (hodnoty v poli)\n" + data3);
		//System.out.println("\nPoptavka zbozi (hodnoty v poli)\n" + data4);
		
		data1.clear();
		data2.clear();
		data3.clear();
		data4.clear();
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
		
		double celkova_cena = 0; // Celkova cena
		
		
		for (int a = 0; a<t;a++) {
			System.out.println("\n-----" + (a+1) + ". Den-----");
			
			for (int x = 0; x<s; x++) {  // Cyklus Supermarketù
				System.out.println("\n----" + (x+1) + ". Supermarket----");
				for (int y = 0; y<z;y++) {  // Cyklus zbozi
					System.out.println("---" + (y+1) + ". Zbozi---");
					
					
					int nutne_zbozi = pocatecni_zasoby[y][x] - poptavka_zbozi[x][y][a]; // potrebne zbozi pro splneni poptavky	
					
					pocatecni_zasoby[y][x] = 0; // Vypotrebujeme zasoby
					
					System.out.println("Pro " + (x+1) + ". supermarket je nutno sehnat " + Math.abs(nutne_zbozi) + " zbozi cislo " + (y+1));
					
					// zjisteji nejlevnejsi tovarny pro supermarket
					
					ArrayList<Integer> Pouzite_tovarny = new ArrayList<Integer>(); // uchovava indexy pouzitych tovaren
					
					int temp = Integer.MAX_VALUE;
					int nejm_cena = 0;
					int cislo_tovarny = 0;
					for (int i = 0; i<d;i++) {
//					System.out.println(cena_provozu[0][i]);
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
//						System.out.println(cena_provozu[0][i]);
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
	 * Vstupní funkce programu, nacte data, zpracuje hodnoty a spusti simulaci, po ktere vypise finalni cenu
	 * @param arg Argumenty pøíkazové øádky
	 */
	public static void main(String[] arg){

		nacteniFilu("pt_2020_2021_data/test_optim.txt");
		nactiData();
		
		zpracujHodnoty();
		
		double cena = simulace();
		
		System.out.println("\nCelkova cena je: " + cena);
		

	}

}
