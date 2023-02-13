package JAVA;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class filozofowie extends Thread {
    static final int MAX = 100;
    static Semaphore[] widelec = new Semaphore[MAX]; // tablica semaforów reprezentujących widelce
    static int wariant, liczbaF; // wariant określa sposób działania filozofów, liczbaF to liczba filozofów
    int mojNum; // numer filozofa
    Random rand = new Random(); // obiekt do generowania losowych liczb

    public filozofowie(int nr){
        this.mojNum = nr;
    }
    public void run(){
        switch(wariant){ // wybierz odpowiedni sposób działania filozofa w zależności od wartości zmiennej wariant
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
        while(true){ // nieskończona pętla
            System.out.println("Filozof " + mojNum + " - Rozmysla");
            try{
                Thread.sleep((long)(7000 * Math.random())); // losowy odpoczynek
            }
            catch(InterruptedException e){
            }
            widelec[mojNum].acquireUninterruptibly(); // chcemy używać lewego widelca
            widelec[(mojNum+1)%liczbaF].acquireUninterruptibly(); // chcemy używać prawego widelca
            System.out.println("Filozof " + mojNum + " - Zaczyna jesc");
            try{
                Thread.sleep((long)(5000 * Math.random())); // losowy czas jedzenia
            }
            catch(InterruptedException e){
            }
            System.out.println("Filozof " + mojNum + " - Konczy jesc");
            widelec[mojNum].release(); // zwalniamy lewy widelec
            widelec[(mojNum + 1)%liczbaF].release(); // zwalniamy prawy widelec
        }
    }
     public void w2(){
        while(true) {
            System.out.println("Filozof " + mojNum + " - Rozmysla");
            try {
                Thread.sleep((long)(5000 * Math.random())); // losowy odpoczynek
            }
            catch(InterruptedException e){}
            if(mojNum == 0) { // pierwszy filozof używa widełek w innej kolejności niż pozostali
                widelec[(mojNum + 1) % liczbaF].acquireUninterruptibly(); // chcemy używać prawego widelca
                widelec[mojNum].acquireUninterruptibly(); // chcemy używać lewego widelca
            }
            else {
                widelec[mojNum].acquireUninterruptibly(); // chcemy używać lewego widelca
                widelec[(mojNum + 1) % liczbaF].acquireUninterruptibly(); // chcemy używać prawego widelca
            }
            System.out.println("Filozof " + mojNum + " - Zaczyna jesc");
            try {
                Thread.sleep((long) (3000 * Math.random())); // losowy czas jedzenia
            }
            catch(InterruptedException e){}
            System.out.println("Filozof " + mojNum + " - Konczy jesc");
            widelec[mojNum].release(); // zwalniamy lewy widelec
            widelec[(mojNum + 1) % liczbaF].release(); //zwalniamy prawy widelec
        }
    }
public void w3(){
        while(true){
            System.out.println("Filozof " + mojNum + " - Rozmysla");
            try {
                Thread.sleep((long) (5000 * Math.random())); // losowy odpoczynek
            }
            catch(InterruptedException e){}

            int strona = rand.nextInt(2); // losujemy, czy filozof chce użyć lewego czy prawego widelca
            boolean dwaWidelce = false;
            do{
                if(strona == 0) {
                    widelec[mojNum].acquireUninterruptibly(); // chcemy używać lewego widelca
                    if(!(widelec[(mojNum + 1) % liczbaF]).tryAcquire()) { // próbujemy używać prawego widelca, ale jeśli jest już używany, to go nie używamy
                        widelec[mojNum].release(); // zwalniamy lewy widelec
                    }
                    else {
                        dwaWidelce = true; // udało nam się użyć obu widelców
                    }
                }
                else {
                    widelec[(mojNum + 1) % liczbaF].acquireUninterruptibly(); // chcemy używać prawego widelca
                    if(!(widelec[mojNum].tryAcquire())) { // próbujemy używać lewego widelca, ale jeśli jest już używany, to go nie używamy
                        widelec[(mojNum + 1) % liczbaF].release(); // zwalniamy prawy widelec
                    }
                    else {
                        dwaWidelce = true; // udało nam się użyć obu widelców
                    }
                }
            } while(!dwaWidelce); // jeśli nie udało nam się użyć obu widelców, to ponawiamy próbę

            System.out.println("Filozof " + mojNum + " - Zaczyna jesc");
            try {
                Thread.sleep((long) (3000 * Math.random())); // losowy czas jedzenia
            }
            catch(InterruptedException e){}
            System.out.println("Filozof " + mojNum + " - Konczy jesc");

            widelec[mojNum].release(); // zwalniamy lewy widelec
            widelec[(mojNum + 1) % liczbaF].release(); // zwalniamy prawy widelec
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
                widelec[i] = new Semaphore(1); // tworzymy nowe widełki, każdy jest dostępny
            }
            for(int i = 0; i < liczbaF; i++){
                new filozofowie(i).start(); // tworzymy nowy wątek dla każdego filozofa
            }
        }
        else {
            System.out.println("Podano niepoprawne dane wejsciowe.");
        }
    }
}