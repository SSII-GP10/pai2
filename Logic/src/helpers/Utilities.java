package helpers;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Utilities {

    private static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static int getOneNonce(){
        SecureRandom secureRandom = new SecureRandom();
        int nonce = secureRandom.nextInt();
        return nonce;
    }
    
    public static String calculateMac(String message, String key, String algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String macAr = "";
        try {
            byte[] messageBytes = message.getBytes("UTF-8");
            byte[] keyBytes = key.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HMac" + algorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HMac" + algorithm);
            mac.init(secretKey);
            mac.update(messageBytes);
            byte[] ar = mac.doFinal();
            macAr = byteArrayToHexString(ar);
        } catch (UnsupportedEncodingException ex){
            throw new UnsupportedEncodingException("Incorrect Enconding");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException("Incorrect algorithm");
        } catch (InvalidKeyException ex) {
            throw new InvalidKeyException("Invalid key");
        }
        return macAr;
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; ++i) {
            result += byteToHexString(b[i]);
        }
        return result;
    }
}
