import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private SecretKey secretKey;
	private int KEY_SIZE = 128;

	public void init() throws NoSuchAlgorithmException{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(KEY_SIZE);
		secretKey = keyGenerator.generateKey();
	}

	private String encrypt(String value, String key) {
		try {
			byte[] ivBytes = { 0x01, 0x1F, 0x3A, 0x0A, 0x08, 0x09, 0x20, 0x05, 0x09, 0x07, 0x05, 0x03, 0x09, 0x02, 0x01,
					0x00 };
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
			byte[] ivBytes = { 0x01, 0x1F, 0x3A, 0x0A, 0x08, 0x09, 0x20, 0x05, 0x09, 0x07, 0x05, 0x03, 0x09, 0x02, 0x01,
					0x00 };

			AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
			SecretKeySpec k = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, k, iv);
			byte encBytes[] = c.doFinal(java.util.Base64.getDecoder().decode(value));
			return new String(encBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) throws NoSuchAlgorithmException {
		AES es = new AES();
		es.init();
		String key = "2s5v8y/B?E(H+KbPeShVmYq3t6w9z$C&"; // 16, 24, 32, ...
		String input = args[0];

		System.out.println("string input: " + input);
		String encdata = es.encrypt(input, key);
		System.out.println("encrypted data: " + encdata);
		String decdata = es.decrypt(encdata, key);
		System.out.println("decryted data: " + decdata);
	}

}