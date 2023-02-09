import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static String filePath = "src\\server\\DatiServer.csv";
    private static String[][] m = new String[1][2];

    public static void main(String[] args) {
        uploadFile();
        printM();
        run();
    }

    public static void run(){
        System.out.println("SERVER READY");
        try{
            ServerSocket ss = new ServerSocket(6024);
            while(true){
                Socket s = ss.accept();
                InputStreamReader isr = new InputStreamReader(s.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter out = new PrintWriter(bw, true);
                String line = in.readLine();
                System.out.println(line);
                int i = findIndex(line);
                if (i != -1){
                    out.println(m[i][1]);
                }else{
                    out.println("NULL");
                }
                in.close();
                out.close();
                s.close();
            }
        }catch(Exception e){
            System.out.println("zio furbo");
        }
    }

    public static int findIndex(String line){
        int index = -1;
        for (int i = 0; i<m.length; i++){
            if (m[i][0].equals(line)){
                index = i;
                break;
            }
        }
        return index;
    }

    public static void uploadFile(){
        m[0][0] = "Matricola";
        m[0][1] = "Numero";
        try {
            Scanner sc = new Scanner(new File(filePath));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(";");
                addElement(data);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printM(){
        for(int i=0; i<m.length; i++) {
            for (int j = 0; j < m[i].length; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addElement(String[] data){
        String[][] current = new String[m.length+1][m[0].length];
        for(int i=0; i<m.length; i++) {
            for (int j = 0; j < m[i].length; j++){
                current[i][j] = m[i][j];
            }
        }
        current[m.length] = data;
        m = current;
    }



}
