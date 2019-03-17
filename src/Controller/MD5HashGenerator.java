package Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashGenerator extends AbstractHashGenerator{

	@Override
	public MessageDigest createAlgorithm() throws NoSuchAlgorithmException {
		
		return MessageDigest.getInstance("MD5");
	}

	@Override
	public byte[] createSalt() {
		String salt = "8@<™";
		return salt.getBytes();
	}

	@Override
	public byte[] createPepper() {
		String pepper = "X";
		return pepper.getBytes();
	}
	
	public static void main(String[] args) {
		AbstractHashGenerator a = new MD5HashGenerator();
		System.out.println(a.generateHash("hello"));
	}
	
}
