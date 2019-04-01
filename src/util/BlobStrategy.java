package util;

import java.io.*;
import java.sql.*;

public interface BlobStrategy {
    public void execute(Blob blob, File file);
}
