import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptSample {
	private String encrypt(String value, String key) {
		try {
			byte[] ivBytes = { 0x01, 0x1F, 0x3A, 0x0A, 0x08, 0x09, 0x20, 0x05, 0x09, 0x07, 0x05, 0x03, 0x09, 0x02, 0x01, 0x00 };
			AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
			SecretKeySpec k = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, k, iv);

			byte encBytes[] = c.doFinal(value.getBytes("UTF-8"));
			return java.util.Base64.getEncoder().encodeToString(encBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String decrypt(String value, String key) {
		try {
			byte[] ivBytes = { 0x01, 0x1F, 0x3A, 0x0A, 0x08, 0x09, 0x20, 0x05, 0x09, 0x07, 0x05, 0x03, 0x09, 0x02, 0x01, 0x00 };
			
			AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
			SecretKeySpec k = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, k, iv);
			byte encBytes[] = c.doFinal( java.util.Base64.getDecoder().decode(value) );
			return new String (encBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static void main (String args[]) {
		EncryptSample es = new EncryptSample();
		String key = "key_value"; // 16, 32, ...
        String input = "input_string";
        System.out.println("string input: " + input);
		String encdata =es.encrypt(input, key);
		System.out.println("encrypted data: "+encdata);
		String decdata = es.decrypt(encdata, key);
		System.out.println("decryted data: "+decdata);
	}

}