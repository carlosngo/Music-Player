package Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractHashGenerator {
	public String generateHash(String data) {
		MessageDigest digest;
		try {
			digest = createAlgorithm();
			digest.reset();
			digest.update(createSalt());
			digest.update(createPepper());
			byte[] hash = digest.digest(data.getBytes());
			return bytesToStringHex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for(int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i*2] = hexArray[v >> 4];
			hexChars[i*2+1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public abstract MessageDigest createAlgorithm() throws NoSuchAlgorithmException;
	public abstract byte[] createSalt();
	public abstract byte[] createPepper();
	
}
