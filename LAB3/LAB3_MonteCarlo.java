import java.util.Random;

class MonteCarlo extends Thread {
    double xStart, xStop, yStart, yStop;
    int tryCount;
    double score;
    Random random;

    public MonteCarlo(double xStart, double xStop, double yStop, double yStart, int counter) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xStop = xStop;
        this.yStop = yStop;
        this.score = 0;
        this.random = new Random();
        this.tryCount = counter;
    }

    public void run() {
        int iter = 0;
        for (int i = 0; i < this.tryCount; i++) {
            double x = Math.random();
            double y = Math.random();
            if ((x * x + y * y) <= 1)
                iter++;
        }
        this.score = iter;
    }
    public double Score() {
        return this.score;
    }
}

public class LAB3_MonteCarlo {
    public static void main(String[] args) {
        MonteCarlo alfa, beta, gamma, omega;
        int attmpts = 1000;
        double a = 10;
        alfa = new MonteCarlo(0,0, a/2, a/2, attmpts);
        beta = new MonteCarlo(a/2,0, 1, a/2, attmpts);
        gamma = new MonteCarlo(0, a/2, a/2, a, attmpts);
        omega = new MonteCarlo(a/2,a/2, a, a, attmpts);
        alfa.run();
        beta.run();
        gamma.run();
        omega.run();

        try {
            alfa.join();
            beta.join();
            gamma.join();
            omega.join();
        }
        catch (Exception e){
        }
        double field = alfa.Score() + beta.Score() + gamma.Score() + omega.Score();
        field = field / ((double)attmpts * 4) * (a * a);
        System.out.println("Pole kola wynosi = " + field);
    }
}