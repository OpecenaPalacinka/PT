public class Obratlovec extends Zivocich{
    private int pocet = 0;
    private static int poradi = 0;
    private String jmeno = "Obratlovec";

    public Obratlovec(){
        poradi++;
        pocet = poradi;
        System.out.println("Byl vytvořen nový "+jmeno+"!");
        System.out.println("Dosud vytvořených: "+ pocet + " a je v pořadí: "+poradi+".");
    }
}
