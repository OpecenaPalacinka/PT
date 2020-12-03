
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * Trida pro generovani dat, nad kterymi je mozne spustit simulaci.
 */
public class data {

    /**
     * Metoda pro generovani dat.
     *
     * @param filename
     * @param sc
     * @throws IOException
     */
    public static void generate(String filename, Scanner sc) throws IOException {
        /**
         * Vyzadame od uzivatele potrebne udaje, na kterych zavisi nasledne generovani dat
         * vcetne rozsahu
         */
        System.out.println("Zadejte počet supermarketů.");
        int s = sc.nextInt();

        System.out.println("Zadejte počet továren.");
        int d = sc.nextInt();

        System.out.println("Zadejte počet dní.");
        int t = sc.nextInt();

        System.out.println("Zadejte počet typů zboží.");
        int z = sc.nextInt();

        System.out.println("Zadejte spodní hranici rozsahu generovaných hodnot.");
        int a = sc.nextInt();

        System.out.println("Zadejte horní hranici rozsahu generovaných hodnot.");
        int b = sc.nextInt();
        sc.close();
        /**
         * Pripravime si potrebne matice, do kterych budeme nasledne generovat data
         */
        int[][] transportPrice = new int[s][d]; //cena prevozu jednoho zbozi
        int[][] initialStocks = new int[z][s]; //pocatecni skladove zasoby
        int[][][] factoriesProduction = new int[d][z][t]; //produkce tovaren
        int[][][] goodsDemand = new int[s][z][t]; //poptavka zbozi

        File myfile = new File(filename);
        try (FileWriter myWriter = new FileWriter(myfile);) {
            myWriter.write(String.format("%d %d %d %d\n\n", d, s , z, t));

            enterMatrixData(transportPrice, s, d, a, b);
            transportPrice = transposeMatrix(transportPrice);
            printMatrix(transportPrice, s, d, myWriter);
            myWriter.write("\n");
            System.out.println(); //mezi maticemi tiskneme prazdny radek pro prehlednost

            enterMatrixData(initialStocks, z, s, a, b);
            printMatrix(initialStocks, z, s, myWriter);
            myWriter.write("\n");
            System.out.println();

            enterMatrixData2(factoriesProduction, d, z, t, a, b);
            printMatrix2(factoriesProduction, d, z, t, myWriter);
            myWriter.write("\n");
            System.out.println();

            enterMatrixData2(goodsDemand, s, z, t, a, b);
            printMatrix2(goodsDemand, s, z, t, myWriter);
            myWriter.write("\n");
            System.out.println();
        }
    }

    /**
     * metoda generujici hodnoty do matic
     * @param matrix matice do ktere generujeme hodnoty
     * @param matrixRow pocet hodnot v radku matice
     * @param matrixCol pocet hodnot v sloupku matice
     * @param min minimum
     * @param max maximum
     */
    public static void enterMatrixData(int[][] matrix, int matrixRow, int matrixCol, int min, int max) {
        Random rand = new Random();
        for (int i = 0; i < matrixRow; i++) {
            for (int j = 0; j < matrixCol; j++){
                matrix[i][j] = rand.nextInt(max - min) + min;
            }
        }
    }
    /**
     * metoda pro nasledne vytisteni hodnot matice
     * @param matrix matice kterou tiskneme
     * @param matrixRow pocet hodnot radku
     * @param matrixCol pocet hodnot sloupce
     * @param myWriter writer
     * @return
     */

    public static void printMatrix(int[][] matrix, int matrixRow, int matrixCol, FileWriter myWriter) {
        try {


            for (int i = 0; i < matrixRow; i++) {
                for (int j = 0; j < matrixCol; j++) {

                    myWriter.write(matrix[i][j]+" ");
                    System.out.print(matrix[i][j]+ "\t");
                }
                System.out.println();
                myWriter.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * metoda pro transpozici matice
     * @param matrix puvodni matice
     * @return transponovana matice
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        int[][] trans = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                trans[j][i] = matrix[i][j];
        return trans;
    }
    /**
     * metoda pro tisteni matic se 3 parametry
     * @param matrix zvolena matice
     * @param matrixRow hodnoty radku
     * @param matrixDiagonal hodnoty na diagonale
     * @param matrixCol hodnoty sloupce
     * @param min minimum
     * @param max maximum
     */
    public static void enterMatrixData2(int[][][] matrix, int matrixRow, int matrixDiagonal, int matrixCol, int min, int max) {
        Random rand = new Random();
        for (int i = 0; i < matrixRow; i++) {
            for (int j = 0; j < matrixDiagonal; j++){
                for (int k = 0; k < matrixCol; k++){
                    matrix[i][j][k] = rand.nextInt(max - min) + min;
                }
            }
        }
    }
    /**
     * tisteni matice o 3 parametrech
     * @param matrix zvolena matice
     * @param matrixRow hodnoty radku
     * @param matrixDiagonal hodnoty diagonaly
     * @param matrixCol hodnoty sloupce
     * @param myWriter writer souboru
     */
    public static void printMatrix2(int[][][] matrix, int matrixRow, int matrixDiagonal, int matrixCol,FileWriter myWriter) {
        try {

            for (int i = 0; i < matrixRow; i++) {

                for (int j = 0; j < matrixDiagonal; j++) {

                    for (int k = 0; k < matrixCol; k++) {



                        myWriter.write(matrix[i][j][k]+" ");




                        System.out.print(matrix[i][j][k]+ "\t");
                    }
                    myWriter.write("\n");
                    System.out.println();



                }

            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    /*
    public static int[][] transposeMatrix2(int[][][] matrix) {
        int[][] trans = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                for (int k = 0; k < matrix[0].length; k++)
                    trans[k][j][i] = matrix[i][j][k];
        return trans;
    }

     */

}
