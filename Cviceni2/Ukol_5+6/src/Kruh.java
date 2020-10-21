public class Kruh extends Obrazec{
    private double obsah;

    public Kruh(double obsah){
        this.obsah = obsah;
    }

    @Override
    public double spoctiObvod() {
        double r = Math.sqrt(obsah/Math.PI);
        return 2*Math.PI*r;
    }
}
