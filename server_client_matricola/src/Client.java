import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static String filePath = "src\\client\\DatiClient.csv";
    private static ArrayList<String> m = new ArrayList<>();


    public static void main(String[] args) {
        uploadFile();
        printM();
        run();
    }

    public static void run(){
        for(int i = 0; i< m.size(); i++) {
            try {
                Socket s = new Socket("localhost", 6024);
                OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter out = new PrintWriter(bw, true);
                InputStreamReader isr = new InputStreamReader(s.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                out.println((m.get(i).split(";"))[0]);
                String line = in.readLine();
                System.out.println(line);
                m.set(i,m.get(i).concat(";" + line));
                in.close();
                out.close();
                s.close();
            } catch (Exception e) {
                System.out.println("zio pera");
            }
        }
        printM();
    }

    public static void uploadFile(){
        try {
            Scanner sc = new Scanner(new File(filePath));
            String line = sc.nextLine();
            while (sc.hasNextLine()) {
                if (line != null) {
                    m.add(line);
                }
                line = sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printM(){
        for(int i=0; i<m.size(); i++) {
            System.out.println(m.get(i));
        }
    }

}
