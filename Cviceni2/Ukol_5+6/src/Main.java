public class Main {

    public static void main(String[] args) {
	Obdelnik prvniObd = new Obdelnik(10,5);
	Trojuhelnik prvniTroj = new Trojuhelnik(5,10,5);
	Kruh prvniKruh = new Kruh(20);

	Obdelnik druhyObd = new Obdelnik(9,16.0);

	System.out.println("Obvod prvního obdélníka je: "+prvniObd.spoctiObvod());
	System.out.println("Obvod prvního trojúhelníka je: "+prvniTroj.spoctiObvod());
	System.out.println("Obvod prvního kruhu je: "+prvniKruh.spoctiObvod());

	double prumernyObvod = (prvniObd.spoctiObvod()+prvniKruh.spoctiObvod()+prvniTroj.spoctiObvod()) / 3;
	System.out.println("Průměrný obvod je: "+prumernyObvod);
	System.out.println();
	System.out.println("Obvod obdelniku 2 je: "+druhyObd.spoctiObvod2());
    }
}
