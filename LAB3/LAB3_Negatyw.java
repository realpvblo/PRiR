import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Negatyw extends Thread{
    BufferedImage pobierz_obraz;
    int xStart, yStart, xStop, yStop;

    public Negatyw( BufferedImage obraz, int x_start, int x_stop, int y_stop, int y_start ){
        this.xStart = x_start;
        this.yStart = y_start;
        this.xStop = x_stop;
        this.yStop = y_stop;
        this.pobierz_obraz = obraz;
    }

    @Override
    public void run() {
        for(int i = xStart; i < xStop; i++){
            for(int j = yStart; j < yStop; j++) {
                Color c = new Color(pobierz_obraz.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int black = c.getBlue();
                int R, G, B;
                R = 255 - red;
                G = 255 - green;
                B = 255 - black;
                Color newColor = new Color(R, G, B);
                pobierz_obraz.setRGB(i, j, newColor.getRGB());
            }
        }
    }

}

class LAB3_Negatyw {

    public static void main(String[] args) throws IOException {
        BufferedImage image;

        File input = new File("zdj.jpg");
        image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();
        int wField = width / 2;
        int hField = height / 2;

        Negatyw t1, t2, t3, t4;
        t1 = new Negatyw(image, 0, 0, wField, hField);
        t2 = new Negatyw(image, wField, 0, width, hField);
        t3 = new Negatyw(image, 0, hField, wField, height);
        t4 = new Negatyw(image, wField, hField, width, height);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (Exception e) { }
        File ouptut = new File("zdj_negatyw.jpg");
        ImageIO.write(image, "jpg", ouptut);
    }
}