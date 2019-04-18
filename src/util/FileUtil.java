package util;

import java.io.*;
import java.net.Socket;

public final class FileUtil {
    private FileUtil() { }

    public static void downloadFile(Socket socket, BufferedReader in, File dest) {
        try {
            System.out.println("Attempting to start a download...");
            int fileSize = Integer.parseInt(in.readLine());
            System.out.println("Getting a " + fileSize + " bytes file from the server.");
            byte[] mybytearray = new byte[fileSize];
            InputStream is = socket.getInputStream();
            System.out.println("Allocating file.");
            dest.createNewFile();
            System.out.println("File allocated.");
            FileOutputStream fos = new FileOutputStream(dest);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            System.out.println("OutputStreams are ready.");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(Socket socket, PrintWriter out, File src) {
        try {
            if (src == null) {
                out.println(0);
                return;
            }
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
//            String response = in.readLine();
//            System.out.println(response);
//            if (response.equals(Protocol.OK)) System.out.println("Destination receieved the file.");
//            else System.out.println("Destination did not receive the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
