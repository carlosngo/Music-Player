package net;

import java.net.*;
import java.io.*;

public class FTPClient {
    private Socket socket;
    private String serverIP;
    private File fileToReceive;
    private int fileSize;

    public FTPClient(String serverIP, File fileToReceive, int fileSize) {
        this.fileToReceive = fileToReceive;
        this.serverIP = serverIP;
        this.fileSize = fileSize;
    }

    public void receive() {
        int bytesRead;
        int current = 0;
        FileOutputStream fos;
        BufferedOutputStream bos;
        try {
            socket = new Socket(serverIP, FTPServer.FTP_PORT);
            System.out.println("Connecting...");

            // receive file
            byte[] mybytearray = new byte[fileSize];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(fileToReceive);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                System.out.println(current);
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1 && current < fileSize);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + fileToReceive
                    + " downloaded (" + current + " bytes read)");
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
