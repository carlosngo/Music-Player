package util;

import java.io.*;
import java.net.Socket;

public final class FileUtil {
    private FileUtil() { }

    public static void downloadFile(Socket socket, BufferedReader in, PrintWriter out, File dest) {
        try {
            int fileSize = Integer.parseInt(in.readLine());
            byte[] mybytearray = new byte[fileSize];
            InputStream is = socket.getInputStream();
            dest.createNewFile();
            FileOutputStream fos = new FileOutputStream(dest);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            System.out.println("Getting a " + fileSize + " bytes file from the server.");
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;
            do {
                System.out.println(current);
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1 && current < fileSize);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + dest
                    + " downloaded (" + current + " bytes read)");
        } catch (IOException e) {
            e.printStackTrace();
            out.println(Protocol.NO);
        }
        out.println(Protocol.OK);
    }

    public static void uploadFile(Socket socket, BufferedReader in, PrintWriter out, File src) {
        try {
            out.println(src.length());
            byte[] mybytearray = new byte[(int) src.length()];
            FileInputStream fis = new FileInputStream(src);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = socket.getOutputStream();
            System.out.println("Sending " + src + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            System.out.println("File successfully uploaded.");
            if (in.readLine().equals(Protocol.OK)) System.out.println("Destination receieved the file.");
            else System.out.println("Destination did not receive the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
