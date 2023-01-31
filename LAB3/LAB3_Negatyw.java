import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

<<<<<<< HEAD
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
=======
public class Negatyw implements Runnable {
    BufferedImage image;
    int width;
    int height;

    public Negatyw() throws IOException {
        File input = new File("logo.jpg");
        image = ImageIO.read(input);
        width = image.getWidth();
        height = image.getHeight();
>>>>>>> 026ad17e79493f101aba6d4ef72d576d1bf48c78
    }

    // Metoda run() z klasy Thread, w której wykonywana jest główna operacja
    @Override
    public void run() {
<<<<<<< HEAD
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
                // Tworzenie obiektu koloru o odwróconych składowych
                Color newColor = new Color(R, G, B);
                // Ustawianie odwróconego koloru jako koloru piksela
                pobierz_obraz.setRGB(i, j, newColor.getRGB());
            }
=======
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                //odczyt składowych koloru RGB
                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed());
                int green = (int) (c.getGreen());
                int blue = (int) (c.getBlue());

                int final_red, final_green, final_blue;

                //negatyw
                final_red = 255 - red;
                final_green = 255 - green;
                final_blue = 255 - blue;
                Color newColor = new Color(final_red, final_green, final_blue);
                image.setRGB(j, i, newColor.getRGB());
            } //koniec dwóch pętli po kolumnach i wierszach obrazu
>>>>>>> 026ad17e79493f101aba6d4ef72d576d1bf48c78
        }
//zapis do pliku zmodyfikowanego obrazu
        File ouptut = new File("negatyw.jpg");
        try {
            ImageIO.write(image, "jpg", ouptut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

<<<<<<< HEAD
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

    // Tworzenie instancji klasy negatyw dla każdej części obrazu
    negatyw t1, t2, t3, t4;
    t1 = new negatyw(image, 0, wField,hField,0);
    t2 = new negatyw(image, wField, width, height, hField);
    t3 = new negatyw(image, 0, wField, height, hField);
    t4 = new negatyw(image, wField, width, hField, 0);

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
=======
    static public void main(String args[]) throws Exception {
        Thread obj = new Thread(new Negatyw());
        obj.start();
    }
>>>>>>> 026ad17e79493f101aba6d4ef72d576d1bf48c78
}