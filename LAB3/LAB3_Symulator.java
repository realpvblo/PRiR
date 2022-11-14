import java.util.concurrent.atomic.AtomicBoolean;

class Pociag extends Thread {

    private String nr;
    private int pojemnosc;
    private int paliwo;

    private final AtomicBoolean czyDziala = new AtomicBoolean();

    public Pociag(String nr, int pojemnosc, int paliwo) {
        this.nr = nr;
        this.pojemnosc = pojemnosc;
        this.paliwo = paliwo;
    }

    public void start() {
        super.start();
    }

    public void tankowanie(int paliwo) {
        System.out.println("Pociąg " + nr + " tankuje");
        paliwo = 100;
    }

    public final void STOP() {
        System.out.println("Pociąg " + nr + " się zatrzymuje");
        czyDziala.set(false);
        tankowanie(paliwo);
    }

    public void START() throws InterruptedException {
        czyDziala.set(true);
        int iter = 0;

        while (czyDziala.get()) {

            Thread.sleep(1000);
            System.out.println("Jedziemy pociągiem " + nr + " o ilości: " + paliwo + "l paliwa");

            paliwo -= 1;
            iter++;
            if (paliwo <= 5) {
                System.out.println("Pociąg " + nr + " musi zatankować");
            }
            if (paliwo == 0) {
                STOP();
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Pociąg rodzaju: " + nr);

        try {
            START();
        } catch (InterruptedException e) {
            STOP();
            e.printStackTrace();
        }
    }
}

class LAB3_Symmulator{
    public static void main(String[] args)  {
        var ICC = new Pociag("ICC (InterCity)",20,20);
        var EIC = new Pociag("EIC (Express InterCity)",30,30);
        var TLK = new Pociag("TLK (Twoje Linie Kolejowe)",40,40);

        ICC.start();
        EIC.start();
        TLK.start();
    }
}