package net;

import java.io.*;
import java.net.*;

public class FTPServer implements Runnable {
    private ServerSocket serverSocket;
    public static final int FTP_PORT = 7777;
    private File fileToSend;

    public FTPServer(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    @Override
    public void run() {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;
        try {
            serverSocket = new ServerSocket(FTP_PORT);
            System.out.println("Waiting...");
            sock = serverSocket.accept();
            System.out.println("Accepted connection : " + sock);
            byte [] mybytearray  = new byte [(int)fileToSend.length()];
            fis = new FileInputStream(fileToSend);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            os = sock.getOutputStream();
            System.out.println("Sending " + fileToSend + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Done.");
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock!=null) sock.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
