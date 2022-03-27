import java.security.MessageDigest;
import java.math.BigInteger;
public class SHA256 {

    private String getHash(String input) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("SHA-256");
            messageDigest.update(input.getBytes());
            byte[] bytes = messageDigest.digest();
            sb.append(new BigInteger(1, bytes).toString(16));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String input = args[0];

        System.out.println("initial value: " + input);

        SHA256 sha256 = new SHA256();
        String digest = sha256.getHash(input);

        System.out.println("hash value: " + digest);
    }
}
