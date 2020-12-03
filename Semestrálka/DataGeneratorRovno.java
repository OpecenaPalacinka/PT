import java.io.*;
import java.util.*;

/**
 * Třída rovnoměrného generátoru dat, generuje data do konzole (pro kontrolu) a poté do souboru "data_generated_rovno.txt".
 * @author Muhr && Pelikán
 */
public class DataGeneratorRovno {
    /**
     * Nacteni scanneru
     */
    public static Scanner sc = new Scanner(System.in);
    /**
     * Vytvoření souboru
     */
    public static File file = new File("data_generated_rovno.txt");
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
     * Minimální hranice
     */
    private static int min = 0;
    /**
     * Maximální hranice
     */
    private static int max = 0;

    public static final String uvod = "# Data pro semestralni praci KIV/PT 2020/2021\n" +
            "# Vytvoreno systemem \"Muhr && Pelikán\", 31.8.2020.\n" +
            "#\n" +
            "# prazdne radky nebo radky zacinajici znakem # se ingoruji\n" +
            "# v opacnem pripade jsou na kazde radce ciselne hodnoty oddelene mezerou\n" +
            "# data jsou popsana vzdy nad prislusnym blokem dat, bloky jsou oddelene\n" +
            "# prazdnym radkem nasledujici radkem viditelne oznacenym vyrazem \"BLOK:\"\n";

    public static final String cenaPrevozuText = "# BLOK: Cena prevozu jednoho zbozi c_{s,d}\n" +
            "#\n" +
            "# c_{1,1} c_{2,1} ... c_{S,1}\n" +
            "# c_{1,2} c_{2,2} ... c_{S,2}\n" +
            "#    .      .    .      .\n" +
            "#    .      .     .     .\n" +
            "#    .      .      .    .\n" +
            "# c_{1,D} c_{2,D} ... c_{S,D}\n";

    public static final String pocatecniZasobyText = "# BLOK: Pocatecni skladove zasoby q_{z,s}\n" +
            "#\n" +
            "# q_{1,1} q_{1,2} ... q_{1,S}\n" +
            "# q_{2,1} q_{2,2} ... q_{2,S}\n" +
            "#    .      .    .      .\n" +
            "#    .      .     .     .\n" +
            "#    .      .      .    .\n" +
            "# q_{Z,1} q_{Z,2} ... q_{Z,S}\n";

    public static final String produkceTovarenText = "# BLOK: Produkce tovaren p_{d,z,t}\n" +
            "# p_{1,1,1} p_{2,1,1} .... p_{D,1,1}\n" +
            "# p_{1,1,2} p_{2,1,2} .... p_{D,1,2}\n" +
            "#      .         .    .         .\n" +
            "#      .         .     .        .\n" +
            "#      .         .      .       .\n" +
            "# p_{1,1,T} p_{2,1,T} .... p_{D,1,T}\n" +
            "# p_{1,2,1} p_{2,2,1} .... p_{D,2,1}\n" +
            "# p_{1,2,2} p_{2,2,2} .... p_{D,2,2}\n" +
            "#      .         .    .         .\n" +
            "#      .         .     .        .\n" +
            "#      .         .      .       .\n" +
            "#      .         .       .      .\n" +
            "# p_{1,Z,T} p_{2,Z,T} .... p_{D,Z,T}\n";

    public static final String poptavkaText = "# BLOK: Poptavka zbozi r_{s,z,t}\n" +
            "# r_{1,1,1} r_{2,1,1} .... r_{S,1,1}\n" +
            "# r_{1,1,2} r_{2,1,2} .... r_{S,1,2}\n" +
            "#      .         .    .         .\n" +
            "#      .         .     .        .\n" +
            "#      .         .      .       .\n" +
            "# r_{1,1,T} r_{2,1,T} .... r_{S,1,T}\n" +
            "# r_{1,2,1} r_{2,2,1} .... r_{S,2,1}\n" +
            "# r_{1,2,2} r_{2,2,2} .... r_{S,2,2}\n" +
            "#      .         .    .         .\n" +
            "#      .         .     .        .\n" +
            "#      .         .      .       .\n" +
            "#      .         .       .      .\n" +
            "# r_{1,Z,T} r_{2,Z,T} .... r_{S,Z,T}\n";

    public static void main(String[] args) throws IOException {
        file.delete();
        fileWriter = new FileWriter("data_generated_rovno.txt",true);
        bw = new BufferedWriter(fileWriter);
        out = new PrintWriter(bw);

        /*
         * Vyzadame od uzivatele potrebne udaje, na kterych zavisi nasledne generovani dat vcetne rozsahu
         */

       nactiData();

        if (min >= max || min < 0){
            System.out.println("Minimum nemůže být menší rovno maximu nebo menší než nula.");
        } else if (s <= 0 || d <= 0 || t <= 0 || z <= 0) {
            System.out.println("Hodnoty pro supermarkety, továrny, dni a typy zboží musí být větší než 0!");
        } else {

            //cena prevozu jednoho zbozi
            int[][] cenaPrevozu = new int[s][d];
            //pocatecni skladove zasoby
            int[][] pocatecniZasoby = new int[z][s];
            //produkce tovaren
            int[][][] produkceTovaren = new int[z][t][d];
            //poptavka zbozi
            int[][][] poptavkaPoZbozi = new int[z][t][s];

            out.println(uvod);

            out.println(cenaPrevozuText);
            out.flush();
            vlozDataRandom2(cenaPrevozu, s, d, min, max);
            vypisMatici2(cenaPrevozu, s, d);
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

        }
    }

    /**
     * Metoda načíta vstupní data od uživatele (počet továren, supermarketů, zboží, dní, spodní a horní hranice)
     */
    public static void nactiData(){
        System.out.print("Zadejte počet továren: ");
        while (!sc.hasNextInt()) {
            System.out.print("Prosím, zadejte číslo! -> ");
            sc.next();
        }
        d = sc.nextInt();

        System.out.print("Zadejte počet supermarketů: ");
        while (!sc.hasNextInt()) {
            System.out.print("Prosím, zadejte číslo! -> ");
            sc.next();
        }
        s = sc.nextInt();

        System.out.print("Zadejte počet typů zboží: ");
        while (!sc.hasNextInt()) {
            System.out.print("Prosím, zadejte číslo! -> ");
            sc.next();
        }
        z = sc.nextInt();

        System.out.print("Zadejte počet dní: ");
        while (!sc.hasNextInt()) {
            System.out.print("Prosím, zadejte číslo! -> ");
            sc.next();
        }
        t = sc.nextInt();

        System.out.print("Zadejte spodní hranici rozsahu generovaných hodnot: ");
        while (!sc.hasNextInt()) {
            System.out.print("Prosím, zadejte číslo! -> ");
            sc.next();
        }
        min = sc.nextInt();

        System.out.print("Zadejte horní hranici rozsahu generovaných hodnot: ");
        while (!sc.hasNextInt()) {
            System.out.print("Prosím, zadejte číslo! -> ");
            sc.next();
        }
        max = sc.nextInt();
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
        random = new Random();
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
        FileWriter fileWriter = new FileWriter("data_generated_rovno.txt",true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        PrintWriter out = new PrintWriter(bw);
        for (int i = 0; i < rada; i++) {
            for (int j = 0; j < sloupec; j++) {
                out.print(matice[i][j]+ "\t");
            }
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
     * Metoda, která vypisuje data z třírozměrné matice
     * @param matice    Předpřipravená matice, ze které chceme vypisovat data
     * @param rada	Počet řad matice
     * @param diagonala Počet diagonál matice
     * @param sloupec	Počet sloupců matice
     */
    public static void vypisMatici3(int[][][] matice, int rada, int diagonala, int sloupec) throws IOException {
        FileWriter fileWriter = new FileWriter("data_generated_rovno.txt",true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        PrintWriter out = new PrintWriter(bw);
        for (int i = 0; i < rada; i++) {
            for (int j = 0; j < diagonala; j++) {
                for (int k = 0; k < sloupec; k++) {
                    out.print(matice[i][j][k]+ "\t");
                }
                out.println();
            }
        }
        out.close();
    }

}
