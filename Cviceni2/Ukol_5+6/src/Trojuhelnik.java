public class Trojuhelnik extends Obrazec{
    private int strana_a;
    private int strana_b;
    private int strana_c;

    public Trojuhelnik(int strana_a, int strana_b, int strana_c){
        this.strana_a = strana_a;
        this.strana_b = strana_b;
        this.strana_c = strana_c;
    }


    @Override
    public double spoctiObvod() {
        return strana_a+strana_b+strana_c;
    }
}
