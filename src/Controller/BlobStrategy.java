package Controller;

import java.io.*;
import java.sql.*;

public interface BlobStrategy {
    public File execute(Blob blob);
}
