public class Main {

    public double wzor(double f, int p){
        double sp = p/(f+(1-f)*p);
        return sp;
    }

    public double calka(double a, double b){
        double wynik =1;
        return wynik;
    }

    public static void main(String[] args) {
        Main m = new Main();
        double f = 0.999;
        int p = 65536;
        System.out.println(m.wzor(f, p));
    }
}