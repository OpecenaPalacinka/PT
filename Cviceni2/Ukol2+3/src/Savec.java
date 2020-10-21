public class Savec extends Obratlovec{
    private  int pocet = 0;
    private static int poradi = 0;
    private String jmeno = "Savec";

    public Savec(){
        poradi++;
        pocet = poradi;
        System.out.println("Byl vytvořen nový "+jmeno+"!");
        System.out.println("Dosud vytvořených: "+ pocet + " a je v pořadí: "+poradi+".");

    }
}
