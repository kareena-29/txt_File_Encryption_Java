package Assignments.Assign6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class FileSenderEncrypt {
    public static void main(String[] args) throws IOException {
        int secret_key = 5;

        ServerSocket serverSocket = new ServerSocket(5900);
        System.out.println("Started Running...");

        Socket socket = serverSocket.accept();
        System.out.println("Client Connected!");

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter ps = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        String dirname = "C:/Users/karee/Downloads/check";
        File dir = new File(dirname);
        File file[] = dir.listFiles();

        Arrays.sort(file);

        int count = 0;
        for (int i = 0; i < file.length; i++) {
            if (file[i].canRead() && ((file[i]).toString()).endsWith(".txt")) {
                count++;
            }
        }

        ps.println(" " + count + " are the total files, listed A-Z");

        for (int i = 0; i < file.length; i++) {
            if (file[i].canRead() && ((file[i]).toString()).endsWith(".txt")) {
                ps.println(" " + i + "   " + file[i].getName() + "  " + file[i].length() + " Bytes");
            }
        }
        //output string stream delimiter
        ps.println("~");
        ps.flush();

        //Converting read ascii value to int
        String temp = br.readLine();
        int t = Integer.parseInt(temp);
        t -= 48;
        System.out.println("Index: " + t);

        //check if file exists
        boolean fileis = false;
        int index = 0;

        if (t >= 0 && t <= file.length) {
            fileis = true;
            index = t;
        } else {
            fileis = false;
        }
        System.out.println(fileis);
        if (fileis) {
            try {
                //File Send Process
                File ff = new File(file[index].getAbsolutePath());
                FileReader fr = new FileReader(ff);
                BufferedReader buff = new BufferedReader(fr);
                String ss;

                while ((ss = buff.readLine()) != null) {
                    String encryptedString = getEncryptedValue(ss, secret_key);
                    ps.println(encryptedString);
                }
                ps.flush();

                if (buff.readLine() == null) System.out.println("File Read Successful. Closing Socket!");

            } catch (IOException ioe) {
                System.out.println("\nError in FTP. Please try again!");
            }
        }


        br.close();
        socket.close();
    }

    private static String getEncryptedValue(String value, int secret_key) {
        String encrypted = "";
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            ch += secret_key;
            encrypted = encrypted + ch;
        }
        return encrypted;
    }
}
