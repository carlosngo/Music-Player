package Controller;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobToFile implements BlobStrategy {
	@Override
	public void execute(Blob blob, File file) {
		try {
			InputStream input = blob.getBinaryStream();
			FileOutputStream output = new FileOutputStream(file);
			byte[] buffer = new byte[4096];
			while(input.read(buffer) > 0) {
				output.write(buffer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
