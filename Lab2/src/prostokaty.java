public class prostokaty extends Thread{
    private double a;
    private double result;

    public prostokaty(double a){
        this.a = a;
    }
        double trapezy (double a, double b, int n) {
        double h = (b-a)/n;
        double calka = 0;
        double[] xi = new double[n];

        for (int j = 1; j <= n-1; j++){
            xi[j] = a + (j*h);
        }

        for (int i = 1; i <= n-1; i++) {
            calka += fp(xi[i]);
        }

        calka += fp(a)/2 + fp(b)/2;
        calka *= h;
        return calka;
    }
    public static double fp(double x){
        return Math.pow(x,2);
    }
    public void run(){
        result = fp(a);
    }
    public double getResult(){
        return result;
    }

    public static void main(String[] args) {
        int n = 100;
        double a = 1.0;
        double b = 4.5;
        double h = (b-a)/n;
        double calka = 0;

        prostokaty[] pr = new prostokaty[n];
        for (int i = 0; i < n; i++){
            pr[i] = new prostokaty(a+i*h);
            pr[i].start();
             calka += pr[i].getResult()*h;
        }
        calka += prostokaty.fp(a+(n*h))*h;
        System.out.println("wart. przyb. całki oznaczonej dla n = " + n + " - M prostokątów: " + calka);

        prostokaty m = new prostokaty(n);
        System.out.println("wart. przyb. całki oznaczonej dla n = " + n + " - M trapezow: " + m.trapezy(a,b,n));

    }
}


