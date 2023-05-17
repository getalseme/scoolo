import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Window");
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setSize(1000, 600);
        String [] stations = {"Venezia", "Salzano", "Resana", "Bassano del Grappa", "Trebaseleghe", "Piombino Dese"};
        Canvas canvas = new Canvas(stations);
        finestra.setContentPane(canvas);
        finestra.setVisible(true);


        System.out.println("inizio processo principale");
        Linea_Bassano_Venezia l1= new Linea_Bassano_Venezia();
        String[] mioPath1 = {"Venezia","b1","sc1","b2","Salzano","b3","sc2","b4","Resana","b5","sc3","b6","Bassano"};
        String[] mioPath2 = {"Bassano","b6","sc3","b5","Resana","b4","sc2","b3","Salzano"};
        String[] mioPath3 = {"Trebaseleghe","b7","sc2","b3","Salzano","b2","sc1","b1","Venezia"};
        String[] mioPath4 = {"Piombino","b8","sc2","b4","Resana","b5","sc3","b6","Bassano"};
        Treno t1 = new Treno("t1", l1, mioPath1, canvas, Color.GREEN, true);
        Treno t2 = new Treno("t2", l1, mioPath2, canvas, Color.BLUE, false);
        Treno t3 = new Treno("t3", l1, mioPath3, canvas, Color.RED, true);
        Treno t4 = new Treno("t4", l1, mioPath4, canvas, Color.MAGENTA, false);
        t1.start();
        //t2.start();
        //t3.start();
        t4.start();
    }
}
