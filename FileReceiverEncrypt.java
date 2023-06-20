package Assignments.Assign6;

import java.io.*;
import java.net.Socket;

public class FileReceiverEncrypt {
    public static void main(String[] args) throws IOException {
        int secret_key = 5;
        Socket socket = new Socket("localhost", 5900);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter ps = new PrintWriter(socket.getOutputStream());
        BufferedReader keyb = new BufferedReader(new InputStreamReader(System.in));

        while (br.read() != '~') System.out.println(br.readLine());
        System.out.println("Enter the index of file: ");
        ps.println(keyb.read());
        ps.flush();

        String ss;
        //File Receive Process
        try {
            BufferedWriter filebw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("check.txt")));
            BufferedWriter filebw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("EncryptedFile.txt")));

            while ((ss = br.readLine()) != null) {
//                System.out.println(ss);
                filebw2.write(ss);
                String decryptedString = getDecyptedValue(ss, secret_key);
                filebw.write(decryptedString);
            }

            filebw2.flush();
            filebw.flush();
            filebw.close();

            if (br.readLine() == null) System.out.println("File Write Successful.Closing the socket!s");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        socket.close();
    }

    private static String getDecyptedValue(String value, int secret_key) {
        String decyptedData = "";
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            ch -= secret_key;
            decyptedData = decyptedData + ch;
        }
        return decyptedData;
    }
}
