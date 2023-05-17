import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Canvas extends JPanel {

    private String[] stations;

    public Color[] colors = new Color[17];


    public Canvas(String[] stations) {
        super();
        Arrays.fill(this.colors, Color.BLACK);
        this.stations = stations;
    }

    public void paint(Graphics g){
        int i;
        for (i = 0; i < 3; i++) {

            // stazione "i"
            g.setColor(this.colors[(4*i)]);
            g.drawRect(10 + (245*i), 200, 100, 70);
            g.drawString(this.stations[i],10 + (245*i), 195);

            // binario stazione-scambio "i"
            g.setColor(this.colors[1 + (4*i)]);
            g.drawLine(110 + (245*i), 235, 160 + (245*i), 235);

            // scambio "i"
            g.setColor(this.colors[2 + (4*i)]);
            g.drawLine(160 + (245*i), 235, 175 + (245*i), 200);
            g.drawLine(160 + (245*i), 235, 175 + (245*i), 270);
            g.drawLine(175 + (245*i), 200, 200 + (245*i), 200);
            g.drawLine(175 + (245*i), 270, 200 + (245*i), 270);
            g.drawLine(200 + (245*i), 200, 215 + (245*i), 235);
            g.drawLine(200 + (245*i), 270, 215 + (245*i), 235);

            // binario scambio-stazione "i"
            g.setColor(this.colors[3 + (4*i)]);
            g.drawLine(215 + (245*i), 235, 255 + (245*i), 235);
        }
        // ultima stazione verso destra
        g.setColor(this.colors[12]);
        g.drawString(this.stations[i],10 + (245*i), 190);
        g.drawRect(10 + (245*i),195,100,70);

        // stazione verso centro-nord del JPanel
        g.setColor(this.colors[13]);
        g.drawLine(188 + (245),200,188 + (245),175);
        g.setColor(this.colors[14]);
        g.drawRect(138 + (245),105,100,70);
        g.drawString(this.stations[4],138 + (245), 100);


        // stazione verso centro-sud del JPanel
        g.setColor(this.colors[15]);
        g.drawLine(188 + (245),270,188 + (245),295);
        g.setColor(this.colors[16]);
        g.drawRect(138 + (245),295,100,70);
        g.drawString(this.stations[5],138 + (245), 380);

        repaint();
    }
}
