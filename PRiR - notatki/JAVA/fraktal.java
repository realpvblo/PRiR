package JAVA;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/*
 * Zmiana koloru wyświetlanego na obrazie: Aby zmienić kolor wyświetlany na obrazie, należy zmienić argumenty tworzenia obiektu koloru Color w liniach Color c = new Color(0, level, 0); na inne wartości.
 * Zmiana formatu pliku wynikowego: Aby zmienić format pliku wynikowego, należy zmienić argument formatu podany w liniach ImageIO.write(img, "PNG", new File("Fraktal_Julii.png")); na inny format (np. "JPEG").
 * Zmiana liczby wątków: Aby zmienić liczbę wątków, należy zmienić wartość stałej 4 w liniach for(int i=0; i<4; i++){ na inną wartość oraz zmienić wartość stałej N/4 w liniach begin = (N/4) * me; i end = (N/4) * (me+1); tak, aby odpowiadała nowej liczbie wątków.
 * Zmiana rozmiaru obrazu: Aby zmienić rozmiar obrazu, należy zmienić wartość stałej N w liniach final static int N = 4096; oraz zmienić wartości stałych ratioY i ratioX tak, aby odpowiadały nowemu rozmiarowi.
 * Zmiana maksymalnej liczby iteracji: Aby zmienić maksymalną liczbę iteracji, należy zmienić wartość stałej CUTOFF w liniach final static int CUTOFF = 100;.
 */

class fraktal extends Thread {
    final static int N = 4096; // rozmiar obrazu
    final static int CUTOFF = 100; // maksymalna ilość iteracji
    static int[][] set = new int[N][N]; // tablica z wartościami pikseli

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis(); // pobranie czasu przed uruchomieniem obliczeń

        // stworzenie i uruchomienie 4 wątków
        fraktal[] watki = new fraktal[4];
        for(int i=0; i<4; i++){
            watki[i] = new fraktal(i);
            watki[i].start();
        }

        // czekanie na zakończenie wszystkich wątków
        for (fraktal watek : watki) {
            watek.join();
        }

        long endTime = System.currentTimeMillis(); // pobranie czasu po zakończeniu obliczeń
        System.out.println("Obliczenia zakończone po: " + (endTime - startTime) + " millisekundach"); // wyświetlenie czasu trwania obliczeń

        // stworzenie obiektu obrazu
        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);

        // ustawienie wartości pikseli obrazu
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = set[i][j];
                float level;
                if (k < CUTOFF) { // jeśli liczba iteracji jest mniejsza niż CUTOFF
                    level = (float) k / CUTOFF; // obliczenie poziomu jasności piksela
                } else {
                    level = 0; // jeśli liczba iteracji jest większa niż CUTOFF, piksel jest całkowicie czarny
                }
                Color c = new Color(0, level, 0); // utworzenie koloru piksela (tylko zielony kanał jest używany)
                img.setRGB(i, j, c.getRGB()); // ustawienie koloru piksela
            }
        }
        // zapisanie obrazu do pliku
        ImageIO.write(img, "PNG", new File("Fraktal_Julii.png")); 
    }

    int me; // numer wątku
    int begin; // indeks początkowy dla wierszy obliczanych przez wątek
    int end; // indeks końcowy dla wierszy obliczanych przez wątek

    static final double ratioY = (1.25 - -1.25) / N; // stosunek wartości rzeczywistych piksela do indeksu wiersza
    static final double ratioX = (1.25 - -1.25) / N; // stosunek wartości urojonych piksela do indeksu kolumny
    public fraktal(int me) {
        this.me = me;
        this.begin = (N/4) * me; // obliczenie indeksu początkowego dla wątku
        this.end = (N/4) * (me+1); // obliczenie indeksu końcowego dla wątku
    }
    public void run() {
        // iteracja po wierszach i kolumnach obrazu
        for (int i = begin; i < end; i++) {
            for (int j = 0; j < N; j++) {
                double rzeczywista = i*ratioY + -1.25; // obliczenie wartości rzeczywistej piksela
                double urojona = j*ratioX + -1.25; // obliczenie wartości urojonej piksela

                Zespolona c = new Zespolona(-0.123, 0.745); // stała zespolona c
                Zespolona z = new Zespolona(rzeczywista, urojona); // utworzenie zespolonej liczby z
                int k = 0; // licznik iteracji

                // iteracja do momentu osiągnięcia maksymalnej ilości iteracji lub gdy moduł z jest większy od 2
                while (k < CUTOFF && z.modul() < 2.0) {
                    z = c.suma(z.kwadrat()); // obliczenie nowej wartości z
                    k++;
                }
                set[i][j] = k; // ustawienie wartości piksela w tablicy
            }
        }
    }
}

class Zespolona{
    private double r; // część rzeczywista
    private double u; // część urojona
    public Zespolona(double r, double u){
        this.r = r;
        this.u = u;
    }

    // metoda obliczająca sumę dwóch liczb zespolonych
    Zespolona suma(Zespolona inna){
        return new Zespolona(r + inna.r, u +inna.u);
    }

    // metoda obliczająca iloczyn dwóch liczb zespolonych
    Zespolona iloczyn(Zespolona inna){
        double rzeczywista = r * inna.r - u * inna.u;
        double urojona = r*inna.u + u*inna.r;
        return new Zespolona(rzeczywista, urojona);
    }

    // metoda obliczająca moduł liczby zespolonej
    double modul(){
        return Math.sqrt(r*r + u*u);
    }

    // metoda obliczająca kwadrat liczby zespolonej
    Zespolona kwadrat(){
        return this.iloczyn(this);
    }
}