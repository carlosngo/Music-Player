package Controller;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class BlobToFile implements BlobStrategy{
	private String parent;
	private String fileName;
	private String fileType;
	public BlobToFile(String parent, String fileName,String fileType) {
		this.parent = parent;
		this.fileName = fileName;
		this.fileType = fileType;
	}
	
	@Override
	public File execute(Blob blob) {
		try {
			File file = new File(parent, fileName + fileType);
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
		return null;
	}

}
