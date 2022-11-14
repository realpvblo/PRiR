import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class LAB3_Filozofowie extends Thread {
    static final int MAX = 100;
    static Semaphore[] widelec = new Semaphore[MAX];
    static int wariant, liczbaF;
    int mojNum;
    Random rand = new Random();


    public LAB3_Filozofowie(int nr){
        this.mojNum = nr;
    }
    public void run(){
        switch(wariant){
            case 1:
                w1();
                break;
            case 2:
                w2();
                break;
            case 3:
                w3();
                break;
        }
    }
    public void w1(){
        while(true){
            System.out.println("Filozof " + mojNum + " - Rozmysla");
            try{
                Thread.sleep((long)(7000 * Math.random()));
            }
            catch(InterruptedException e){
            }
            widelec[mojNum].acquireUninterruptibly();
            widelec[(mojNum+1)%liczbaF].acquireUninterruptibly();
            System.out.println("Filozof " + mojNum + " - Zaczyna jesc");
            try{
                Thread.sleep((long)(5000 * Math.random()));
            }
            catch(InterruptedException e){
            }
            System.out.println("Filozof " + mojNum + " - Konczy jesc");
            widelec[mojNum].release();
            widelec[(mojNum + 1)%liczbaF].release();
        }
    }
    public void w2(){
        while(true) {
            System.out.println("Filozof " + mojNum + " - Rozmysla");
            try {
                Thread.sleep((long)(5000 * Math.random()));
            }
            catch(InterruptedException e){}
            if(mojNum == 0) {
                widelec[(mojNum + 1) % liczbaF].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            }
            else {
                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum + 1) % liczbaF].acquireUninterruptibly();
            }
            System.out.println("Filozof " + mojNum + " - Zaczyna jesc");
            try {
                Thread.sleep((long) (3000 * Math.random()));
            }
            catch(InterruptedException e){}
            System.out.println("Filozof " + mojNum + " - Konczy jesc");
            widelec[mojNum].release();
            widelec[(mojNum + 1) % liczbaF].release();
        }
    }
    public void w3(){
        while(true){
            System.out.println("Filozof " + mojNum + " - Rozmysla");
            try {
                Thread.sleep((long) (5000 * Math.random()));
            }
            catch(InterruptedException e){}

            int strona = rand.nextInt(2);
            boolean dwaWidelce = false;
            do{
                if(strona == 0) {
                    widelec[mojNum].acquireUninterruptibly();
                    if(!(widelec[(mojNum + 1) % liczbaF]).tryAcquire()) {
                        widelec[mojNum].release();
                    }
                    else {
                        dwaWidelce = true;
                    }
                }
                else {
                    widelec[(mojNum + 1) % liczbaF].acquireUninterruptibly();
                    if(!(widelec[mojNum].tryAcquire())) {
                        widelec[(mojNum + 1) % liczbaF].release();
                    }
                    else {
                        dwaWidelce = true;
                    }
                }
            } while(!dwaWidelce);

            System.out.println("Filozof " + mojNum + " - Zaczyna jesc");
            try {
                Thread.sleep((long) (3000 * Math.random()));
            }
            catch(InterruptedException e){}
            System.out.println("Filozof " + mojNum + " - Konczy jesc");

            widelec[mojNum].release();
            widelec[(mojNum + 1) % liczbaF].release();
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Wybierz liczbe filozofow od 2 do 100: ");
        liczbaF = sc.nextInt();
        System.out.println("Wybierz problem:\n" +
                "1 - problem ucztujacych filozofow\n" +
                "2 - problem ucztujacych filozofow z sieganiem po widelec\n" +
                "3 - rzut moneta w rozwiazaniu problemu ucztujacych filozofow");
        wariant = sc.nextInt();
        sc.close();
        if((liczbaF >= 2 && liczbaF <= 100) && (wariant >= 1 && wariant <= 3)) {
            widelec = new Semaphore[liczbaF];
            for(int i = 0; i < liczbaF; i++){
                widelec[i] = new Semaphore(1);
            }
            for(int i = 0; i < liczbaF; i++){
                new LAB3_Filozofowie(i).start();
            }
        }
        else System.out.println("Bledna liczba filozofow albo problem");
    }
}