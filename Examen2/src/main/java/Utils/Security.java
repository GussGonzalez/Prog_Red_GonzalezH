package Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Security {
	public static SecureRandom sr = new SecureRandom();

    private static SecretKeySpec deriveKey(String clave) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(clave.getBytes(StandardCharsets.UTF_8));
        byte[] key16 = new byte[16];
        System.arraycopy(keyBytes, 0, key16, 0, 16);
        return new SecretKeySpec(key16, "AES");
    }

    public static byte[] encriptarBytes(String clave, byte[] iv, byte[] plain) {
        try {
            SecretKeySpec sks = deriveKey(clave);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(iv));
            return cipher.doFinal(plain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decriptarBytes(String clave, byte[] iv, byte[] cipherBytes) {
        try {
            SecretKeySpec sks = deriveKey(clave);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sks, new IvParameterSpec(iv));
            return cipher.doFinal(cipherBytes);
        } catch (Exception e) {
        }
        return null;
    }
}//Security
