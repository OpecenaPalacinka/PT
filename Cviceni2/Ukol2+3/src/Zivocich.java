public class Zivocich {
    private int pocet = 0;
    private static int poradi = 0;
    private static String jmeno = "živočich";

    public Zivocich(){
        poradi++;
        pocet = poradi;
        System.out.println("Byl vytvořen nový "+jmeno+"!");
        System.out.println("Dosud vytvořených: "+ pocet + " a je v pořadí: "+poradi+".");

    }
}
