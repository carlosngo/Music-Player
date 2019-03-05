package Controller;

import java.io.*;
import java.sql.*;

public class BlobParser {
    private static BlobStrategy bs;

    public static void setStrategy(BlobStrategy bs) {
        BlobParser.bs = bs;
    }
    
    public static File executeStrategy(Blob blob) {
        return bs.execute(blob);
    }
}
