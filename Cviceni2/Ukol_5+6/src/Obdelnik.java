public class Obdelnik extends Obrazec{
    private int sirka;
    private int vyska;

    private int strana;
    private double uhlopricka;

    public Obdelnik(int sirka, int vyska){
        this.sirka = sirka;
        this.vyska = vyska;
    }
    public Obdelnik(int strana, double uhlopricka){
        this.strana = strana;
        this.uhlopricka = uhlopricka;
    }

    @Override
    public double spoctiObvod() {
        return sirka+vyska;
    }
    public double spoctiObvod2(){
        double b = Math.sqrt(uhlopricka)-Math.sqrt(strana);
        return 2*strana + 2*b;
    }
}
