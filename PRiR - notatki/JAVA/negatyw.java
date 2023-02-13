package JAVA;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Tworzenie klasy negatyw, która rozszerza klasę Thread
class negatyw extends Thread{
    // Pola przechowujące obraz i zakres pikseli do przetworzenia przez wątek
    BufferedImage pobierz_obraz;
    int xStart, yStart, xStop, yStop;

    // Konstruktor, przyjmujący obraz i zakres pikseli do przetworzenia
    public negatyw( BufferedImage obraz, int x_start, int x_stop, int y_stop, int y_start ){
        this.xStart = x_start;
        this.yStart = y_start;
        this.xStop = x_stop;
        this.yStop = y_stop;
        this.pobierz_obraz = obraz;
    }

    // Metoda run() z klasy Thread, w której wykonywana jest główna operacja
    @Override
    public void run() {
        // Pętla po wszystkich pikselach w zakresie przekazanym do konstruktora
        for(int i = xStart; i < xStop; i++){
            for(int j = yStart; j < yStop; j++) {
                // Pobieranie koloru piksela
                Color c = new Color(pobierz_obraz.getRGB(i, j));
                // Pobieranie składowych R, G, B
                int red = c.getRed();
                int green = c.getGreen();
                int black = c.getBlue();

                // Obliczanie składowych koloru odwróconego
                int R, G, B;
                R = 255 - red;
                G = 255 - green;
                B = 255 - black;

                /*
                    Obliczanie składowych koloru z efektem sepii
                    int R, G, B;
                    R = (int)(0.393 * red + 0.769 * green + 0.189 * black);
                    G = (int)(0.349 * red + 0.686 * green + 0.168 * black);
                    B = (int)(0.272 * red + 0.534 * green + 0.131 * black);
                    Ustawianie ograniczenia składowych R, G, B do wartości maksymalnej 255
                    R = Math.min(R, 255);
                    G = Math.min(G, 255);
                    B = Math.min(B, 255);
                */

                // Tworzenie obiektu koloru o odwróconych składowych
                Color newColor = new Color(R, G, B);
                // Ustawianie odwróconego koloru jako koloru piksela
                pobierz_obraz.setRGB(i, j, newColor.getRGB());
            }
        }
    }

}
class LAB3_Negatyw {
public static void main(String[] args) throws IOException {
    // Odczyt pliku obrazu i utworzenie obiektu BufferedImage
    BufferedImage image;
    File input = new File("zdj.jpg");
    image = ImageIO.read(input);

    // Pobieranie szerokości i wysokości obrazu
    int width = image.getWidth();
    int height = image.getHeight();

    // Dzielenie obrazu na 4 równe części
    int wField = width / 2;
    int hField = height / 2;

    /*
     * Dzielenie obrazu na 8 równych części
     * int wField = width / 4;
     * int hField = height / 2;
     */

    // Tworzenie instancji klasy negatyw dla każdej części obrazu
    negatyw t1, t2, t3, t4;
    t1 = new negatyw(image, 0, wField,hField,0);
    t2 = new negatyw(image, wField, width, height, hField);
    t3 = new negatyw(image, 0, wField, height, hField);
    t4 = new negatyw(image, wField, width, hField, 0);

    /*
     * Tworzenie instancji klasy negatyw dla każdej części obrazu
     * negatyw t1, t2, t3, t4, t5, t6, t7, t8;
     * t1 = new negatyw(image, 0, wField, hField, 0);
     * t2 = new negatyw(image, wField, wField * 2, hField, 0);
     * t3 = new negatyw(image, wField * 2, wField * 3, hField, 0);
     * t4 = new negatyw(image, wField * 3, width, hField, 0);
     * t5 = new negatyw(image, 0, wField, hField * 2, hField);
     * t6 = new negatyw(image, wField, wField * 2, hField * 2, hField);
     * t7 = new negatyw(image, wField * 2, wField * 3, hField * 2, hField);
     * t8 = new negatyw(image, wField * 3, width, hField * 2, hField);
     */

    // Uruchomienie wszystkich wątków
    t1.start();
    t2.start();
    t3.start();
    t4.start();

    // Oczekiwanie na zakończenie pracy wszystkich wątków
    try {
        t1.join();
        t2.join();
        t3.join();
        t4.join();
    } catch (Exception e) { }

    // Zapis zmodyfikowanego obrazu do nowego pliku
    File ouptut = new File("zdj_negatyw.jpg");
    ImageIO.write(image, "jpg", ouptut);
}
}