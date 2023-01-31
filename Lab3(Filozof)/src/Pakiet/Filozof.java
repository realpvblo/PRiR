package Pakiet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static Pakiet.Filozof.Filozof2.widelec2;
import static Pakiet.Filozof.Filozof3.widelec3;
import static Pakiet.Filozof.widelec;
import static Pakiet.Global.MAX;

class Global{
    static public int MAX = 101;
}

class MyFrame extends JFrame {

    public MyFrame() {

        super( "Not Hello World" );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 400);
        setLocation(100,100);
        setLayout(new FlowLayout());


        JButton Button1 = new JButton("Filozof 1");

        JButton Button2 = new JButton("Filozof 2");

        JButton Button3 = new JButton("Filozof 3");


        JTextField texfield1 = new JTextField(3);

        JButton Button4 = new JButton("Zapisz liczbę ");

        add(texfield1);
        add(Button4);


        add(Button1);
        add(Button2);
        add(Button3);




        Button1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < MAX; i++) {
                    widelec[i] = new Semaphore(1);
                }
                for (int i = 0; i < MAX; i++) {
                    new Filozof(i).start();
                }
            }
        });

        Button2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < MAX; i++) {
                    widelec2[i] = new Semaphore(1);
                }
                for (int i = 0; i < MAX; i++) {
                    new Filozof.Filozof2(i).start();
                }
            }
        });

        Button3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < MAX; i++) {
                    widelec3[i] = new Semaphore(1);
                }
                for (int i = 0; i < MAX; i++) {
                    new Filozof.Filozof3(i).start();
                }
            }
        });

        Button4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(texfield1.getText()) >= 2){
                Global.MAX = Integer.parseInt(texfield1.getText());}
                else{
                    System.out.println("Wartość spoza zakresu! ");
                }
                //System.out.println(Global.MAX);
            }
        });


        setVisible(true);
    }
}






//pierwszy
public class Filozof extends Thread {
    //static final int MAX = 5;
    static Semaphore[] widelec = new Semaphore[MAX];
    int mojNum;

    public Filozof(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (7000 * Math.random()));
            } catch (InterruptedException e) {
            }
            widelec[mojNum].acquireUninterruptibly(); //przechwycenie L widelca
            widelec[(mojNum + 1) % MAX].acquireUninterruptibly(); //przechwycenie P widelca
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release(); //zwolnienie L widelca
            widelec[(mojNum + 1) % MAX].release(); //zwolnienie P widelca
        }
    }


    //drugi

    static class Filozof2 extends Thread {
        //static final int MAX = 5;
        static Semaphore[] widelec2 = new Semaphore[MAX];
        int mojNum;

        public Filozof2(int nr) {
            mojNum = nr;
        }

        public void run() {

            while (true) {
// myslenie
                System.out.println("Mysle ¦ " + mojNum);
                try {
                    Thread.sleep((long) (5000 * Math.random()));
                } catch (InterruptedException e) {
                }
                if (mojNum == 0) {
                    widelec2[(mojNum + 1) % MAX].acquireUninterruptibly();
                    widelec2[mojNum].acquireUninterruptibly();
                } else {
                    widelec2[mojNum].acquireUninterruptibly();
                    widelec2[(mojNum + 1) % MAX].acquireUninterruptibly();
                }
// jedzenie
                System.out.println("Zaczyna jesc " + mojNum);
                try {
                    Thread.sleep((long) (3000 * Math.random()));
                } catch (InterruptedException e) {
                }
                System.out.println("Konczy jesc " + mojNum);
                widelec2[mojNum].release();
                widelec2[(mojNum + 1) % MAX].release();
            }
        }

    }


    //trzeci

     static class Filozof3 extends Thread {
         //static final int MAX=5;
        static Semaphore [] widelec3 = new Semaphore [MAX] ;
        int mojNum;
        Random losuj ;
        public Filozof3 ( int nr ) {
            mojNum=nr ;
            losuj = new Random(mojNum) ;
        }
        public void run ( ) {
            while ( true ) {
// myslenie
                System.out.println ( "Mysle ¦ " + mojNum) ;
                try {
                    Thread.sleep ( ( long ) (5000 * Math.random( ) ) ) ;
                } catch ( InterruptedException e ) {
                }
                int strona = losuj.nextInt ( 2 ) ;
                boolean podnioslDwaWidelce = false ;

                do {
                    if ( strona == 0) {
                        widelec3 [mojNum].acquireUninterruptibly ( ) ;
                        if( ! ( widelec3 [ (mojNum+1)%MAX].tryAcquire ( ) ) ) {
                            widelec3[mojNum].release ( ) ;
                        } else {
                            podnioslDwaWidelce = true ;
                        }
                    } else {
                        widelec3[(mojNum+1)%MAX].acquireUninterruptibly ( ) ;
                        if ( ! (widelec3[mojNum].tryAcquire ( ) ) ) {
                            widelec3[(mojNum+1)%MAX].release ( ) ;
                        } else {
                            podnioslDwaWidelce = true ;
                        }
                    }
                } while ( podnioslDwaWidelce == false ) ;
                System.out.println ( "Zaczyna jesc "+mojNum) ;
                try{
                    Thread.sleep ( ( long ) (3000 * Math.random( ) ) ) ;
                } catch ( InterruptedException e ) {
                }
                System.out.println ( "Konczy jesc "+mojNum) ;
                widelec3 [mojNum].release ( ) ;
                widelec3 [ (mojNum+1)%MAX].release ( ) ;
            }
        }
        }


    public static void main(String[] args) {

        new MyFrame();


    }

}